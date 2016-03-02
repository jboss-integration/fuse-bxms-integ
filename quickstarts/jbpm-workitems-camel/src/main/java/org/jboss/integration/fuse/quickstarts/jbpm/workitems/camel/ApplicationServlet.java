package org.jboss.integration.fuse.quickstarts.jbpm.workitems.camel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.process.workitem.camel.CamelHandler;
import org.jbpm.process.workitem.camel.CamelHandlerFactory;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServlet.class);

    /* Range for mortgage ammount */
    private static final int MORTGAGE_MIN = 100000;
    private static final int MORTGAGE_MAX = 1000000;

    /* The range for applicant income */
    private static final int INCOME_MIN = 20000;
    private static final int INCOME_MAX = 200000;

    private KieSession kieSession;

    private File tempDir;

    private static final String ACCEPTED_APPS_FILENAME = "acceptedApplications.txt";
    private static final String REJECTED_APPS_FILENAME = "rejectedApplications.txt";

    @Override
    public void init(ServletConfig config) throws ServletException {

        tempDir = new File(System.getProperty("java.io.tmpdir"));

        CamelHandler handler = CamelHandlerFactory.fileHandler();

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer(this.getClass().getClassLoader());
        kieSession = kieContainer.newKieSession("camel-workitem-ksession");
        kieSession.getWorkItemManager().registerWorkItemHandler("CamelFile", handler);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Random random = new Random();

        /* Create 10 applications. Each application is evaluated by "camelFileProcess" */
        for (int i = 1; i <= 10; i++) {
            /* Create application */
            MortgageApplication mortgageApplication = new MortgageApplication();
            mortgageApplication.setId(i);
            mortgageApplication.setIncome(random.nextInt(INCOME_MAX - INCOME_MIN) + INCOME_MIN);
            mortgageApplication.setAmmount(random.nextInt(MORTGAGE_MAX - MORTGAGE_MIN) + MORTGAGE_MIN);

            /* Initialize process parameters */
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("payloadVar", "");
            params.put("pathVar", tempDir.getAbsolutePath());
            params.put("fileNameVar", "");
            params.put("applicationVar", mortgageApplication);

            kieSession.startProcess("camelFileProcess", params); // run process
        }
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.error("Error Sleeping:", e);
        }
        PrintWriter out = resp.getWriter();
        out.println("<h1>Accepted Applications</h1>");
        out.println("<ul>");
        BufferedReader reader = new BufferedReader(new FileReader(new File(tempDir.getAbsolutePath() + File.separatorChar + ACCEPTED_APPS_FILENAME)));
        String line;
        while ((line = reader.readLine()) != null) {
            out.println("<li>" + line + "</li>");
        }
        reader.close();
        out.println("</ul>");
        out.println("<br>");
        out.println("<h1>Rejected Applications</h1>");
        reader = new BufferedReader(new FileReader(new File(tempDir.getAbsolutePath() + File.separatorChar + REJECTED_APPS_FILENAME)));
        while ((line = reader.readLine()) != null) {
            out.println("<li>" + line + "</li>");
        }
        reader.close();
        out.println("</ul>");
    }

    @Override
    public void destroy() {
        if (kieSession != null) {
            kieSession.dispose();
        }
    }

}
