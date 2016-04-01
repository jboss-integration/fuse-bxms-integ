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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * The Class HttpClient.
 */
public class HttpClient {

    /**
     * Send.
     *
     * @param message the message
     * @param url the url
     * @param method the method
     * @return the string
     * @throws Exception the exception
     */
    public String send(String message, String url, String method) throws Exception {
        URL location = new URL(url);
        HttpURLConnection client = (HttpURLConnection) location.openConnection();
        int respCode = -1;
        client.setRequestMethod(method);
        client.setDoInput(true);

        int len = message.length();
        client.setRequestProperty("Content-Length", Integer.toString(len));

        if (message.length() > 0) {
            client.setDoOutput(true);
        }

        client.connect();

        if (message.length() > 0) {
            OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream());
            out.write(message, 0, len);
            out.flush();

            client.getOutputStream().close();
        }

        respCode = client.getResponseCode();

        InputStream response = null;
        if (respCode < 400) {
            response = client.getInputStream();
        } else {
            response = client.getErrorStream();
        }

        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader rd = new BufferedReader(new InputStreamReader(response));

        while ((line = rd.readLine()) != null) {
            sb.append(line + '\n');
        }

        response.close();
        rd.close();
        client.disconnect();

        return sb.toString();

    }
}
