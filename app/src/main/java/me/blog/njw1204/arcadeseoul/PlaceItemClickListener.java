package me.blog.njw1204.arcadeseoul;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class PlaceItemClickListener implements RecyclerView.OnItemTouchListener{
    private Context context;
    private GestureDetector mGestureDetector;
    private ClickEvents clickEvents;

    public interface ClickEvents {
        void onItemSingleTapUp(View view, int position);
    };

    PlaceItemClickListener(Context context, ClickEvents clickEvents) {
        this.context = context;
        this.mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
        this.clickEvents = clickEvents;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mGestureDetector.onTouchEvent(e)) {
            clickEvents.onItemSingleTapUp(childView, rv.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}