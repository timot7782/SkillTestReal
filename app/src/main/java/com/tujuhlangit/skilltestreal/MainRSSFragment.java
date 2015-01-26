package com.tujuhlangit.skilltestreal;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Timothy on 1/26/2015.
 */
public class MainRSSFragment extends Fragment{
    private static final String TAG = "MainRSSFragment";
    private TextView textView;
    private String[] rssSites;
    private ImageView imgView;

    public MainRSSFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rss, container, false);
        textView = (TextView) rootView.findViewById(R.id.drawer_list_text);
        rssSites = getResources().getStringArray(R.array.rss_sites);
        imgView = (ImageView) rootView.findViewById(R.id.imageView);
        return rootView;
    }
}
