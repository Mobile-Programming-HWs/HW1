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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutCompat = (LinearLayoutCompat) inflater.inflate(R.layout.fragment_course_list, container, false);
        recyclerView = layoutCompat.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        updateAdapter();
        setupSpinner();
        return layoutCompat;
    }

    private void updateAdapter() {
        adapter = new RecyclerAdapter(getContext(), MainActivity.departments.get(chosenDepartment).getCourses(), this);
        recyclerView.setAdapter(adapter);
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
        String message = String.format("%s" + '\n' +
                        "واحد: " + "%d" + '\n' +
                        "ظرفیت: " + "%d" + '\n' +
                        "زمان برگزاری: " + "%s" + " تا " + "%s" + '\n' +
                        "زمان امتحان: " + "%s",
                course.getInfo(), course.getUnits(), course.getCapacity(), course.getClassTimeBeginningText(), course.getClassTimeEndingText(), course.getExam_time());
        new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom)
                .setTitle(course.getName())
                .setMessage(message)
                .setPositiveButton("اضافه کن", (dialogInterface, i) -> {
                   if (SelectedCourse.addSelectedCourse(course)) {
                       Toast.makeText(getContext(), "درس موردنظر با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(getContext(), "درس موردنظر با دروس برنامه در تداخل است", Toast.LENGTH_SHORT).show();
                   }
                })
                .setNegativeButton("لغو", null)
                .show();
    }
}
