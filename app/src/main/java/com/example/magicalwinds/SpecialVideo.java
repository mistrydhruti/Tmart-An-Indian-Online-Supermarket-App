package com.example.magicalwinds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class SpecialVideo extends AppCompatActivity {

    VideoView videoView;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_video);
        videoView=findViewById(R.id.videoview);
        mediaController=new MediaController(this);

        videoplay();

    }

    private void videoplay() {
        String videopath="android.resource://com.example.magicalwinds/"+R.raw.thr;
        Uri uri=Uri.parse(videopath);
        videoView.setVideoURI(uri);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();


    }

    public void Go_to_home(View view) {
        Intent i = new Intent(SpecialVideo.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(0,0);
    }
}
