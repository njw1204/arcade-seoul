package me.blog.njw1204.arcadeseoul;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.Locale;

public class PlaceRecyclerAdapter extends RecyclerView.Adapter<PlaceRecyclerAdapter.PlaceItemViewHolder> {
    private Context context;
    private ArrayList<PlaceItem> items, displayItems;
    private boolean displayAll;

    PlaceRecyclerAdapter(Context context, ArrayList<PlaceItem> items) {
        this.context = context;
        this.items = items;
        this.displayItems = new ArrayList<>();
        this.displayAll = true;
    }

    @Override
    public PlaceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        return new PlaceItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaceItemViewHolder holder, int position) {
        PlaceItem cItem;
        if (displayAll) cItem = items.get(position);
        else cItem = displayItems.get(position);

        holder.name.setText(cItem.getName());
        holder.location.setText(cItem.getLocation());
        holder.openTime.setText(cItem.getOpenTime());
        holder.rating.setRating(cItem.getRating());
        holder.ratingInfo.setText(String.format(Locale.KOREA, "%.1f (%d명)", cItem.getRating(), cItem.getVoteCount()));

        for (int i = 0; i < holder.tags.size(); i++) {
            TextView nowTagView = holder.tags.get(i);
            if (i < cItem.getTags().size()) {
                nowTagView.setText("#" + cItem.getTags().get(i));
                nowTagView.setVisibility(View.VISIBLE);
            }
            else {
                nowTagView.setText("");
                nowTagView.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (displayAll ? items.size() : displayItems.size());
    }

    public void filter(String keywords) {
        displayItems.clear();

        if (keywords.length() == 0) {
            displayAll = true;
            notifyDataSetChanged();
            return;
        } else {
            displayAll = false;
        }

        for (PlaceItem item : items) {
            boolean succ = false;

            if (CUtils.SearchableString(item.getName()).contains(keywords))
                succ = true;
            else if (CUtils.SearchableString(item.getLocation()).contains(keywords))
                succ = true;
            else {
                for (String tag : item.getTags()) {
                    if (CUtils.SearchableString(tag).contains(keywords)) {
                        succ = true;
                        break;
                    }
                }
            }

            if (succ) {
                displayItems.add(item);
            }
        }
        notifyDataSetChanged();
    }

    class PlaceItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView location;
        private TextView openTime;
        private ArrayList<TextView> tags;
        private BaseRatingBar rating;
        private TextView ratingInfo;

        PlaceItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_place_item_name);
            location = itemView.findViewById(R.id.textView_place_item_location);
            openTime = itemView.findViewById(R.id.textView_place_item_openTime);
            rating = itemView.findViewById(R.id.ratingBar_place_item_rating);
            ratingInfo = itemView.findViewById(R.id.textView_place_item_rating_info);
            tags = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                int id = itemView.getResources().getIdentifier(
                        String.format(Locale.KOREA, "textView_place_item_tag%d", i + 1),
                        "id",
                        "me.blog.njw1204.arcadeseoul"
                );
                TextView textView = itemView.findViewById(id);
                textView.setVisibility(View.INVISIBLE);
                tags.add(textView);
            }
        }
    }
}
