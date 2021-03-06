package utils;

import com.google.gson.Gson;
import javafx.scene.image.Image;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Manager {

    public static Path tempdir;

    public static String EURO = "\u20ac";

    public static String getDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
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
            tempdir = Files.createTempDirectory("ciao");
            BufferedWriter writer = new BufferedWriter(new FileWriter(
                    new File(tempdir+"/id.txt")));
            writer.write(uid);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUIDFromFile() {
        InputStream myObj = null;
        try {
            myObj = new FileInputStream(tempdir+"/id.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
            return data;
        }
        myReader.close();
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

    public static boolean isUserCliente()  {
        String uid = getUIDFromFile();
        return uid.matches("UC-[\\d]+");
    }

    public static boolean isUserResponsabile() {
        String uid = getUIDFromFile();
        return uid.matches("UR-[\\d]+");
    }

    public static String encodeImage(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStream.read(bytes);
            fileInputStream.close();
            return new String(Base64.encodeBase64(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image decodeImage(String base64) {
        byte[] decodedBase64 = Base64.decodeBase64(base64);
        return new Image(new ByteArrayInputStream(decodedBase64));
    }

}