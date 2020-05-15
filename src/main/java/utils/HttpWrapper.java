package utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpWrapper {
    private String uri = "http://127.0.0.1:5000";
    private String r = "";

    public String login(String username,String password) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            HttpGet request = new HttpGet(uri+"/login?"+"username="+username+"&"+"password="+password);
            request.addHeader(HttpHeaders.USER_AGENT,"JAVACLIENT");
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return(result);
            }
        }
        finally{
            httpClient.close();
        }
        return "Error";
    }

    //TODO user creation. return boolean, parameters Utente

    //TODO get products per availability

    //TODO get products per name
    public String getProductsPerName(String prodName) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            HttpGet request = new HttpGet(uri+"/getProdByName/"+prodName);
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

    //TODO request all user's orders
}
