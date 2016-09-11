/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import classes.Objet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class Categories extends Objet {
    
    private int nb;
    private String[] categories;

    /** Creates a new instance of Categories */
    public Categories() {
        super();
        this.nb=0;
    }
    
    public void initListeAdmin() {
        try {
            this.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_categories";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nb=result.getInt("nb");
            result.close();
            state.close();
            if(this.getNb()>0) {
                this.categories=new String[this.nb];
                query="SELECT categorie FROM table_categories ORDER BY categorie ASC LIMIT 0,"+this.getNb();
                state=this.con.createStatement();
                result=state.executeQuery(query);
                int i=0;
                while(result.next()) {
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


}
