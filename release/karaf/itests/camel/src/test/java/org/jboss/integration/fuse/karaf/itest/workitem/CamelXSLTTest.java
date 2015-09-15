package org.jboss.integration.fuse.karaf.itest.workitem;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.integration.fuse.karaf.itest.KarafConfigUtil;
import org.jbpm.process.workitem.camel.CamelHandler;
import org.jbpm.process.workitem.camel.request.RequestPayloadMapper;
import org.jbpm.process.workitem.camel.uri.XSLTURIMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by jiripetrlik@gmail.com on 9/7/15.
 *
 */

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class CamelXSLTTest {

    private KieServices kieServices;
    private KieSession kieSession;

    private static File testDir;
    private static File xslFile;
    private static File outputFile;

    private static final String XML_FILE = "org/jboss/integration/fuse/karaf/itest/example.xml";
    private static final String XSL_FILE = "org/jboss/integration/fuse/karaf/itest/transform.xsl";

    private static final String HEADER_KEY_XSLT_OUTPUT_FILE = "CamelXsltFileName";
    private static final String PREFIX_TEST_DIR = "test_dir_";
    private static final String PREFIX_TRANSFORM_FILE = "transform_";
    private static final String PREFIX_OUTPUT_FILE = "output_";

    private static final String TRANSFORMED_ELEMENT = "<transformedElement>";

    @Configuration
    public static Option[] configure() {
        return KarafConfigUtil.karafConfiguration();
    }

    @BeforeClass
    public static void initialize() {
        String randomUUID = UUID.randomUUID().toString();

        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        testDir = new File(tempDir, PREFIX_TEST_DIR + randomUUID);
        testDir.mkdir();

        String fileName = PREFIX_TRANSFORM_FILE + CamelFileTest.class.getName() + ".xsl";
        xslFile = new File(testDir, fileName);

        fileName = PREFIX_OUTPUT_FILE + CamelFileTest.class.getName() + ".xml";
        outputFile = new File(testDir, fileName);
    }

    @Before
    public void prepare() {
        this.kieServices = KieServices.Factory.get();
        final KieContainer kieContainer = this.kieServices.newKieClasspathContainer(this.getClass().getClassLoader());
        this.kieSession = kieContainer.newKieSession("camel-workitem-ksession");
        assertNotNull(this.kieSession);
    }

    @Test
    public void testSingleXSLTProcess() throws IOException {

        // Load input example into the string
        String inputXML = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream(XML_FILE));
        // Copy XSL file into the temp directory
        FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream(XSL_FILE), xslFile);

        Set<String> headers = new HashSet<String>();
        headers.add(HEADER_KEY_XSLT_OUTPUT_FILE); // header under this key defines output files
        CamelHandler handler = new CamelHandler(new XSLTURIMapper(), new RequestPayloadMapper("payload", headers));
        kieSession.getWorkItemManager().registerWorkItemHandler("CamelXSLT", handler);

        // Run XSLT transformation
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("templateNameVar", "file:///" + xslFile.getAbsolutePath());
        params.put("payloadVar", inputXML);
        params.put("outputFileVar", outputFile.getAbsolutePath());
        params.put("outputVar", "file");

        ProcessInstance pi = kieSession.startProcess("camelXSLTProcess", params);
        ProcessInstance result = kieSession.getProcessInstance(pi.getId());

        // Verify the output file exists and was successfully processed by XSLT transformations
        assertTrue(outputFile.exists());
        // After the running of XSLT transformation, the output file should contain TRANSFORMED_ELEMENT
        String outputXML = FileUtils.readFileToString(outputFile,"UTF-8");
        assertTrue(outputXML.contains(TRANSFORMED_ELEMENT));
    }

    @After
    public void cleanup() {
        if (this.kieSession != null) {
            this.kieSession.dispose();
        }
    }

    @AfterClass
    public static void clean() throws IOException {
        FileUtils.deleteDirectory(testDir);
    }
}
