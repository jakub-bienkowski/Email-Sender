package com.jb.servlets;

import com.jb.MailService.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/mail")
public class MainServlet extends HttpServlet {

    private static final Logger STDOUT = LoggerFactory.getLogger("CONSOLE_OUT");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("mailAddress");
        String subject = req.getParameter("subject");
        String message = req.getParameter("message");

        Service service = new Service();
        service.loadProperties();
        try {
            service.send(email, subject, message);
        } catch (MessagingException e) {
            STDOUT.error(e.toString());
        }

        PrintWriter writer = resp.getWriter();
        writer.println("Your e-mail was sent successfully");

    }




}
