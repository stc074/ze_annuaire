/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pj
 */
public class Sites extends Objet {
    private int nb;
    private int nbSites;
    private String[] urls;
    private String[] titres;
    private String[] descriptions;
    private String[] lignes1;
    private String[] lignes2;
    private double nb2;
    private long[] ids;
    private long idCategorie;
    private int page;
    private int nbSitesPage;
    private int nbSitesPage2;
    private int nbPages;
    private int prem;
    private int der;
    private long idSousCategorie;
    private String idCat;
    private int index1;
    private int index2;
    
    public Sites() {
        super();
        this.idCat="0-0";
        this.index1=0;
        this.index2=10;
    }
    
    public Sites(long idCategorie) {
        super();
        this.idCategorie=idCategorie;
        this.page=0;
        this.nbSitesPage=0;
        this.nbSitesPage2=0;
    }
    
    public Sites (long idCategorie, long idSousCategorie) {
        super();
        this.idCategorie=idCategorie;
        this.idSousCategorie=idSousCategorie;
        this.page=0;
        this.nbSitesPage=0;
    }

    public void initListeIndex() {
        try {
            this.nb=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_sites";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nb=this.nbSites=result.getInt("nb");
            result.close();
            state.close();
            if(this.nb>=Datas.getNB_SITES())
                this.nb=Datas.getNB_SITES();
            if(this.nb>0) {
                this.nb2=(int)(double)this.nb/(double)2;
                this.ids=new long[this.nb];
                this.urls=new String[this.nb];
                this.titres=new String[this.nb];
                this.descriptions=new String[this.nb];
                this.lignes1=new String[this.nb];
                this.lignes2=new String[this.nb];
                query="SELECT t1.id,t1.url,t1.titre,t1.description,t1.nb_visites,t1.timestamp,t1.id_categorie,t1.id_sous_categorie FROM table_sites AS t1 ORDER BY timestamp DESC LIMIT 0,"+this.nb;
                state=this.con.createStatement();
                result=state.executeQuery(query);
                this.initListe(result);
                result.close();
                state.close();
            }
            query="UPDATE table_flag SET flag=? WHERE id=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setInt(1, 0);
            prepare.setInt(2, 1);
            prepare.executeUpdate();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
                this.nb=0;
            }
        }
    }

    private void initListe(ResultSet result) throws SQLException {
        Calendar cal=Calendar.getInstance();
        int i=0;
        String query2;
        PreparedStatement prepare2;
        ResultSet result2;
        while(result.next()) {
            long idSite=result.getLong("id");
            String url=result.getString("url");
            String titre=result.getString("titre");
            String description=result.getString("description");
            int nbVisites=result.getInt("nb_visites");
            long timestamp=result.getLong("timestamp");
            long idCat=result.getLong("id_categorie");
            long idSousCat=result.getLong("id_sous_categorie");
            String cat="", sousCat="";
            if(idSousCat==0) {
                query2="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
                prepare2=this.con.prepareStatement(query2);
                prepare2.setLong(1, idCat);
                result2=prepare2.executeQuery();
                result2.next();
                cat=result2.getString("categorie");
                result2.close();
                prepare2.close();
            } else if(idSousCat!=0) {
                query2="SELECT t1.categorie,t2.sous_categorie FROM table_categories AS t1,table_sous_categories AS t2 WHERE t1.id=? AND t2.id=? AND t1.id=t2.id_categorie LIMIT 0,1";
                prepare2=this.con.prepareStatement(query2);
                prepare2.setLong(1, idCat);
                prepare2.setLong(2, idSousCat);
                result2=prepare2.executeQuery();
                result2.next();
                cat=result2.getString("categorie");
                sousCat=result2.getString("sous_categorie");
                result2.close();
                prepare2.close();
                
            }
            cal.setTimeInMillis(timestamp);
            this.ids[i]=idSite;
            this.urls[i]=url;
            this.titres[i]=titre;
            this.descriptions[i]=description;
            this.lignes1[i]="Inscrit depuis le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
            if(idSousCat==0)
                this.lignes2[i]=cat.toUpperCase()+"&nbsp|&nbsp;Nombre de visites : "+nbVisites+".";
            else if(idSousCat!=0)
                this.lignes2[i]=cat.toUpperCase()+"&rarr;"+sousCat.toUpperCase()+"&nbsp|&nbsp;Nombre de visites : "+nbVisites+".";
            i++;
        }
    }
    
    public void initListeCategorie() {
        try {
            this.getConnection();
            this.nbSitesPage=0;
            String query="SELECT COUNT(id) AS nbSites FROM table_sites WHERE id_categorie=? AND id_sous_categorie=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.getIdCategorie());
            prepare.setLong(2, 0);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbSites=result.getInt("nbSites");
            result.close();
            prepare.close();
            if(this.nbSites>0) {
                this.nbPages=(int)(Math.ceil((double)this.nbSites/(double)Datas.getNB_SITES()));
                if(this.nbSites<=Datas.getNB_SITES())
                    this.nbSitesPage=this.nbSites;
                else if(this.page<(this.nbPages-1))
                    this.nbSitesPage=Datas.getNB_SITES();
                else if(this.page==(this.nbPages-1))
                    this.nbSitesPage=this.nbSites-(this.nbPages-1)*Datas.getNB_SITES();
                if(this.nbSitesPage>0) {
                    this.prem=this.page-5;
                    if(this.prem<0)
                        this.prem=0;
                    this.der=this.page+5;
                    if(this.der>=(this.nbPages-1))
                        this.der=this.nbPages-1;
                    this.ids=new long[this.nbSitesPage];
                    this.urls=new String[this.nbSitesPage];
                    this.titres=new String[this.nbSitesPage];
                    this.descriptions=new String[this.nbSitesPage];
                    this.lignes1=new String[this.nbSitesPage];
                    this.lignes2=new String[this.nbSitesPage];
                    query="SELECT t1.id,t1.url,t1.titre,t1.description,t1.nb_visites,t1.timestamp,t1.id_categorie,t1.id_sous_categorie FROM table_sites AS t1 WHERE t1.id_categorie=? AND t1.id_sous_categorie=? ORDER BY nb_visites DESC LIMIT "+(this.page*Datas.getNB_SITES())+","+this.nbSitesPage;
                    prepare=this.con.prepareStatement(query);
                    prepare.setLong(1, this.getIdCategorie());
                    prepare.setLong(2, 0);
                    result=prepare.executeQuery();
                    this.initListe(result);
                    result.close();
                    prepare.close();
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
            this.nbSitesPage=0;
        } catch (SQLException ex) {
            Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
            this.nbSitesPage=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
                this.nbSitesPage=0;
            }
        }
    }

    public boolean testFlag() {
        boolean fl=false;
        try {
            this.getConnection();
            String query="SELECT flag FROM table_flag WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setInt(1, 1);
            ResultSet result=prepare.executeQuery();
            result.next();
            int flag=result.getInt("flag");
            result.close();
            prepare.close();
            if(flag==1)
                fl=true;
        } catch (NamingException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
            fl=false;
        } catch (SQLException ex) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
            fl=false;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, ex);
                fl=false;
            }
        }
        return fl;
    }

    public void getGetPage(HttpServletRequest request) {
        if(request.getParameter("page")!=null)
            this.page=Integer.parseInt(request.getParameter("page"));
    }

    public void initListeSousCategorie() {
        try {
            this.nbSitesPage=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nbSites FROM table_sites WHERE id_categorie=? AND id_sous_categorie=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            prepare.setLong(2, this.getIdSousCategorie());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbSites=result.getInt("nbSites");
            result.close();
            prepare.close();
            this.nbPages=(int)(Math.ceil((double)this.nbSites/(double)Datas.getNB_SITES()));
            if(this.nbSites<=Datas.getNB_SITES())
                this.nbSitesPage=this.nbSites;
            else if(this.page<(this.nbPages-1))
                this.nbSitesPage=Datas.getNB_SITES();
            else if(this.page==(this.nbPages-1))
                this.nbSitesPage=this.nbSites-((this.nbPages-1)*Datas.getNB_SITES());
            if(this.nbSitesPage>0) {
                this.urls=new String[this.nbSitesPage];
                this.titres=new String[this.nbSitesPage];
                this.descriptions=new String[this.nbSitesPage];
                this.ids=new long[this.nbSitesPage];
                this.lignes1=new String[this.nbSitesPage];
                this.lignes2=new String[this.nbSitesPage];
                this.prem=this.page-5;
                if(this.prem<0)
                    this.prem=0;
                this.der=this.page+5;
                if(this.der>(this.nbPages-1))
                    this.der=this.nbPages-1;
                query="SELECT id,id_categorie,id_sous_categorie,url,titre,description,nb_visites,timestamp FROM table_sites WHERE id_categorie=? AND id_sous_categorie=? ORDER BY nb_visites DESC LIMIT "+(this.page*Datas.getNB_SITES())+","+this.nbSitesPage;
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setLong(2, this.getIdSousCategorie());
                result=prepare.executeQuery();
                this.initListe(result);
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
            this.nbSitesPage=0;
        } catch (SQLException ex) {
            Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
            this.nbSitesPage=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
                this.nbSitesPage=0;
            }
        }
    }

    public void initListeTop() {
        try {
            this.nb=0;
            this.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_sites";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nb=result.getInt("nb");
            result.close();
            state.close();
            if(this.nb>0) {
                if(this.nb>this.getNB_SITES_TOP())
                    this.nb=this.getNB_SITES_TOP();
                this.ids=new long[this.nb];
                this.urls=new String[this.nb];
                this.titres=new String[this.nb];
                this.descriptions=new String[this.nb];
                this.lignes1=new String[this.nb];
                this.lignes2=new String[this.nb];
                query="SELECT id,id_categorie,id_sous_categorie,url,titre,description,nb_visites,timestamp FROM table_sites ORDER BY nb_visites DESC LIMIT 0,"+this.nb;
                state=this.con.createStatement();
                result=state.executeQuery(query);
                this.initListe(result);
                result.close();
                state.close();
                query="UPDATE table_flag SET flag=? WHERE id=?";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setInt(1, 0);
                prepare.setInt(2, 1);
                prepare.executeUpdate();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
                this.nb=0;
            }
        }
    }

    public void getGetCat(HttpServletRequest request) {
        try {
            if(request.getParameter("idCategorie")!=null) 
                this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
            if(request.getParameter("idSousCategorie")!=null)
                this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
            this.idCat=this.idCategorie+"-"+this.idSousCategorie;
        } catch(Exception ex) {
            this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
        }
    }

    public void getPostsListe(HttpServletRequest request) {
        try {
            this.idCat=this.codeHTML(request.getParameter("idCat"));
            this.index1=Integer.parseInt(request.getParameter("index1"));
            this.index2=Integer.parseInt(request.getParameter("index2"));
        } catch(Exception ex) {
            this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
        }
    }

    public void verifPostsListe() {
        try {
            String[] arrayCats=new String[2];
            arrayCats=this.idCat.split("-");
            this.idCategorie=Integer.parseInt(arrayCats[0]);
            this.idSousCategorie=Integer.parseInt(arrayCats[1]);
            int index=0;
            if(this.index2<this.index1) {
                index=this.index1;
                this.index1=this.index2;
                this.index2=index;
            }
        } catch(Exception ex) {
            this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
        }
    }

    public void initListeListe() {
        try {
            this.getConnection();
            String queryCat="";
            if(this.idCategorie!=0) {
                queryCat+=" WHERE id_categorie='"+this.idCategorie+"'";
                if(this.idSousCategorie!=0)
                    queryCat+=" AND id_sous_categorie='"+this.idSousCategorie+"'";
                else
                    queryCat+=" AND id_sous_categorie='0'";
            }
            String query="SELECT COUNT(id) AS nb FROM table_sites"+queryCat;
            //this.setErrorMsg(query);
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nb=result.getInt("nb");
            result.close();
            state.close();
            if(index2-index1<this.nb)
                this.nb=index2-index1;
            if(this.nb>0) {
                this.urls=new String[this.nb];
                this.ids=new long[this.nb];
                this.titres=new String[this.nb];
                this.lignes1=new String[this.nb];
                this.lignes2=new String[this.nb];
                this.descriptions=new String[this.nb];
                query="SELECT id,id_categorie,id_sous_categorie,url,titre,description,nb_visites,timestamp FROM table_sites"+queryCat+" ORDER BY timestamp DESC LIMIT "+index1+","+this.nb;
                state=this.con.createStatement();
                result=state.executeQuery(query);
                this.initListe(result);
                result.close();
                state.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
            this.setErrorMsg("<div>ERREUR : "+ex.getMessage()+"</div>");
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Sites.class.getName()).log(Level.SEVERE, null, ex);
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
     * @return the nbSites
     */
    public int getNbSites() {
        return nbSites;
    }

    /**
     * @return the urls
     */
    public String[] getUrls() {
        return urls;
    }

    /**
     * @return the titres
     */
    public String[] getTitres() {
        return titres;
    }

    /**
     * @return the descriptions
     */
    public String[] getDescriptions() {
        return descriptions;
    }

    /**
     * @return the lignes1
     */
    public String[] getLignes1() {
        return lignes1;
    }

    /**
     * @return the lignes2
     */
    public String[] getLignes2() {
        return lignes2;
    }

    /**
     * @return the nb2
     */
    public double getNb2() {
        return nb2;
    }

    /**
     * @return the ids
     */
    public long[] getIds() {
        return ids;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @return the nbSitesPage
     */
    public int getNbSitesPage() {
        return nbSitesPage;
    }

    /**
     * @return the nbSitesPage2
     */
    public int getNbSitesPage2() {
        return nbSitesPage2;
    }

    /**
     * @return the prem
     */
    public int getPrem() {
        return prem;
    }

    /**
     * @return the der
     */
    public int getDer() {
        return der;
    }

    /**
     * @return the idCategorie
     */
    public long getIdCategorie() {
        return idCategorie;
    }

    /**
     * @return the idSousCategorie
     */
    public long getIdSousCategorie() {
        return idSousCategorie;
    }

    /**
     * @return the idCat
     */
    public String getIdCat() {
        return idCat;
    }

    /**
     * @return the index1
     */
    public int getIndex1() {
        return index1;
    }

    /**
     * @return the index2
     */
    public int getIndex2() {
        return index2;
    }

}
