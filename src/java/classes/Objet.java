/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import interfaces.Dats;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author pj
 */
public class Objet implements Dats {
    
    private String errorMsg;
    private int test;
    protected Connection con;
    
    public Objet() {
        this.test=0;
        this.errorMsg="";
    }
    

    public void getConnection() throws NamingException, SQLException {
        Context initCtx = new InitialContext();
        DataSource ds=(DataSource) initCtx.lookup("java:comp/env/jdbc/MyDB");
        this.con=ds.getConnection();
    }
    
    public void closeConnection() throws SQLException {
        this.con.close();
    }
    
    public String codeHTML(String texte) {
        texte.replaceAll("<", "&lt;");
        texte.replaceAll(">", "&gt;");
        return texte;
    }
    
    public String codeHTML2(String texte) {
        texte.replaceAll("<%", "&lt;%");
        texte.replaceAll("%>", "%&lg;");
        texte.replaceAll("<script", "&lt;script");
        texte.replaceAll("script>", "script&gt;");
        return texte;
    }

    public String encodeTitre(String titre) {
        titre=titre.toLowerCase();
        for(int i=0;i<Datas.getARRAY_REPLACE1().length;i++) {
            titre=titre.replaceAll(Datas.getARRAY_REPLACE1()[i], Datas.getARRAY_REPLACE2()[i]);
            }
        return titre;
    }

    public String getEncoded(String key) throws NoSuchAlgorithmException {
    	byte[] uniqueKey = key.getBytes();
	byte[] hash = null;
	hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
	StringBuilder hashString = new StringBuilder();
	for ( int i = 0; i < hash.length; ++i ) {
	String hex = Integer.toHexString(hash[i]);
	if ( hex.length() == 1 ) {
	hashString.append('0');
	hashString.append(hex.charAt(hex.length()-1));
	} else {
	hashString.append(hex.substring(hex.length()-2));
	}
	}
	return hashString.toString();
	}
    public String encodeAcc(String texte) {
        texte=texte.toLowerCase();
        for(int i=0; i<Datas.getARRAY_CHARS_ACC1().length; i++)
            texte=texte.replaceAll(Datas.getARRAY_CHARS_ACC1()[i], Datas.getARRAY_CHARS_ACC2()[i]);
        return texte;
    }
    
    public HttpSession initSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String idSession=session.getId();
        Cookie cookie=new Cookie("JSESSIONID", idSession);
        cookie.setMaxAge(60*60*24*300*1000);
        response.addCookie(cookie);
        session=request.getSession();
        return session;
    }

    public void blank() {
        this.setTest(0);
        this.setErrorMsg("");
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg += errorMsg;
    }

    /**
     * @return the test
     */
    public int getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(int test) {
        this.test = test;
    }
    
    public int getNB_SITES_TOP() {
        return NB_SITES_TOP;
    }
    
    public String getURL_ROOT() {
        return URL_ROOT;
    }
 
}
