package com.example.digitaldocs.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.digitaldocs.R;

public class SearchActivityPage extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.search_page);
        setContentView(R.layout.search_page);
        toolbar = findViewById(R.id.myToolBar);

        setSupportActionBar(toolbar);
    }


    /**
     * Displaying the search bar and been able to type in it
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);


        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(SearchActivityPage.this, "SEARCH IS EXAPANDED", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(SearchActivityPage.this, "SEARCH IS COLLAPSE", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        /**

        menu.findItem(R.id.search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search Data here......");
         **/

        return true;
    }



}
