package com.anf.core.models;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;

/**
 * Begin Code - @author Navya.s
 */

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchPropertyModel {

    private static final Logger logger = LoggerFactory.getLogger(SearchPropertyModel.class);

    @ValueMapValue
    @Default(values = "/content/anf-code-challenge/us/en")
    String searchRoot;

    @ValueMapValue
    @Default(values = "anfCodeChallenge")
    String propertyName;


    @ValueMapValue
    String searchResultsLimit;

    @ValueMapValue
    @Default(values = "queryBuilder")
    public String searchType;

    @Self
    SlingHttpServletRequest request;

    @OSGiService
    QueryBuilder builder;
    List<HashMap<String, String>> searchResult = new ArrayList<HashMap<String, String>>();

    @PostConstruct
    protected void init() throws RepositoryException {
        Session session = request.getResourceResolver().adaptTo(Session.class);
        if(StringUtils.endsWithIgnoreCase(searchType,"queryBuilder")) {
            getResultsFromQueryBuilder(session);
        } else if (StringUtils.endsWithIgnoreCase(searchType,"sql2")) {
            getResultsFromSQL2(session);
        }

    }

    private void getResultsFromQueryBuilder(Session session) throws RepositoryException {
        if(StringUtils.isNotBlank(searchRoot) && StringUtils.isNotBlank(propertyName)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("path", searchRoot);
            map.put("type", "cq:PageContent");
            map.put("property", propertyName); // combine this group with OR
            map.put("p.offset", "0");
            if(StringUtils.isNotBlank(searchResultsLimit)) {
                map.put("p.limit", searchResultsLimit);
            }
            map.put("orderby", "@jcr:created");
            map.put("orderby.sort", "asc");
            Query query = builder.createQuery(PredicateGroup.create(map), session);
            query.setStart(0);
            query.setHitsPerPage(Integer.parseInt(searchResultsLimit));
            SearchResult result = query.getResult();
            for (Hit hit : result.getHits()) {

                Node resultNode = hit.getNode();
                String title = resultNode.getProperty("jcr:title").getString();
                String resultHit = resultNode.getParent().getPath();
                HashMap<String, String> resultDetails = new LinkedHashMap<String, String>();
                resultDetails.put("path",resultHit);
                resultDetails.put("title",title);
                searchResult.add(resultDetails);
            }
        }
    }

    private void getResultsFromSQL2(Session session) throws RepositoryException {

        final String sqlQuery = "SELECT * FROM [cq:PageContent] AS page WHERE ISDESCENDANTNODE(page ,\"" + searchRoot + "\") AND [" + propertyName + "] = \"true\" ORDER BY page.[jcr:created] ASC";
        Iterator<Resource> results = request.getResourceResolver().findResources(sqlQuery, javax.jcr.query.Query.JCR_SQL2);
           int pageResourceCount = 1;
        while(results.hasNext() && pageResourceCount < Integer.parseInt(searchResultsLimit)) {
            Resource resultResource = results.next();
            Node resultNode = resultResource.adaptTo(Node.class);
            String title = resultNode.getProperty("jcr:title").getString();
            String resultHit = resultNode.getParent().getPath();
            HashMap<String, String> resultDetails = new LinkedHashMap<String, String>();
            resultDetails.put("path",resultHit);
            resultDetails.put("title",title);
            searchResult.add(resultDetails);
            }
    }

    public List<HashMap<String, String>> getSearchResult() {
        return searchResult;
    }


}

/**
 * END CODE
 */
