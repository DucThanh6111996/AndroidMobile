package com.mta.studyenglish.activity;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mta.studyenglish.R;
import com.mta.studyenglish.fragment.ExercisesFragment;
import com.mta.studyenglish.fragment.GrammarListFragment;
import com.mta.studyenglish.helper.PrefManager;
import com.mta.studyenglish.model.GrammarItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GrammarListFragment grammarListFragment = new GrammarListFragment();
    private ExercisesFragment exercisesFragment = new ExercisesFragment();

    private int currentFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_main);

        initNavigation();

        showFragment(grammarListFragment);
        currentFragmentId = R.id.nav_grammar;
    }

    private void initNavigation() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        TextView tvUsername = header.findViewById(R.id.tv_username_header);
        tvUsername.setText(PrefManager.getUsername());
    }


    private long prevTime = 0;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long currentTime = System.currentTimeMillis();
            if (currentTime - prevTime <= 1500) {
                super.onBackPressed();
            } else {
                Toast.makeText(this, "Nhấn Back 2 lần liên tiếp để thoát!", Toast.LENGTH_SHORT).show();
            }
            prevTime = currentTime;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.grammar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_all_grammar) {
            grammarListFragment.filter(GrammarListFragment.FILTER_ALL_GRAMMAR);
            return true;
        } else if (id == R.id.action_marked_grammar) {
            grammarListFragment.filter(GrammarListFragment.FILTER_MARKED_GRAMMAR);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_grammar) {
            if (id != currentFragmentId) {
                showFragment(grammarListFragment);
                currentFragmentId = id;
            }
        } else if (id == R.id.nav_exercise) {
            if (id != currentFragmentId) {
                showFragment(exercisesFragment);
                currentFragmentId = id;
            }
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Fragment fragment) {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void goToGrammarDetail(GrammarItem grammarItem) {
        Intent mIntent = new Intent(this, GrammarDetailActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("item_grammar", grammarItem);

        mIntent.putExtras(b);
        startActivity(mIntent);
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng xuất");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PrefManager.setLogin(false);

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}
