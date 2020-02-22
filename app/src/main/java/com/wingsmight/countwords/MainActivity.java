package com.wingsmight.countwords;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity
{
    private static Context mainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainContext = MainActivity.this;
    }

    public void CountWords(View view)
    {
        EPubReader ePubReader = new EPubReader();
        TreeMap<String, Integer> text = ePubReader.ReadFile("/storage/sdcard0/Books/TheLawOfSuccess.epub");

//        TreeMap<String, Integer> text = new TreeMap<>();
//        String path = "/storage/sdcard0";
//        try
//        {
//            //text = FileManager.ReadFromFile2(path, "Books/LostBoy", ".txt");
//            text = FileManager.ReadFromFile2(path, "Books/LostBoy", ".txt");//TheLawOfSuccess.epub
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }

        InitializeRecyclerView(text);
    }

    private void InitializeRecyclerView(TreeMap<String, Integer> text)
    {
        RecyclerView recyclerView = findViewById(R.id.words);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, text);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static Context GetContext()
    {
        return MainActivity.mainContext;
    }
}
