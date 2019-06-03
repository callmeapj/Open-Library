package com.example.aprajeyakhouri.finalapp;

import android.app.Activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView textView2,Title,author,publishyear;
    SearchView searchView;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        textView2 = findViewById(R.id.textView2);
        searchView=findViewById(R.id.search);
        submit=findViewById(R.id.submit);
        Title=findViewById(R.id.Title);
        author=findViewById(R.id.author);
        publishyear=findViewById(R.id.publishyear);

       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {

                    case R.id.menu1:
                        fragment = new Fragment1();
                        break;
                    case R.id.menu2:
                        fragment = new Fragment2();
                        break;
                    case R.id.menu3:
                        fragment = new Fragment3();
                        break;
                }

                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.relative_layout,fragment);
                    ft.commit();
                }

                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String title=searchView.getQuery().toString();
               callRetro(title);
           }
       });
        }
        public void callRetro(String title){
            Retrofit retrofit=new Retrofit.Builder().baseUrl("http://openlibrary.org/").addConverterFactory(GsonConverterFactory.create()).build();

            Basicsearchservice basicsearchservice=retrofit.create(Basicsearchservice.class);
            basicsearchservice.getsearchdata(title).enqueue(new Callback<Basicsearch>() {
                @Override
                public void onResponse(Call<Basicsearch> call, Response<Basicsearch> response) {
                    Basicsearch basicsearch=response.body();
                    Title.setText(basicsearch.docs.book.title_suggest);
                    author.setText(basicsearch.docs.book.author_name);
                    publishyear.setText(basicsearch.docs.book.first_publish_year);

                }

                @Override
                public void onFailure(Call<Basicsearch> call, Throwable t) {
                    Toast.makeText(Main2Activity.this, "Book not available", Toast.LENGTH_SHORT).show();

                }
            });
    }
}