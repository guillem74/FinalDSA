package edu.upc.dsa.finaldsa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.bumptech.glide.Glide;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Followers extends AppCompatActivity {

    private final String BASE_URL= "https://api.github.com"; //URL
    private List<Follower> listaFollow;
    private List<String> listaNombres;
    private ListView listv;
    private ImageView imagen;
    private ProgressBar progressbar;
    final String tag = "MAPACT";

    //FEM UN GETFOLLOWERS AMB EL PARAMETRE QUE HEM OBTINGUT COM NAME
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        //LIST OF FOLLOWERS
        try {
            Bundle extra = getIntent().getExtras();
            String name = extra.getString("name");

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
//
            Retrofit retrofit =
                    builder.client(httpClient.build()).build();

            // Create an instance of our GitHub API interface.
            Service getList = retrofit.create(Service.class);

            // Create a call instance for looking up Retrofit contributors.
            Call<List<Follower>> call = getList.getList(name);

            // Fetch and print a list of the contributors to the library.
            call.enqueue(new Callback<List<Follower>>() {
                @Override
                public void onResponse(Call<List<Follower>> call, Response<List<Follower>> response) {
                    progressbar = (ProgressBar) findViewById(R.id.progress);
                    if(response.code()==200){
                        listaFollow=(List<Follower>) response.body();
                        listv = (ListView) findViewById(R.id.listaV);
                        listaNombres = new ArrayList<>();
                        for (int j=0; j < listaFollow.size(); j++){
                            String item = listaFollow.get(j).getLogin();
                            listaNombres.add (item);
                        }
                        ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>
                                (Followers.this, android.R.layout.simple_list_item_1, listaNombres);
                        listv.setAdapter(arrayAdapter);
                    }
                    else {
                        Toast.makeText(Followers.this, "No funciona: "+response.code(), Toast.LENGTH_SHORT).show();
                        Followers.this.finish();
                    }
                    progressbar.setVisibility(ListView.GONE);
                }

                @Override
                public void onFailure(Call<List<Follower>> call, Throwable t) {
                    Toast.makeText(Followers.this, "No funciona", Toast.LENGTH_SHORT).show();
                    Followers.this.finish();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(Followers.this, "No funciona", Toast.LENGTH_SHORT).show();
        }


        //NAME AND IMAGE USER
        try {
            Bundle extra = getIntent().getExtras();
            String name = extra.getString("name");

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
//
            Retrofit retrofit =
                    builder.client(httpClient.build()).build();

            // Create an instance of our GitHub API interface.
            Service getList = retrofit.create(Service.class);

            // Create a call instance for looking up Retrofit contributors.
            Call<Follower> call = getList.getFollower(name);

            // Fetch and print a list of the contributors to the library.
            call.enqueue(new Callback<Follower>() {
                @Override
                public void onResponse(Call<Follower> call, Response<Follower> response) {
                    if(response.code()==200){
                        Follower a = (Follower) response.body();
                        //NAME
                        String name = a.getName();
                        TextView visualizarnombre = (TextView)findViewById(R.id.textView2);
                        visualizarnombre.setText(name);
                        Log.d(tag, " !!!!!iconName: "+name);

                        //NUMBER OF REPOS
                        int numrepos = a.getPublic_repos();
                        TextView visualizarrepos = (TextView)findViewById(R.id.textView5);
                        visualizarrepos.setText(""+numrepos);
                        Log.d(tag, " numrepos: "+numrepos);

                        //NUMBER OF FOLLOWINGS
                        int numfollow = a.getFollowing();
                        TextView visualizarfollows = (TextView)findViewById(R.id.textView6);
                        visualizarfollows.setText(""+numfollow);
                        Log.d(tag, " numfollows: "+numrepos);

                        //IMAGE
                        String AvatarUrl = a.getAvatar_url();
                       /* try {
                        String AvatarUrl = a.getAvatar_url();
                         try {
                              drawableFromUrl(AvatarUrl);
                          } catch (IOException e) {
                              e.printStackTrace();
                          }*/
                        ImageView imageView = (ImageView) findViewById(R.id.imageView);
                        Log.d(tag, "IMAGE");
                        Glide.with(Followers.this)
                                .load(AvatarUrl)
                                .into(imageView);
                    }
                    else {
                        Toast.makeText(Followers.this, "No funciona 1: "+response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Follower> call, Throwable t) {
                    Toast.makeText(Followers.this, "No funciona 2", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(Followers.this, "No funciona", Toast.LENGTH_SHORT).show();
        }

    }
   /* public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }
    */
}