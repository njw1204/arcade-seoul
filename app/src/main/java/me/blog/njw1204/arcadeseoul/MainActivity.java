package me.blog.njw1204.arcadeseoul;

import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Console;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static me.blog.njw1204.arcadeseoul.CUtils.StartSimpleWebActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applyFontAwesome();

        findViewById(R.id.imageView_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "아케이드 서울이 오픈했습니다!\n모두 축하해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.license_menu, menu);
        getSupportActionBar().setTitle("Arcade Seoul");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_license:
                Intent intent = new Intent(this, LicenseActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickSearchAct(View v) {
        Intent intent = new Intent(this, PlaceAllListActivity.class);
        intent.putExtra("type", "all");
        intent.putExtra("title", "오락실 전체 검색");
        startActivity(intent);
    }

    public void onClickLiveNews(View v) {
        Intent intent = new Intent(this, LiveNewsActivity.class);
        startActivity(intent);
    }

    public void onClickAreaPlace(View v) {
        Intent intent = new Intent(this, AreaPlaceActivity.class);
        startActivity(intent);
    }

    public void onClickThemePlace(View v) {
        Intent intent = new Intent(this, ThemeAreaActivity.class);
        startActivity(intent);
    }

    public void onClickCom(View v) {
        StartSimpleWebActivity(this, "http://appboard.dothome.co.kr/arcade_seoul?m=1", "커뮤니티");
    }

    public void onClickFavorite(View v) {
        doFavorite(this);
    }

    public static void doFavorite(Context context) {
        TinyDB tinyDB = new TinyDB(context);
        ArrayList<Integer> arr = tinyDB.getListInt("favorite");
        if (arr != null && arr.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Integer i : arr) {
                stringBuilder.append(i);
                stringBuilder.append(',');
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            Intent intent = new Intent(context, PlaceAllListActivity.class);
            intent.putExtra("type", "fav");
            intent.putExtra("fav", stringBuilder.toString());
            intent.putExtra("title", "즐겨찾기");
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "즐겨찾기가 비어있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickDict(View v) {
        StartSimpleWebActivity(this, "http://appboard.dothome.co.kr/arcade_dict?m=1", "오락실 사전");
    }

    public void onClickQuiz(View v) {
        StartSimpleWebActivity(this, "http://apk.dothome.co.kr/arcade-seoul/test.html", "오락실 퀴즈");
    }

    private void applyFontAwesome() {
        Typeface fas = Typeface.createFromAsset(getAssets(), "fa_solid_900.ttf");
        int[] buttonIdArrForFas = {
                R.id.button_search_act, R.id.button_trend_act, R.id.button_community_act,
                R.id.button_location_act, R.id.button_theme_act
        };

        for (int i = 0; i < buttonIdArrForFas.length; i++) {
            Button button = (Button) findViewById(buttonIdArrForFas[i]);
            /* not working < 8.0
            SpannableStringBuilder styledStr = new SpannableStringBuilder(button.getText());
            styledStr.setSpan(new CustomTypefaceSpan(fas), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            button.setText(styledStr);
            */
            button.setTypeface(fas);
        }
    }
}