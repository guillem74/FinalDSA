package edu.upc.dsa.finaldsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Followers extends AppCompatActivity {

    private final String BASE_URL= "https://api.github.com";
    private List<Follower> listFollowers;
    private List<String> listNames;
    private ListView lv;
    private ProgressBar pb;

    //FEM UN GETFOLLOWERS AMB EL PARAMETRE QUE HEM OBTINGUT COM NAME
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        try {
            Bundle extra = getIntent().getExtras();
            String name = extra.getString("name");
            getFollowers(name);
        }catch (Exception e){
            Toast.makeText(Followers.this, "Error1", Toast.LENGTH_SHORT).show();
        }

    }
        //FUNCIO QUE MOSTRA ELS SEGUIDORS QUE TENIM
    private void getFollowers(String name){
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
            //LLISTA DE SEGUIDORS
            //***************Comprobacion de que recoge los datos**********
            @Override
            public void onResponse(Call<List<Follower>> call, Response<List<Follower>> response) {
                pb =(ProgressBar) findViewById(R.id.progressBar);
                if(response.code()==200){

                    listFollowers=(List<Follower>) response.body();
                    lv = (ListView) findViewById(R.id.listV);
                    listNames = new ArrayList<>();
                    for (int j=0; j < listFollowers.size(); j++){
                        String item = listFollowers.get(j).getLogin();
                        listNames.add (item);
                    }
                    ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>
                            (Followers.this, android.R.layout.simple_list_item_1, listNames);
                    lv.setAdapter(arrayAdapter);
                }
                else if (response.code() == 404) {
                    Toast.makeText(Followers.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    Followers.this.finish();
                }
                else {
                    Toast.makeText(Followers.this, "Error2: "+response.code(), Toast.LENGTH_SHORT).show();
                    Followers.this.finish();
                }

                pb.setVisibility(ListView.GONE);
            }

            @Override
            public void onFailure(Call<List<Follower>> call, Throwable t) {
                Toast.makeText(Followers.this, "Error3", Toast.LENGTH_SHORT).show();
                Followers.this.finish();
            }
        });
    }
}