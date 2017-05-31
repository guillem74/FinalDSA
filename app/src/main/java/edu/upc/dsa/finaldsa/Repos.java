package edu.upc.dsa.finaldsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class Repos extends AppCompatActivity {

    private final String BASE_URL= "https://api.github.com";
    private List<Follower> listFollowers;
    private List<String> listNames;
    private ListView lv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
        try {
            Bundle extra = getIntent().getExtras();
            String name = extra.getString("name");
            getFollowers(name);
        }catch (Exception e){
            Toast.makeText(Repos.this, "Error1", Toast.LENGTH_SHORT).show();
        }

    }
}
