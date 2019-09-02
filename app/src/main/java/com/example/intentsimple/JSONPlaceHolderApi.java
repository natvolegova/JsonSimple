package com.example.intentsimple;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {

    @GET("posts")
    Call<List<Post>> getPostsOfUser(
            @Query("userId") Integer[] userId, //чтобы выбрать всех можно в параметре указать null
            @Query("_sort") String sort, //чтобы выбрать любую сортировку нужно добавить просто null
            @Query("_order") String order
    );

    @GET("/posts/{id}/comments")
    Call<List<Comment>> getPostComments(@Path("id") int postId);

    @GET("/comments")
    Call<List<Comment>> getPostComments2(@Query("postId") int postId);

}
