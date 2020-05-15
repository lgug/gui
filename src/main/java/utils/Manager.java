package utils;

import com.google.gson.Gson;

public class Manager {


    public static String objectToJson(Object obj){
            Gson gson = new Gson();
            return gson.toJson(obj);
    }

    public <T> T jsonToObject(String json, Class<T> obj){
        Gson gson = new Gson();
        return gson.fromJson(json, obj );
    }
}