/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pj
 */
public class SousCategorie extends Objet {
    
    private long idCategorie;
    private String sousCategorie;
    private long id;
    private String tagTitle;
    private String tagDescription;
    private String categorie;
    private String encodedSousCategorie;
    
    public SousCategorie() {
        super();
        this.idCategorie=0;
    }

    public SousCategorie(long idCategorie, long idSousCategorie) {
        super();
        this.idCategorie=idCategorie;
        this.id=idSousCategorie;
    }

    public void getGetIdCat(HttpServletRequest request) {
        try {
            if(request.getParameter("idCategorie")!=null)
                this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
        } catch(Exception ex) {
            this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
        }
    }
    
    public void getPosts(HttpServletRequest request) {
        try {
            this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
            this.sousCategorie=this.codeHTML(request.getParameter("sousCategorie"));
        } catch(Exception ex) {
            this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
        }
    }

    public void verifPosts() {
        if(this.idCategorie==0)
            this.setErrorMsg("<div>Veuillez choisir une CATÉGORIE SVP.</div>");
        if(this.sousCategorie.length()==0)
            this.setErrorMsg("<div>Champ SOUS-CATÉGORIE vide.</div>");
        else if(this.sousCategorie.length()>100)
            this.setErrorMsg("<div>Champ SOUS-CATÉGORIE trop long.</div>");
        else {
            try {
                this.getConnection();
                String query="SELECT COUNT(id) AS nb FROM table_sous_categories WHERE id_categorie=? AND sous_categorie=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setString(2, this.sousCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                if(nb!=0)
                    this.setErrorMsg("<div>Désolé, cette SOUS-CATÉGORIE existe déjà.</div>");   
            } catch (NamingException ex) {
                Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void enregSousCat() {
        if(this.getErrorMsg().length()==0) {
            try {
                this.getConnection();
                String query="INSERT INTO table_sous_categories (id_categorie,sous_categorie) VALUES (?,?)";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setString(2, this.sousCategorie);
                prepare.executeUpdate();
                prepare.close();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    this.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void initInfos() {
        try {
            this.tagTitle="Répertoire de sites internet";
            this.tagDescription="Ze-annuaire - répertoire de sites internet de qualité.";
            this.getConnection();
            String query="SELECT t1.categorie,t2.sous_categorie FROM table_categories AS t1, table_sous_categories AS t2 WHERE t1.id=? AND t2.id=? AND t2.id_categorie=t1.id LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            prepare.setLong(2, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.categorie=result.getString("categorie");
            this.sousCategorie=result.getString("sous_categorie");
            result.close();
            prepare.close();
            this.encodedSousCategorie=this.encodeTitre(this.sousCategorie);
            this.tagTitle="Répertoire de sites internet - "+this.sousCategorie;
            this.tagDescription="Ze-annuaire - Répertoire de sites internet - "+this.categorie+" - "+this.sousCategorie+".";
        } catch (NamingException ex) {
            Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(SousCategorie.class.getName()).log(Level.SEVERE, null, ex);
                this.id=0;
            }
        }
    }

    /**
     * @return the idCategorie
     */
    public long getIdCategorie() {
        return idCategorie;
    }

    /**
     * @return the sousCategorie
     */
    public String getSousCategorie() {
        return sousCategorie;
    }

    /**
     * @return the tagTitle
     */
    public String getTagTitle() {
        return tagTitle;
    }

    /**
     * @return the tagDescription
     */
    public String getTagDescription() {
        return tagDescription;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return the encodedSousCategorie
     */
    public String getEncodedSousCategorie() {
        return encodedSousCategorie;
    }

}
