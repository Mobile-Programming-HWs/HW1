package com.sharif.terminator;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public static ArrayList<Department> departments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initializeDepartments();
        addItemsFromJSON();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CourseListFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_course_lists);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_course_lists:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CourseListFragment()).commit();
                break;
            case R.id.nav_weakly_chart:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WeaklyChartFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initializeDepartments() {
        departments.clear();
        Department.clearDepartmentNames();
        departments.add(new Department(R.raw.d3, "دانشکده ریاضی"));
        departments.add(new Department(R.raw.d5, "دانشکده فیزیک"));
        departments.add(new Department(R.raw.d38, "دانشکده کامپیوتر"));
    }


    private void addItemsFromJSON() {
        try {

            for (int j = 0; j < departments.size(); j++) {
                Department department = departments.get(j);
                String jsonDataString = readJSONDataFromFile(department.getFile_name());
                JSONArray jsonArray = new JSONArray(jsonDataString);

                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject itemObj = jsonArray.getJSONObject(i);

                    String info = itemObj.getString("info");
                    String course_id = itemObj.getString("course_id");
                    String course_number = itemObj.getString("course_number");
                    String name = itemObj.getString("name");
                    int units = itemObj.getInt("units");
                    int capacity = itemObj.getInt("capacity");
                    String instructor = itemObj.getString("instructor");
                    String class_times = itemObj.getString("class_times");
                    int id = itemObj.getInt("id");
                    String exam_time = itemObj.getString("exam_time");

                    Course course = new Course(info, course_id, course_number, name, units, capacity, instructor, class_times, id, exam_time);
                    department.addCourse(course);
                }

            }

        } catch (JSONException | IOException e) {
            Log.d("MainActivity", "addItemsFromJSON: ", e);
        }
    }


    private String readJSONDataFromFile(int resourceName) throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {

            String jsonString;
            inputStream = getResources().openRawResource(resourceName);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            while ((jsonString = bufferedReader.readLine()) != null) {
                builder.append(jsonString);
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }
}
