package utils;

import com.google.gson.Gson;

import java.io.*;
import java.util.Scanner;

public class Manager {


    public static String objectToJson(Object obj){
            Gson gson = new Gson();
            return gson.toJson(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> obj){
        Gson gson = new Gson();
        return gson.fromJson(json, obj );
    }

    public static void createIDFile(String uid) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(ClassLoader.getSystemClassLoader().getResource("id.txt").getFile()));
            writer.write(uid);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUIDFromFile(){
        File myObj = new File(ClassLoader.getSystemClassLoader().getResource("id.txt").getFile());
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                return data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return "ERROR";
    }
}