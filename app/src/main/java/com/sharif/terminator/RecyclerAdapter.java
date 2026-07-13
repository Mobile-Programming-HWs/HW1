package com.sharif.terminator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Course> courses;
    private SelectListener listener;

    public RecyclerAdapter(Context context, ArrayList<Course> courses, SelectListener listener) {
        this.context = context;
        this.courses = courses;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false);

        return new ItemViewHolder((layoutView));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Course course = (Course) courses.get(position);

        itemViewHolder.getName().setText(course.getName());
        itemViewHolder.getInstructor().setText(course.getInstructor());
        itemViewHolder.getCardView().setOnClickListener(view -> listener.onItemClicked(course));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}
