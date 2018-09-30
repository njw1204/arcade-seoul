package me.blog.njw1204.arcadeseoul;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentPlaceReviewList extends Fragment {
    private View view;

    public FragmentPlaceReviewList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_place_review_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        PlaceInfoActivity av = ((PlaceInfoActivity)getActivity());
        av.setReviewRecyclerAdapter(new ReviewRecyclerAdapter(av.getReviewItems()));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_review_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(av.getReviewRecyclerAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
