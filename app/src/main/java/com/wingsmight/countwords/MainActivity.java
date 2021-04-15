package com.wingsmight.countwords;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity
{
    private final int GET_BOOK_REQUEST_CODE = 0;


    private static Context mainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainContext = MainActivity.this;
    }

    public void OpenBook(View view)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, GET_BOOK_REQUEST_CODE);
    }

    public void AnalyzeBook(String filePath)
    {
        File bookFile = new File(filePath);

        if (bookFile.exists())
        {
            EPubReader ePubReader = new EPubReader();

            TreeMap<String, Integer> text = ePubReader.ReadFile(bookFile);

            InitializeRecyclerView(text);
        }
        else
        {
            Toast.makeText(mainContext, "Не удалось обратиться к файлу. Некорректный путь", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GET_BOOK_REQUEST_CODE)
        {
            String filePath = UriConvert.getPath(mainContext, data.getData());

            AnalyzeBook(filePath);
        }
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
