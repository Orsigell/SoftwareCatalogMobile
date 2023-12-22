package ru.pin120.via.accountingsoftwaremobile.rvadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.pin120.via.accountingsoftwaremobile.R;
import ru.pin120.via.accountingsoftwaremobile.models.Categories;
import ru.pin120.via.accountingsoftwaremobile.models.Software;

public class SoftwareAdapter extends RecyclerView.Adapter<SoftwareAdapter.SoftwareViewHolder> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Software software);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    List<Software> softwareList = new ArrayList<>();

    static class SoftwareViewHolder extends RecyclerView.ViewHolder {

        ImageView softwareImage;
        TextView softwareName;
        TextView softwareLicenseName;
        TextView softwareLicenseType;
        TextView softwareLicensePrice;
        TextView softwareLicenseDuration;

        public SoftwareViewHolder(@NonNull View itemView) {
            super(itemView);

            softwareImage = itemView.findViewById(R.id.imageView_rv_software); // Замените на свой ID
            softwareName = itemView.findViewById(R.id.textView_rv_licence_software_name);
            softwareLicenseName = itemView.findViewById(R.id.textView_rv_license_name);
            softwareLicenseType = itemView.findViewById(R.id.textView_rv_license_type);
            softwareLicensePrice = itemView.findViewById(R.id.textView_rv_license_price);
            softwareLicenseDuration = itemView.findViewById(R.id.textView_rv_license_duration);
        }
    }
    private String selectedCategory = null;

    public void setSelectedCategory(String category) {
        selectedCategory = category;
        notifyDataSetChanged();
    }

    private boolean softwareContainsCategory(Software software, String category) {
        if (category == null) {
            return true;
        }

        for (Categories softwareCategory : software.getCategories()) {
            if (softwareCategory.getName().equals(category)) {
                return true;
            }
        }
        return false;
    }
    @NonNull
    @Override
    public SoftwareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_software_item, parent, false);
        return new SoftwareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoftwareViewHolder holder, int position) {
        Software software = softwareList.get(position);

        if (selectedCategory == null || softwareContainsCategory(software, selectedCategory)) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.softwareName.setText(software.getName());
            holder.softwareLicenseName.setText(String.format("Лицензия: %s", software.getLicenseName()));
            holder.softwareLicenseType.setText(String.format("Тип лицензии: %s", software.getLicenseType()));
            holder.softwareLicensePrice.setText(String.format("Цена: %.2f", software.getLicensePrice()));
            holder.softwareLicenseDuration.setText(String.format("Длительность: %.2f", software.getLicenseDuration()));
            if (software.getImageUrl() != null && !software.getImageUrl().isEmpty()) {
                String imgScr = "http://192.168.1.145:8080" + software.getImageUrl();
                Picasso.get().load(imgScr).into(holder.softwareImage);
            }
        } else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(software);
            }
        });
    }

    @Override
    public int getItemCount() {
        return softwareList.size();
    }

    public void setData(List<Software> newList) {
        softwareList.clear();
        softwareList.addAll(newList);
        notifyDataSetChanged();
    }
}
