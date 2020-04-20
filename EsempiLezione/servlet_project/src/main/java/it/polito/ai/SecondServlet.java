package it.polito.ai;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet("/image")
public class SecondServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedImage bi = new BufferedImage(400, 400, BufferedImage.TYPE_4BYTE_ABGR); // Immagine con trasparenza
        Graphics2D g = bi.createGraphics(); // Mi procuro l'oggetto Graphics, che è l'oggetto che Java mette a disposizione per disegnare dentro le immagini
        g.setStroke(new BasicStroke(3.0f));
        g.drawRect(10, 10, 380, 380);
        ImageIO.write(bi, "png", resp.getOutputStream());   // Pre scrivere in uscita: output stream
        // Abbiamo convertito la nostra immagine in un png e poi lo abbiamo messo in uscita

        resp.setContentType("image/png");
    }
}
