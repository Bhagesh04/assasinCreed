package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
public class ImaginaryFriendDetailsActivity extends AppCompatActivity implements netServiceModule.NetworkingListener {
        JsonModule jsonService;
        netServiceModule networkingManager;
        Bitmap image;
        Bundle bundle;
        FriendListManager iFManager;
    FragmentManager fm = getFragmentManager();
    FragmentTransaction trans = fm.beginTransaction();
    MainFragment firstFragment = new MainFragment();
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imaginary_friend_details);
        iFManager = ((DatingApplication)getApplication()).getIFManager();
        networkingManager = ((DatingApplication)getApplication()).getNetworkingService();
        networkingManager.listener = this;
        jsonService = ((DatingApplication)getApplication()).getJsonService();
        FloatingActionButton fab = findViewById(R.id.fab2);
        String data = "";
        String URLString  = "";
        if (getIntent().hasExtra("data")) {
            data = getIntent().getStringExtra("data");
            id = getIntent().getIntExtra("ID", 0);
            bundle = new Bundle();
            firstFragment.setArguments(bundle);
            bundle.putString("data", data);
            URLString = jsonService.getImageUrl(data);
            Intent intent = new Intent(this, SelectedListClass.class);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction trans = fm.beginTransaction();
            MainFragment firstFragment = new MainFragment();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iFManager.deleteImaginaryFriend(id);
                    startActivity(intent);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Friend Removed from The list",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            networkingManager.getImage(URLString);
        }
    }

    @Override
    public void dataListener(String jsonString) {}
    @Override
    public void imageListener(Bitmap imagea) {
        bundle.putParcelable("image", imagea);
        trans.replace(R.id.add_remove_area, firstFragment);
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }
}