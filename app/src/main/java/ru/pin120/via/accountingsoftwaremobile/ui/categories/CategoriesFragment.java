package ru.pin120.via.accountingsoftwaremobile.ui.categories;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;


import java.util.List;

import ru.pin120.via.accountingsoftwaremobile.R;
import ru.pin120.via.accountingsoftwaremobile.api.CategoriesApi;
import ru.pin120.via.accountingsoftwaremobile.api.ServiceApi;
import ru.pin120.via.accountingsoftwaremobile.databinding.FragmentCategoriesBinding;
import ru.pin120.via.accountingsoftwaremobile.models.Categories;
import ru.pin120.via.accountingsoftwaremobile.rvadapters.CategoriesAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.pin120.via.accountingsoftwaremobile.ui.BaseFragment;

public class CategoriesFragment extends BaseFragment<FragmentCategoriesBinding> {

    @Override
    protected Class<? extends ViewModel> getViewModelClass() {
        return CategoriesViewModel.class;
    }

    @Override
    protected @NonNull ViewBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCategoriesBinding.inflate(inflater, container, false);
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.rv_categories;
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new CategoriesAdapter();
    }

    @Override
    protected void fetchData(ViewModel viewModel) {
        CategoriesApi categoriesApi = ServiceApi.getCategoriesApi();
        Call<List<Categories>> call = categoriesApi.getCategories();

        call.enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (response.isSuccessful()) {
                    List<Categories> categoriess = response.body();
                    CategoriesViewModel categoriesViewModel = (CategoriesViewModel) viewModel;
                    categoriesViewModel.getText().observe(getViewLifecycleOwner(), data -> {
                        ((CategoriesAdapter) getAdapter()).setData(categoriess);
                    });
                } else {
                    System.out.println("НЕТ КОНТАКТА");
                }
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                try {
                    throw new Exception(t);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}