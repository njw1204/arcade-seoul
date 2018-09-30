package me.blog.njw1204.arcadeseoul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class ThemeAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_area);
        getSupportActionBar().setTitle("테마별 오락실");

        for (int i = 1; i <= 12; i++) {
            TextView textView = findViewById(getResources().getIdentifier(
                String.format(Locale.KOREA, "textview_theme_%d", i),
                "id",
                "me.blog.njw1204.arcadeseoul"
            ));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView)v;
                    Intent intent = new Intent(ThemeAreaActivity.this, PlaceAllListActivity.class);
                    intent.putExtra("type", "theme");
                    intent.putExtra("theme", tv.getText().toString().split(" ")[0]);
                    intent.putExtra("title", "테마 : " + tv.getText());
                    startActivity(intent);
                }
            });
        }
    }
}
