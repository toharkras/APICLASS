package org.example;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

public class Main {
    private CloseableHttpClient client;

    private static final String ACCESS_KEY = "118b75e3194f4476a6ae99f867ecd045";
    private static final String API_URL = "https://data.fixer.io/api/latest";

    public static <JasonObject> void main(String[] args) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();

            URI uri = new URIBuilder("https://data.fixer.io/api/latest").setParameter("access_key","118b75e3194f4476a6ae99f867ecd045" ).
                    setParameter("symbols", "symbol").build();
            HttpGet request = new HttpGet(uri);
            CloseableHttpResponse response= client.execute(request);
          String response1= EntityUtils.toString(response.getEntity());
          JSONObject jsonObject= new JSONObject(response1);
          JSONObject rates= new jsonObject.getJSONObject("rates");
            System.out.println(jsonObject);
          JSONObject symbols= jsonObject.getJSONObject("symbols");
            Iterator<String>keys= jsonObject.keys();

            while (keys.hasNext()){
                String key= keys.next();
                    System.out.println(key+ ":"+ symbols.get(key));
            }
            getExchangeRate("USD");


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String>getAvailableSymbols(){

    }


    public static void getExchangeRate(String symbol) {
        try {
            URI uri = new URIBuilder(API_URL)
                    .setParameter("access_key", ACCESS_KEY)
                    .setParameter("symbols", symbol)
                    .build();

            HttpGet request = new HttpGet(uri);
            CloseableHttpResponse response = client.execute(request);

            try {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = new JSONObject(jsonResponse);
                System.out.println(jsonObject);
            } finally {
                response.close();
            }

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
