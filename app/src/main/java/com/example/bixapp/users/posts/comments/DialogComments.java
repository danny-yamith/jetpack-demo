package com.example.bixapp.users.posts.comments;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bixapp.R;
import com.example.bixapp.users.posts.comments.CommentsDialogAdapter;
import com.example.bixapp.users.posts.comments.Comment;
import com.example.bixapp.users.posts.comments.CommentViewModel;

import java.util.List;

public class DialogComments extends DialogFragment {

    private ListView lvComments;
    private CommentViewModel commentViewModel;
    private CommentsDialogAdapter adapter;
    private int postId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_comments_posted, null);
        final ProgressBar pbar = view.findViewById(R.id.pbar_comments);
        final TextView txtEmpty = view.findViewById(R.id.txtEmpty);

        lvComments = view.findViewById(R.id.lvComments);

        if (savedInstanceState != null) {
            postId = savedInstanceState.getInt("id");
        }

        adapter = new CommentsDialogAdapter(this.getActivity().getApplicationContext());
        lvComments.setAdapter(adapter);

        commentViewModel = new CommentViewModel();
        commentViewModel = ViewModelProviders.of(this).get(CommentViewModel.class);
        commentViewModel.getComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable List<Comment> comments) {
                adapter.setComments(comments);
            }
        });

        commentViewModel.loadComments(postId);

        commentViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                pbar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                txtEmpty.setVisibility(!isLoading && commentViewModel.getComments().getValue().isEmpty() ?
                        View.VISIBLE : View.GONE);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("Comentarios");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("postId", postId);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
        getDialog().getWindow().setWindowAnimations(
                R.style.dialog_animation_fade);
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}