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
package com.example.switchyard.rules.csv;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.switchyard.quickstart.rules.csv.Person;
import org.switchyard.remote.RemoteInvoker;
import org.switchyard.remote.RemoteMessage;
import org.switchyard.remote.http.HttpInvoker;


/**
 * The Class DTableCSVClient.
 */
public class DTableCSVClient {

    /** The Constant SERVICE. */
    private static final QName SERVICE = new QName("urn:switchyard-quickstart.switchyard:switchyard-rules-csv:1.0", "Service");

    /**
     * Private no-args constructor.
     */
    private DTableCSVClient() {
    }

    /**
     * Only execution point for this application.
     * 
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

    /**
     * Creates the request server.
     *
     * @param id the id
     * @param invoker the invoker
     * @throws IOException Signals that an I/O exception has occurred.
     */
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