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
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class HttpWrapper {

    private final String uri = "http://0.0.0.0:9440";

    public String login(String email, String password) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/login");
            uriBuilder.setParameters(new BasicNameValuePair("email", email), new BasicNameValuePair("password", password));

            HttpGet request = new HttpGet(uriBuilder.build());
            request.addHeader(HttpHeaders.USER_AGENT, "JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                System.out.println(result);
                return result;
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public boolean sendNewUser(Utente utente) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/register/" + utente.getId());

            HttpPost httpPost = new HttpPost(uriBuilder.build());
            HttpEntity httpEntity = new StringEntity(Manager.objectToJson(utente));
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return response.getStatusLine().getReasonPhrase().equalsIgnoreCase("OK");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String availability(String id) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/getFirstProducts/" + id);

            HttpGet request = new HttpGet(uriBuilder.build());
            request.addHeader(HttpHeaders.USER_AGENT, "JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public List<Prodotto> getFirst10Prod() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/getFirstProducts");

            HttpGet httpGet = new HttpGet(uriBuilder.build());
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
            return prodottoList;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Prodotto> getProductsPerName(String prodName) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/getProdByName/" + prodName);

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            String jsonResponse = EntityUtils.toString(responseEntity);
            List<Prodotto> prodottoList = new ArrayList<>();
            if(jsonResponse.equalsIgnoreCase("PRODOTTO INESISTENTE")){
                return prodottoList;
            }
            else {
                JsonArray list = JsonParser.parseString(jsonResponse).getAsJsonArray();
                for (JsonElement jsonElement : list) {
                    JsonArray prodottoElement = jsonElement.getAsJsonArray();
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
                return prodottoList;
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Prodotto> getProductByCategory(String userId, Categoria... categories) {
        StringBuilder categoriesString = new StringBuilder();
        Arrays.asList(categories).forEach(categoria -> categoriesString.append(categoria.toRealString()).append(";"));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/getProdByCat/" + categoriesString.toString());

            HttpGet httpGet = new HttpGet(uriBuilder.build());
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
            return prodottoList;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Prodotto> tag(String id, CaratteristicheProdotto... tag) {
        StringBuilder categoriesString = new StringBuilder();
        Arrays.asList(tag).forEach(tags -> categoriesString.append(tags.realToString()).append(";"));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/getProdByTag/" + categoriesString.toString());


            HttpGet httpGet = new HttpGet(uriBuilder.build());
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
            return prodottoList;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String addOrdine(String userId, Ordine ordine) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/buyOrder/" + userId);

            HttpPost httpPost = new HttpPost(uriBuilder.build());
            HttpEntity httpEntity = new StringEntity(Manager.objectToJson(ordine));
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return "false";
    }

    public boolean addUnitOfProdotto(String userId, int pid) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/addQuantity/" + pid);
            uriBuilder.setParameter("uid", userId);

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return response.getStatusLine().getReasonPhrase().equalsIgnoreCase("OK");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeUnitOfProdotto(String userId, int pid) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/removeQuantity/" + pid);
            uriBuilder.setParameter("uid", userId);

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return response.getStatusLine().getReasonPhrase().equalsIgnoreCase("OK");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addProdotto(String userId, Prodotto prodotto) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/addProduct/"+ userId);

            HttpPost httpPost = new HttpPost(uriBuilder.build());
            HttpEntity httpEntity = new StringEntity(Manager.objectToJson(prodotto));
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setHeader(HttpHeaders.ACCEPT_CHARSET, "utf-16");
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return response.getStatusLine().getReasonPhrase().equalsIgnoreCase("OK");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String remove(String uid, int pid) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/removeProd/" + pid);
            uriBuilder.setParameter("uid", uid);

            HttpPost request = new HttpPost(uriBuilder.build());
            request.addHeader(HttpHeaders.USER_AGENT, "JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public List<Long> getAllOrdersDate(String userId) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/getAllOrdersDate/" + userId);

            HttpGet httpGet = new HttpGet(uriBuilder.build());
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
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Ordine getAllProductsByOrder(String userId, Long date) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder1 = new URIBuilder(uri);
            uriBuilder1.setPath("/getAllProdByOrder/" + userId);
            uriBuilder1.setParameter("date", String.valueOf(date));
            URIBuilder uriBuilder2 = new URIBuilder(uri);
            uriBuilder2.setPath("/getOrderID/" + userId);
            uriBuilder2.setParameter("date", String.valueOf(date));

            HttpGet httpGet = new HttpGet(uriBuilder1.build());
            HttpGet httpGet2 = new HttpGet(uriBuilder2.build());
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
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utente getUserByID(String userId, Class<? extends Utente> utenteClass) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/getUserInfo/" + userId);

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            CloseableHttpResponse response = httpClient.execute(httpGet);
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
                utente.setRuolo(RuoloResponsabile.valueOf(utenteinfo.get(10).getAsString()));

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
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Prodotto> getProductsPerBrand(String brandName, String uid) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/getProdByBrand/" + brandName);


            HttpGet httpGet = new HttpGet(uriBuilder.build());
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
                    prodotto.setQuantita(prodottoElement.get(8).getAsInt());
                    prodottoList.add(prodotto);

                }
                return prodottoList;
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String addTessera(TesseraFedelta tessera, String uid) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/addTessera/" + uid);

            HttpPost httpPost = new HttpPost(uriBuilder.build());
            HttpEntity httpEntity = new StringEntity(Manager.objectToJson(tessera));
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return response.getStatusLine().getReasonPhrase();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public boolean addTesseraPoint(String idt,int punti) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/addPoint/" + idt);
            uriBuilder.setParameter("punti", String.valueOf(punti));

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return response.getStatusLine().getReasonPhrase().equalsIgnoreCase("OK");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String changePassword(String uid, String oldPassw, String newPassw) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/changePassword/" + uid);
            uriBuilder.setParameters(new BasicNameValuePair("old", oldPassw), new BasicNameValuePair("new", newPassw));

            HttpPost request = new HttpPost(uriBuilder.build());
            request.addHeader(HttpHeaders.USER_AGENT, "JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public List<String> getAllUserID(String uid) {
        List<String> userList = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/getAllUserID/" + uid);

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            String jsonResponse = EntityUtils.toString(responseEntity);
            JsonArray list = JsonParser.parseString(jsonResponse).getAsJsonArray();
            Iterator<JsonElement> it = list.iterator();
            while (it.hasNext()) {
                JsonArray orderElement = it.next().getAsJsonArray();
                userList.add(orderElement.get(0).getAsString());
            }
            return userList;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String updateUserInfo(String uid,UtenteCliente utente) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/updateUserInfo/" + uid);

            HttpPost httpPost = new HttpPost(uriBuilder.build());
            HttpEntity httpEntity = new StringEntity(Manager.objectToJson(utente));
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return response.getStatusLine().getReasonPhrase();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public List<Long> getAllDeliveryDate(){
        List<Long> dateList = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.setPath("/getAllDeliveryDate");

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            String jsonResponse = EntityUtils.toString(responseEntity);
            JsonArray list = JsonParser.parseString(jsonResponse).getAsJsonArray();
            Iterator<JsonElement> it = list.iterator();
            while (it.hasNext()) {
                JsonArray orderElement = it.next().getAsJsonArray();
                dateList.add(orderElement.get(0).getAsLong());
            }
            return dateList;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
