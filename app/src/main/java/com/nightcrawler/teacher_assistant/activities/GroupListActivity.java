package com.nightcrawler.teacher_assistant.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.activities.editors.GroupEditorActivity;
import com.nightcrawler.teacher_assistant.viewmodels.GroupListViewModel;

import java.util.Objects;

public class GroupListActivity extends AppCompatActivity {
    protected ActivityResultLauncher<Intent> editorLauncher;
    protected TextView emptyLabel;

    protected Class<?> editorActivityClass = GroupEditorActivity.class;
    protected Class<?> nextActivityClass = StudentListActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        emptyLabel = findViewById(R.id.empty_label);

        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayHomeAsUpEnabled(true);

        bind();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void bind() {
        setTitle( R.string.group_list);
        GroupListViewModel viewModel = new ViewModelProvider(this).get(GroupListViewModel.class);

        editorLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), viewModel::onEditorResult);
        viewModel.setup(findViewById(R.id.list_view), emptyLabel);

        findViewById(R.id.add_button).setOnClickListener((v) -> launchEditor(new Intent()));

        viewModel.labelAnimationRequest.observe(this,
                (inOut) -> startFadeAnimation(emptyLabel, inOut));
        viewModel.editorRequest.observe(this, this::launchEditor);
        viewModel.nextActivityRequest.observe(this, this::startNextActivity);
    }

    protected void launchEditor(Intent intent) {
        Intent newIntent = new Intent(this, editorActivityClass);
        newIntent.putExtras(intent);
        editorLauncher.launch(newIntent);
    }

    protected void startFadeAnimation(View view, boolean inOut) {
        ObjectAnimator animator =
                ObjectAnimator.ofFloat(view, "alpha", view.getAlpha(), inOut ? 1f : 0f);
        animator.setDuration(200);
        animator.start();
    }

    protected void startNextActivity(Intent intent) {
        Intent newIntent = new Intent(this, nextActivityClass);
        newIntent.putExtras(intent);
        startActivity(newIntent);
    }
}