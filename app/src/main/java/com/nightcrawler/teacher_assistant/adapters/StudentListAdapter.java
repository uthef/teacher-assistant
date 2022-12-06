package com.nightcrawler.teacher_assistant.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nightcrawler.teacher_assistant.ListContextMenu;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.delegates.OnContextMenuItemSelected;
import com.nightcrawler.teacher_assistant.delegates.OnItemSelected;
import com.nightcrawler.teacher_assistant.storage.StudentModel;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {
    List<StudentModel> students;

    private final OnItemSelected onItemSelectedListener;
    private final OnContextMenuItemSelected onContextMenuItemSelectedListener;

    public StudentListAdapter(List<StudentModel> students,
                              OnItemSelected onItemSelectedListener,
                              OnContextMenuItemSelected onContextMenuItemSelectedListener) {
        this.students = students;

        this.onItemSelectedListener = onItemSelectedListener;
        this.onContextMenuItemSelectedListener = onContextMenuItemSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_list_item, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentModel student = students.get(position);
        Context ctx = holder.itemView.getContext();

        holder.studentName.setText(student.getFullName());
        holder.studentLabel.setText(
                String.format("%s %s, %s %s, %s %s",
                        ctx.getString(R.string.subgroup_label),
                        student.subgroup,
                        ctx.getString(R.string.lab_variant_label),
                        student.labVariant,
                        ctx.getString(R.string.kp_variant_label),
                        student.kpVariant
                )
        );
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {
        public final TextView studentName, studentLabel;
        public final StudentListAdapter adapter;

        public ViewHolder(@NonNull View itemView, StudentListAdapter adapter) {
            super(itemView);

            this.adapter = adapter;

            studentName = itemView.findViewById(R.id.student_full_name);
            studentLabel = itemView.findViewById(R.id.student_label);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener((v) ->
                    adapter.onItemSelectedListener.onItemSelected(getAdapterPosition()));
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu,
                                        View view,
                                        ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0, ListContextMenu.EDIT_ITEM_ID, 0, R.string.edit_item)
                    .setOnMenuItemClickListener((item) ->
                            adapter.onContextMenuItemSelectedListener.onItemSelected(item, getAdapterPosition()));
            contextMenu.add(0, ListContextMenu.REMOVE_ITEM_ID, 0, R.string.remove_item)
                    .setOnMenuItemClickListener((item) ->
                            adapter.onContextMenuItemSelectedListener.onItemSelected(item, getAdapterPosition()));
        }
    }
}
