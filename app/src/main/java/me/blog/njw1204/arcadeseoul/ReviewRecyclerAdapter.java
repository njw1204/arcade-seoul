package me.blog.njw1204.arcadeseoul;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.willy.ratingbar.BaseRatingBar;

import java.util.ArrayList;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewItemViewHolder> {
    private ArrayList<ReviewItem> items;

    public ReviewRecyclerAdapter(ArrayList<ReviewItem> items) {
        this.items = items;
    }

    @Override
    public ReviewItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewItemViewHolder holder, int position) {
        holder.contents.setText(items.get(position).getContents());
        holder.date.setText(items.get(position).getDate());
        holder.author.setText(items.get(position).getAuthor());
        holder.rating.setRating(items.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ReviewItemViewHolder extends RecyclerView.ViewHolder {
        private TextView contents;
        private TextView date;
        private TextView author;
        private BaseRatingBar rating;

        public ReviewItemViewHolder(View itemView) {
            super(itemView);
            contents = itemView.findViewById(R.id.textView_review_contents);
            date = itemView.findViewById(R.id.textView_review_date);
            author = itemView.findViewById(R.id.textView_review_author);
            rating = itemView.findViewById(R.id.ratingBar_review_rating);
        }
    }
}
