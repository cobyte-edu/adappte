package adapp.controle.mvc;

import java.io.*;
import java.lang.reflect.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.eclipse.jdt.core.compiler.CharOperation;

/**
 *
 * @author Gilberto Hiragi
 *
 * Esta � uma servlet do tipo Front controller. Ser� a controladora da
 * execu��o (Controle do MVC) Todas as requisi��es web ir�o passar por
 * ela. Com estas caracter�sticas iremos implementar nela a parte de
 * seguran�a, quando for necess�rio
 *
 * URL padr�o: Projeto/Controller?acao=Classe.metodo Servlet ir� instanciar
 * a classe (Classe) e rodar o m�todo da classe instanciada (metodo) al�m de
 * renderizar o HTML que estiver em P�ginas Web\\htmls\\Classe\\metodo.html
 */
public class Executar extends HttpServlet {

    protected HttpSession session;

    public String getPath() {
        return this.getServletContext().getRealPath("/");
    }

    public Executar() {

    }

    private void rodar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mapeamento = request.getParameter("acao");

        // Processar se for responder em HTML
        response.setCharacterEncoding("ISO-8859-1");
        response.setContentType("text/html; charset=ISO-8859-1");
        session = request.getSession();
        PrintWriter out = response.getWriter();

        // Decompondo mapeamento
        String pacote = mapeamento.substring(0, mapeamento.indexOf("."));
        String classe = mapeamento.substring(mapeamento.indexOf(".") + 1, mapeamento.lastIndexOf("."));
        String metodo = mapeamento.substring(mapeamento.lastIndexOf(".") + 1);

        try {
            if (naoPermiteAcesso(classe, metodo)) {
                erro(out, "pt-BR: Restri��o de acesso. Entrada nao permitida.<BR><BR>"
                        + "en-US: Restricting access. Entry not allowed.");
                return;
            }

            //Verificar pacote;
            Acao action = (Acao) Class.forName("ud.sistema." + pacote + "." + classe).newInstance();
            action.setAmbiente(this, request, response, session);

            Class cl = action.getClass();
            Method me = cl.getMethod(metodo, null);
            HashMap hashMap = (HashMap) me.invoke(action, null);

            // Jogando os parametros do mapeamento para o gerador de conteudo dinamico
            // Mescla o template HTML com o HashMap de dados din?micos da Acao
            GeraHTML tpl = new GeraHTML();
            tpl.setPath(getPath() + "\\WEB-INF\\htmls\\");
            tpl.setTemplate(classe + "\\" + metodo + ".html");

            // Mandando dados para o chamador (na web: WebBrowser)
            out.print(tpl.getResult(hashMap));
            out.close();

        } catch (Exception err) {
            erro(out, "Dica-1: " + err.toString() + " <br><br>Dica-2: " + err.getLocalizedMessage() + " <br><br>Dica-3: " + err.getMessage() + "<br><br>Dica-4: " + err.getCause());
        }
    }

    private boolean naoPermiteAcesso(String classe, String metodo) throws Exception {
        String acesso = (classe + "." + metodo);

        List<String> lista = new ArrayList<String>();
        lista = (List<String>) session.getAttribute("usuario_regra");
        if (lista == null) {
            return false;
        } else {
            if (lista.contains("*") || lista.contains(acesso)) {
                return false;
            } else {
                return true;
            }
        }
    }

    private void erro(PrintWriter out, String err) {
        String html = "<table style='height:100px; width:80%; padding: 10px 10px 10px 10px; background:#DDDDDD; color:blue;' "
                + ">"
                + "<tr><td>"
                + "<span style='vertical-align: middle;float: left; margin-right: 0.3em;' "
                + "></span><b>"
                + "Verifique o problema na sua programa��o. A dica esta a seguir:"
                + "</b><br><br><b>"
                + err + "</b></td></tr>"
                + "</table>";

        out.println(html);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        rodar(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        rodar(request, response);
    }
}
