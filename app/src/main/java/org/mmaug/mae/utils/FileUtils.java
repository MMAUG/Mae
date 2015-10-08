package org.mmaug.mae.utils;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yemyatthu on 5/5/15.
 */
public class FileUtils {

  /***
   * Save contact list to storage
   * eg. saveData(this,convertToJson(contactList),"MY_CONTACT.json");
   *
   * Load contact list from storage
   * contactList = convertToJava(loadData(this,"MY_CONTACT.json"));
   ***/

  //Convert contacts list to string
  public static String convertToJson(List data) {
    Gson gson = new Gson();
    return gson.toJson(data);
  }

  //Convert String to contact list
  public static List convertToJava(String jsonString, Type type) {
    List datas = new ArrayList<>();
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    datas = gson.fromJson(jsonString, type);
    return datas;
  }

  //Write Json to Local Storage
  public static void saveData(Context context, String jsonString, String fileName) {
    OutputStream outputStream = null;
    try {
      outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
      outputStream.write(jsonString.getBytes());
      outputStream.close();
    } catch (FileNotFoundException exception) {
      Log.d("FileNotFound", "File not found Error");
    } catch (IOException ioException) {
      Log.d("IOError", "IO Error");
    }
  }

  //Load Json from Local Storage
  public static String loadData(Context context, String fileName) {
    StringBuilder builder = null;
    InputStream inputStream = null;
    try {
      inputStream = context.openFileInput(fileName);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      builder = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }
      reader.close();
    } catch (FileNotFoundException fileNotFoundExcepiton) {
      Log.d("FileNotFound", "No File Found");
    } catch (IOException ioException) {
      Log.d("IO Exception", "IO Exception");
    }
    if (builder != null) {
      return builder.toString();
    } else {
      return null;
    }
  }
}
