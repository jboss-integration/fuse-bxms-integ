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

public class ApplicationMain {

    public static final String DEFAULT_PORT = "DEFAULT_PORT";

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
