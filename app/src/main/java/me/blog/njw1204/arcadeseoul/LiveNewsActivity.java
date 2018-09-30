package me.blog.njw1204.arcadeseoul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static me.blog.njw1204.arcadeseoul.CUtils.StartSimpleWebActivity;

public class LiveNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_news);
        getSupportActionBar().setTitle("실시간 트렌드");

        Button button = findViewById(R.id.button_twitter_news);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartSimpleWebActivity(getApplicationContext(), "https://mobile.twitter.com/search?q=%EC%98%A4%EB%9D%BD%EC%8B%A4", "오락실 인기 트윗");
            }
        });

        button = findViewById(R.id.button_inven_news);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartSimpleWebActivity(getApplicationContext(), "http://m.inven.co.kr/webzine/wznews.php?hotnews=1", "인벤 주요뉴스");
            }
        });

        button = findViewById(R.id.button_gamemeca_news);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartSimpleWebActivity(getApplicationContext(), "http://m.gamemeca.com/news.php?ca=all&rts=text", "게임메카 주요뉴스");
            }
        });

        button = findViewById(R.id.button_gamemeca_sungji);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartSimpleWebActivity(getApplicationContext(), "http://m.gamemeca.com/feature.php?s=214&rts=text", "게임메카 성지순례");
            }
        });

        button = findViewById(R.id.button_thisisgame_news);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartSimpleWebActivity(getApplicationContext(), "http://m.thisisgame.com/webzine/news/nboard/263/?category=2", "디스이즈게임 주요뉴스");
            }
        });
    }
}
