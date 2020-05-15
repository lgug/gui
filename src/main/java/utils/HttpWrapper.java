package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import objects.CaratteristicheProdotto;
import objects.Categoria;
import objects.Prodotto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.IOException;

import objects.Utente;
import org.apache.http.entity.StringEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HttpWrapper {
    private String uri = "http://127.0.0.1:5000";
    private String r = "";

    public String login(String username, String password) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            HttpGet request = new HttpGet(uri + "/login?" + username + "&" + password);
            request.addHeader(HttpHeaders.USER_AGENT, "JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }
        } finally {
            httpClient.close();
        }
        return r;
    }

    //TODO user access method. return string, parameters email, password

    public boolean sendNewUser(Utente utente) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri + "/register/" + utente.getId());
        try {
            HttpEntity httpEntity = new StringEntity(Manager.objectToJson(utente));
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return response.getStatusLine().getReasonPhrase().equalsIgnoreCase("OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO get products per availability

    public String availability() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(uri + "/getFirstProducts");
            request.addHeader(HttpHeaders.USER_AGENT, "JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } finally {
            httpClient.close();
        }
    }

    //TODO get products per name
    public String getProductsPerName(String prodName,String id) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            HttpGet request = new HttpGet(uri+"/getProdByName/"+prodName+"?uid="+id);
            request.addHeader(HttpHeaders.USER_AGENT,"JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                System.out.println(result);
                return result;
            }
        }
        finally{
            httpClient.close();
        }
        return "Error";
    }

    //TODO get products per category
    public List<Prodotto> getProductByCategory(String userId, Categoria... categories) {
        StringBuilder categoriesString = new StringBuilder();
        Arrays.asList(categories).forEach(categoria -> categoriesString.append(categoria.toString()).append(";"));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/getProdByCat/" + categoriesString.toString() + "?uid=" + userId);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            String jsonResponse = EntityUtils.toString(responseEntity);

            List<Prodotto> prodottoList = new ArrayList<>();
            JsonArray list = JsonParser.parseString(jsonResponse).getAsJsonArray();
            while (list.iterator().hasNext()) {
                JsonArray prodottoElement = list.iterator().next().getAsJsonArray();
                Prodotto prodotto = new Prodotto();
                prodotto.setNome(prodottoElement.get(0).getAsString());
                prodotto.setMarca(prodottoElement.get(1).getAsString());
                prodotto.setCaratteristiche(CaratteristicheProdotto.valueOf(prodottoElement.get(2).getAsString()));
                prodotto.setCategoria(Categoria.valueOf(prodottoElement.get(3).getAsString()));
                prodotto.setPrezzo(prodottoElement.get(4).getAsFloat());
                prodotto.setImmagine(prodottoElement.get(5).getAsString());
                prodottoList.add(prodotto);
            }
            return prodottoList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO get products per tag

    public String tag(String tag) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(uri + "/getProdByTag/"+tag);
            request.addHeader(HttpHeaders.USER_AGENT, "JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } finally {
            httpClient.close();
        }
    }

    //TODO order sending, return boolean, parameters Ordine

    //TODO products adding
    public boolean addProdotto(String userId, Prodotto prodotto) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri + "/add?uid=" + userId);
        try {
            HttpEntity httpEntity = new StringEntity(Manager.objectToJson(prodotto));
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return response.getStatusLine().getReasonPhrase().equalsIgnoreCase("OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO products removing
    public String remove(String name,String uid) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost request = new HttpPost(uri+"/removeProdByName/"+name+"?uid="+uid);
            request.addHeader(HttpHeaders.USER_AGENT, "JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } finally {
            httpClient.close();
        }
    }

    //TODO request all user's orders

}
