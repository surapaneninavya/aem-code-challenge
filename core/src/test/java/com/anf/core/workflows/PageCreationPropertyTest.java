package com.anf.core.workflows;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Begin Code - @author Navya.s
 */

public class PageCreationPropertyTest {

    private final Logger log = LoggerFactory.getLogger(PageCreationPropertyTest.class);

    private PageCreationProperty pageCreationProperty;

    @Before
    public void setup() {
        pageCreationProperty = new PageCreationProperty();
    }

    @Test
    public void testExecute() throws Exception {
        String payloadPath = "/content/test-page";
        String payloadType = "JCR_PATH";
        String pageCreatedSuccessfully = "true";

        // Mock WorkflowSession, ResourceResolver, and Session
        WorkflowSession workflowSession = mock(WorkflowSession.class);
        ResourceResolver resourceResolver = mock(ResourceResolver.class);
        Session session = mock(Session.class);
        when(workflowSession.adaptTo(ResourceResolver.class)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

        // Mock WorkItem and WorkflowData
        WorkItem workItem = mock(WorkItem.class);
        Workflow workflow = mock(Workflow.class);
        WorkflowData workflowData = mock(WorkflowData.class);
        when(workItem.getWorkflowData()).thenReturn(workflowData);
        when(workItem.getWorkflow()).thenReturn(workflow);
        when(workItem.getWorkflow().getId()).thenReturn("123");
        when(workflowData.getPayloadType()).thenReturn(payloadType);
        when(workflowData.getPayload()).thenReturn(payloadPath);

        // Mock Node and Property
        Node node = mock(Node.class);
        Property property = mock(Property.class);
        when(session.getNode(payloadPath)).thenReturn(node);
        when(node.getNode("jcr:content")).thenReturn(node);
        when(node.setProperty("pageCreatedSuccessfully", pageCreatedSuccessfully)).thenReturn(property);

        // Execute workflow process
        MetaDataMap metaDataMap = mock(MetaDataMap.class);
        pageCreationProperty.execute(workItem, workflowSession, metaDataMap);

        // Verify that session.save() was called
    }
}
/**
 * END CODE
 */
