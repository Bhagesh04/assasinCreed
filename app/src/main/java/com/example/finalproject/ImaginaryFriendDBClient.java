package com.example.finalproject;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.room.Room;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ImaginaryFriendDBClient {
    static ImaginaryFriendDB DataUser;
    static Context dataBaseContext;
    interface DataBaseListener{
        public void ListOfImaginaryFriendsListener(List<ImaginaryFriend> iFList);
        public void ImaginaryFriendsListener(ImaginaryFriend iF);
    }
   static DataBaseListener listener;
    public static final ExecutorService dbExectuor = Executors.newFixedThreadPool(4);
    public static Handler handler = new Handler(Looper.getMainLooper());
    ImaginaryFriendDBClient(Context context){
        dataBaseContext = context;
        DataUser= Room.databaseBuilder(context, ImaginaryFriendDB.class, "database-ImaginaryFriend").build();
    }
    public static ImaginaryFriendDB getDbClient(){
        if(DataUser == null){
            DataUser = new ImaginaryFriendDBClient(dataBaseContext).DataUser;
        }
        return DataUser;
    }
    public static void insertNewImaginaryFriend(ImaginaryFriend newImaginaryFriend){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                DataUser.getImaginaryFriendDao().insert(newImaginaryFriend);
            }
        });
    }
    public static void deleteImaginaryFriend(int id){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                DataUser.getImaginaryFriendDao().deleteImaginaryFriend(id);
            }
        });
    }
    public static void deleteAllImaginaryFriend(){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                DataUser.getImaginaryFriendDao().deleteAllImaginaryFriend();
            }
        });
    }
    public static void getAllImaginaryFriends(){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                List<ImaginaryFriend> listOfIF = DataUser.getImaginaryFriendDao().getAll();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.ListOfImaginaryFriendsListener(listOfIF);

                    }
                });
            }
        });
    }
    public static void getImaginaryFriendsbyName(String name){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
               List<ImaginaryFriend> iF = DataUser.getImaginaryFriendDao().findByName(name);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.ListOfImaginaryFriendsListener(iF);
                    }
                });
            }
        });
    }

    public static void getImaginaryFriendsbyId(int Id){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                ImaginaryFriend iF = DataUser.getImaginaryFriendDao().getImaginaryFriendById(Id);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.ImaginaryFriendsListener(iF);

                    }
                });
            }
        });
    }



}
