package ru.pin120.via.accountingsoftwaremobile.rvadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.pin120.via.accountingsoftwaremobile.R;
import ru.pin120.via.accountingsoftwaremobile.models.Categories;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{
    private List<Categories> categoriesList = new ArrayList<>();
    static class CategoriesViewHolder extends RecyclerView.ViewHolder{

        TextView categoriesNumber;
        TextView viewHolderIndex;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            categoriesNumber = itemView.findViewById(R.id.textView_rv_categories_number);
            viewHolderIndex = itemView.findViewById(R.id.textView_rv_categories_holder_number);
        }
    }
    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_categories_item, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        Categories categories = categoriesList.get(position);
        holder.categoriesNumber.setText(categories.getName());
        holder.viewHolderIndex.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }
    public void setData(List<Categories> newList) {
        categoriesList.clear();
        categoriesList.addAll(newList);
        notifyDataSetChanged();
    }
}
