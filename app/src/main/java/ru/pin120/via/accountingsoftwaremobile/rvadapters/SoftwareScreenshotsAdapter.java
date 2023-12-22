package ru.pin120.via.accountingsoftwaremobile.rvadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.pin120.via.accountingsoftwaremobile.R;
import ru.pin120.via.accountingsoftwaremobile.models.Screens;

public class SoftwareScreenshotsAdapter extends RecyclerView.Adapter<SoftwareScreenshotsAdapter.ScreenshotViewHolder> {

    private List<Screens> screenshotUrls;

    public SoftwareScreenshotsAdapter(List<Screens> screenshotUrls) {
        this.screenshotUrls = screenshotUrls;
    }

    @NonNull
    @Override
    public ScreenshotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_screenshot, parent, false);
        return new ScreenshotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScreenshotViewHolder holder, int position) {
        String screenshotUrl = "http://192.168.1.145:8080" + screenshotUrls.get(position).getScreen();

        Picasso.get().load(screenshotUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return screenshotUrls != null ? screenshotUrls.size() : 0;
    }

    public static class ScreenshotViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ScreenshotViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_screenshot);
        }
    }
}

