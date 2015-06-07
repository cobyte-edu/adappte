/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adapp.controle.mvc;

import java.text.SimpleDateFormat;

/**
 *
 * @author hiragi
 */
public class Util {

    public static java.sql.Date stringToDate(String dta) {
        java.sql.Date data;
        int dia = Integer.parseInt(dta.substring(0, 2));
        int mes = Integer.parseInt(dta.substring(3, 5));
        int ano = Integer.parseInt(dta.substring(6, 10));
        data = new java.sql.Date(ano - 1900, mes - 1, dia);
        return data;
    }

    public static String dateToString(java.sql.Date data) {
        String dta;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dta = sdf.format(data);
        return dta;
    }
}
