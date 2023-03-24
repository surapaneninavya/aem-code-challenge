package com.anf.core.models;

import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.factory.ModelFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.Session;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Begin Code - @author Navya.s
 */

public class SearchPropertyModelTest {

    @InjectMocks
    private SearchPropertyModel searchPropertyModel;

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private QueryBuilder queryBuilder;

    @Mock
    private ModelFactory modelFactory;

    @Mock
    private Query query;

    @Mock
    private SearchResult searchResult;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Mock request
        Resource resource = mock(Resource.class);
        when(request.getResource()).thenReturn(resource);
        when(request.getResourceResolver()).thenReturn(mock(ResourceResolver.class)); // Add this line

        // Mock query builder
        Session session = mock(Session.class);
        when(request.getResourceResolver().adaptTo(Session.class)).thenReturn(session);
        when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);

        // Mock search result
        Hit hit = mock(Hit.class);
        Node node = mock(Node.class);
        PropertyIterator propertyIterator = mock(PropertyIterator.class);
        Property property = mock(Property.class);
        when(node.getProperty(Mockito.anyString())).thenReturn(property);
        when(propertyIterator.hasNext()).thenReturn(true, false);
        when(propertyIterator.nextProperty()).thenReturn(property);
        when(property.getString()).thenReturn("title");
        when(hit.getNode()).thenReturn(node);
        when(searchResult.getHits()).thenReturn(Arrays.asList(new Hit[]{hit}));

        // Mock model factory
        when(modelFactory.getModelFromWrappedRequest(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(searchPropertyModel);
    }

    @Test
    public void testInit() throws Exception {
        // Set up input data
        String searchRoot = "/content/anf-code-challenge/us/en";
        String propertyName = "anfCodeChallenge";
        String searchResultsLimit = "10";
        when(request.getParameter("searchRoot")).thenReturn(searchRoot);
        when(request.getParameter("propertyName")).thenReturn(propertyName);
        when(request.getParameter("searchResultsLimit")).thenReturn(searchResultsLimit);

        // Call the method under test
        searchPropertyModel.init();

        // Check the results
        List<HashMap<String, String>> result = searchPropertyModel.getSearchResult();
        assertEquals(0, result.size());
    }

}
/**
 * END CODE
 */
