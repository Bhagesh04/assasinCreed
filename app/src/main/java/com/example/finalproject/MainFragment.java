package com.example.finalproject;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class MainFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.main_fragment,container,false);
        TextView personName = v.findViewById(R.id.personNameId);
        TextView personAddress = v.findViewById(R.id.personState);
        TextView personAge = v.findViewById(R.id.personDateofBirthID);
        TextView personEmail = v.findViewById(R.id.personEmailID);
        ImageView img = v.findViewById(R.id.image);
        Bundle extras = getArguments();
        String data = extras.getString("data");Boolean flag = extras.getBoolean("flag");
            String nameStr = "";
            String emailStr = "";
            String cityStr= "";
            String ageStr = "";
            String imageStr = "";

        int count = 0;
        int length = data.length();

        for (int i = 0; i < length; i++) {
            char currentChar = data.charAt(i);
            if (currentChar == '$') {
                String subString = data.substring(count, i);
                if (nameStr.isEmpty()) {
                    nameStr = subString;
                } else if (cityStr.isEmpty()) {
                    cityStr = subString;
                } else if (emailStr.isEmpty()) {
                    emailStr = subString;
                } else if (ageStr.isEmpty()) {
                    ageStr = subString;
                } else if (imageStr.isEmpty()) {
                    imageStr = subString;
                }
                count = i + 1;
            }
        }
            personName.setText(nameStr);
            personAge.setText(ageStr);
            personEmail.setText(emailStr);
            personAddress.setText(cityStr);
            img.setImageBitmap(extras.getParcelable("image"));
        return v;

    }
}
