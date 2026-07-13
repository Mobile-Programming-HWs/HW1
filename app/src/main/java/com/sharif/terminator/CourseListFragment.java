package com.sharif.terminator;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseListFragment extends Fragment implements SelectListener {
    private int chosenDepartment = 0;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private LinearLayoutCompat layoutCompat;
    private String searchQuery = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutCompat = (LinearLayoutCompat) inflater.inflate(R.layout.fragment_course_list, container, false);
        recyclerView = layoutCompat.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        updateAdapter();
        setupSearchView();
        setupSpinner();
        return layoutCompat;
    }

    private void updateAdapter() {
        ArrayList<Course> courses = MainActivity.departments.get(chosenDepartment).getCourses();
        adapter = new RecyclerAdapter(getContext(), CourseSearch.filter(courses, searchQuery), this);
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchView() {
        SearchView searchView = layoutCompat.findViewById(R.id.course_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query == null ? "" : query;
                updateAdapter();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText == null ? "" : newText;
                updateAdapter();
                return true;
            }
        });
    }

    private void setupSpinner() {
        Spinner spinner = layoutCompat.findViewById(R.id.spinner);
        ArrayList<String> departmentNames = Department.getDepartmentNames();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                departmentNames);
        arrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenDepartment = i;
                updateAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onItemClicked(Course course) {
        String capacityText = course.getCapacity() > 0 ? String.valueOf(course.getCapacity()) : "Not set";
        String message = course.getInfo() + '\n' +
                "Course number: " + course.getCourse_number() + '\n' +
                "Units: " + course.getUnits() + '\n' +
                "Capacity: " + capacityText + '\n' +
                "Class time: " + course.getScheduleSummaryText() + '\n' +
                "Exam time: " + course.getExamTimeText();
        new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom)
                .setTitle(course.getName())
                .setMessage(message)
                .setPositiveButton("Add course", (dialogInterface, i) -> {
                    SelectedCourse.AddCourseStatus status = SelectedCourse.addSelectedCourseWithStatus(course);
                    if (status.isAdded()) {
                        SelectedCourseStorage.save(getContext());
                    }
                    Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
