package com.trial.smp.trial;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.File;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    final String MEDIA_PATH = "/storage/";
    final String MEDIA_PATH2 = Environment.getExternalStorageDirectory() + "/";
    private ArrayList<File> songsList = new ArrayList<>();
    private ArrayList<File> songsList2 = new ArrayList<>();
    ArrayList<File> finsongsList;
    ArrayList<File> finsongsList2;
    private String mp3Pattern = ".mp3";
    private String wavpattern = ".wav";
    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finsongsList = new ArrayList<>();
        finsongsList2 = new ArrayList<>();
        finsongsList = getPlayList1();
        finsongsList2 = getPlayList2();
        int ext = finsongsList.size();
        int seez = finsongsList.size()+finsongsList2.size();
        items = new String[seez];
        for(int i=0;i<seez;i++){
            if(i<ext) {
                items[i] = finsongsList.get(i).getName().toString().replace(".mp3","");
            }
            else{
                items[i] = finsongsList2.get(i).getName().toString().replace(".mp3","");
            }

        }
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        ListView tv = (ListView) findViewById(R.id.llvv);
        tv.setAdapter(itemsAdapter);
        tv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Player.class).putExtra("pos",position).putExtra("ps", finsongsList);
                startActivity(intent);
            }
        });
    }
    public ArrayList<File> getPlayList1()
    {
        System.out.println(MEDIA_PATH);
        if (MEDIA_PATH != null) {
            File home = new File(MEDIA_PATH);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }}}}
        return songsList;
    }
    public ArrayList<File> getPlayList2()
    {
        System.out.println(MEDIA_PATH);
        if (MEDIA_PATH2 != null) {
            File home = new File(MEDIA_PATH2);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList2(file);
                    }}}}
        return songsList2;
    }
    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }}}}}
    private void addSongToList(File song) {
        if (song.getName().endsWith(mp3Pattern) || song.getName().endsWith(wavpattern)) {
            songsList.add(song);
        }
    }

    private void addSongToList2(File song) {
        if (song.getName().endsWith(mp3Pattern) || song.getName().endsWith(wavpattern)) {
            songsList.add(song);
        }
    }
}

