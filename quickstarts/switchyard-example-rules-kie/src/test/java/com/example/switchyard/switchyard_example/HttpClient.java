package com.example.switchyard.switchyard_example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

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
