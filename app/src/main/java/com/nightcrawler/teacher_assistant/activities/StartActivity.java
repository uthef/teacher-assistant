package com.nightcrawler.teacher_assistant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.viewmodels.StartViewModel;

public class StartActivity extends AppCompatActivity {
    private StartViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        viewModel = new ViewModelProvider(this).get(StartViewModel.class);
        viewModel.setup(findViewById(R.id.tile_grid));
    }
}