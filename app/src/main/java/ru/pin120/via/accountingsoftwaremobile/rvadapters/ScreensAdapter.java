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
import ru.pin120.via.accountingsoftwaremobile.models.Screens;

public class ScreensAdapter extends RecyclerView.Adapter<ScreensAdapter.ScreensViewHolder>{
    List<Screens> screensList = new ArrayList<>();
    static class ScreensViewHolder extends RecyclerView.ViewHolder{

        TextView screensPath;
        TextView screenNumber;
        public ScreensViewHolder(@NonNull View itemView) {
            super(itemView);
            screensPath = itemView.findViewById(R.id.textView_rv_screens_path);
            screenNumber = itemView.findViewById(R.id.textView_rv_screen_number);
        }
    }
    @NonNull
    @Override
    public ScreensAdapter.ScreensViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_screens_item, parent, false);
        return new ScreensAdapter.ScreensViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScreensAdapter.ScreensViewHolder holder, int position) {
        Screens screens = screensList.get(position);
        holder.screensPath.setText(screens.getScreen());
        holder.screenNumber.setText(String.valueOf(screens.getId()));
    }


    @Override
    public int getItemCount() {
        return screensList.size();
    }
    public void setData(List<Screens> newList) {
        screensList.clear();
        screensList.addAll(newList);
        notifyDataSetChanged();
    }
}
