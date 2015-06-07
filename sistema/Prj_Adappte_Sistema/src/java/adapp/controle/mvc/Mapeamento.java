package adapp.controle.mvc;

/**
 *
 * @author Gilberto Hiragi
 *
 * Esta classe guarda as informações necessárias para compor uma ação do
 * sistema
 */
public class Mapeamento {

    private String classe;
    private String metodo;
    private String template;

    public Mapeamento(String classe, String metodo, String template) {
        this.classe = classe;
        this.metodo = metodo;
        this.template = template;
    }

    public String getClasse() {
        return this.classe;
    }

    public String getMetodo() {
        return metodo;
    }

    public String getTemplate() {
        return this.template;
    }

}
