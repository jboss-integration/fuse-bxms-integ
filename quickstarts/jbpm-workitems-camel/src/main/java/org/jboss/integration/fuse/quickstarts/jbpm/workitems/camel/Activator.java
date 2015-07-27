package org.jboss.integration.fuse.quickstarts.jbpm.workitems.camel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.jbpm.process.workitem.camel.CamelHandler;
import org.jbpm.process.workitem.camel.CamelHandlerFactory;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/*
 * The main purpose of this quickstart is to show how to write into
 * file using jbpm workitems and Camel endpoint. The base of the quickstart
 * is simple process which is capable to decides mortgage application and
 * potentially calculates repayments. The bundle activator creates few
 * mortgage applications and run the process for each of these applications.
 * The results are saved into "acceptedApplications.txt" and
 * "rejectedApplications.txt" files. These files are located in server temporary
 * folder.
 */
public class Activator implements BundleActivator {
    
    int APPLICATION_COUNT = 10; // The number of applications to create at the activation of bundle
    
    /* Range for mortgage ammount */
    int MORTGAGE_MIN = 100000;
    int MORTGAGE_MAX = 1000000;
    
    /* The range for applicant income */
    int INCOME_MIN = 20000;
    int INCOME_MAX = 200000;
    
    KieSession kieSession;

    @Override
    public void start(BundleContext bc) throws Exception {
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        
        CamelHandler handler = CamelHandlerFactory.fileHandler();
        
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer(this.getClass().getClassLoader());
        kieSession=kieContainer.newKieSession("camel-workitem-ksession");
        kieSession.getWorkItemManager().registerWorkItemHandler("CamelFile", handler);
        
        Random random = new Random();
        
        /* Create 10 applications. Each application is evaluated by "camelFileProcess" */
        for(int i=1 ; i<=10 ; i++) {
            /* Create application */
            MortgageApplication mortgageApplication = new MortgageApplication();
            mortgageApplication.setId(i);
            mortgageApplication.setIncome(random.nextInt(INCOME_MAX - INCOME_MIN) + INCOME_MIN);
            mortgageApplication.setAmmount(random.nextInt(MORTGAGE_MAX - MORTGAGE_MIN) + MORTGAGE_MIN);
            
            /* Initialize process parameters */
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("payloadVar","");
            params.put("pathVar", tempDir.getAbsolutePath());
            params.put("fileNameVar", "");
            params.put("applicationVar",mortgageApplication);

            kieSession.startProcess("camelFileProcess", params); // run process
        }
    }

    @Override
    public void stop(BundleContext bc) throws Exception {
        kieSession.dispose();
    }
}
