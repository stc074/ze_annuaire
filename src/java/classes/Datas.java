/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author pj
 */
public class Datas {
    
    public Datas() {
        
    }

    private static final String DIR="/var/datas/zean/";
    private static final String URL_ROOT="http://www.ze-annuaire.net";
    private static final String TITRE_SITE="ze-annuaire";
    private static final String EMAIL_ADMIN="hardibopj@yahoo.fr";
    private static final String[] ARRAY_CHARS={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
    private static final String[] ARRAY_REPLACE1={"%","=","\\^",":","/","&quot;","&","\\?"," ","é","è","ê","ë","à","ü","ô","ö","'","\\.","\""};
    private static final String[] ARRAY_REPLACE2={"pourcent","","","","-","","et","-","-","e","e","e","e","a","u","o","o","-","","-"};
    private static final int NB_SITES=10;
    private static final String[] ARRAY_CHARS_ACC1={"é", "è", "ë", "ê", "î", "ï", "ô", "ö", "à", "â", "ä"};
    private static final String[] ARRAY_CHARS_ACC2={"e", "e", "e", "e", "i", "i", "o", "o", "a", "a", "a"};
    /**
     * @return the URL_ROOT
     */
    public static String getURL_ROOT() {
        return URL_ROOT;
    }

    /**
     * @return the ARRAY_REPLACE1
     */
    public static String[] getARRAY_REPLACE1() {
        return ARRAY_REPLACE1;
    }

    /**
     * @return the ARRAY_REPLACE2
     */
    public static String[] getARRAY_REPLACE2() {
        return ARRAY_REPLACE2;
    }

    /**
     * @return the DIR
     */
    public static String getDIR() {
        return DIR;
    }

    /**
     * @return the ARRAY_CHARS
     */
    public static String[] getARRAY_CHARS() {
        return ARRAY_CHARS;
    }

    /**
     * @return the NB_SITES
     */
    public static int getNB_SITES() {
        return NB_SITES;
    }

    /**
     * @return the ARRAY_CHARS_ACC1
     */
    public static String[] getARRAY_CHARS_ACC1() {
        return ARRAY_CHARS_ACC1;
    }

    /**
     * @return the ARRAY_CHARS_ACC2
     */
    public static String[] getARRAY_CHARS_ACC2() {
        return ARRAY_CHARS_ACC2;
    }

    /**
     * @return the TITRE_SITE
     */
    public static String getTITRE_SITE() {
        return TITRE_SITE;
    }

    /**
     * @return the EMAIL_ADMIN
     */
    public static String getEMAIL_ADMIN() {
        return EMAIL_ADMIN;
    }
}
