package com.nightcrawler.teacher_assistant.viewmodels;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nightcrawler.teacher_assistant.ListContextMenu;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.nightcrawler.teacher_assistant.adapters.GroupListAdapter;
import com.nightcrawler.teacher_assistant.storage.GroupModel;
import com.nightcrawler.teacher_assistant.storage.LocalDatabase;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class GroupListViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> labelAnimationRequestMutable = new MutableLiveData<>();
    private final MutableLiveData<Intent> activityRequestMutable = new MutableLiveData<>();
    public final LiveData<Boolean> labelAnimationRequest = labelAnimationRequestMutable;
    public final LiveData<Intent> activityRequest = activityRequestMutable;
    public List<GroupModel> groups;
    private final LocalDatabase dbInstance;
    private GroupListAdapter adapter;

    public GroupListViewModel(@NonNull Application application) {
        super(application);

        dbInstance = LocalDatabase.getInstance();
        groups = dbInstance.listGroups();
    }

    public void setup(RecyclerView recyclerView, TextView emptyLabel) {
        AppCompatActivity activity = (AppCompatActivity) recyclerView.getContext();

        emptyLabel.setAlpha(groups.size() == 0 ? 1f : 0f);
        adapter = new GroupListAdapter(groups, this::onItemSelected, this::onContextMenuItemSelected);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
    }

    private boolean onContextMenuItemSelected(MenuItem item, int position) {
        if (item.getItemId() == ListContextMenu.EDIT_ITEM_ID) {
            Intent intent = new Intent()
                    .putExtra("position", position)
                    .putExtra("group", groups.get(position));
            activityRequestMutable.setValue(intent);
        }
        else
            removeGroup(position);

        return true;
    }

    private void onItemSelected(int position) {

    }

    public void onEditorResult(ActivityResult result) {
        if (result.getResultCode() != RESULT_OK || result.getData() == null) return;

        Intent intent = result.getData();
        GroupModel newGroup = (GroupModel) Objects.requireNonNull(intent.getSerializableExtra("group"));

        if (intent.hasExtra("position")) {
            int position = intent.getIntExtra("position", -1);
            groups.set(position, newGroup);
            updateGroup(newGroup, position);
        }
        else {
            insertGroup(newGroup);
        }
    }

    private void insertGroup(GroupModel group) {
        groups.add(group);
        Collections.sort(groups, (a, b) -> a.getLoweredName().compareTo(b.getLoweredName()));
        int index = groups.indexOf(group);
        dbInstance.insertGroup(group);
        adapter.notifyItemInserted(index);

        if (groups.size() == 1) {
            labelAnimationRequestMutable.setValue(false);
        }
    }

    private void updateGroup(GroupModel group, int position) {

        Collections.sort(groups, (a, b) -> a.getLoweredName().compareTo(b.getLoweredName()));
        int newIndex = groups.indexOf(group);

        dbInstance.updateGroup(group);
        adapter.notifyItemChanged(position);
        adapter.notifyItemMoved(position, newIndex);
    }

    private void removeGroup(int position) {
        dbInstance.removeGroup(groups.get(position));
        groups.remove(position);
        adapter.notifyItemRemoved(position);

        if (groups.size() == 0)
            labelAnimationRequestMutable.setValue(true);
    }
}
