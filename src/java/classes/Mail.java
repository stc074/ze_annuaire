/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author pj
 */
public class Mail extends Objet {
    
    private String destinataireEmail;
    private String destinataireNom;
    private String sujet;
    private String contenu;
    private HtmlEmail email;

    public Mail(String destinataireEmail, String destinataireNom, String sujet) {
        this.destinataireEmail=destinataireEmail;
        this.destinataireNom=destinataireNom;
        this.sujet=sujet;
        this.contenu="";
    }
    public void send() {
        try {
            email=new HtmlEmail();
            email.setHostName("localhost");
            email.setSmtpPort(25);
            email.setAuthenticator(new DefaultAuthenticator("noreply@ze-annuaire.net", "paradize"));
            email.addTo(this.destinataireEmail, this.destinataireNom);
            email.setFrom("noreply@ze-annuaire.net", Datas.getTITRE_SITE());
            email.setSubject(this.sujet);
            email.setHtmlMsg(this.contenu);
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        public void initMailNouveauSite() {
        this.contenu="<html";
        this.contenu+="<head>";
        this.contenu+="<style type=\"text/css\">";
        this.contenu+="body {";
        this.contenu+="font-family: Arial,Serif;";
	this.contenu+="background-color: #ffffff;";
        this.contenu+="}";
        this.contenu+="div,p {";
	this.contenu+="font-size: 11px; {";
	this.contenu+="font-size: 11px;";
	this.contenu+="color: #000000;";
	this.contenu+="font-weight: 600;";
        this.contenu+="}";
        this.contenu+="h1 {";
	this.contenu+="font-size: 15px;";
	this.contenu+="color: #000000;";
	this.contenu+="font-weight: 600;";
        this.contenu+="}";
        this.contenu+="h2 {";
        this.contenu+="font-size: 13px;";
        this.contenu+="color: #111111;";
        this.contenu+="font-weight: 600;";
        this.contenu+="}";
        this.contenu+="div.info {";
        this.contenu+="background-color: #a1978d;";
        this.contenu+="font-weight: 600;";
        this.contenu+="color: #333333;";
        this.contenu+="}";
        this.contenu+="a {";
        this.contenu+="font-size: #12px;";
        this.contenu+="color: #0000cc;";
        this.contenu+="font-weight: 700;";
        this.contenu+="}";
        this.contenu+="a:hover {";
        this.contenu+="color: #cc0000;";
        this.contenu+="text-decoration: none;";
        this.contenu+="}";
        this.contenu+="</style>";
        this.contenu+="</head>";
        this.contenu+="<body>";
    this.contenu+="<br/>";
    this.contenu+="<hr/>";
    this.contenu+="<br/>";
    this.contenu+="<h1 align=\"center\">"+Datas.getTITRE_SITE()+" -- Un nouveau site !</h1>";
    this.contenu+="<br/>";
    this.contenu+="<hr/>";
    this.contenu+="<br/>";
    this.contenu+="<br/>";
    this.contenu+="<div class=\"info\">Ceci est un mail automatique, inutile d'y répondre.</div>";
    this.contenu+="<br/>";
    this.contenu+="</body>";
    this.contenu+="</html>";
    }

}
