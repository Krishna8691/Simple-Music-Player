package com.trial.smp.trial;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.media.MediaMetadataRetriever;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import java.io.File;
        import java.io.InterruptedIOException;
        import java.util.ArrayList;

public class Player extends AppCompatActivity implements View.OnClickListener {
    static MediaPlayer mp;
    ArrayList<File> finsongsList;
    Uri u;
    TextView songname;
    String[] items;
    SeekBar progress;
    int position;
    Button play,prev,next;
    Thread updateseekbar;
    ImageView album_art;
    TextView album;
    TextView artist;
    MediaMetadataRetriever metaRetriver;
    byte[] art;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent in =getIntent();
        Bundle b =in.getExtras();
        finsongsList = (ArrayList) b.getParcelableArrayList("ps");
        position = b.getInt("pos",0);
        songname= (TextView) findViewById(R.id.songname);


        album_art = (ImageView) findViewById(R.id.album_art);
        album = (TextView) findViewById(R.id.Album);
        artist = (TextView) findViewById(R.id.artist_name);





        metaRetriver = new MediaMetadataRetriever();
        metaRetriver.setDataSource(finsongsList.get(position).toString());
        try {
            art = metaRetriver.getEmbeddedPicture();
            Bitmap songImage = BitmapFactory
                    .decodeByteArray(art, 0, art.length);
            album_art.setImageBitmap(songImage);
            album.setText(metaRetriver
                    .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            artist.setText(metaRetriver
                    .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        } catch (Exception e) {
            album_art.setBackgroundResource(R.drawable.defaa);
            album.setText("Unknown Album");
            artist.setText("Unknown Artist");
        }


        play = (Button) findViewById(R.id.player);
        prev = (Button) findViewById(R.id.prever);
        next = (Button) findViewById(R.id.nexter);

        play.setOnClickListener(this);
        prev.setOnClickListener(this);
        next.setOnClickListener(this);

        progress = (SeekBar) findViewById(R.id.seekBar);
        updateseekbar = new Thread(){
            @Override
            public void run() {
                //super.run();
                int totalduration = mp.getDuration();
                int currentposition=0;

                while (currentposition < totalduration)
                {
                    try{
                        sleep(500);
                        currentposition = mp.getCurrentPosition();
                        progress.setProgress(currentposition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        if(mp!=null)
        {
            mp.stop();
            mp.release();
        }

        u = Uri.parse(finsongsList.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(),u);
        songname.setText(finsongsList.get(position).getName().toString());
        mp.start();
        progress.setMax(mp.getDuration());
        updateseekbar.start();

        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.player:
                if(mp.isPlaying()){
                    play.setText(">");
                    mp.pause();
                }else {
                    play.setText("||");
                    mp.start();
                }
                break;
            case R.id.nexter:
                mp.stop();
                mp.release();
                position = (position+1)%finsongsList.size();
                u = Uri.parse(finsongsList.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(),u);
                songname.setText(finsongsList.get(position).getName().toString());

                //MediaMetadataRetriever metaRetriver;
                //byte[] art;
                metaRetriver = new MediaMetadataRetriever();
                metaRetriver.setDataSource(finsongsList.get(position).toString());
                try {
                    art = metaRetriver.getEmbeddedPicture();
                    Bitmap songImage = BitmapFactory
                            .decodeByteArray(art, 0, art.length);
                    album_art.setImageBitmap(songImage);
                    album.setText(metaRetriver
                            .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
                    artist.setText(metaRetriver
                            .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                } catch (Exception e) {
                    album_art.setBackgroundResource(R.drawable.defaa);
                    album.setText("Unknown Album");
                    artist.setText("Unknown Artist");
                }

                mp.start();
                progress.setMax(mp.getDuration());
                break;
            case R.id.prever:
                mp.stop();
                mp.release();
                position = (position-1<0?finsongsList.size()-1:position-1);
                u = Uri.parse(finsongsList.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(),u);
                songname.setText(finsongsList.get(position).getName().toString());

                //MediaMetadataRetriever metaRetriver;
                //byte[] art;
                metaRetriver = new MediaMetadataRetriever();
                metaRetriver.setDataSource(finsongsList.get(position).toString());
                try {
                    art = metaRetriver.getEmbeddedPicture();
                    Bitmap songImage = BitmapFactory
                            .decodeByteArray(art, 0, art.length);
                    album_art.setImageBitmap(songImage);
                    album.setText(metaRetriver
                            .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
                    artist.setText(metaRetriver
                            .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                } catch (Exception e) {
                    album_art.setBackgroundResource(R.drawable.defaa);
                    album.setText("Unknown Album");
                    artist.setText("Unknown Artist");
                }

                mp.start();
                progress.setMax(mp.getDuration());
                break;
        }
    }
}









