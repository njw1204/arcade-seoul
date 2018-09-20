package me.blog.njw1204.arcadeseoul;

import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Console;
import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applyFontAwesome();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setTitle("Arcade Seoul");
        return true;
    }

    public void onClickSearchAct(View v) {
        Toast.makeText(this, "검색 액티비티", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PlaceAllListActivity.class);
        startActivity(intent);
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