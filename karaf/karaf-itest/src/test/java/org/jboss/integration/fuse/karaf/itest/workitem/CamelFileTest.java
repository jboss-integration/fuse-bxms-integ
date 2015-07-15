/*
 * Copyright 2015 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.integration.fuse.karaf.itest.workitem;

import org.apache.commons.io.FileUtils;
import org.drools.core.process.instance.WorkItem;
import org.drools.core.process.instance.impl.DefaultWorkItemManager;
import org.drools.core.process.instance.impl.WorkItemImpl;
import org.jbpm.process.workitem.camel.CamelHandler;
import org.jbpm.process.workitem.camel.CamelHandlerFactory;
import org.jbpm.process.workitem.camel.request.RequestPayloadMapper;
import org.jbpm.process.workitem.camel.uri.FileURIMapper;
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
import org.kie.api.runtime.process.WorkItemManager;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.inject.Inject;
import org.apache.karaf.features.FeaturesService;

import org.jboss.integration.fuse.karaf.itest.KarafConfigUtil;
import org.ops4j.pax.exam.Configuration;

/**
 * Uploading a file via File endpoint using Camel in Karaf.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class CamelFileTest {
    
    private static File tempDir;
    private static File testDir;
    private static File testFile;

    private KieServices kieServices;

    private KieSession kieSession;
    
    @Inject
    FeaturesService fs;
    
    @Configuration
    public static Option[] configure() {
        return KarafConfigUtil.karafConfiguration();
    }
    
    @BeforeClass
    public static void initialize() {
        tempDir = new File(System.getProperty("java.io.tmpdir"));
        testDir = new File(tempDir, "test_dir");
        String fileName = "test_file_" + CamelFileTest.class.getName() + "_" + UUID.randomUUID().toString();
        testFile = new File(tempDir, fileName);
    }

    @AfterClass
    public static void clean() throws IOException {
        FileUtils.deleteDirectory(testDir);
    }
    
    @Before
    public void prepare() {
        this.kieServices = KieServices.Factory.get();
        final KieContainer kieContainer = this.kieServices.newKieClasspathContainer(this.getClass().getClassLoader());
        this.kieSession = kieContainer.newKieSession("camel-workitem-ksession");
        assertNotNull(this.kieSession);
    }

    @After
    public void cleanup() {
        if (this.kieSession != null) {
            this.kieSession.dispose();
        }
    }

    /**
     * Test with entire BPMN process.
     * @throws java.io.IOException
     */
    @Test
    public void testSingleFileProcess() throws IOException {
        final String testData = "test-data";

        CamelHandler handler = CamelHandlerFactory.fileHandler();
        kieSession.getWorkItemManager().registerWorkItemHandler("CamelFile", handler);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("payloadVar", testData);
        params.put("pathVar", tempDir.getAbsolutePath());
        params.put("fileNameVar", testFile.getName());

        ProcessInstance pi = kieSession.startProcess("camelFileProcess", params);

        ProcessInstance result = kieSession.getProcessInstance(pi.getId());
        assertNull(result);

        assertTrue(testFile.exists());

        String resultText = FileUtils.readFileToString(testFile);
        assertEquals(testData,resultText);
    }

    /**
     * File to upload has been specified by Camel header.
     *
     * @throws java.io.IOException */
    @Test
    public void testSingleFileWithHeaders() throws IOException {
        Set<String> headers = new HashSet<String>();
        headers.add("CamelFileName");
        CamelHandler handler = new CamelHandler(new FileURIMapper(), new RequestPayloadMapper("payload", headers));

        final String testData = "test-data";
        final WorkItem workItem = new WorkItemImpl();
        workItem.setParameter("path", tempDir.getAbsolutePath());
        workItem.setParameter("payload", testData);
        workItem.setParameter("CamelFileName", testFile.getName());

        WorkItemManager manager = new DefaultWorkItemManager(null);
        handler.executeWorkItem(workItem, manager);

        assertTrue(testFile.exists());

        String resultText = FileUtils.readFileToString(testFile);
        assertEquals(testData,resultText);
    }
}
