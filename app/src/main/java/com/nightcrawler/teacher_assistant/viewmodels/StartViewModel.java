package com.nightcrawler.teacher_assistant.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.activities.GroupListActivity;
import com.nightcrawler.teacher_assistant.storage.LocalDatabase;

import java.io.File;

public class StartViewModel extends AndroidViewModel {
    public StartViewModel(@NonNull Application application) {
        super(application);

        LocalDatabase.setFile(new File(application.getFilesDir(), LocalDatabase.DEFAULT_PATH));
        LocalDatabase.getInstance();
    }

    public void setup(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            FrameLayout frameLayout = (FrameLayout) gridLayout.getChildAt(i);
            TextView textView = (TextView) frameLayout.getChildAt(0);
            textView.setOnClickListener((v) -> onTileClick(v, frameLayout.getId()));
        }
    }

    private void onTileClick(View view, int tileId) {
        Context context = view.getContext();

        if (tileId == R.id.groups_tile) {
            Intent intent = new Intent(context, GroupListActivity.class);
            context.startActivity(intent);
        }
    }
}
