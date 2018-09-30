package me.blog.njw1204.arcadeseoul;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceAllListActivity extends AppCompatActivity {
    private String type, area, theme, fav;
    private ArrayList<PlaceItem> placeItems = new ArrayList<>();
    PlaceRecyclerAdapter placeRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_all_list);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        type = getIntent().getStringExtra("type");
        area = getIntent().getStringExtra("area");
        theme = getIntent().getStringExtra("theme");
        fav = getIntent().getStringExtra("fav");

        InitPlaceRecyclerList();
        FetchListFromServer();
        EnableSearchPlaceList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                if (type.equals("fav")) {
                    MainActivity.doFavorite(this);
                    finish();
                    return true;
                }
                Intent intent = new Intent(this, PlaceAllListActivity.class);
                intent.putExtras(getIntent());
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void FetchListFromServer() {
        PlaceRESTService rest = PlaceRESTService.retrofit.create(PlaceRESTService.class);
        Call<ResponseBody> http = rest.getPlaceList(type, area, theme, fav);
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
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String location = jsonObject.getString("location_short");
                        String openTime = jsonObject.getString("time_short");
                        float rating = (float)jsonObject.getDouble("star");
                        int voteCount = jsonObject.getInt("review_cnt");

                        String[] tempTags = jsonObject.getString("tags").split(",");
                        ArrayList<String> tags = new ArrayList<>();
                        for (int j = 1; j < tempTags.length; j++) {
                            tags.add(tempTags[j]);
                        }

                        PlaceItem item = new PlaceItem(id, name, location, openTime, tags, rating, voteCount, "");
                        placeItems.add(item);
                    }
                    placeRecyclerAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    onFailure(call, new Throwable(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlaceAllListActivity.this);
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

    private void InitPlaceRecyclerList() {
        placeItems.clear();
        placeRecyclerAdapter = new PlaceRecyclerAdapter(placeItems);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_place_all_list);
        recyclerView.setAdapter(placeRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new PlaceItemClickListener(this, new PlaceItemClickListener.ClickEvents() {
            @Override
            public void onItemSingleTapUp(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), PlaceInfoActivity.class);
                intent.putExtra("name", placeRecyclerAdapter.getDisplayItems().get(position).getName());
                intent.putExtra("url", placeRecyclerAdapter.getDisplayItems().get(position).getUrl());
                intent.putExtra("id", placeRecyclerAdapter.getDisplayItems().get(position).getId());
                startActivity(intent);
            }
        }));
    }

    private void AddPlaceToRecyclerList(PlaceItem item) {
        placeItems.add(item);
        placeRecyclerAdapter.notifyDataSetChanged();
    }

    private void EnableSearchPlaceList() {
        EditText search = findViewById(R.id.editText_search_place_all_list);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keywords = CUtils.SearchableString(s.toString());
                placeRecyclerAdapter.filter(keywords);
            }
        });
    }

    private void testing() {
        PlaceItem test = new PlaceItem(1, "name", "location", "openTime", new ArrayList<String>(), 3.4f, 127, "http://naver.com");
        AddPlaceToRecyclerList(test);
        test = new PlaceItem(2, "삼성동 코엑스 메가박스 큐브존", "★ 강남구", "◆ 09:00~24:00",
            new ArrayList<>(Arrays.asList("가나다라", "나다라마", "다라마바사")), 4.7f, 59, "http://naver.com");
        AddPlaceToRecyclerList(test);
    }
}