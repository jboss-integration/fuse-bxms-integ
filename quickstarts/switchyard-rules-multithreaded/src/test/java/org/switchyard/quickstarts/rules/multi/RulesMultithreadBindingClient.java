package org.switchyard.quickstarts.rules.multi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;

public class RulesMultithreadBindingClient implements Runnable {

    private final String URL_BASE;

    public RulesMultithreadBindingClient(List<Item> items, int threadNumber) {
        super();
        this.THREAD_NUMBER = threadNumber;
        this.items = items;
        String port = System.getProperty("org.switchyard.component.rules.multi.client.port", "8080");
        URL_BASE = "http://localhost:" + port + "/rules-multi/";
    }

    private final String USER_AGENT = "Mozilla/5.0";
    private final static int THREADS = 5;

    private final List<Item> items;

    int THREAD_NUMBER;


	public static void main(String[] args) throws Exception {



        List<List<Item>> items = new ArrayList<List<Item>>();

        List<Item> items0 = new ArrayList<Item>();
        items0.add(new Item(1, "DELLXX", 400));
        items0.add(new Item(2, "LENOVO%20XX", 700));
        items0.add(new Item(3, "SAMSUNG%20XX", 750));
        items0.add(new Item(4, "DELLXX", 400));

        List<Item> items1 = new ArrayList<Item>();
        items1.add(new Item(16, "LENOVO%20YY", 1700));
        items1.add(new Item(17, "SAMSUNG%20YY", 1750));
        items1.add(new Item(18, "DELL%20YY", 1400));

        List<Item> items2 = new ArrayList<Item>();
        items2.add(new Item(6, "LENOVO%20ZZ", 380));
        items2.add(new Item(7, "SAMSUNG%20ZZ", 730));
        items2.add(new Item(8, "DELL%20ZZ", 450));

        List<Item> items3 = new ArrayList<Item>();
        items3.add(new Item(9, "LENOVO%20XY", 50));
        items3.add(new Item(10, "SAMSUNG%20XY", 950));
        items3.add(new Item(11, "DELL%20XY", 400));

        List<Item> items4 = new ArrayList<Item>();
        items4.add(new Item(12, "LENOVO%20ZY", 200));
        items4.add(new Item(13, "SAMSUNG%20ZY", 175));
        items4.add(new Item(14, "DELL%20ZY", 100));

        items.add(items0);
        items.add(items1);
        items.add(items2);
        items.add(items3);
        items.add(items4);

		for (int i = 0; i < THREADS; i++) {
            (new Thread(new RulesMultithreadBindingClient(items.get(i), i))).start();
		}
	}

	@Override
	public void run() {
        if (THREAD_NUMBER == 2) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (THREAD_NUMBER == 3) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (THREAD_NUMBER == 4) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        String urlAddObject = URL_BASE + "warehouse";
        for (Item item : items) {
            String requestURL = urlAddObject + "/" + item.getItemId() + "/" + item.getName() + "/" + item.getPrice();
            HttpPut request = new HttpPut(requestURL);
            request.addHeader("User-Agent", USER_AGENT);
            try {
                HttpClient client = RulesMultiThreadBindingUtils.getClient();
                // System.out.println("PUT REQUEST " + requestURL);
                client.execute(request);
                // System.out.println("Response Code : " +
                // response.getStatusLine().getStatusCode());
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        String url = URL_BASE + "order/bestOption";
		try {
			//HttpClient client = new DefaultHttpClient();

            HttpClient client = RulesMultiThreadBindingUtils.getClient();
			HttpGet request = new HttpGet(url);

			request.addHeader("User-Agent", USER_AGENT);
			HttpResponse response = client.execute(request);
            // System.out.println("Back!");

			//System.out.println("\nSending 'GET' request to URL : " + url);
            // System.out.println("Response Code : " +
            // response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
            System.out.println("THREAD: " + THREAD_NUMBER + " ITEM SELECTED: " + result.toString());

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}





}




