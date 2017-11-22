package cs5.puida.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsServlet extends AddServlet {

    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getParameter("okButton") != null) {
            req.getRequestDispatcher("/browse").forward(req, resp);
            return;
        }
        showPage(req,resp);
    }

    protected void showPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/details.jsp").forward(req, resp);

    }
}