package com.jb.servlets;

import com.jb.MailService.Service;
import com.jb.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/mailService")
public class MainServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "index.ftml";
    private static final Logger STDOUT = LoggerFactory.getLogger("CONSOLE_OUT");

    @Inject
    TemplateProvider templateProvider;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> map = new HashMap<>();

        Template template = templateProvider.createTemplate(getServletContext(), TEMPLATE_NAME);
        try {
            template.process(map, resp.getWriter());
        } catch (TemplateException e) {
            STDOUT.error("Error while processing template: ", e);
        }
        }
    }
