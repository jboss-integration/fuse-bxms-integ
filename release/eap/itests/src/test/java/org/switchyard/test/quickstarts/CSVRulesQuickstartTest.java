package org.switchyard.test.quickstarts;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.quickstart.rules.csv.Person;
import org.switchyard.remote.RemoteInvoker;
import org.switchyard.remote.RemoteMessage;
import org.switchyard.remote.http.HttpInvoker;
import org.switchyard.test.quickstarts.util.BRMSArquillianUtil;

@RunWith(Arquillian.class)
public class CSVRulesQuickstartTest {

    private static final QName SERVICE = new QName("urn:switchyard-quickstart.switchyard:switchyard-rules-csv:1.0", "Service");

    @Deployment(testable = false)
    public static JavaArchive createDeployment() {
        return BRMSArquillianUtil.createJarQSDeployment("switchyard-rules-csv");
    }

    @Ignore
    @Test
    public void testDeployment() throws IOException {
        RemoteInvoker invoker = new HttpInvoker("http://localhost:8080/switchyard-remote");

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
        Assert.assertTrue((((Person) remoteResponse.getContent()).getId()) == id + 1);
    }
}
