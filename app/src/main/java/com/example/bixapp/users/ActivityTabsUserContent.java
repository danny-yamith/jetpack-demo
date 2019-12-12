package com.example.bixapp.users;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.bixapp.R;
import com.example.bixapp.users.UserTabPagerAdapter;
import com.example.bixapp.users.User;

public class ActivityTabsUserContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tabs_user_content);
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        TextView title = findViewById(R.id.titleTab);

        User user = getIntent().getExtras().getParcelable("user");
        title.setText(user.getFullName());

        UserTabPagerAdapter sectionsPagerAdapter = new UserTabPagerAdapter(this, getSupportFragmentManager());
        sectionsPagerAdapter.setUserId(user.getId());
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs.setupWithViewPager(viewPager);

    }
}