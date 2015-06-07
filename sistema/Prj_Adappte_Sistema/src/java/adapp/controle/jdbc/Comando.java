package adapp.controle.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Pedro Reis
 */
public class Comando {

    private Connection cn;

    public Comando() throws Exception {
        PoolConexao conect = new PoolConexao();
        cn = conect.getCn();
    }

    public PreparedStatement gerarPreparedStatement(String SQL, String... params) throws Exception {
        PreparedStatement ps = cn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                ps.setString(i + 1, params[i]);
            }
        }
        return ps;
    }
 

}
