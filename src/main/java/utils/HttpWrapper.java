package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import objects.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import org.apache.http.entity.StringEntity;

import java.util.*;

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
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return response.getStatusLine().getReasonPhrase().equalsIgnoreCase("OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO get products per availability

    public String availability(String id) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(uri + "/getFirstProducts/"+"?uid="+id);
            request.addHeader(HttpHeaders.USER_AGENT, "JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return result;
            }
        } finally {
            httpClient.close();
        }
        return "Error";
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
            Iterator<JsonElement> it = list.iterator();
            while (it.hasNext()) {
                JsonArray prodottoElement = it.next().getAsJsonArray();
                Prodotto prodotto = new Prodotto();
                prodotto.setNome(prodottoElement.get(0).getAsString());
                prodotto.setMarca(prodottoElement.get(1).getAsString());
                prodotto.setCaratteristiche(CaratteristicheProdotto.valueOf(prodottoElement.get(2).getAsString()));
                prodotto.setCategoria(Categoria.valueOf(prodottoElement.get(3).getAsString()));
                prodotto.setPrezzo(prodottoElement.get(4).getAsFloat());
                prodotto.setImmagine(prodottoElement.get(5).getAsString());
                prodotto.setDisponibilita(prodottoElement.get(6).getAsInt());
                prodottoList.add(prodotto);
            }
            return prodottoList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO get products per tag

    public String tag(String tag, String id) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(uri + "/getProdByTag/"+tag);
            request.addHeader(HttpHeaders.USER_AGENT, "JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return result;
            }
        } finally {
            httpClient.close();
        }
        return "error";
    }

    //TODO order sending, return boolean, parameters Ordine

    //TODO products adding
    public boolean addProdotto(String userId, Prodotto prodotto) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri + "/add?uid=" + userId);
        try {
            HttpEntity httpEntity = new StringEntity(Manager.objectToJson(prodotto));
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
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
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return result;
            }
        } finally {
            httpClient.close();
        }
        return "Error";
    }

    //TODO request all user's orders
    public List<String> getAllOrdersDate(String userId) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/getAllOrdersDate/"+userId);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            String jsonResponse = EntityUtils.toString(responseEntity);
            List<String> orderDate = new ArrayList<>();
            JsonArray list = JsonParser.parseString(jsonResponse).getAsJsonArray();
            Iterator<JsonElement> it = list.iterator();
            while (it.hasNext()) {
                JsonArray orderElement = it.next().getAsJsonArray();
                orderDate.add(orderElement.get(0).getAsString());
            }
            return orderDate;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Ordine getAllProductsByOrder(String userId, String date){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/getAllProdByOrder/"+userId+"?date="+date);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            String jsonResponse = EntityUtils.toString(responseEntity);
            List<Prodotto> prodottoList = new ArrayList<>();
            JsonArray list = JsonParser.parseString(jsonResponse).getAsJsonArray();
            Iterator<JsonElement> it = list.iterator();
            while (it.hasNext()) {
                JsonArray prodottoElement = it.next().getAsJsonArray();
                Prodotto prodotto = new Prodotto();
                prodotto.setId(prodottoElement.get(0).getAsInt());
                prodotto.setNome(prodottoElement.get(1).getAsString());
                prodotto.setDisponibilita(prodottoElement.get(2).getAsInt());
                //prodotto.setCaratteristiche(CaratteristicheProdotto.valueOf(prodottoElement.get(4).getAsString()));
                prodotto.setPrezzo(prodottoElement.get(3).getAsFloat());
                prodotto.setImmagine(prodottoElement.get(4).getAsString());
                prodotto.setCategoria(Categoria.valueOf(prodottoElement.get(6).getAsString()));
                prodotto.setMarca(prodottoElement.get(7).getAsString());
                prodottoList.add(prodotto);
            }
            Ordine ordine = new Ordine();
            ordine.setData(date);
            ordine.setProdotti(prodottoList);
            return ordine;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
