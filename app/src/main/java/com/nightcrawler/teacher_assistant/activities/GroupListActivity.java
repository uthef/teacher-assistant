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

import com.nightcrawler.teacher_assistant.ListViewModel;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.activities.editors.GroupEditorActivity;
import com.nightcrawler.teacher_assistant.viewmodels.GroupListViewModel;

import java.util.List;
import java.util.Objects;

public class GroupListActivity extends AppCompatActivity {
    private ListViewModel viewModel;
    private ActivityResultLauncher<Intent> editorLauncher;
    public TextView emptyLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        emptyLabel = findViewById(R.id.empty_label);
        findViewById(R.id.add_button).setOnClickListener((v) -> launchEditor(new Intent()));

        setTitle( R.string.group_list);

        viewModel = new ViewModelProvider(this).get(GroupListViewModel.class);
        viewModel.setup(findViewById(R.id.list_view), emptyLabel);

        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewModel.labelAnimationRequest.observe(this,
                (inOut) -> startFadeAnimation(emptyLabel, inOut));
        editorLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), viewModel::onEditorResult);
        viewModel.activityRequest.observe(this, this::launchEditor);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void launchEditor(Intent intent) {
        Intent newIntent = new Intent(this, GroupEditorActivity.class);
        newIntent.putExtras(intent);
        editorLauncher.launch(newIntent);
    }

    public void startFadeAnimation(View view, boolean inOut) {
        ObjectAnimator animator =
                ObjectAnimator.ofFloat(view, "alpha", view.getAlpha(), inOut ? 1f : 0f);
        animator.setDuration(200);
        animator.start();
    }
}