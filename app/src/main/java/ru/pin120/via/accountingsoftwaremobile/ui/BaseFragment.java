package ru.pin120.via.accountingsoftwaremobile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {
    protected T binding;
    RecyclerView.Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewModel viewModel = new ViewModelProvider(this).get(getViewModelClass());

        binding = (T) createBinding(inflater, container);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(getRecyclerViewId());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);

        adapter = createAdapter();
        recyclerView.setAdapter(adapter);

        fetchData(viewModel);

        return root;
    }

    protected abstract Class<? extends ViewModel> getViewModelClass();

    protected abstract @NonNull ViewBinding createBinding(LayoutInflater inflater, ViewGroup container);

    protected abstract int getRecyclerViewId();

    protected abstract RecyclerView.Adapter createAdapter();

    protected abstract void fetchData(ViewModel viewModel);

    protected RecyclerView.Adapter getAdapter(){ return adapter; }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
