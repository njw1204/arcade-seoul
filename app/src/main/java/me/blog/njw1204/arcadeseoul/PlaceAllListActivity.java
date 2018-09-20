package me.blog.njw1204.arcadeseoul;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class PlaceAllListActivity extends AppCompatActivity {
    private ArrayList<PlaceItem> placeItems = new ArrayList<>();
    PlaceRecyclerAdapter placeRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_all_list);
        InitPlaceRecyclerList();
        EnableSearchPlaceList();
        testing();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        getMenuInflater().inflate(R.menu.place_search_actionbar, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.action_place_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("검색");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
        */

        getSupportActionBar().setTitle("오락실 전체 검색");
        return super.onCreateOptionsMenu(menu);
    }

    private void InitPlaceRecyclerList() {
        placeItems.clear();
        placeRecyclerAdapter = new PlaceRecyclerAdapter(this, placeItems);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_place_all_list);
        recyclerView.setAdapter(placeRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new PlaceItemClickListener(this, new PlaceItemClickListener.ClickEvents() {
            @Override
            public void onItemSingleTapUp(View view, int position) {
                Toast.makeText(getApplicationContext(), placeItems.get(position).getName(), Toast.LENGTH_SHORT).show();
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
        PlaceItem test = new PlaceItem("name", "location", "openTime", new ArrayList<String>(), 3.4f, 127);
        AddPlaceToRecyclerList(test);
        test = new PlaceItem("삼성동 코엑스 메가박스 큐브존", "★ 강남구", "◆ 09:00~24:00",
            new ArrayList<>(Arrays.asList("가나다라", "나다라마", "다라마바사")), 4.7f, 59);
        AddPlaceToRecyclerList(test);
    }
}