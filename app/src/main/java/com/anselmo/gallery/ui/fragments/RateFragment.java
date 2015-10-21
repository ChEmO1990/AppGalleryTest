package com.anselmo.gallery.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anselmo.gallery.R;
import com.anselmo.gallery.db.Querys;
import com.anselmo.gallery.models.ImageGallery;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by naranya on 10/21/15.
 */
public class RateFragment extends Fragment {
    private ArrayList<ImageGallery> items;
    private static final String INDEX = "index";
    private int index;

    public static RateFragment newInstance( int index ) {

        // Instantiate a new fragment
        RateFragment fragment = new RateFragment();

        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putInt(INDEX, index);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.index = (getArguments() != null) ? getArguments().getInt(INDEX) : -1;

        items = Querys.getImagesFromDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_rate_image, container, false);

        ImageView image = (ImageView) rootView.findViewById(R.id.image_rate);
        TextView tvIndex = (TextView) rootView.findViewById(R.id.title_rate_image);

        tvIndex.setText(items.get(index).getTitle());

        Glide.with(getActivity())
                .load( new File( items.get(index).getPath() ))
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(image);

        return rootView;
    }
}
