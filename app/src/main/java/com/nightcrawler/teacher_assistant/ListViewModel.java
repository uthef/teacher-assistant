package com.nightcrawler.teacher_assistant;

import android.app.Application;
import android.content.Intent;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

public abstract class ListViewModel extends AndroidViewModel {
    protected final MutableLiveData<Boolean> labelAnimationRequestMutable = new MutableLiveData<>();
    protected final MutableLiveData<Intent> activityRequestMutable = new MutableLiveData<>();
    public final LiveData<Boolean> labelAnimationRequest = labelAnimationRequestMutable;
    public final LiveData<Intent> activityRequest = activityRequestMutable;
    public ListViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public abstract void setup(RecyclerView recyclerView, TextView emptyLabel);
    public abstract void onEditorResult(ActivityResult result);

}
