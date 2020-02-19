package com.studentlog.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.studentlog.R;
import com.studentlog.dashboard.fragment.StudentFormFragment;
import com.studentlog.dashboard.fragment.studentlist.StudentListFragment;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDraweLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        mDraweLayout = findViewById(R.id.drawer_lay);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mToggle = new ActionBarDrawerToggle(this, mDraweLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDraweLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new StudentListFragment()).commit();

    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_student_list: {
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new StudentListFragment()).commit();
                //Toast.makeText(this, "list is open", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_student_form: {
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new StudentFormFragment()).commit();
                //Toast.makeText(this, "form is open", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        mDraweLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
