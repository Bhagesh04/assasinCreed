package com.example.finalproject;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;
public class netServiceModule {
    private String api = "https://randomuser.me/api/";
    public static ExecutorService netExecutor = Executors.newFixedThreadPool(4);
    public static Handler networkingHandler = new Handler(Looper.getMainLooper());
    interface NetworkingListener{
        void dataListener(String jsonString);
        void imageListener(Bitmap img);
    }
    public NetworkingListener listener;
    public void getIF(){
        netExecutor.execute(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection httpsURLConnection = null;
                try {
                    String jsonData="";
                    URL url = new URL(api);
                    httpsURLConnection = (HttpsURLConnection ) url.openConnection();
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.setRequestProperty("Content-Type","application/json");
                    InputStream in = httpsURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int inputSteamData = 0;
                    while ( (inputSteamData = reader.read()) != -1){
                        char current = (char)inputSteamData;
                        jsonData += current;
                    }
                    final String finalData = jsonData;
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.dataListener(finalData);
                        }
                    });
                } catch (MalformedURLException err) {
                    err.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    httpsURLConnection.disconnect();
                }
            }
        });
    }
    public void getImage(String urlS) {
        netExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap image = null;
                try {
                    URL url = new URL(urlS);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException err) {
                    System.out.println(err);
                }
                final Bitmap finalImage = image;
                networkingHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.imageListener(finalImage);
                    }
                });
            }
        });
    }

}
