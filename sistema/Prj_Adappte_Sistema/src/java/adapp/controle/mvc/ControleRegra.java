/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adapp.controle.mvc;

import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.lang.annotation.Annotation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import adapp.controle.jdbc.Comando;
import adapp.controle.annotations.Menu;

/**
 *
 * @author Marcus
 */
public class ControleRegra extends Executar {

    public List<String> listas = new ArrayList<String>();
    public List<String> selecionado = new ArrayList<String>();

    /* ****************************
    
     **************************** */
    public List<String> verificarAcesso(String acao) throws ClassNotFoundException {

        scan(Thread.currentThread().getContextClassLoader());

        for (String classe : listas) {

            Class aClass = Class.forName(classe);
            java.lang.annotation.Annotation[] annotations = aClass.getAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation instanceof Menu) {
                    Menu myAnnotation = (Menu) annotation;

                    System.out.println("Menu: " + myAnnotation.Menu());
                    System.out.println("URL: " + myAnnotation.URL());
                }
            }
        }
        return listas;
    }

    /* ****************************
    
     **************************** */
    private void scan(ClassLoader classLoader) {
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

    /* ****************************
    
     **************************** */
    private void getClassesInDirectory(String parent, File location) {
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

    /* ****************************
     Cria uma variável de sessão com um ResultSet contendo a regra de acesso
     do perfil para determinado sistema.
     Deve ser executado quando o usuário solicitar acesso a um dos sistemas.
     **************************** */
    public void consultarRegras() throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String query = "SELECT nme_regra FROM tb_regra "
                + "INNER JOIN tb_perfil ON cod_perfil = idt_perfil "
                + "INNER JOIN tb_sistema ON idt_sistema = cod_sistema "
                + "INNER JOIN ta_pessoa_perfil ON idt_perfil = cod_perfil "
                + "INNER JOIN tb_pessoa ON cod_pessoa = idt_pessoa "
                + "WHERE num_cpf_pessoa = ? AND idt_perfil = ? AND sts_habilitado_pessoa = ?";
        Comando cmd = new Comando();

        ps = cmd.gerarPreparedStatement(query, "1", "1");
        rs = ps.executeQuery();
    }
}
