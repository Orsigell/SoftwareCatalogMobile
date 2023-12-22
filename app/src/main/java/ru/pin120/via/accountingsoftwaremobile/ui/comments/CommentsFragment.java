package ru.pin120.via.accountingsoftwaremobile.ui.comments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import ru.pin120.via.accountingsoftwaremobile.R;
import ru.pin120.via.accountingsoftwaremobile.api.CommentsApi;
import ru.pin120.via.accountingsoftwaremobile.api.ServiceApi;
import ru.pin120.via.accountingsoftwaremobile.databinding.FragmentCommentsBinding;
import ru.pin120.via.accountingsoftwaremobile.models.Comments;
import ru.pin120.via.accountingsoftwaremobile.rvadapters.CommentsAdapter;

public class CommentsFragment extends Fragment {

    private FragmentCommentsBinding binding;
    private CommentsAdapter commentsAdapter;
    private CommentsViewModel commentsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCommentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        commentsAdapter = new CommentsAdapter(null);
        binding.rvComments.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvComments.setAdapter(commentsAdapter);

        commentsViewModel = new ViewModelProvider(this).get(CommentsViewModel.class);
        commentsViewModel.getCommentsLiveData().observe(getViewLifecycleOwner(), comments -> {
            Log.d("CommentsFragment", "Received comments from ViewModel: " + comments.size());

        });

        commentsViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            Log.e("CommentsFragment", "Error from ViewModel: " + error);
        });

        commentsViewModel.fetchComments();

        return root;
    }
}
