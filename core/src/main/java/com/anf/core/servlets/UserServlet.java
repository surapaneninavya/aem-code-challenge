package com.anf.core.servlets;

import com.anf.core.services.ContentService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

@Component(service = { Servlet.class })
@SlingServletPaths(
        value = "/bin/saveUserDetails"
)
public class UserServlet extends SlingAllMethodsServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);

    private static final long serialVersionUID = 1L;

    @Reference
    ContentService contentService;

    @Override
    protected void doPost(final SlingHttpServletRequest req,
                          final SlingHttpServletResponse resp) throws ServletException, IOException {
        /**
         * Begin Code - @author Navya.s
         */

        ResourceResolver resolver=req.getResourceResolver();
        Session session=(Session) resolver.adaptTo(Session.class);
        Resource resource=resolver.getResource("/var/anf-code-challenge");
        String firstName=req.getParameter("fname");
        String lastName=req.getParameter("lname");
        String age=req.getParameter("age");
        String country=req.getParameter("country");


        try {
            Node resourceNode=resource.adaptTo(Node.class);
            Node user = resourceNode.addNode(firstName.replace(" ","_").toLowerCase(Locale.ROOT)+"_"+UUID.randomUUID().toString());
            user.setProperty("firstName", firstName);
            user.setProperty("lastName", lastName);
            user.setProperty("age", age);
            user.setProperty("country", country);
            session.save();
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        resp.getWriter().print("Form DataSaved");
        /**
         * END CODE
         */


    }
}
