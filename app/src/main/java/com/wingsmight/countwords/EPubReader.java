package com.wingsmight.countwords;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;

public class EPubReader
{
    private EpubReader epubReader;

    public EPubReader()
    {
        epubReader = new EpubReader();
    }

    public TreeMap<String, Integer> ReadFile(File file)
    {
        FileInputStream epubInputStream = null;
        Book book = null;
        try
        {
            epubInputStream = new FileInputStream(file);

            book = epubReader.readEpub(epubInputStream);

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        // Log the book's authors
        Log.i("author", " : " + book.getMetadata().getAuthors());

        // Log the book's title
        Log.i("title", " : " + book.getTitle());

        List<TOCReference> tocReferences = book.getTableOfContents().getTocReferences();
        return logTableOfContents(tocReferences);
    }

    @SuppressWarnings("unused")
    private TreeMap<String, Integer> logTableOfContents(List<TOCReference> book)
    {
        Map<String, Integer> words = new HashMap<String, Integer>();

        for (TOCReference tocReference : book)
        {
            Log.i("TOC", tocReference.getTitle());

            try
            {
                InputStream inputStream = tocReference.getResource().getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                int countRepWords;
                String line = "";
                while ((line = bufferedReader.readLine()) != null)
                {
                    String line1 = Html.fromHtml(line).toString();

                    String[] formatedWords = line1.replaceAll("[^A-za-z ]", "").split("\\s+");

                    for (String formatedWord : formatedWords)
                    {
                        if (!formatedWord.isEmpty())
                        {
                            countRepWords = words.containsKey(formatedWord) ? words.get(formatedWord) + 1 : 1;

                            formatedWord = Character.toLowerCase(formatedWord.charAt(0)) + formatedWord.substring(1);
                            words.put(formatedWord, countRepWords);
                        }
                    }
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        ValueComparator bvc = new ValueComparator(words);
        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
        sorted_map.putAll(words);

        return sorted_map;
    }
}
