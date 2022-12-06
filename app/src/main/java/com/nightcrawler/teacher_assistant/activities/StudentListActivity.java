package com.nightcrawler.teacher_assistant.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.activities.editors.StudentEditorActivity;
import com.nightcrawler.teacher_assistant.storage.GroupModel;
import com.nightcrawler.teacher_assistant.viewmodels.StudentListViewModel;

public class StudentListActivity extends GroupListActivity {
    GroupModel group;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editorActivityClass = StudentEditorActivity.class;
        group = (GroupModel) getIntent().getSerializableExtra("group");
        setTitle(group.name);
    }

    @Override
    protected void bind() {
        StudentListViewModel viewModel = new ViewModelProvider(this)
                .get(StudentListViewModel.class);

        viewModel.setup(findViewById(R.id.list_view), emptyLabel);
        editorLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), viewModel::onEditorResult);
        findViewById(R.id.add_button).setOnClickListener((v) -> launchEditor(
                new Intent().putExtra("group", group))
        );
    }
}
