package com.example.finalproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.util.List;
public class SelectedListClass extends AppCompatActivity implements ImaginaryFriendDBClient.DataBaseListener, AdapterClass.FavouriteClickListner{
    RecyclerView recyclerView;
    AdapterClass adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_list);
        recyclerView = findViewById(R.id.imaginaryFriendList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImaginaryFriendDBClient.listener = this;
        ImaginaryFriendDBClient.getAllImaginaryFriends();
    }
    @Override
    public void ListOfImaginaryFriendsListener(List<ImaginaryFriend> iFList) {
        adapter = new AdapterClass(this,iFList);
        recyclerView.setAdapter(adapter);
        setTitle("Search Favourites");
    }
    @Override
    public void ImaginaryFriendsListener(ImaginaryFriend iF) {
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchViewMenuItem.getActionView();
        String searchFor = searchView.getQuery().toString();
        if (!searchFor.isEmpty()) {
            searchView.setIconified(false);
            searchView.setQuery(searchFor, false);
        }

        searchView.setQueryHint("Search for Favourites");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ImaginaryFriendDBClient.getImaginaryFriendsbyName(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    @Override
    public void favouriteClicked(ImaginaryFriend selectediF) {
        Intent intent = new Intent(this,ImaginaryFriendDetailsActivity.class);
        intent.putExtra("data",selectediF.getData());
        intent.putExtra("URL",selectediF.getURL());
        intent.putExtra("ID",selectediF.getId());

        startActivity(intent);
    }
}
