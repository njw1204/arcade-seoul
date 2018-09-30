package me.blog.njw1204.arcadeseoul;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.blog.njw1204.arcadeseoul.CUtils.DP;

public class PlaceInfoActivity extends AppCompatActivity {
    public int placeId, voteCount;
    public String placeUrl, placeName, location, openTime, phone, description, tags;
    public float rating;
    public double latitude, longitude;
    public JSONArray devices;
    private int lastFmId = -1;

    private ArrayList<ReviewItem> reviewItems = new ArrayList<>();
    private ReviewRecyclerAdapter reviewRecyclerAdapter;

    public ArrayList<ReviewItem> getReviewItems() {
        return reviewItems;
    }

    public ReviewRecyclerAdapter getReviewRecyclerAdapter() {
        return reviewRecyclerAdapter;
    }

    public void setReviewRecyclerAdapter(ReviewRecyclerAdapter reviewRecyclerAdapter) {
        this.reviewRecyclerAdapter = reviewRecyclerAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        placeUrl = getIntent().getStringExtra("url");
        placeName = getIntent().getStringExtra("name");
        placeId = getIntent().getIntExtra("id", -1);
        getSupportActionBar().setTitle(placeName);
        FetchPlaceInfoFromServer();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.place_info_menu, menu);
        TinyDB tinyDB = new TinyDB(this);
        ArrayList<Integer> arr = tinyDB.getListInt("favorite");
        if (arr.contains(placeId)) {
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this,getResources().getIdentifier("btn_star_big_on", "drawable", "android")));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                Intent intent = new Intent(this, PlaceInfoActivity.class);
                intent.putExtras(getIntent());
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_add_favorite:
                TinyDB tinyDB = new TinyDB(this);
                ArrayList<Integer> arr = tinyDB.getListInt("favorite");
                if (arr.contains(placeId)) {
                    Toast.makeText(this, "즐겨찾기에서 제거되었습니다.", Toast.LENGTH_SHORT).show();
                    item.setIcon(ContextCompat.getDrawable(this,getResources().getIdentifier("btn_star_big_off", "drawable", "android")));
                    arr.remove(Integer.valueOf(placeId));
                } else {
                    Toast.makeText(this, "즐겨찾기에 추가했습니다.", Toast.LENGTH_SHORT).show();
                    item.setIcon(ContextCompat.getDrawable(this,getResources().getIdentifier("btn_star_big_on", "drawable", "android")));
                    arr.add(placeId);
                }
                tinyDB.putListInt("favorite", arr);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SetNaviClickListener(BottomNavigationView navi) {
        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (lastFmId == item.getItemId()) return false;
                switch (item.getItemId()) {
                    case R.id.navi_place_info:
                        FragmentSetting(new FragmentPlaceInfoDetail());
                        lastFmId = item.getItemId();
                        return true;
                    case R.id.navi_place_review_list:
                        FragmentSetting(new FragmentPlaceReviewList());
                        lastFmId = item.getItemId();
                        return true;
                    case R.id.navi_place_review_write:
                        FragmentSetting(new FragmentPlaceReviewWrite());
                        lastFmId = item.getItemId();
                        return true;
                }
                return false;
            }
        });
    }

    public void FragmentSetting(Fragment fm) {
        FragmentTransaction fmt = getSupportFragmentManager().beginTransaction();
        fmt.replace(R.id.fragment_group_place_info, fm);
        fmt.commit();
    }

    private void AddCommentsToRecycler(JSONArray comments) {
        for (int i = 0; i < comments.length(); i++) {
            JSONObject review = null;
            try {
                review = comments.getJSONObject(i);
                ReviewItem item = new ReviewItem(
                    review.getString("content"),
                    review.getString("written_date"),
                    review.getString("author"),
                    (float)review.getDouble("star")
                );
                reviewItems.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void FetchPlaceInfoFromServer() {
        PlaceRESTService rest = PlaceRESTService.retrofit.create(PlaceRESTService.class);
        Call<ResponseBody> http = rest.getPlaceInfo(placeId);
        http.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() >= 400) {
                    onFailure(call, new Throwable(response.message()));
                    return;
                }

                String result;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String placeholder = "정보 없음";
                    latitude = jsonObject.getDouble("latitude");
                    longitude = jsonObject.getDouble("longitude");
                    location = FuckJsonNullString(jsonObject.optString("location_long"), placeholder);
                    openTime = FuckJsonNullString(jsonObject.optString("time_long"), placeholder);
                    phone = FuckJsonNullString(jsonObject.optString("phone"), placeholder);
                    description = FuckJsonNullString(jsonObject.optString("description"), placeholder);
                    tags = FuckJsonNullString(jsonObject.optString("tags"), placeholder);
                    rating = (float)jsonObject.getDouble("star");
                    voteCount = jsonObject.getInt("review_cnt");
                    devices = jsonObject.getJSONArray("devices");

                    AddCommentsToRecycler(jsonObject.getJSONArray("comments"));
                    FragmentSetting(new FragmentPlaceInfoDetail());
                    SetNaviClickListener((BottomNavigationView)findViewById(R.id.BottomNavigationView_place_info));
                } catch (JSONException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlaceInfoActivity.this);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setTitle("오류");
                builder.setMessage("네트워크 상태가 원활하지 않습니다.\n다시 시도해주세요.");
                builder.setCancelable(false);
                builder.show();
            }
        });
    }

    private String FuckJsonNullString(String s, String ifNull) {
        if (CUtils.IsEmpty(s) || s.equals("null")) return ifNull;
        else return s;
    }

    public void PrintDevicesList(Context context, View view, JSONArray devices) {
        for (int i = 0; i < devices.length(); i++) {
            try {
                int pad = DP(context, 5);
                JSONObject jsonObject = devices.getJSONObject(i);
                TableRow tableRow = new TableRow(context);
                tableRow.setBackground(context.getResources().getDrawable(R.drawable.simple_border));

                String[] arr = {"kind", "name", "count", "price"};
                for (String var : arr) {
                    TextView textView = new TextView(context);
                    textView.setPadding(pad, pad, pad, pad);
                    textView.setBackground(context.getResources().getDrawable(R.drawable.simple_border));
                    textView.setTextColor(context.getResources().getColor(R.color.lightBlack));
                    textView.setGravity(Gravity.CENTER);
                    textView.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
                    );
                    textView.setText(jsonObject.getString(var));
                    tableRow.addView(textView);
                }

                ((TableLayout)view.findViewById(R.id.tablelayout_devices)).addView(tableRow);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
