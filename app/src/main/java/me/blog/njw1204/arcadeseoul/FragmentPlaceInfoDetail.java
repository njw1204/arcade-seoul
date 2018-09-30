package me.blog.njw1204.arcadeseoul;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.Locale;

public class FragmentPlaceInfoDetail extends Fragment {
    View v;

    public FragmentPlaceInfoDetail() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_place_info_detail, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final PlaceInfoActivity av = (PlaceInfoActivity)getActivity();
        ((TextView)v.findViewById(R.id.textview_name_info)).setText(av.placeName);
        ((TextView)v.findViewById(R.id.textview_location_info)).setText(av.location);
        ((TextView)v.findViewById(R.id.textview_opentime_info)).setText(av.openTime);
        ((TextView)v.findViewById(R.id.textview_phone_info)).setText(av.phone);
        ((TextView)v.findViewById(R.id.textview_description_info)).setText(av.description);
        ((TextView)v.findViewById(R.id.textview_tags_info)).setText(av.tags);
        ((TextView)v.findViewById(R.id.textview_rating_info)).setText(
            String.format(Locale.KOREA, "%.1f점 (%d명)", av.rating, av.voteCount)
        );
        av.PrintDevicesList(getContext(), v, av.devices);
        v.findViewById(R.id.button_googlemap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                intent.putExtra("title", av.placeName);
                intent.putExtra("latitude", av.latitude);
                intent.putExtra("longitude", av.longitude);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.button_navermap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SimpleWebViewActivity.class);
                intent.putExtra("title", av.placeName);
                intent.putExtra("url",
                    String.format(Locale.KOREA, "http://m.map.naver.com/route.nhn?menu=route&ename=%s&ex=%f&ey=%f", av.placeName, av.longitude, av.latitude)
                );
                startActivity(intent);
            }
        });
    }
}
