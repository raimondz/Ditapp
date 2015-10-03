package cl.apd.ditapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Raimondz on 03-10-15.
 */
public class ValidationUtil {

    final static private String email_pattern ="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    final static private String rut_pattern="^(\\d{2}\\.\\d{3}\\.\\d{3}-)([a-zA-Z]{1}$|\\d{1}$)\n";

    static public Boolean emailValidation(String email){
        Pattern p= Pattern.compile(email_pattern);
        Matcher m=p.matcher(email);
        while(m.find())
        {
            return true;
        }
        return false;
    }

    static public Boolean rutValidation(String rut)
    {
        Pattern p= Pattern.compile(rut_pattern);
        Matcher m=p.matcher(rut);
        while(m.find())
        {
            return true;
        }
        return false;
    }
}
