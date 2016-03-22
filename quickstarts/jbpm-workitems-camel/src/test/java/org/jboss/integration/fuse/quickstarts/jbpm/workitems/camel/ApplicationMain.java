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
package org.jboss.integration.fuse.quickstarts.jbpm.workitems.camel;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


/**
 * The Class ApplicationMain.
 */
public class ApplicationMain {

    /** The Constant DEFAULT_PORT. */
    public static final String DEFAULT_PORT = "DEFAULT_PORT";

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws ClientProtocolException the client protocol exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void main(String args[]) throws ClientProtocolException, IOException {

        int port = 8181;
        String port_property = System.getProperty(DEFAULT_PORT);
        System.out.println("port_property=" + port_property);
        if (port_property != null && !port_property.equals("")) {
            port = Integer.parseInt(port_property);
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:" + port + "/jbpm-workitems-camel/mortgage");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity1 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            StringWriter writer = new StringWriter();
            IOUtils.copy(entity1.getContent(), writer);
            String content = writer.toString();
            System.out.println("RESPONSE:\n" + content);
        } finally {
            response.close();
        }
    }
}
