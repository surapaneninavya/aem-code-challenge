package com.anf.core.servlets;

import com.anf.core.services.ContentService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Begin Code - @author Navya.s
 */
public class UserServletTest {

    private UserServlet servlet;

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private SlingHttpServletResponse response;

    @Mock
    private ResourceResolver resolver;

    @Mock
    private Session session;

    @Mock
    private Resource resource;

    @Mock
    private Node resourceNode;

    private StringWriter writer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        servlet = new UserServlet();
        servlet.contentService = mock(ContentService.class);

        writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(printWriter);

        when(request.getResourceResolver()).thenReturn(resolver);
        when(resolver.adaptTo(Session.class)).thenReturn(session);

        // Mock the getResource method to return a non-null value
        when(resolver.getResource("/var/anf-code-challenge")).thenReturn(resource);

        when(resource.adaptTo(Node.class)).thenReturn(resourceNode);
        when(resourceNode.addNode("test_first_name_" + UUID.randomUUID().toString())).thenReturn(resourceNode);
    }


    @Test
    public void testDoPost() throws ServletException, IOException, RepositoryException {
        when(request.getParameter("fname")).thenReturn("Test First Name");
        when(request.getParameter("lname")).thenReturn("Test Last Name");
        when(request.getParameter("age")).thenReturn("30");
        when(request.getParameter("country")).thenReturn("Test Country");

        resourceNode.setProperty("fname",request.getParameter("fname"));
        resourceNode.setProperty("lname",request.getParameter("fname"));
        resourceNode.setProperty("age",request.getParameter("fname"));
        resourceNode.setProperty("country",request.getParameter("fname"));


    }
}

/**
 * END CODE
 */

