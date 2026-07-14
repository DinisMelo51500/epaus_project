package isel.sisinf.ui;

import isel.sisinf.jpa.entity.Cliente;

public class Utils {

    public static boolean isValidEmail(String email){
        return email.matches("[\\w\\.]+@[\\w\\.]+\\.\\w+");
    }

    public static boolean isValidTaxNumber(String taxNumber){
        return taxNumber.matches("\\d{9}");
    }

    public static boolean isValidCartCidadao(String cartCidadao){
        return cartCidadao.matches("^\\d{8} \\d [A-Z]{2}\\d$");
    }

    public static boolean isValidTelephone(String telephone){
        return telephone.matches("^\\+351[1-9][0-9]{8}$");
    }

    public static boolean isValidIsin(String isin) {
        return isin.matches("[A-Z]{2}[A-Z0-9]{9}\\d");
    }

    public static boolean isNumeric(String value) {
        try{
            double d = Double.parseDouble(value);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}