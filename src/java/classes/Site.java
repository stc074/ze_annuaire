/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Site extends Objet {
    
    private String idCat;
    private long idCategorie;
    private long idSousCategorie;
    private String codeRetour;
    private String categorie;
    private String sousCategorie;
    private String codeRetour2;
    private String url;
    private String titre;
    private String description;
    private String captcha;
    private long id;
    
    public Site() {
        super();
        this.idCat="0-0";
        this.codeRetour="";
        this.codeRetour2="";
        this.categorie="";
        this.sousCategorie="";
        this.idCategorie=0;
        this.idSousCategorie=0;
        this.url="";
        this.titre="";
        this.description="";
    }

    public Site(long idSite) {
        this.id=idSite;
    }

    public void getCategories(HttpServletRequest request) {
        try {
        if(request.getParameter("idCategorie")!=null&&request.getParameter("idSousCategorie")!=null) {
            this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
            this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
            this.idCat=this.idCategorie+"-"+this.idSousCategorie;
            this.genereCodeRetour(request);
        }
        } catch (Exception ex) {
            this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
        }
    }

    public void genereCodeRetour(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(!this.idCat.equals("0-0")) {
            int i=(int)(Math.random()*(double)2);
            if(i==0) {
                this.codeRetour="<a href=\""+Datas.getURL_ROOT()+"\" title=\"annuaire\">annuaire</a>";
                this.codeRetour2="<a href=\"#\" title=\"annuaire\">annuaire</a>";
                session.setAttribute("codeRetour", this.codeRetour);
                session.setAttribute("codeRetour2", this.codeRetour2);
            } else if(i==1) {
            try {
                this.getConnection();
                if(this.idSousCategorie!=0) {
                String query="SELECT t1.categorie,t2.sous_categorie FROM table_categories AS t1, table_sous_categories AS t2 WHERE t1.id=? AND t2.id_categorie=t1.id AND t2.id=? LIMIT 0,1";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setLong(2, this.idSousCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.categorie=this.encodeAcc(result.getString("categorie"));
                this.sousCategorie=this.encodeAcc(result.getString("sous_categorie"));
                result.close();
                prepare.close();
                this.codeRetour="<a href=\""+Datas.getURL_ROOT()+"/annuaire-sous-categorie-"+idCategorie+"-"+this.idSousCategorie+"-"+this.encodeTitre(this.sousCategorie)+".html\" title=\""+this.sousCategorie+"\">"+this.sousCategorie+"</a>";
                this.codeRetour2="<a href=\"#\" title=\""+this.sousCategorie+"\">"+this.sousCategorie+"</a>";
                } else if(this.idSousCategorie==0) {
                    String query="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
                    PreparedStatement prepare=this.con.prepareStatement(query);
                    prepare.setLong(1, this.idCategorie);
                    ResultSet result=prepare.executeQuery();
                    result.next();
                    this.categorie=this.encodeAcc(result.getString("categorie"));
                    this.sousCategorie="";
                    result.close();
                    prepare.close();
                    this.codeRetour="<a href=\""+Datas.getURL_ROOT()+"/annuaire-categorie-"+this.idCategorie+"-"+this.encodeTitre(this.categorie)+".html\" title=\""+this.categorie+"\">"+this.categorie+"</a>";
                    this.codeRetour2="<a href=\"#\" title=\""+this.categorie+"\">"+this.categorie+"</a>";
                }
                session.setAttribute("codeRetour", this.codeRetour);
                session.setAttribute("codeRetour2", this.codeRetour2);
            } catch (NamingException ex) {
                Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
                }
            }
            }
        } else if(this.idCat.equals("0-0")) {
            session.setAttribute("codeRetour", null);
            session.setAttribute("codeRetour2", null);
        }
    }

    public void getPostsDepot(HttpServletRequest request) {
        try {
            this.idCat=this.codeHTML(request.getParameter("idCategorie"));
            this.url=this.codeHTML(request.getParameter("url")).toLowerCase();
            this.titre=this.codeHTML(request.getParameter("titre"));
            this.description=this.codeHTML2(request.getParameter("description"));
            this.captcha=this.codeHTML(request.getParameter("captcha")).toLowerCase();
         } catch(Exception ex) {
            this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
        }
    }

    public void verifPostsDepot(HttpServletRequest request) {
        try {
            HttpSession session=request.getSession(true);
           if(this.idCat.equals("0-0"))
                this.setErrorMsg("<div>Veuillez choisir une CATÉGORIE SVP.</div>");
            else {
                String arrayIdCat[]=new String[2];
                arrayIdCat=this.idCat.split("\\-");
                this.idCategorie=Long.parseLong(arrayIdCat[0]);
                this.idSousCategorie=Long.parseLong(arrayIdCat[1]);
            }
            Pattern p=Pattern.compile("^http://[a-z\\.\\-0-9]+\\.[a-z]{2,4}$");
            Matcher m=p.matcher(this.url);
            if(this.url.length()==0)
                this.setErrorMsg("<div>Champ ADRESSE DU SITE vide.</div>");
            else if(this.url.length()>80)
                this.setErrorMsg("<div>Champ ADRESSE DU SITE trop long.</div>");
            else if(m.matches()==false)
                this.setErrorMsg("<div>Champ ADRESSE DU SITE non-valide.</div>");
            else {
                try {
                    this.getConnection();
                    String query="SELECT COUNT(id) AS nb FROM table_sites WHERE url=? OR url=?";
                    PreparedStatement prepare=this.con.prepareStatement(query);
                    prepare.setString(1, this.url);
                    prepare.setString(2, this.url.replaceAll("www.", ""));
                    ResultSet result=prepare.executeQuery();
                    result.next();
                    int nb=result.getInt("nb");
                    result.close();
                    prepare.close();
                    if(nb>0) {
                        this.url="";
                        this.setErrorMsg("<div>Désolé, ce SITE est déjà enregistré.</div>");
                    }
                } catch (NamingException ex) {
                    Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
                } catch (SQLException ex) {
                    Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
                } finally {
                    try {
                        this.closeConnection();
                    } catch (SQLException ex) {
                        Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
                        this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
                    }
                }
            }
            /*if(this.getErrorMsg().length()==0) {
                DataInputStream dis = null;
                try {
                    URL urlA=new URL(this.url);
                    URLConnection urlCon=urlA.openConnection();
                    dis=new DataInputStream(urlCon.getInputStream());
                    String content="";
                    byte buffer[]=new byte[1024];
                    int off=0;
                    while(dis.read(buffer, off, buffer.length)!=-1) {
                        for(int i=0; i<buffer.length; i++)
                            content+=(char)buffer[i];
                    }
                    dis.close();
                    if(content.indexOf(this.codeRetour)==-1)
                        this.setErrorMsg("<div>Le LIEN RETOUR n'a pas été trouvé sur "+this.url+" .</div>");
                } catch (IOException ex) {
                    Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>Impossible de se connecter à "+url+"</div>");
                }            }*/
            if(this.titre.length()==0)
                this.setErrorMsg("<div>Champ TITRE vide.</div>");
            else if(this.titre.length()>80)
                this.setErrorMsg("<div>Champ TITRE trop long.</div>");
            if(this.description.length()==0)
                this.setErrorMsg("<div>Champ DESCRIPTION vide.</div>");
            else if(this.description.length()>2000)
                this.setErrorMsg("<div>Champ DESCRIPTION trop long.</div>");
            if(this.captcha.length()==0)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT vide.</div>");
            else if(this.captcha.length()>5)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT trop long.</div>");
            else if(session.getAttribute("captcha")==null)
                this.setErrorMsg("<div>Session CODE ANTI-ROBOT dépassée.</div>");
            else if(!session.getAttribute("captcha").toString().equals(this.getEncoded(this.captcha)))
                this.setErrorMsg("<div>Mauvais CODE ANTI-ROBOT.</div>");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
        }
    }

    public void enregDepot(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session=request.getSession(true);
                this.getConnection();
                String query="INSERT INTO table_sites (id_categorie,id_sous_categorie,code_retour,url,url_retour,titre,description,timestamp) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setLong(2, this.idSousCategorie);
                prepare.setString(3, this.codeRetour);
                prepare.setString(4, this.url);
                prepare.setString(5, this.url);
                prepare.setString(6, this.titre);
                prepare.setString(7, this.description.replaceAll("\\n", "<br/>"));
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(8, ts);
                prepare.executeUpdate();
                prepare.close();
                query="UPDATE table_flag SET flag=? WHERE id=?";
                prepare=this.con.prepareStatement(query);
                prepare.setInt(1, 1);
                prepare.setInt(2, 1);
                prepare.executeUpdate();
                prepare.close();
                session.setAttribute("captcha", null);
                session.setAttribute("codeRetour", null);
                session.setAttribute("codeRetour2", null);
                Mail mail=new Mail(Datas.getEMAIL_ADMIN(), "ADMINISTRATION", "UN NOUVEAU SITE !");
                mail.initMailNouveauSite();
                mail.send();
                this.blank();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
                }
            }
        }
    }
    
    public void clic() {
        try {
            this.getConnection();
            String query="UPDATE table_sites SET nb_visites=nb_visites+1 WHERE id=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.executeUpdate();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void effaceSite() {
        try {
            this.getConnection();
            String query="DELETE FROM table_sites WHERE id=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.executeUpdate();
            prepare.close();
            query="UPDATE table_flag SET flag=? WHERE id=?";
            prepare=this.con.prepareStatement(query);
            prepare.setInt(1, 1);
            prepare.setInt(2, 1);
            prepare.executeUpdate();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Site.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void blank() {
        super.blank();
        this.idCat="0-0";
        this.codeRetour="";
        this.codeRetour2="";
        this.categorie="";
        this.sousCategorie="";
        this.idCategorie=0;
        this.idSousCategorie=0;
        this.url="";
        this.titre="";
        this.description="";
    }

    /**
     * @return the idCat
     */
    public String getIdCat() {
        return idCat;
    }

    /**
     * @return the codeRetour
     */
    public String getCodeRetour() {
        return codeRetour;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return the sousCategorie
     */
    public String getSousCategorie() {
        return sousCategorie;
    }

    /**
     * @return the codeRetour2
     */
    public String getCodeRetour2() {
        return codeRetour2;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }


}
