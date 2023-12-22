package ru.pin120.via.accountingsoftwaremobile.ui.software;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.pin120.via.accountingsoftwaremobile.R;
import ru.pin120.via.accountingsoftwaremobile.SoftwareDetailActivity;
import ru.pin120.via.accountingsoftwaremobile.api.CategoriesApi;
import ru.pin120.via.accountingsoftwaremobile.api.ServiceApi;
import ru.pin120.via.accountingsoftwaremobile.api.SoftwareApi;
import ru.pin120.via.accountingsoftwaremobile.databinding.FragmentSoftwareBinding;
import ru.pin120.via.accountingsoftwaremobile.models.Categories;
import ru.pin120.via.accountingsoftwaremobile.models.Software;
import ru.pin120.via.accountingsoftwaremobile.rvadapters.SoftwareAdapter;
import ru.pin120.via.accountingsoftwaremobile.ui.BaseFragment;

public class SoftwareFragment extends BaseFragment<FragmentSoftwareBinding> {

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinnerCategories = binding.spinnerCategories;

        CategoriesApi categoriesApi = ServiceApi.getCategoriesApi();
        categoriesApi.getCategories().enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (response.isSuccessful()) {
                    List<Categories> categoriesList = response.body();
                    List<String> categoryNames = getCategoryNames(categoriesList);
                    ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categoryNames);
                    categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategories.setAdapter(categoriesAdapter);
                    spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            String selectedCategory = categoryNames.get(position);
                            if ("Выберите категорию".equals(selectedCategory)) {
                                ((SoftwareAdapter) getAdapter()).setSelectedCategory(null);
                            } else {
                                ((SoftwareAdapter) getAdapter()).setSelectedCategory(selectedCategory);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                        }
                    });
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
            }
        });
    }
    private List<String> getCategoryNames(List<Categories> categoriesList) {
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("Выберите категорию");
        for (Categories category : categoriesList) {
            categoryNames.add(category.getName());
        }
        return categoryNames;
    }

    @Override
    protected Class<? extends ViewModel> getViewModelClass() {
        return SoftwareViewModel.class;
    }

    @NonNull
    @Override
    protected ViewBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSoftwareBinding.inflate(inflater, container, false);
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.rv_software;
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        SoftwareAdapter adapter = new SoftwareAdapter();

        adapter.setOnItemClickListener(software -> {
            Intent intent = new Intent(requireContext(), SoftwareDetailActivity.class);
            intent.putExtra("software", software);
            startActivity(intent);
        });

        return adapter;
    }

    @Override
    protected void fetchData(ViewModel viewModel) {
        SoftwareApi softwareApi = ServiceApi.getSoftwareApi();
        Call<List<Software>> call = softwareApi.getSoftwares();

        call.enqueue(new Callback<List<Software>>() {
            @Override
            public void onResponse(Call<List<Software>> call, Response<List<Software>> response) {
                if (response.isSuccessful()) {
                    List<Software> softwares = response.body();
                    SoftwareViewModel softwareViewModel = (SoftwareViewModel) viewModel;
                    softwareViewModel.getText().observe(getViewLifecycleOwner(), data -> {
                        ((SoftwareAdapter) getAdapter()).setData(softwares);
                    });
                } else {
                    System.out.println("НЕТ КОНТАКТА");
                }
            }

            @Override
            public void onFailure(Call<List<Software>> call, Throwable t) {
                try {
                    throw new Exception(t);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}