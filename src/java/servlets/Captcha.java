/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Datas;
import classes.Objet;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Captcha extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("image/png");
            HttpSession session=request.getSession(true);
            File file=new File(Datas.getDIR()+"GFXs/fonts.png");
            BufferedImage buffer = ImageIO.read(file);
            BufferedImage captcha=new BufferedImage(160, 32, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D)captcha.getGraphics();
            String captchaTexte="";
            int index;
            for(int i=0; i<5; i++) {
                index=(int)((double)(Datas.getARRAY_CHARS().length)*Math.random());
                g.drawImage(buffer, i*32, 0, (i*32)+32, 32, index*32, 0, (index*32)+32, 32, null);
                captchaTexte+=Datas.getARRAY_CHARS()[index];
            }
            OutputStream os = response.getOutputStream();
            ImageIO.write(captcha, "PNG", os);
            os.close();
            String captchaCryptee = null;
            Objet obj=new Objet();
            captchaCryptee = obj.getEncoded(captchaTexte);
            session.setAttribute("captcha", captchaCryptee);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Captcha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
