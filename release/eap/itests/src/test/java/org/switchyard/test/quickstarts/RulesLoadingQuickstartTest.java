package org.switchyard.test.quickstarts;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.jboss.arquillian.container.test.api.Deployer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.http.HTTPMixIn;
import org.switchyard.test.quickstarts.util.BRMSArquillianUtil;

import com.google.common.io.CharStreams;

@RunWith(Arquillian.class)
@Ignore
public class RulesLoadingQuickstartTest {
    private static final String HELLO_SERVICE_ADDRESS = "http://localhost:8080/hello-greeting-service";
    private static final String CIAO_SERVICE_ADDRESS = "http://localhost:8080/ciao-greeting-service";
    private static final String ORIGINAL_KIE_MODULE_NAME = "original-project.jar";
    private static final String MODIFIED_KIE_MODULE_NAME = "modified-project.jar";

    @ArquillianResource
    private Deployer deployer;

    @Deployment(testable = false)
    public static JavaArchive createDeployment() {
        try {
            installModule(ORIGINAL_KIE_MODULE_NAME);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return BRMSArquillianUtil.createJarQSDeployment("switchyard-rules-loading");
    }

    static {
        // assemble a Kie module
        ShrinkWrap.create(JavaArchive.class).addAsResource("HelloGreetingService.drl").addAsResource("CiaoGreetingService.drl")
                .addAsManifestResource("kmodule.xml").as(ZipExporter.class).exportTo(new File("target", ORIGINAL_KIE_MODULE_NAME), true);

        // assemble a modified Kie module
        ShrinkWrap.create(JavaArchive.class).addAsResource("HelloGreetingService-modified.drl").addAsResource("CiaoGreetingService-modified.drl")
                .addAsManifestResource("kmodule.xml").as(ZipExporter.class).exportTo(new File("target", MODIFIED_KIE_MODULE_NAME), true);
    }

    private static void touchModule(String name) {
        File module = new File("target", name);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        module.setLastModified(new Date().getTime());
    }

    private static void installModule(String name) throws IOException {
        System.out.println("Installing " + name);
        // it is necessary to touch a module to give a Kie container to know
        // that it is updated
        touchModule(name);
        final Process process = Runtime.getRuntime()
                .exec(String.format("mvn install:install-file -Dfile=target/%s -DpomFile=src/test/resources/pom.xml", name));
        // read stdout/stderr
        String out = CharStreams.toString(new InputStreamReader(process.getInputStream()));
        String err = CharStreams.toString(new InputStreamReader(process.getErrorStream()));
        System.out.println(out);
        System.out.println(err);
    }


    @Test
    public void testHelloService() throws IOException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
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
