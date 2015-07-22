package com.example.switchyard.rules.csv;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.switchyard.quickstart.rules.csv.Person;
import org.switchyard.remote.RemoteInvoker;
import org.switchyard.remote.RemoteMessage;
import org.switchyard.remote.http.HttpInvoker;

public class DTableCSVClient {
    private static final QName SERVICE = new QName("urn:switchyard-quickstart.switchyard:switchyard-rules-csv:1.0", "Service");

    /**
     * Private no-args constructor.
     */
    private DTableCSVClient() {
    }

    /**
     * Only execution point for this application.
     * @param ignored not used.
     * @throws Exception if something goes wrong.
     */
    public static void main(final String[] ignored) throws Exception {
        // Create a new remote client invoker
        String port = System.getProperty("org.switchyard.component.sca.client.port", "8080");
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
        System.out.println("NEW ID of Person is: " + ((Person) remoteResponse.getContent()).getId());
    }
}