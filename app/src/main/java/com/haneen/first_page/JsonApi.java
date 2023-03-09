package com.haneen.first_page;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface JsonApi {

    @GET("6f5b63b0-9ed4-40b6-b4f3-81e0ea43ef39")
    Call<List<Post>> getPosts();


    @GET("9dba2ce1-4918-4e03-a4c4-8d35dd256079")
    Call<List<Post2>> getPosts2();

}
