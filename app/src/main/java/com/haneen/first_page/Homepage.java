package com.haneen.first_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Homepage extends AppCompatActivity {
    TextView account_Number;
    TextView amount;
    TextView type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        account_Number = findViewById(R.id.account_Number);
        amount = findViewById(R.id.amount);
        type = findViewById(R.id.type);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mocki.io/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<List<Post2>> call = jsonApi.getPosts2();
        call.enqueue(new Callback<List<Post2>>() {
            @Override
            public void onResponse(Call<List<Post2>> call, Response<List<Post2>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("not successful");
                }

//                Bundle data = getIntent().getExtras();
//                String name;
//                if(data != null){
//                    name = data.getString("name");
//                }else{
//                    name = data.getString("name");;
//                }

                Bundle bundle = getIntent().getExtras();
                String name = bundle.getString("name");
                System.out.println("user: " + name);

                List<Post2> posts = response.body();
                for (Post2 post : posts) {
                    if (name.equals(post.getUsername())) {
                        char[] arrString = String.valueOf(post.getAccountnumber()).toCharArray();
                        for (int i = 0; i < arrString.length; i++) {
                            System.out.println("i: " + arrString[i]);
                            if (i < 6) {
                                arrString[i] = '*';
                            } else
                                arrString[i] = arrString[i];
                            System.out.println("i: " + arrString[i]);
                        }
                        String finalNumber = String.valueOf(arrString);
                        account_Number.setVisibility(View.VISIBLE);
                        amount.setVisibility(View.VISIBLE);
                        type.setVisibility(View.VISIBLE);
                        account_Number.setText(finalNumber);
                        amount.setText(String.valueOf(post.getAmount()));
                        type.setText(post.getType());
//                        System.out.println("user: "+post.getUsername());
//                        System.out.println("user: "+post.getAccountnumber());
//                        System.out.println("user: "+post.getAmount());
//                        System.out.println("user: "+post.getType());

                        break;
                    }


                }
            }

            @Override
            public void onFailure(Call<List<Post2>> call, Throwable t) {

            }
        });


    }
}