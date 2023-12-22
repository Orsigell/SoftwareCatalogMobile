package ru.pin120.via.accountingsoftwaremobile.ui.screens;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.pin120.via.accountingsoftwaremobile.R;
import ru.pin120.via.accountingsoftwaremobile.api.ScreensApi;
import ru.pin120.via.accountingsoftwaremobile.api.ServiceApi;
import ru.pin120.via.accountingsoftwaremobile.databinding.FragmentScreensBinding;
import ru.pin120.via.accountingsoftwaremobile.models.Screens;
import ru.pin120.via.accountingsoftwaremobile.rvadapters.ScreensAdapter;
import ru.pin120.via.accountingsoftwaremobile.ui.BaseFragment;

public class ScreensFragment extends BaseFragment<FragmentScreensBinding> {
    @Override
    protected Class<? extends ViewModel> getViewModelClass() {
        return ScreensViewModel.class;
    }

    @Override
    protected @NonNull ViewBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentScreensBinding.inflate(inflater, container, false);
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.rv_screens;
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new ScreensAdapter();
    }

    @Override
    protected void fetchData(ViewModel viewModel) {
        ScreensApi screensApi = ServiceApi.getScreensApi();
        Call<List<Screens>> call = screensApi.getScreens();

        call.enqueue(new Callback<List<Screens>>() {
            @Override
            public void onResponse(Call<List<Screens>> call, Response<List<Screens>> response) {
                if (response.isSuccessful()) {
                    List<Screens> screenss = response.body();
                    ScreensViewModel screensViewModel = (ScreensViewModel) viewModel;
                    screensViewModel.getText().observe(getViewLifecycleOwner(), data -> {
                        ((ScreensAdapter) getAdapter()).setData(screenss);
                    });
                } else {
                    System.out.println("НЕТ КОНТАКТА");
                }
            }

            @Override
            public void onFailure(Call<List<Screens>> call, Throwable t) {
                try {
                    throw new Exception(t);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
