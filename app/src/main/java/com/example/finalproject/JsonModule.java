package com.example.finalproject;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
public class JsonModule {
    public ImaginaryFriend getImaginaryFriendData(String jsonString) {
        ImaginaryFriend iFData = new ImaginaryFriend();
        try {
            String data = "";
            JSONObject obj = new JSONObject(jsonString);
            JSONArray dataArray = obj.getJSONArray("results");
            JSONObject datObj = dataArray.getJSONObject(0);
            JSONObject friendName = datObj.getJSONObject("name");
            String titleVal = friendName.getString("title");
            String nameVal = friendName.getString("first");
            String surnameVal = friendName.getString("last");
            JSONObject friendAddress = datObj.getJSONObject("location");
            JSONObject streetNumber = friendAddress.getJSONObject("street");
            String streetValue = streetNumber.getString("number") +" "+ streetNumber.getString("name");
            String stateValue = friendAddress.getString("state");
            String cityValue = friendAddress.getString("city");
            String mailVal = datObj.getString("email");
            JSONObject DateOfbirth = datObj.getJSONObject("dob");
            String dateValue = DateOfbirth.getString("date");
            String ageValue = DateOfbirth.getString("age");
            JSONObject IFPicture = datObj.getJSONObject("picture");
            String pictureValue = IFPicture.getString("large");
            Bitmap myBitmap = null;
            try {
                URL url = new URL(pictureValue);
                myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException err) {
                System.out.println(err);
            }
            data = titleVal + ". " + nameVal + " " + surnameVal + "$" + streetValue + ", " + cityValue + " - " + stateValue + "$" + mailVal + "$" + ageValue + " - " + dateValue + "$" + pictureValue;
            iFData = new ImaginaryFriend(nameVal + " " + surnameVal, data, pictureValue, myBitmap);
        } catch (JSONException err) {
            err.printStackTrace();
        }
        return iFData;
    }
    public String getImageUrl(String data){
        String nameString = "";
        String emailString= "";
        String cityString = "";
        String ageString = "";
        String imageString = "";
        int count = 0;
        int length = data.length();
        for (int i = 0; i < length; i++) {
            char currentChar = data.charAt(i);
            if (currentChar == '$') {
                String subString = data.substring(count, i);
                if (nameString.isEmpty()) {
                    nameString = subString;
                } else if (cityString.isEmpty()) {
                    cityString = subString;
                } else if (emailString.isEmpty()) {
                    emailString = subString;
                } else if (ageString.isEmpty()) {
                    ageString = subString;
                    imageString = data.substring(i + 1, length);
                }
                count = i + 1;
            }
        }
        return  imageString;
    }

}