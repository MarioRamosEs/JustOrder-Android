package client.marpolex.com.justorder_android.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import client.marpolex.com.justorder_android.R;

/**
 * Created by mario on 22/03/2018.
 */

public class ScanFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.scan, container, false);
        return myView;
    }
}
