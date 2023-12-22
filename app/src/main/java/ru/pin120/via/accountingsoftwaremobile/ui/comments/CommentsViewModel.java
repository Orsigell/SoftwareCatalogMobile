package ru.pin120.via.accountingsoftwaremobile.ui.comments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.pin120.via.accountingsoftwaremobile.api.CommentsApi;
import ru.pin120.via.accountingsoftwaremobile.api.ServiceApi;
import ru.pin120.via.accountingsoftwaremobile.models.Comments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsViewModel extends ViewModel {

    private MutableLiveData<List<Comments>> commentsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public LiveData<List<Comments>> getCommentsLiveData() {
        return commentsLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void fetchComments() {
        CommentsApi commentsApi = ServiceApi.getCommentsApi();
        Call<List<Comments>> call = commentsApi.getCommentsForSoftware(1);

        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                if (response.isSuccessful()) {
                    List<Comments> comments = response.body();
                    Log.d("CommentsViewModel", "Received comments: " + comments.size());
                    commentsLiveData.setValue(comments);
                } else {
                    String errorMessage = "Error: " + response.code() + " - " + response.message();
                    Log.e("CommentsViewModel", errorMessage);
                    errorLiveData.setValue(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                String errorMessage = "Failure: " + t.getMessage();
                Log.e("CommentsViewModel", errorMessage);
                errorLiveData.setValue(errorMessage);
            }
        });
    }
}
