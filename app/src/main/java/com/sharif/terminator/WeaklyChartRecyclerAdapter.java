package com.sharif.terminator;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class WeaklyChartRecyclerAdapter extends RecyclerView.Adapter<WeaklyChartRecyclerAdapter.ViewHolder> {

    private ArrayList<Course> selectedCoursesTimes = new ArrayList<>();

    public WeaklyChartRecyclerAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weakly_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public String dateCalculator(int date) {
        switch (date) {
            case -1: return "نامشخص";
            case 0: return "شنبه";
            case 1: return "یکشنبه";
            case 2: return "دوشنبه";
            case 3: return "سه شنبه";
            case 4: return "چهارشنبه";
            case 5: return "پنج شنبه";
            case 6: return "جمعه";
            default: return "";
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = selectedCoursesTimes.get(position);
        holder.weaklyChartName.setText(course.getName());
        holder.weaklyChartInstructor.setText(course.getInstructor());
        holder.weaklyChartHour.setText(course.getClassTimeRangeText());
        holder.weaklyChartDate.setText(dateCalculator(course.getClassDate()));
        holder.weaklyChartMeta.setText(course.getSummaryText());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = String.format("%s" + '\n' +
                                "واحد: " + "%d" + '\n' +
                                "ظرفیت: " + "%d" + '\n' +
                                "زمان برگزاری: " + "%s" + '\n' +
                                "زمان امتحان: " + "%s",
                        course.getInfo(), course.getUnits(), course.getCapacity(), course.getClassTimeRangeText(), course.getExam_time());
                new AlertDialog.Builder(view.getContext(), R.style.AlertDialogCustom)
                        .setTitle(course.getName())
                        .setMessage(message)
                        .setNegativeButton("حذف", (dialogInterface, i) -> {
                            SelectedCourse.removeById(course.getId());
                            SelectedCourseStorage.save(view.getContext());
                            refreshSelectedCourses();
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedCoursesTimes.size();
    }

    public void refreshSelectedCourses() {
        this.selectedCoursesTimes = SelectedCourse.getSelectedCourses();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView cardView;
        private TextView weaklyChartDate, weaklyChartName, weaklyChartInstructor, weaklyChartHour, weaklyChartMeta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.weakly_chart_card_view);
            weaklyChartDate = itemView.findViewById(R.id.weakly_chart_date);
            weaklyChartName = itemView.findViewById(R.id.weakly_chart_name);
            weaklyChartInstructor = itemView.findViewById(R.id.weakly_chart_instructor);
            weaklyChartHour = itemView.findViewById(R.id.weakly_chart_hour);
            weaklyChartMeta = itemView.findViewById(R.id.weakly_chart_meta);
        }
    }
}
