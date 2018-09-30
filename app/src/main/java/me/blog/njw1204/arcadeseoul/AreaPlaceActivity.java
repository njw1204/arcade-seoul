package me.blog.njw1204.arcadeseoul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class AreaPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_place);
        getSupportActionBar().setTitle("지역별 오락실");

        for (int i = 1; i <= 24; i++) {
            TextView textView = findViewById(getResources().getIdentifier(
                String.format(Locale.KOREA, "textview_area_%d", i),
                "id",
                "me.blog.njw1204.arcadeseoul"
            ));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView)v;
                    Intent intent = new Intent(AreaPlaceActivity.this, PlaceAllListActivity.class);
                    intent.putExtra("type", "area");
                    intent.putExtra("area", tv.getText());
                    intent.putExtra("title", tv.getText() + " 오락실");
                    startActivity(intent);
                }
            });
        }
    }
}
