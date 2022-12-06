package com.nightcrawler.teacher_assistant.activities.editors;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.storage.GroupModel;
import com.nightcrawler.teacher_assistant.storage.StudentModel;

import java.util.Objects;

public class StudentEditorActivity extends AppCompatActivity {
    @Nullable
    public StudentModel student = null;
    private GroupModel group;

    private RadioButton firstSubgroupRadio;
    private ConstraintLayout rootView;
    private int position = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_editor);
        Intent intent = getIntent();

        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayHomeAsUpEnabled(true);

        group = (GroupModel) intent.getSerializableExtra("group");

        if (intent.hasExtra("student")) {
            setTitle(R.string.student_editor_title);
            student = (StudentModel) intent.getSerializableExtra("student");
            position = intent.getIntExtra("position", position);
        }
        else setTitle(R.string.student_editor_addition_title);

        firstSubgroupRadio = findViewById(R.id.first_subgroup_radio);
        rootView = findViewById(R.id.student_editor_root);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_icon_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            setResult(RESULT_CANCELED);
            return true;
        }

        boolean validationResult = true;

        if (student == null) student = new StudentModel(group.getId());

        for (int i = 0; i < rootView.getChildCount(); i++) {
            View child = rootView.getChildAt(i);
            if (!child.getClass().equals(TextInputLayout.class)) continue;

            TextInputLayout textInputLayout = (TextInputLayout) child;
            EditText editText = Objects.requireNonNull(textInputLayout.getEditText());
            String value = editText.getText().toString().trim();

            if (value.length() == 0) {
                validationResult = false;
                textInputLayout.setError(getString(R.string.required_field));
            }
            else {
                try {
                    student.getClass()
                            .getField(textInputLayout.getTag().toString())
                            .set(student, editText.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                textInputLayout.setError(null);
            }
        }

        student.subgroup = firstSubgroupRadio.isChecked() ? 1 : 2;

        if (!validationResult) return true;

        Intent intent = new Intent().putExtra("student", student);

        if (position != -1)
            intent.putExtra("position", position);

        setResult(RESULT_OK, intent);

        return true;
    }
}
