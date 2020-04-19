package it.polito.ai;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/index.html")
public class FirstServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {

            PrintWriter pw = resp.getWriter();
            pw.println("<html><head><link rel=\"stylesheet\" href=\"style.css\"></head><body><h1>Hello Servlet!</h1>");
            pw.println("<ul>");
            Enumeration<String> names = req.getHeaderNames();
            while(names.hasMoreElements()){
                String name = names.nextElement();
                pw.print("<li>" );
                pw.print(name);
                pw.print(":");
                pw.print(req.getHeader(name));
                pw.println("</li>");
            }
            pw.println("</ul>");
            pw.println("<p> SESSION ID: "+req.getSession().getId()+"</p>");
            pw.println("</body></html>");
            resp.setContentType("text/html");
        } catch (IOException ioe){
            throw new RuntimeException(ioe);
        }
    }

}
