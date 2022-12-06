package com.nightcrawler.teacher_assistant.activities.editors;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.storage.GroupModel;
import com.nightcrawler.teacher_assistant.storage.LocalDatabase;

import java.util.Objects;

public class GroupEditorActivity extends AppCompatActivity {
    private int position;
    @Nullable
    private GroupModel group = null;
    private EditText groupNameEditText;
    private TextInputLayout groupNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_editor);

        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra("group"))
            group = (GroupModel) getIntent().getSerializableExtra("group");
        else
            group = null;

        position = getIntent().getIntExtra("position", -1);

        groupNameInput = findViewById(R.id.group_name_input);
        groupNameEditText = Objects.requireNonNull(groupNameInput.getEditText());

        if (group != null) {
            groupNameEditText.setText(group.name);
            setTitle(R.string.group_editor_title);
        }
        else {
            setTitle(R.string.group_creator_title);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        groupNameEditText.setText(savedInstanceState.getString("group_name"));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("group_name", groupNameEditText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_icon_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Back button
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            setResult(RESULT_CANCELED);
            return true;
        }

        // Input validation
        String newGroupName = groupNameEditText.getText().toString().trim();

        if (newGroupName.length() == 0) {
            groupNameInput.setError(getString(R.string.required_field));
            return true;
        }

        if (group == null || !newGroupName.toLowerCase().equals(group.getLoweredName())) {
            if (LocalDatabase.getInstance().hasGroupNamed(newGroupName)) {
                groupNameInput.setError(getString(R.string.group_name_conflict));
                return true;
            }
        }

        if (group != null) {
            group.name = newGroupName;
            Intent intent = new Intent()
                    .putExtra("group", group)
                    .putExtra("position", position);
            setResult(RESULT_OK, intent);
        }
        else {
            Intent intent = new Intent()
                    .putExtra("group", new GroupModel(newGroupName));
            setResult(RESULT_OK, intent);
        }

        finish();
        return true;
    }
}
