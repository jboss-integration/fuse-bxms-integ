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
package org.switchyard.quickstarts.channels;

import javax.xml.namespace.QName;

import org.switchyard.remote.RemoteInvoker;
import org.switchyard.remote.RemoteMessage;

import org.switchyard.test.Invoker;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;

import org.switchyard.remote.http.HttpInvoker;

/**
 * The Class ChannelsClient.
 */
public class ChannelsClient {

    private static final QName SERVICE = new QName("urn:switchyard-quickstart.switchyard:switchyard-drools-channels:1.0", "Channel");

    /**
     * Test process.
     *
     * @throws Exception the exception
     */
    public static void main(String args[]) throws Exception {
        String port = System.getProperty("org.switchyard.component.sca.client.port", "8080");
        RemoteInvoker invoker = new HttpInvoker("http://localhost:" + port + "/switchyard-remote");

        String msg = "message";   
	RemoteMessage message = new RemoteMessage();
        message.setService(SERVICE).setOperation("process").setContent(msg);

	RemoteMessage remoteResponse = invoker.invoke(message);
        if ((remoteResponse != null) && (remoteResponse.isFault())) { 
           System.out.println("Service returned fault message : " + remoteResponse.getContent());
              
           if (remoteResponse.getContent() instanceof Throwable) {
              Throwable.class.cast(remoteResponse.getContent()).printStackTrace();
           } 
        }
    }

}
