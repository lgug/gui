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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class HttpWrapper {
    private String uri = "http://127.0.0.1:5000";

    public String login(String email, String password) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(uri + "/login?"+"email=" + email + "&"+"password=" + password);
            request.addHeader(HttpHeaders.USER_AGENT, "JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                System.out.println(result);
                return result;
            }
        } finally {
            httpClient.close();
        }
        return "Error";
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
            HttpGet request = new HttpGet(uri + "/getFirstProducts/"+id);
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
    public List<Prodotto> getFirst10Prod() throws IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/getFirstProducts");
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
                prodotto.setPrezzo(prodottoElement.get(3).getAsFloat());
                prodotto.setImmagine(prodottoElement.get(4).getAsString());
                prodotto.setCaratteristiche(CaratteristicheProdotto.valueOf(prodottoElement.get(5).getAsString()));
                prodotto.setCategoria(Categoria.valueOf(prodottoElement.get(6).getAsString()));
                prodotto.setMarca(prodottoElement.get(7).getAsString());
                prodotto.setQuantita(1);

                prodottoList.add(prodotto);
            }
            return prodottoList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO get products per name
    public List<Prodotto> getProductsPerName(String prodName) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/getProdByName/"+prodName);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            String jsonResponse = EntityUtils.toString(responseEntity);
            List<Prodotto> prodottoList = new ArrayList<>();
            if(jsonResponse.equalsIgnoreCase("PRODOTTO INESISTENTE")){
                return prodottoList;
            }
            else {
                JsonArray list = JsonParser.parseString(jsonResponse).getAsJsonArray();
                Iterator<JsonElement> it = list.iterator();
                while (it.hasNext()) {
                    JsonArray prodottoElement = it.next().getAsJsonArray();
                    Prodotto prodotto = new Prodotto();
                    prodotto.setId(prodottoElement.get(0).getAsInt());
                    prodotto.setNome(prodottoElement.get(1).getAsString());
                    prodotto.setDisponibilita(prodottoElement.get(2).getAsInt());
                    prodotto.setPrezzo(prodottoElement.get(3).getAsFloat());
                    prodotto.setImmagine(prodottoElement.get(4).getAsString());
                    prodotto.setCaratteristiche(CaratteristicheProdotto.valueOf(prodottoElement.get(5).getAsString()));
                    prodotto.setCategoria(Categoria.valueOf(prodottoElement.get(6).getAsString()));
                    prodotto.setMarca(prodottoElement.get(7).getAsString());
                    prodottoList.add(prodotto);
                    return prodottoList;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO get products per category
    public List<Prodotto> getProductByCategory(String userId, Categoria... categories) {
        StringBuilder categoriesString = new StringBuilder();
        Arrays.asList(categories).forEach(categoria -> categoriesString.append(categoria.toRealString()).append(";"));
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
                prodotto.setId(prodottoElement.get(0).getAsInt());
                prodotto.setNome(prodottoElement.get(1).getAsString());
                prodotto.setDisponibilita(prodottoElement.get(2).getAsInt());
                prodotto.setPrezzo(prodottoElement.get(3).getAsFloat());
                prodotto.setImmagine(prodottoElement.get(4).getAsString());
                prodotto.setCaratteristiche(CaratteristicheProdotto.valueOf(prodottoElement.get(5).getAsString()));
                prodotto.setCategoria(Categoria.valueOf(prodottoElement.get(6).getAsString()));
                prodotto.setMarca(prodottoElement.get(7).getAsString());
                prodottoList.add(prodotto);
            }
            return prodottoList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO get products per tag

    public List<Prodotto> tag(String id, CaratteristicheProdotto... tag) throws IOException {
        StringBuilder categoriesString = new StringBuilder();
        Arrays.asList(tag).forEach(tags -> categoriesString.append(tags.realToString()).append(";"));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/getProdByTag/" + categoriesString.toString() + "?uid=" + id);
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
                prodotto.setPrezzo(prodottoElement.get(3).getAsFloat());
                prodotto.setImmagine(prodottoElement.get(4).getAsString());
                prodotto.setCaratteristiche(CaratteristicheProdotto.valueOf(prodottoElement.get(5).getAsString()));
                prodotto.setCategoria(Categoria.valueOf(prodottoElement.get(6).getAsString()));
                prodotto.setMarca(prodottoElement.get(7).getAsString());
                prodottoList.add(prodotto);
            }
            return prodottoList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO order sending, return boolean, parameters Ordine
    public String addOrdine(String userId, Ordine ordine) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri + "/buyOrder/"+userId);
        try {
            HttpEntity httpEntity = new StringEntity(Manager.objectToJson(ordine));
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }

    public boolean addUnitOfProdotto(String userId, int pid) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/addQuantity/" + pid+"?uid="+userId);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return response.getStatusLine().getReasonPhrase().equalsIgnoreCase("OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeUnitOfProdotto(String userId, int pid) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/removeQuantity/"+pid+"?uid="+userId);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return response.getStatusLine().getReasonPhrase().equalsIgnoreCase("OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

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
    public String remove(String uid, int pid) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost request = new HttpPost(uri+"/removeProdByName/" + pid + "?uid="+uid);
            request.addHeader(HttpHeaders.USER_AGENT, "JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    //TODO request all user's orders
    public List<Long> getAllOrdersDate(String userId) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/getAllOrdersDate/"+userId);

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            String jsonResponse = EntityUtils.toString(responseEntity);
            List<Long> orderDate = new ArrayList<>();
            JsonArray list = JsonParser.parseString(jsonResponse).getAsJsonArray();
            Iterator<JsonElement> it = list.iterator();
            while (it.hasNext()) {
                JsonArray orderElement = it.next().getAsJsonArray();
                orderDate.add(orderElement.get(0).getAsLong());
            }
            return orderDate;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Ordine getAllProductsByOrder(String userId, Long date){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/getAllProdByOrder/"+userId+"?date="+date);
        HttpGet httpGet2 = new HttpGet(uri+"/getOrderID/"+userId+"?date="+date);
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
                prodotto.setPrezzo(prodottoElement.get(3).getAsFloat());
                prodotto.setImmagine(prodottoElement.get(4).getAsString());
                prodotto.setCaratteristiche(CaratteristicheProdotto.valueOf(prodottoElement.get(5).getAsString()));
                prodotto.setCategoria(Categoria.valueOf(prodottoElement.get(6).getAsString()));
                prodotto.setMarca(prodottoElement.get(7).getAsString());
                prodotto.setQuantita(prodottoElement.get(8).getAsInt());
                prodottoList.add(prodotto);
            }
            response.close();
            CloseableHttpResponse resp = httpClient.execute(httpGet2);
            HttpEntity respEntity = resp.getEntity();
            String jsonResponse2 = EntityUtils.toString(respEntity);
            JsonArray list2 = JsonParser.parseString(jsonResponse2).getAsJsonArray();
            Ordine ordine = new Ordine();
            ordine.setData(date);
            ordine.setProdotto(prodottoList);
            JsonArray listina = list2.get(0).getAsJsonArray();
            ordine.setID(listina.get(0).getAsString());
            ordine.setDataConsegna(listina.get(1).getAsLong());
            return ordine;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utente getUserByID(String userId, Class<? extends Utente> utenteClass) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/getUserInfo/"+userId);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            String jsonResponse = EntityUtils.toString(responseEntity);

            JsonArray list = JsonParser.parseString(jsonResponse).getAsJsonArray();
            JsonArray utenteinfo = list.get(0).getAsJsonArray();
            JsonArray indirizzoinfo = list.get(1).getAsJsonArray();

            if (utenteClass.equals(UtenteCliente.class)) {
                UtenteCliente utente = new UtenteCliente();

                if (list.size() == 3) {
                    JsonArray tesserainfo = list.get(2).getAsJsonArray();
                    TesseraFedelta tessera = new TesseraFedelta();
                    tessera.setId(tesserainfo.get(0).getAsString());
                    tessera.setDataEmissione(tesserainfo.get(1).getAsLong());
                    tessera.setSaldoPunti(tesserainfo.get(2).getAsInt());
                    utente.setTesseraFedelta(tessera);
                }

                utente.setId(utenteinfo.get(0).getAsString());
                utente.setNome(utenteinfo.get(1).getAsString());
                utente.setCognome(utenteinfo.get(2).getAsString());
                utente.setTelefono(utenteinfo.get(3).getAsString());
                utente.setPagamento(FormaDiPagamento.valueOf(utenteinfo.get(4).getAsString()));
                utente.setDatiDelPagamento(utenteinfo.get(5).getAsString());
                utente.setEmail(utenteinfo.get(6).getAsString());
                utente.setPassword(utenteinfo.get(7).getAsString());

                Indirizzo indirizzo = new Indirizzo();
                indirizzo.setVia(indirizzoinfo.get(1).getAsString());
                indirizzo.setCap(indirizzoinfo.get(2).getAsString());
                indirizzo.setLocalita(indirizzoinfo.get(3).getAsString());
                indirizzo.setProvincia(indirizzoinfo.get(4).getAsString());
                indirizzo.setPaese(indirizzoinfo.get(5).getAsString());
                indirizzo.setCivico(indirizzoinfo.get(6).getAsString());
                utente.setIndirizzo(indirizzo);
                return utente;
            } else if (utenteClass.equals(UtenteResponsabile.class)) {
                UtenteResponsabile utente = new UtenteResponsabile();

                utente.setId(utenteinfo.get(0).getAsString());
                utente.setNome(utenteinfo.get(1).getAsString());
                utente.setCognome(utenteinfo.get(2).getAsString());
                utente.setTelefono(utenteinfo.get(3).getAsString());
                utente.setEmail(utenteinfo.get(6).getAsString());
                utente.setPassword(utenteinfo.get(7).getAsString());
                utente.setMatricola(utenteinfo.get(8).getAsString());
                utente.setRuolo(utenteinfo.get(9).getAsString().equals("<null>") ?
                        null : RuoloResponsabile.valueOf(utenteinfo.get(9).getAsString()));

                Indirizzo indirizzo = new Indirizzo();
                indirizzo.setVia(indirizzoinfo.get(1).getAsString());
                indirizzo.setCap(indirizzoinfo.get(2).getAsString());
                indirizzo.setLocalita(indirizzoinfo.get(3).getAsString());
                indirizzo.setProvincia(indirizzoinfo.get(4).getAsString());
                indirizzo.setPaese(indirizzoinfo.get(5).getAsString());
                indirizzo.setCivico(indirizzoinfo.get(6).getAsString());
                utente.setIndirizzo(indirizzo);
                return utente;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Prodotto> getProductsPerBrand(String brandName,String uid) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/getProdByBrand/"+brandName+"?uid="+uid);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            String jsonResponse = EntityUtils.toString(responseEntity);
            List<Prodotto> prodottoList = new ArrayList<>();
            if(jsonResponse.equalsIgnoreCase("MARCA INESISTENTE")){
                return prodottoList;
            }
            else {
                JsonArray list = JsonParser.parseString(jsonResponse).getAsJsonArray();
                Iterator<JsonElement> it = list.iterator();
                while (it.hasNext()) {
                    JsonArray prodottoElement = it.next().getAsJsonArray();
                    Prodotto prodotto = new Prodotto();
                    prodotto.setId(prodottoElement.get(0).getAsInt());
                    prodotto.setNome(prodottoElement.get(1).getAsString());
                    prodotto.setDisponibilita(prodottoElement.get(2).getAsInt());
                    prodotto.setPrezzo(prodottoElement.get(3).getAsFloat());
                    prodotto.setImmagine(prodottoElement.get(4).getAsString());
                    prodotto.setCaratteristiche(CaratteristicheProdotto.valueOf(prodottoElement.get(5).getAsString()));
                    prodotto.setCategoria(Categoria.valueOf(prodottoElement.get(6).getAsString()));
                    prodotto.setMarca(prodottoElement.get(7).getAsString());
                    prodottoList.add(prodotto);
                    return prodottoList;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addTessera(TesseraFedelta tessera, String uid){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri + "/add?uid=" + uid);
        try {
            HttpEntity httpEntity = new StringEntity(Manager.objectToJson(tessera));
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return response.getStatusLine().getReasonPhrase().equalsIgnoreCase("OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addTesseraPoint(String idt,int punti){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri + "/addPoint/"+idt+"?punti="+String.valueOf(punti));
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return response.getStatusLine().getReasonPhrase().equalsIgnoreCase("OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
