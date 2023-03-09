package com.haneen.first_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Authentication extends AppCompatActivity {
    EditText username;
    EditText password;
    Button submit;
    TextView text;
    Toolbar toolbar;
    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        submit=findViewById(R.id.submit);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        text= findViewById(R.id.textView9);
        toolbar = findViewById(R.id.toolbar2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://mocki.io/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonApi jsonApi = retrofit.create(JsonApi.class);
                Call<List<Post>> call=jsonApi.getPosts();
                call.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        if (!response.isSuccessful()){
                            System.out.println("not successful");
                        }
                        List<Post> posts=response.body();
                        for(Post post:posts){
                            if(username.getText().toString().equals(post.getUsername())&&password.getText().toString().equals(post.getPassword())){
                                System.out.println("user: "+post.getUsername());
                                System.out.println("password: "+post.getPassword());
                                text.setVisibility(View.VISIBLE);
                                text.setText("successful");
                                Toast.makeText(Authentication.this,"successful", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(Authentication.this, Homepage.class);
                                String usernamevalue=username.getText().toString();

                                Bundle bundle = new Bundle();
                                bundle.putString("name", usernamevalue);
                                i.putExtras(bundle);
                                startActivity(i);
                                Intent intent = new Intent(Authentication.this, Homepage.class);
                                startActivity(intent);
                                break;

                            }
                            System.out.println("user not: "+post.getUsername());
                            System.out.println("password not: "+post.getPassword());


                        }
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {

                    }
                });



            }
        });


    }


}