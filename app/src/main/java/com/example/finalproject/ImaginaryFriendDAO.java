package com.example.finalproject;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
@Dao
public interface ImaginaryFriendDAO {
    @Query("SELECT * FROM ImaginaryFriend")
    List<ImaginaryFriend> getAll();
    @Query("SELECT * FROM ImaginaryFriend WHERE first_name LIKE :first")
    List<ImaginaryFriend> findByName(String first);
    @Insert
    void insert(ImaginaryFriend imaginaryFriend);
    @Query("DELETE FROM ImaginaryFriend WHERE id = :Id")
    void deleteImaginaryFriend(int Id);
    @Query("Select * FROM ImaginaryFriend WHERE id = :Id")
    ImaginaryFriend getImaginaryFriendById(int Id);
    @Query("DELETE FROM ImaginaryFriend")
    void deleteAllImaginaryFriend();
}
