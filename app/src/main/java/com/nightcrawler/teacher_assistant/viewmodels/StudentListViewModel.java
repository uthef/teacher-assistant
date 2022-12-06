package com.nightcrawler.teacher_assistant.viewmodels;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nightcrawler.teacher_assistant.adapters.StudentListAdapter;
import com.nightcrawler.teacher_assistant.storage.StudentModel;

import java.util.List;

public class StudentListViewModel extends GroupListViewModel {
    private List<StudentModel> students;
    private LinearLayoutManager layoutManager;
    public StudentListViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void bindList() {
        students = dbInstance.listStudents();
    }

    @Override
    public void setup(RecyclerView recyclerView, TextView emptyLabel) {
        emptyLabel.setAlpha(students.size() == 0 ? 1f : 0f);

        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        StudentListAdapter adapter = new StudentListAdapter(students, (a) -> {}, (a, b) -> true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEditorResult(ActivityResult result) {
        if (result.getResultCode() != RESULT_OK || result.getData() == null) return;

        Intent intent = result.getData();

        if (intent.hasExtra("position")) {

        }
        else {

        }
    }

    private void addStudent() {

    }
}
