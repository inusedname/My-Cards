package com.example.mycards.controller.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"ResultOfMethodCallIgnored", "UnusedDeclaration", "unchecked"})
public class FileLoader {
    public static <T> void saveToFile(Context context, String fileName, List<T> ls){
        File file = new File(context.getFilesDir(), fileName);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file, false);
            // Append mode is now false
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ls);
            oos.close();
            fos.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static <T> List<T> loadFromFile(Context context, String fileName){
        File file = new File(context.getFilesDir(), fileName);
        List<T> tmp = new ArrayList<>();
        if (file.length() > 0) {
            try {
                file.createNewFile();
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                tmp = (List<T>) ois.readObject();
                ois.close();
                fis.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return tmp;
    }
}
