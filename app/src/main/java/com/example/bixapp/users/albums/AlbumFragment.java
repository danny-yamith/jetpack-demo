package com.example.bixapp.users.albums;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bixapp.R;
import com.example.bixapp.common.LineItemDecoration;
import com.example.bixapp.users.albums.photos.PhotosFragment;

import java.util.List;

public class AlbumFragment extends Fragment {

    private RecyclerView rvAlbum;
    private AlbumAdapter albumAdapter;
    private int userId;
    private ViewGroup container;

    private AlbumViewModel albumViewModel;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Album album = albumViewModel.getAlbums().getValue().get(position);
            PhotosFragment photosFragment = PhotosFragment.newInstance();
            photosFragment.setAlbumId(album.getId());
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(container.getId(), photosFragment, "FRG_PHOTO")
                    .addToBackStack(null)
                    .commit();


        }
    };

    public AlbumFragment() {
    }

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        this.container = container;

        final ProgressBar pbar = view.findViewById(R.id.pbar_albums);
        final TextView txtEmpty = view.findViewById(R.id.txtEmpty);

        rvAlbum = view.findViewById(R.id.rvAlbum);
        rvAlbum.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAlbum.setHasFixedSize(true);
        rvAlbum.setHasFixedSize(true);
        rvAlbum.setItemAnimator(new DefaultItemAnimator());
        rvAlbum.addItemDecoration(new LineItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL, 36));

        albumAdapter = new AlbumAdapter();
        albumAdapter.setOnItemClickListener(onItemClickListener);
        rvAlbum.setAdapter(albumAdapter);

        albumViewModel = new AlbumViewModel();
        albumViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);

        albumViewModel.getAlbums().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(@Nullable List<Album> albums) {
                albumAdapter.setUsers(albums);
            }
        });
        albumViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                pbar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                txtEmpty.setVisibility(!isLoading && albumViewModel.getAlbums().getValue().isEmpty() ?
                        View.VISIBLE : View.GONE);

            }
        });

        albumViewModel.loadPosts(userId);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        albumViewModel.clearData();
    }

}
