package me.blog.njw1204.arcadeseoul;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PlaceRESTService {
    @GET("arcade-seoul/place-list.php")
    Call<ResponseBody> getPlaceList(@Query("type") String type, @Query("area") String area, @Query("theme") String theme, @Query("fav") String fav);

    @GET("arcade-seoul/place-info.php")
    Call<ResponseBody> getPlaceInfo(@Query("id") int id);

    @FormUrlEncoded
    @POST("arcade-seoul/review-write.php")
    Call<ResponseBody> postReview(@Field("place_id") int place_id, @Field("author") String author,
                                  @Field("content") String content, @Field("star") float star);

    public final static Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl("http://apk.dothome.co.kr/")
                                                .build();
}
