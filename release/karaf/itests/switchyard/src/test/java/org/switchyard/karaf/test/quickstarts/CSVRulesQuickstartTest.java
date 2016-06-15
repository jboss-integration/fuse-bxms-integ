package org.switchyard.karaf.test.quickstarts;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.switchyard.quickstart.rules.csv.Person;
import org.switchyard.remote.RemoteInvoker;
import org.switchyard.remote.RemoteMessage;
import org.switchyard.remote.http.HttpInvoker;

public class CSVRulesQuickstartTest extends AbstractQuickstartTest {
    private static String bundleName = "org.jboss.integration.fuse.quickstarts.switchyard.rules.csv";
    private static String featureName = "fuse-bxms-quickstart-switchyard-rules-csv";
    private static final QName SERVICE = new QName("urn:switchyard-quickstart.switchyard:switchyard-rules-csv:1.0", "Service");

    @BeforeClass
    public static void before() throws Exception {
        startTestContainer(featureName, bundleName);
    }

    @Override
    @Ignore
    @Test
    public void testDeployment() throws IOException {
        String port = "8181";
        RemoteInvoker invoker = new HttpInvoker("http://localhost:" + port + "/switchyard-remote");

        createRequestServer(0, invoker);
        createRequestServer(1, invoker);
        createRequestServer(2, invoker);
    }
    public static void createRequestServer(int id, RemoteInvoker invoker) throws IOException {
        // Create request payload
        Person person = new Person();
        person.setId(id);
        // Create the request message
        RemoteMessage message = new RemoteMessage();
        message.setService(SERVICE).setOperation("invoke").setContent(person);

        // Invoke the service
        System.out.println("Request of NEW ID of a person with ID:" + person.getId());
        RemoteMessage remoteResponse = invoker.invoke(message);
        if (remoteResponse != null) {
            System.out.println("RemoteResponse !=null");
            if (remoteResponse.getContent() != null) {
                System.out.println("remoteResponse.getContent=" + remoteResponse.getContent());
            } else {
                System.out.println("getContent=null");
            }
        } else {
            System.out.println("remoteResponse is null");
        }
        Assert.assertTrue((((Person) remoteResponse.getContent()).getId()) == id + 1);
    }
}
