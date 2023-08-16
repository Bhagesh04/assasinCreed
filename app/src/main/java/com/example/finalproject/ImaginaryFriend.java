package com.example.finalproject;
import android.graphics.Bitmap;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity
public class ImaginaryFriend {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "data")
    public String data;
    @ColumnInfo(name = "url")
    public String url;
    @Ignore
    public Bitmap image;
    public ImaginaryFriend(){
        this.firstName = "Empty";
        this.data = "No Data Found";
        this.url = "No url found";
    }
    public ImaginaryFriend(String name, String data, String url, Bitmap image){
        this.firstName = name;
        this.data = data;
        this.url = url;
        this.image = image;
    }
    public ImaginaryFriend(ImaginaryFriend cpy) {
        this.firstName = cpy.firstName;
        this.data = cpy.data;
        this.url = cpy.url;
        this.image = cpy.image;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getData(){
        return data;
    }
    public int getId(){
        return id;
    }
    public String getURL(){
        return url;
    }

    public Bitmap getImage(){
        return image;
    }


}
