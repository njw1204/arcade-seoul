package me.blog.njw1204.arcadeseoul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Handler hnd = new Handler();
        hnd.postDelayed(new startMain(), 1200);
    }

    private class startMain implements Runnable{
        @Override
        public void run() {
            Intent showAct = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(showAct);
            Splash.this.finish();
        }
    }
}
