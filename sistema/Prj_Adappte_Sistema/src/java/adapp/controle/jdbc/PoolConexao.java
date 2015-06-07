package adapp.controle.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro Reis
 */
public class PoolConexao {

    private static Connection cnPrincipal;
    private static final int tamanhoPool = 4;
    private static int instanciaPool = 0;
    private static final String bdDriver = "org.postgresql.Driver";
    private static final String dataBaseUrl = "jdbc:postgresql://127.0.0.1:5432/bd_gestao_ud";
    private static final String dataUserName = "pedro";
    private static final String dataPassword = "reis";
    private static List<Connection> cp = new ArrayList<Connection>();

    public PoolConexao() throws Exception {
        if (cnPrincipal == null || cnPrincipal.isClosed() || !cnPrincipal.isValid(0)) {
            for (int i = 0; i < tamanhoPool; i++) {
                cp.add(i, criarNovaConexaoPool());
            }
        }
    }

    public static Connection getCn() throws Exception {
        if (instanciaPool >= tamanhoPool) {
            instanciaPool = 0;
        }
        instanciaPool++;
        return cp.get(instanciaPool - 1);
    }

    private static Connection criarNovaConexaoPool() {
        cnPrincipal = null;

        try {
            Class.forName(bdDriver);
            cnPrincipal = DriverManager.getConnection(dataBaseUrl, dataUserName, dataPassword);
        } catch (SQLException sqle) {
            System.err.println("SQLException: " + sqle);
            return null;
        } catch (ClassNotFoundException cnfe) {
            System.err.println("ClassNotFoundException: " + cnfe);
            return null;
        }
        return cnPrincipal;
    }
}
