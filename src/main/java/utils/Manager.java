package utils;

import com.google.gson.Gson;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Manager {

    public static String EURO = "\u20ac";

    public static String getDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        return simpleDateFormat.format(date);
    }
    public static String getSimpleDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }


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
            BufferedWriter writer = new BufferedWriter(new FileWriter(
                    new File(ClassLoader.getSystemClassLoader().getResource("id.txt").getFile())));
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

    public static boolean saveResource(File file) {
        File dest = new File("src/main/resources/" + file.getName());
        try {
            FileInputStream is = new FileInputStream(file);
            FileOutputStream os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            is.close();
            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}