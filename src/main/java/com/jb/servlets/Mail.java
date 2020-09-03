package com.jb.servlets;

import com.jb.mailservice.Service;
import com.jb.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/mail")
public class Mail extends HttpServlet {

    private static final String TEMPLATE_NAME = "result.ftml";
    private static final Logger STDOUT = LoggerFactory.getLogger("CONSOLE_OUT");
    private static final String ENCODING = "UTF-8";

    @Inject
    TemplateProvider templateProvider;


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Template template = templateProvider.createTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> map = new HashMap<>();

        resp.setCharacterEncoding(ENCODING);
        req.setCharacterEncoding(ENCODING);
        PrintWriter writer = resp.getWriter();

        HashMap<String, String> emailData = new HashMap<>();
        emailData.put("email", req.getParameter("mailAddress"));
        emailData.put("subject", req.getParameter("subject"));
        emailData.put("message", req.getParameter("message"));

        Service service = new Service();
        service.loadProperties();
        if (service.send(emailData)){
            map.put("Success", "Success");
        } else {
            map.put("Error", "Error");
        }

        try {
            template.process(map, resp.getWriter());
        } catch (TemplateException e) {
            STDOUT.error("Error while processing template: ", e);
        }
    }
}
