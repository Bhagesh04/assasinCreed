package com.example.finalproject;
import android.app.Application;
public class DatingApplication extends Application {
    private FriendListManager FriendManager = new FriendListManager();
    public FriendListManager getIFManager() {
        return FriendManager;
    }
    private netServiceModule netServ = new netServiceModule();
    public JsonModule getJsonService() {
        return jsonService;
    }
    private JsonModule jsonService = new JsonModule();


    public netServiceModule getNetworkingService() {
        return netServ;
    }

}
