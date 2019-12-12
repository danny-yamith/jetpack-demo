package com.example.bixapp.users.albums;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bixapp.R;

public class GalleryFragment extends Fragment {

    private int userId;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        AlbumFragment frg = AlbumFragment.newInstance();
        frg.setUserId(userId);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.gallery_container, frg);
        ft.commit();

        return view;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
