package me.blog.njw1204.arcadeseoul;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.willy.ratingbar.ScaleRatingBar;

import java.io.IOException;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPlaceReviewWrite extends Fragment {
    public FragmentPlaceReviewWrite() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_place_review_write, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((EditText)view.findViewById(R.id.editText_review_write)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final View view = getView();
                ((TextView)view.findViewById(R.id.textView_review_char_count)).setText(
                    String.format(Locale.KOREA, "%d/150 글자", s.length())
                );
            }
        });
        view.findViewById(R.id.button_review_contents_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = getView();
                ((EditText)view.findViewById(R.id.editText_review_write)).setText("");
            }
        });
        view.findViewById(R.id.button_review_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = getView();
                String content = ((EditText)view.findViewById(R.id.editText_review_write)).getText().toString();
                float star = ((ScaleRatingBar)view.findViewById(R.id.ratingBar_review_write_rating)).getRating();

                if (star == 0) {
                    CUtils.SimpleDialogShow(getContext(), "별점을 부여해주세요.", true);
                    return;
                } else if (CUtils.IsEmpty(content)) {
                    CUtils.SimpleDialogShow(getContext(), "리뷰를 작성해주세요.", true);
                    return;
                } else if (content.length() > 150) {
                    CUtils.SimpleDialogShow(getContext(), "150자 이하로 입력해주세요.", true);
                    return;
                }

                final PlaceInfoActivity av = (PlaceInfoActivity)getActivity();
                PlaceRESTService rest = PlaceRESTService.retrofit.create(PlaceRESTService.class);
                Call<ResponseBody> http = rest.postReview(av.placeId, "익명", content, star);
                http.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.v("arcade-seoul", response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getContext(), "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), PlaceInfoActivity.class);
                        intent.putExtras(av.getIntent());
                        startActivity(intent);
                        av.finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "리뷰 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), PlaceInfoActivity.class);
                        intent.putExtras(av.getIntent());
                        startActivity(intent);
                        av.finish();
                    }
                });
            }
        });
    }
}
