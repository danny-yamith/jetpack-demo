package com.example.bixapp.users.posts.comments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bixapp.R;

import java.util.ArrayList;
import java.util.List;

public class CommentsDialogAdapter extends BaseAdapter {

    private List<Comment> comments;
    private Context context;

    public CommentsDialogAdapter(Context context) {
        this.context = context;
        this.comments = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_comment, null);
            Comment comment = comments.get(position);

            TextView name = view.findViewById(R.id.txtName);
            TextView body = view.findViewById(R.id.txtBody);

            name.setText(comment.getName());
            body.setText(comment.getBody());

        }
        return view;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }
}
