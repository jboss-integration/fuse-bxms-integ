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
package org.jboss.integration.fuse.camel.test;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.test.quickstarts.util.BRMSArquillianUtil;



@RunWith(Arquillian.class)
public class KieCamelJbpmWorkitemsQuickstartTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return BRMSArquillianUtil.createWarQSDeployment("jbpm-workitems-camel-quickstart");
    }

    @Test
    public void test() throws Exception {
        int port = 8080;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:" + port + "/jbpm-workitems-camel/mortgage");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            System.out.println(response.getStatusLine());
            assertTrue(response.getStatusLine().getStatusCode() == 200);
            HttpEntity entity1 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            StringWriter writer = new StringWriter();
            IOUtils.copy(entity1.getContent(), writer);
            String content = writer.toString();
            System.out.println("RESPONSE:\n" + content);
            assertTrue(content.contains("Accepted Applications"));
            assertTrue(content.contains("Rejected Applications"));

        } finally {
            response.close();
        }
    }
}
