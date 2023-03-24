package com.anf.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * Begin Code - @author Navya.s
 */
@ExtendWith(AemContextExtension.class)
class NewsFeedModelTest {

    private final AemContext aemContext = new AemContext();
    NewsFeedModel model = new NewsFeedModel();

    private static final String VAR_PATH = "/var/commerce/products/anf-code-challenge";
    private static final String VAR_DATA_PATH = VAR_PATH + "/data/newsData";

    @BeforeEach
    public void setUp() throws Exception {
        aemContext.addModelsForClasses(NewsFeedModel.class);
        aemContext.load().json("/content/anf-code-challenge/newsfeed.json", VAR_PATH);
        model = aemContext.currentResource(VAR_PATH + "/newsFeed").adaptTo(NewsFeedModel.class);
    }

    @Test
    void testGetNewsFeedPath() {
        assertEquals(VAR_DATA_PATH, model.getNewsFeedPath());
    }


    @Test
    void testNewsFeed() {
        aemContext.currentResource(VAR_DATA_PATH);
        assertEquals(10, model.getNewsFeed().size());
    }

}

/**
 * END CODE
 */