package com.anf.core.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * Begin Code - @author Navya.s
 */

@Component(immediate = true, service = WorkflowProcess.class, name = "Page Creation Property", property = {"process.label=" + "Page Creation Property"})
public class PageCreationProperty implements WorkflowProcess {
    private final Logger log = LoggerFactory.getLogger(PageCreationProperty.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
            throws WorkflowException {
        Session session = null;
        Workflow workflow = null;
        final WorkflowData workflowData = workItem.getWorkflowData();
        final String type = workflowData.getPayloadType();
        final String payloadPath = workflowData.getPayload().toString();
        try {
            ResourceResolver resourceResolver = workflowSession.adaptTo(ResourceResolver.class);
            session = resourceResolver.adaptTo(Session.class);
            workflow = workflowSession.getWorkflow(workItem.getWorkflow().getId());
            Node payloadPathNode = session.getNode(payloadPath);
            payloadPathNode.getNode("jcr:content").setProperty("pageCreatedSuccessfully", "true");
            session.save();
        } catch (RepositoryException e) {
            log.error("RepositoryException {}", e.getMessage());
            workflowSession.terminateWorkflow(workflow);
        }

    }
}
/**
 * END CODE
 */
