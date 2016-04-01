/*
 * Copyright 2016 Red Hat Inc. and/or its affiliates and other contributors.
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
package org.switchyard.quickstarts.rules.loading;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.jboss.arquillian.container.test.api.Deployer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.http.HTTPMixIn;

import com.google.common.io.CharStreams;


/**
 * The Class GreetingServiceTest.
 */
@RunAsClient
@RunWith(Arquillian.class)
@Ignore
public class GreetingServiceTest {

    /** The Constant HELLO_SERVICE_ADDRESS. */
    private static final String HELLO_SERVICE_ADDRESS = "http://localhost:8080/hello-greeting-service";
    
    /** The Constant CIAO_SERVICE_ADDRESS. */
    private static final String CIAO_SERVICE_ADDRESS = "http://localhost:8080/ciao-greeting-service";
    
    /** The Constant ORIGINAL_KIE_MODULE_NAME. */
    private static final String ORIGINAL_KIE_MODULE_NAME = "original-project.jar";
    
    /** The Constant MODIFIED_KIE_MODULE_NAME. */
    private static final String MODIFIED_KIE_MODULE_NAME = "modified-project.jar";
    
    /** The Constant KIE_SCANNER_TEST. */
    private static final String KIE_SCANNER_TEST = "switchyard-rules-loading";
    
    /** The deployer. */
    @ArquillianResource
    private Deployer deployer;

    /**
     * Creates the deployment.
     *
     * @return the java archive
     */
    @Deployment(name = KIE_SCANNER_TEST, managed = false, testable = false)
    public static JavaArchive createDeployment() {
        return ShrinkWrap.createFromZipFile(JavaArchive.class, new File("target", String.format("%s.jar", KIE_SCANNER_TEST)))
                .addClasses(HttpClient.class);
    }

    static {
        // assemble a Kie module
        ShrinkWrap.create(JavaArchive.class).addAsResource("HelloGreetingService.drl").addAsResource("CiaoGreetingService.drl")
                .addAsManifestResource("kmodule.xml").as(ZipExporter.class)
                .exportTo(new File("target", ORIGINAL_KIE_MODULE_NAME), true);

        // assemble a modified Kie module
        ShrinkWrap.create(JavaArchive.class).addAsResource("HelloGreetingService-modified.drl")
                .addAsResource("CiaoGreetingService-modified.drl").addAsManifestResource("kmodule.xml").as(ZipExporter.class)
                .exportTo(new File("target", MODIFIED_KIE_MODULE_NAME), true);
    }

    /**
     * Touch module.
     *
     * @param name the name
     */
    private static void touchModule(String name) {
        File module = new File("target", name);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        module.setLastModified(new Date().getTime());
    }

    /**
     * Install module.
     *
     * @param name the name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void installModule(String name) throws IOException {
        System.out.println("Installing " + name);
        // it is necessary to touch a module to give a Kie container to know
        // that it is updated
        touchModule(name);
        final Process process = Runtime.getRuntime().exec(
                String.format("mvn install:install-file -Dfile=target/%s -DpomFile=src/main/resources/pom.xml", name));
        // read stdout/stderr
        String out = CharStreams.toString(new InputStreamReader(process.getInputStream()));
        String err = CharStreams.toString(new InputStreamReader(process.getErrorStream()));
        System.out.println(out);
        System.out.println(err);
    }

    /**
     * Before method.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    @Before
    public void beforeMethod() throws IOException, InterruptedException {
        installModule(ORIGINAL_KIE_MODULE_NAME);
        deployer.deploy(KIE_SCANNER_TEST);
    }

    /**
     * After method.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    @After
    public void afterMethod() throws IOException, InterruptedException {
        deployer.undeploy(KIE_SCANNER_TEST);
    }

    /**
     * Test hello service.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testHelloService() throws IOException {
        final HTTPMixIn http = new HTTPMixIn();
        http.initialize();
        String response = http.sendString(HELLO_SERVICE_ADDRESS, "first", HTTPMixIn.HTTP_POST);
        Assert.assertEquals("Hello first!", response);
        response = http.sendString(CIAO_SERVICE_ADDRESS, "second", HTTPMixIn.HTTP_POST);
        Assert.assertEquals("Ciao second!", response);

        installModule(MODIFIED_KIE_MODULE_NAME);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response = http.sendString(HELLO_SERVICE_ADDRESS, "first", HTTPMixIn.HTTP_POST);
        Assert.assertEquals("Modified Hello first!", response);
        // PROBLEM: "Ciao second!" is received instead of "Modified Ciao second"
        response = http.sendString(CIAO_SERVICE_ADDRESS, "second", HTTPMixIn.HTTP_POST);
        Assert.assertEquals("Modified Ciao second!", response);
    }
}
