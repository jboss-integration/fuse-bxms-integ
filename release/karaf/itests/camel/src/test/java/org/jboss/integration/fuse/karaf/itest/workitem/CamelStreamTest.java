package org.jboss.integration.fuse.karaf.itest.workitem;

import org.apache.camel.CamelContext;
import org.apache.commons.io.FileUtils;
import org.jboss.integration.fuse.karaf.itest.KarafConfigUtil;
import org.jbpm.process.workitem.camel.CamelHandler;
import org.jbpm.process.workitem.camel.request.RequestPayloadMapper;
import org.jbpm.process.workitem.camel.response.ResponsePayloadMapper;
import org.jbpm.process.workitem.camel.uri.GenericURIMapper;
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

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by jpetrlik on 9/9/15.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class CamelStreamTest {

    @Inject
    CamelContext camelContext;

    private KieServices kieServices;
    private KieSession kieSession;

    private static File testDir;
    private static File testFile;

    private static final String TEST_DATA = "test-data";
    private static final String PREFIX_TEST_DIR = "test_dir_";
    private static final String PREFIX_TEST_FILE = "test_file_";

    @Configuration
    public static Option[] configure() {
        return KarafConfigUtil.karafConfiguration();
    }

    @BeforeClass
    public static void initialize() {
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        testDir = new File(tempDir, PREFIX_TEST_DIR + UUID.randomUUID().toString());
        testDir.mkdir();
        String fileName = PREFIX_TEST_FILE + CamelFileTest.class.getName() + ".txt";
        testFile = new File(testDir, fileName);
    }

    @Before
    public void prepare() {
        this.kieServices = KieServices.Factory.get();
        final KieContainer kieContainer = this.kieServices.newKieClasspathContainer(this.getClass().getClassLoader());
        this.kieSession = kieContainer.newKieSession("camel-workitem-ksession");
        assertNotNull(this.kieSession);
    }

    @Test
    public void testSingleStreamProcess() throws Exception {

        CamelHandler handler = new CamelHandler(new GenericURIMapper("stream"), new RequestPayloadMapper("payload"), new ResponsePayloadMapper(), camelContext);
        kieSession.getWorkItemManager().registerWorkItemHandler("CamelStream", handler);

        // Write into the file using Camel stream
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("payloadVar", TEST_DATA);
        params.put("pathVar", "file");
        params.put("fileNameVar", testFile.getAbsolutePath());

        ProcessInstance pi = kieSession.startProcess("camelStreamProcess", params);
        ProcessInstance result = kieSession.getProcessInstance(pi.getId());
        assertNull(result);

        // Verify the output file exists and has correct content
        assertTrue(testFile.exists());
        String resultText = FileUtils.readFileToString(testFile);
        assertTrue(resultText.contains(TEST_DATA));
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
