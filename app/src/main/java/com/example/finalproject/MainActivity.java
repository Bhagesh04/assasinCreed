package com.example.finalproject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
public class MainActivity extends AppCompatActivity implements netServiceModule.NetworkingListener {
    ImaginaryFriendDBClient dataBaseUser;
    FriendListManager FriendsManager;
    netServiceModule netManagement;
    Boolean isFavourte;
    ImaginaryFriend selectedFriend;
    JsonModule jsonService;
    Bundle bundleInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.actionButton);
        fab.setImageResource(R.drawable.ic_action_favorite);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        isFavourte = false;
         dataBaseUser = new ImaginaryFriendDBClient(this);
        FriendsManager = ((DatingApplication)getApplication()).getIFManager();
        netManagement = ((DatingApplication)getApplication()).getNetworkingService();
        netManagement.listener = this;
        jsonService = ((DatingApplication)getApplication()).getJsonService();
        netManagement.getIF();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFavourte) {
                    FriendsManager.addNewImaginaryFriend(new ImaginaryFriend(selectedFriend));
                    isFavourte = true;
                    Toast toast = Toast.makeText(getApplicationContext(), "Added to Favorite List!", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Already in the Favourite list!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
    private void makeFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MainFragment firstFragment = new MainFragment();
        bundleInstance = new Bundle();
        firstFragment.setArguments(bundleInstance);
        bundleInstance.putString("name", selectedFriend.getFirstName());
        bundleInstance.putString("data", selectedFriend.getData());
        bundleInstance.putParcelable("image", selectedFriend.getImage());
        transaction.replace(R.id.add_remove_area, firstFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    public void fetchImaginaryFriend(View view) {
        netManagement.getIF();
    }
    @Override
    public void dataListener(String jsonString) {
        isFavourte = false;
        selectedFriend = new ImaginaryFriend(jsonService.getImaginaryFriendData(jsonString));
        makeFragment();
    }
    @Override
    public void imageListener(Bitmap image) {
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.favorites: {
                Intent intent = new Intent(this, SelectedListClass.class);
                startActivity(intent);
                break;
            }
            case R.id.resetF: {
                FriendsManager.deleteAllImaginaryFriend();
                Toast toast = Toast.makeText(getApplicationContext(), "Removed from the list", Toast.LENGTH_SHORT);
                toast.show();
                break;
            }
        }
        return true;
    }
}