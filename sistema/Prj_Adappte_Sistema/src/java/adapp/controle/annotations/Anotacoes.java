/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adapp.controle.annotations;

import com.sun.corba.se.spi.orb.OperationFactory;
import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marculino
 */
public class Anotacoes {

    public static List<String> listas = new ArrayList<String>();

    public static void main(String[] args) throws ClassNotFoundException {
        scan(Thread.currentThread().getContextClassLoader());

        for (String classe : listas) {

            Class aClass = Class.forName(classe);

            Annotation[] annotations = aClass.getAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation instanceof Menu) {
                    Menu myAnnotation = (Menu) annotation;
                    System.out.println("Classe:" + aClass.getCanonicalName());
                    System.out.println("Menu: " + myAnnotation.Menu());
                    System.out.println("URL: " + myAnnotation.URL());
                }
            }
        }
//exibe a lista classes
    }

    private static void scan(ClassLoader classLoader) {
        if (!(classLoader instanceof URLClassLoader)) {
            return;
        }

        URL[] urls = ((URLClassLoader) classLoader).getURLs();

        for (URL url : urls) {
            File location = null;

            try {
                location = new File(url.toURI());
            } catch (URISyntaxException e) {
                System.out.println("ERROR: " + e.toString());

                return;
            }

            if (location.isDirectory()) {
                getClassesInDirectory(null, location);
            }
        }
    }

    /**
     * getClassesInDirectory
     *
     * @param parent String
     * @param location File
     */
    private static void getClassesInDirectory(String parent, File location) {
        File[] files = location.listFiles();

        for (File file : files) {
            StringBuilder builder = new StringBuilder();

            builder.append(parent).append(".").append(file.getName());

            String path = (parent == null ? file.getName() : builder.toString());

            if (file.isDirectory()) {
                getClassesInDirectory(path, file);
            } else if (file.getName().endsWith(".class")) {
                int p = path.indexOf("$");

                if (p != -1) {
                    path = path.substring(0, p);
                }

                if (path.contains("class")) {
                    listas.add(path.substring(0, path.indexOf(".class")));
                }
            }
        }
    }

}
