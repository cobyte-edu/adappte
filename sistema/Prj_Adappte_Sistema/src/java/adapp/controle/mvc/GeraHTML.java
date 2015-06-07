package adapp.controle.mvc;

import java.io.*;
import java.util.*;

/**
 *
 * @author Gilberto Hiragi
 */
public class GeraHTML {

    private String path = "";
    private String template = "";

    /**
     * Creates a new instance of GeraHTML
     */
    public GeraHTML() {
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    private String getSource(String fileName) throws Exception {
        String source = "";

        FileReader reader = new FileReader(this.path + fileName);
        BufferedReader breader = new BufferedReader(reader);

        String line = null;
        while ((line = breader.readLine()) != null) {
            source += line + "\n";
        }
        breader.close();
        reader.close();

        return source;
    }

    public String getResult(HashMap dinamico) throws Exception {
        String name, value, source = "";
        Collection names;
        Iterator it;

        source = getSource(this.template);

        names = dinamico.keySet();
        it = names.iterator();
        while (it.hasNext()) {
            name = (String) it.next();
            value = (String) dinamico.get(name);
            source = troca(source, "{" + name + "}", value);
        }

        return source;
    }

    private String troca(String conteudo, String proc, String subst) {
        int pos = 0;
        while (pos != -1) {
            pos = conteudo.indexOf(proc);
            if (pos != -1) {
                conteudo = conteudo.substring(0, pos) + subst + conteudo.substring(pos + proc.length());
            }
        }
        return conteudo;
    }
}
