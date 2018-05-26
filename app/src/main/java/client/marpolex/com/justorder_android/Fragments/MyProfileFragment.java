package client.marpolex.com.justorder_android.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import client.marpolex.com.justorder_android.Models.User;
import client.marpolex.com.justorder_android.R;

/**
 * Created by mario on 22/03/2018.
 */

public class MyProfileFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.myprofile, container, false);
        onCreate();
        return myView;
    }

    public void onCreate() {
        //Set profile data
        TextView name = (TextView) myView.findViewById(R.id.tvName);
        TextView surname = (TextView) myView.findViewById(R.id.tvSurname);
        TextView expPoints = (TextView) myView.findViewById(R.id.tvExpPoints);
        TextView gender = (TextView) myView.findViewById(R.id.tvGender);
        TextView age = (TextView) myView.findViewById(R.id.tvAge);

        User user = User.listAll(User.class).get(0); //Obtiene el primer usuario. Solo debería haber uno.

        name.setText(user.getName());
        surname.setText(user.getSurname());
        expPoints.setText(user.getExpPoints() + " " + getString(R.string.expPoints));
        age.setText(user.getBirthDate());
        switch (user.getGender()) {
            case 0:
                gender.setText("Indefinido");
                break;
            case 1:
                gender.setText("Hombre");
                break;
            case 2:
                gender.setText("Mujer");
                break;
        }
    }
}
