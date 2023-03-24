package com.anf.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Begin Code - @author Navya.s
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NewsFeedModel {

    private static final Logger logger = LoggerFactory.getLogger(NewsFeedModel.class);



    @ValueMapValue
    @Default(values = "/var/commerce/products/anf-code-challenge/newsData")
    public String newsFeedPath;

    @Self
    Resource resource;

    private List<FeedModel> newsFeed = new ArrayList<>();


    @PostConstruct
    protected void init()  {
        Resource resourceData = resource.getResourceResolver().getResource(newsFeedPath);
        if(resourceData != null) {
            Iterator<Resource> feed = resourceData.listChildren();
            while(feed.hasNext()) {
                Resource feedResource = feed.next();
                newsFeed.add(getFeedItem(feedResource));
            }
        }
    }


    FeedModel getFeedItem(Resource feedResource) {
        ValueMap valueMap = feedResource.getValueMap();

        FeedModel feedModel = new FeedModel();
        feedModel.setAuthor(valueMap.get("author", String.class));
        feedModel.setContent(valueMap.get("content", String.class));
        feedModel.setDescription(valueMap.get("description", String.class));
        feedModel.setTitle(valueMap.get("title", String.class));
        feedModel.setUrl(valueMap.get("url", String.class));
        feedModel.setUrlImage(valueMap.get("urlImage", String.class));
        return feedModel;
    }


    public List<FeedModel> getNewsFeed() {
        return newsFeed;
    }

    public String getNewsFeedPath() {
        return newsFeedPath;
    }
}
/**
 * END CODE
 */
