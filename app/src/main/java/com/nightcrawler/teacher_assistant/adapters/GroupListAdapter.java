package com.nightcrawler.teacher_assistant.adapters;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nightcrawler.teacher_assistant.ListContextMenu;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.delegates.OnContextMenuItemSelected;
import com.nightcrawler.teacher_assistant.delegates.OnItemSelected;
import com.nightcrawler.teacher_assistant.storage.GroupModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    private final List<GroupModel> groups;
    private final OnItemSelected onItemSelectedListener;
    private final OnContextMenuItemSelected onContextMenuItemSelectedListener;
    public GroupListAdapter(List<GroupModel> groups,
                            OnItemSelected onItemSelectedListener,
                            OnContextMenuItemSelected onContextMenuItemSelectedListener) {
        this.groups = groups;
        this.onItemSelectedListener = onItemSelectedListener;
        this.onContextMenuItemSelectedListener = onContextMenuItemSelectedListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_list_item, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.groupLabel.setText(groups.get(position).name);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        protected TextView groupLabel;
        private final GroupListAdapter adapter;
        public ViewHolder(@NonNull @NotNull View itemView, GroupListAdapter adapter) {
            super(itemView);
            groupLabel = itemView.findViewById(R.id.group_item_label);
            this.adapter = adapter;

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener((v) -> adapter.onItemSelectedListener.onItemSelected(getAdapterPosition()));
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu,
                                        View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            contextMenu.add(0, ListContextMenu.EDIT_ITEM_ID, 0, R.string.edit_item)
                    .setOnMenuItemClickListener((item) ->
                            adapter.onContextMenuItemSelectedListener.onItemSelected(item, getAdapterPosition()));
            contextMenu.add(0, ListContextMenu.REMOVE_ITEM_ID, 0, R.string.remove_item)
                    .setOnMenuItemClickListener((item) ->
                            adapter.onContextMenuItemSelectedListener.onItemSelected(item, getAdapterPosition()));
        }
    }
}
