package com.example.finalproject;
public class FriendListManager {
    public void addNewImaginaryFriend(ImaginaryFriend iF){
        ImaginaryFriendDBClient.insertNewImaginaryFriend(iF);
    }
    public void deleteImaginaryFriend(int id){
        ImaginaryFriendDBClient.deleteImaginaryFriend(id);
    }
    public void deleteAllImaginaryFriend(){
        ImaginaryFriendDBClient.deleteAllImaginaryFriend();
    }
}
