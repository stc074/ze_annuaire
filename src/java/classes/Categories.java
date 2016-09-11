/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class Categories extends Objet {
    
    private int nb;
    private int[] nbs;
    private String[] categories;
    private String[][] categories2;
    private long[] ids;
    private String[] ids21;
    private String[][] ids22;
    private int nb2;
    private String[] urls;
    
    public Categories() {
        super();
    }

    
    public void initListeAdmin() {
        try {
            this.nb=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_categories";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nb=result.getInt("nb");
            result.close();
            state.close();
            if(this.nb>0) {
                this.categories=new String[this.nb];
                this.ids=new long[this.nb];
                query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC LIMIT 0,"+this.nb;
                state=this.con.createStatement();
                result=state.executeQuery(query);
                int i=0;
                while(result.next()) {
                    this.ids[i]=result.getLong("id");
                    this.categories[i]=result.getString("categorie");
                    i++;
                }
                result.close();
                state.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
                this.nb=0;
            }
        }
    }

    public void initListeAjout() {
        try {
            this.nb=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_categories";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nb=result.getInt("nb");
            result.close();
            state.close();
            if(this.nb>0) {
                this.nbs=new int[this.nb];
                this.ids21=new String[this.nb];
                this.categories=new String[this.nb];
                this.categories2=new String[this.nb][];
                this.ids22=new String[this.nb][];
                query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC LIMIT 0,"+this.nb;
                state=this.con.createStatement();
                result=state.executeQuery(query);
                PreparedStatement prepare;
                ResultSet result2;
                String query2="";
                int i=0;
                while(result.next()) {
                    long idCat=result.getLong("id");
                    String cat=result.getString("categorie");
                    this.ids21[i]=idCat+"-0";
                    this.categories[i]=cat;
                    this.nbs[i]=0;
                    query2="SELECT COUNT(id) AS nb2 FROM table_sous_categories WHERE id_categorie=?";
                    prepare=this.con.prepareStatement(query2);
                    prepare.setLong(1, idCat);
                    result2=prepare.executeQuery();
                    result2.next();
                    this.nb2=result2.getInt("nb2");
                    result2.close();
                    prepare.close();
                    if(this.nb2>0) {
                        this.nbs[i]=this.nb2;
                        this.categories2[i]=new String[this.nb2];
                        this.ids22[i]=new String[this.nb2];
                        query2="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC LIMIT 0,"+this.nb2;
                        prepare=this.con.prepareStatement(query2);
                        prepare.setLong(1, idCat);
                        result2=prepare.executeQuery();
                        int j=0;
                        while(result2.next()) {
                            this.ids22[i][j]=idCat+"-"+result2.getLong("id");
                            this.categories2[i][j]=cat+"&rarr;"+result2.getString("sous_categorie");
                            j++;
                        }
                        result2.close();
                        prepare.close();
                    }
                    i++;
                }
                result.close();
                state.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
                this.nb=0;
            }
        }
    }
    
    public void initListeIndex() {
        try {
            this.nb=0;
            this.getConnection();
            String query="SELECT COUNT(id)  AS nb FROM table_categories";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nb=result.getInt("nb");
            result.close();
            state.close();
            if(this.nb>0) {
                this.categories=new String[this.nb];
                this.urls=new String[this.nb];
                query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC LIMIT 0,"+this.nb;
                state=this.con.createStatement();
                result=state.executeQuery(query);
                int i=0;
                while(result.next()) {
                    long idCat=result.getLong("id");
                    String cat=result.getString("categorie");
                    this.urls[i]="./categorie-"+idCat+"-"+this.encodeTitre(cat)+".html";
                    this.categories[i]=cat;
                    i++;
                }
                result.close();
                state.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
                this.nb=0;
            }
        }
    }

    /**
     * @return the nb
     */
    public int getNb() {
        return nb;
    }

    /**
     * @return the categories
     */
    public String[] getCategories() {
        return categories;
    }

    /**
     * @return the ids
     */
    public long[] getIds() {
        return ids;
    }

    /**
     * @return the nb2
     */
    public int getNb2() {
        return nb2;
    }

    /**
     * @return the ids21
     */
    public String[] getIds21() {
        return ids21;
    }

    /**
     * @return the ids22
     */
    public String[][] getIds22() {
        return ids22;
    }

    /**
     * @return the nbs
     */
    public int[] getNbs() {
        return nbs;
    }

    /**
     * @return the categories2
     */
    public String[][] getCategories2() {
        return categories2;
    }

    /**
     * @return the urls
     */
    public String[] getUrls() {
        return urls;
    }

}
