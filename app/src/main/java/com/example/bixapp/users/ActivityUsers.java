package com.example.bixapp.users;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bixapp.R;
import com.example.bixapp.common.LineItemDecoration;

import java.util.List;

public class ActivityUsers extends AppCompatActivity {

    private UserViewModel userViewModel;
    private RecyclerView rvUser;
    private SearchView svUser;
    private ImageView closeButton;
    private UserAdapter userAdapter;
    private boolean isLoading = false;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            User user = userViewModel.getUsers().getValue().get(position);
            Intent intent = new Intent(ActivityUsers.this, ActivityTabsUserContent.class);
            intent.putExtra("user", user);
            startActivity(intent);
            ActivityUsers.this.overridePendingTransition(R.anim.in_right_left, R.anim.out_right_left);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        final ProgressBar pbarUsers = findViewById(R.id.pbar_users);
        final TextView txtEmpty = findViewById(R.id.txtEmpty);

        svUser = findViewById(R.id.svUser);
        rvUser = findViewById(R.id.rvUsers);
        closeButton = svUser.findViewById(R.id.search_close_btn);

        svUser.setFocusable(true);

        rvUser.setLayoutManager(new LinearLayoutManager(this));
        rvUser.setHasFixedSize(true);
        rvUser.setItemAnimator(new DefaultItemAnimator());
        rvUser.addItemDecoration(new LineItemDecoration(this, DividerItemDecoration.VERTICAL, 36));

        userAdapter = new UserAdapter(this);
        userAdapter.setOnItemClickListener(onItemClickListener);
        rvUser.setAdapter(userAdapter);

        userViewModel = new UserViewModel();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                userAdapter.setUsers(users);
            }
        });

        svUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svUser.setIconified(false);
            }
        });

        userViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                pbarUsers.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                txtEmpty.setVisibility(!isLoading && userViewModel.getUsers().getValue().isEmpty() ?
                        View.VISIBLE : View.GONE);
            }
        });

        initScrollListener();
        initSearchViewListener();

    }

    private void initSearchViewListener() {
        svUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String oldQuery = userViewModel.getQuery().getValue();
                if (query != oldQuery) {
                    userViewModel.resetPage();
                }
                userViewModel.setQuery(query);
                userViewModel.loadUsers();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.search_src_text);
                et.setText("");
                svUser.setQuery("", false);
                userViewModel.setQuery(null);
                userViewModel.resetPage();
                userViewModel.loadUsers();
                svUser.onActionViewCollapsed();
            }
        });
    }

    private void initScrollListener() {
        rvUser.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading
                    && linearLayoutManager != null
                    && linearLayoutManager.findLastCompletelyVisibleItemPosition() == userAdapter.getItemCount() - 1
                ) {
                        userViewModel.loadNextPage();
                }
            }
        });
    }
}
