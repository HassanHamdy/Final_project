package com.hamdi.hasan.hi_news.View;




import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hamdi.hasan.hi_news.Model.articles;
import com.hamdi.hasan.hi_news.Model.source;
import com.hamdi.hasan.hi_news.R;
import com.hamdi.hasan.hi_news.View.fragment.DetailsFragment;
import com.hamdi.hasan.hi_news.View.fragment.MainFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(getIntent().getStringExtra("frag") != null){
            Intent intent = getIntent();
            final articles Data = new articles(new source("-1", intent.getStringExtra("source_Name")),
                    " ", intent.getStringExtra("title"), intent.getStringExtra("description"),
                    "", intent.getStringExtra("image"),intent.getStringExtra("publishAt"), 1);

            Fragment fragment = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("newsDetails",Data);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment,fragment)
                    .addToBackStack(null).commit();
        }else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment,new MainFragment())
                    .addToBackStack(null).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.favourite_menu_item:
                startActivity(new Intent(MainActivity.this, FavouriteActivity.class));
        }
        return true;
    }
}
