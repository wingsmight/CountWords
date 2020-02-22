package com.wingsmight.countwords;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FileManager
{
    public static TreeMap<String, Integer> ReadFromFile2(String filePath, String fileName, String extensionWithDot) throws IOException
    {
        FileReader fr = null;
        try
        {
            fr = new FileReader(filePath + "/" + fileName + extensionWithDot);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        String text = new String();
        Map<String, Integer> words = new HashMap<String, Integer>();
        int i;
        char curChar;
        int countRepWords;
        while ((i=fr.read()) != -1)
        {
            curChar = (char) i;

            if(curChar != ' ' && curChar != '.' && curChar != ',' && curChar != '\n' && curChar != '\r')
            {
                text += curChar;
            }
            else
            {
                countRepWords = words.containsKey(text) ? words.get(text) + 1 : 1;
                words.put(text, countRepWords);
                text = "";
            }
        }

        ValueComparator bvc = new ValueComparator(words);
        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);

        sorted_map.putAll(words);

        return sorted_map;
    }

    private void AddWord(String newWord)
    {
        ArrayList<String> words = new ArrayList<String>();

        for (String word : words)
        {
            if(word.equals(newWord))
            {
                words.add(newWord);
            }
        }

        words.add(newWord);
    }

    public static byte[] FileToBytes(String filePath, String fileName, String extensionWithDot)
    {
        File file = new File(filePath, fileName + extensionWithDot);
        boolean isExists = file.exists();
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length()];
        try
        {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return bFile;
    }

    public static void BytesToFile(byte fileContent[], String filePath, String fileName, String extensionWithDot)
    {
        FileOutputStream fileOuputStream = null;

        try {
            fileOuputStream = new FileOutputStream(filePath + "/" + fileName + extensionWithDot);
            fileOuputStream.write(fileContent);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOuputStream != null) {
                try {
                    fileOuputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void CopyFile(File src, File dst) throws IOException
    {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    public static void DeleteFile(File fdelete)
    {
        if (fdelete.exists()) {
            fdelete.delete();
        }
    }
}

