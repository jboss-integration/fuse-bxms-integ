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

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Date;

import org.switchyard.component.test.mixins.http.HTTPMixIn;
import com.google.common.io.CharStreams;

/**
 * The Class HttpClient.
 */
public class HttpClient {

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

    public static void main (String ignored[]) {
        try { 
            installModule(ORIGINAL_KIE_MODULE_NAME);
            System.out.println("Waiting 10s for deploy...");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        String value = System.getProperty("install-kiemodule");
        if ((value !=null) && ("true".equals(value))) {
            return;
        }

        final HTTPMixIn http = new HTTPMixIn();
        http.initialize();
        String response = http.sendString(HELLO_SERVICE_ADDRESS, "first", HTTPMixIn.HTTP_POST);
        System.out.println("Response should equal 'Hello first!' : " + response);
        response = http.sendString(CIAO_SERVICE_ADDRESS, "second", HTTPMixIn.HTTP_POST);
        System.out.println("Response should equal 'Ciao second!' : " + response);

        try {
            installModule(MODIFIED_KIE_MODULE_NAME);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        response = http.sendString(HELLO_SERVICE_ADDRESS, "first", HTTPMixIn.HTTP_POST);
        System.out.println("Response should equal 'Modified Hello first!' : " + response);
        response = http.sendString(CIAO_SERVICE_ADDRESS, "second", HTTPMixIn.HTTP_POST);
        System.out.println("Response should equal 'Modified Ciao second!' : " + response);
    }

}
