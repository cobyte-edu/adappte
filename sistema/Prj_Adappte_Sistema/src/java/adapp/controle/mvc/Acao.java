package adapp.controle.mvc;

import javax.servlet.http.*;
import java.util.*;

/**
 *
 * @author Gilberto Hiragi
 */
public class Acao {

    protected Executar controller;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected HashMap dados;

    /**
     * Creates a new instance of Acao
     */
    public Acao() {
        this.dados = new HashMap();
    }

    public void setAmbiente(Executar controller, HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        this.controller = controller;
        this.request = request;
        this.response = response;
        this.session = session;
    }

}
