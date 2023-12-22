package ru.pin120.via.accountingsoftwaremobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telecom.Call;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import retrofit2.Callback;
import ru.pin120.via.accountingsoftwaremobile.api.ServiceApi;
import ru.pin120.via.accountingsoftwaremobile.models.Comments;
import ru.pin120.via.accountingsoftwaremobile.models.Software;
import ru.pin120.via.accountingsoftwaremobile.rvadapters.CommentsAdapter;
import ru.pin120.via.accountingsoftwaremobile.rvadapters.SoftwareScreenshotsAdapter;

public class SoftwareDetailActivity extends AppCompatActivity {
    public static Software software;
    public static CommentsAdapter commentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("software")) {
            software = intent.getParcelableExtra("software");
            ImageView imageView = findViewById(R.id.imageView_software_detail);
            TextView nameTextView = findViewById(R.id.textView_software_name);
            TextView licenseNameTextView = findViewById(R.id.textView_license_name);
            TextView description = findViewById(R.id.textView_description);
            TextView systemRequirements = findViewById(R.id.textView_systemRequirements);
            TextView link = findViewById(R.id.textView_link);
            TextView licenseTypeTextView = findViewById(R.id.textView_license_type);
            TextView licensePriceTextView = findViewById(R.id.textView_license_price);
            TextView licenseDurationTextView = findViewById(R.id.textView_license_duration);

            nameTextView.setText(software.getName());
            description.setText(software.getDescription());
            systemRequirements.setText("Системные требования: " + software.getSystemRequirements());
            link.setText("Сайт программы: " + software.getLink());
            String imgSrc = "http://192.168.1.145:8080" + software.getImageUrl();
            Picasso.get()
                    .load(imgSrc)
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView);
            licenseNameTextView.setText("Лицензия: " + software.getLicenseName());
            licenseTypeTextView.setText("Тип лицензии: " + software.getLicenseType());
            licensePriceTextView.setText("Цена: " + software.getLicensePrice());
            licenseDurationTextView.setText("Длительность: " + software.getLicenseDuration());

            RecyclerView recyclerViewComments = findViewById(R.id.recyclerView_comments);
            EditText editTextComment = findViewById(R.id.editText_comment);
            Button buttonAddComment = findViewById(R.id.button_add_comment);

            buttonAddComment.setOnClickListener(view -> {
                String commentText = editTextComment.getText().toString();
                if (!commentText.isEmpty()) {
                    sendCommentToServer(commentText);
                } else {
                    Toast.makeText(this, "Введите комментарий", Toast.LENGTH_SHORT).show();
                }
            });

            commentsAdapter = new CommentsAdapter(software.getComments());
            recyclerViewComments.setAdapter(commentsAdapter);
            recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
            ViewPager2 viewPagerScreenshots = findViewById(R.id.viewPager_screenshots);
            TabLayout tabLayoutScreenshots = findViewById(R.id.tabLayout_screenshots);
            SoftwareScreenshotsAdapter screenshotsAdapter = new SoftwareScreenshotsAdapter(software.getScreens());
            viewPagerScreenshots.setAdapter(screenshotsAdapter);

            new TabLayoutMediator(tabLayoutScreenshots, viewPagerScreenshots, (tab, position) -> {
            }).attach();
        }
    }

    private void sendCommentToServer(String commentText) {
        new SendCommentTask().execute(commentText);
    }

    private class SendCommentTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL("http://192.168.1.145:8080/api/comments/adding/" + SoftwareDetailActivity.software.getId());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Передайте параметры в тело запроса
                JSONObject postData = new JSONObject();
                postData.put("comment", params[0]);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = postData.toString().getBytes("UTF-8");
                    os.write(input, 0, input.length);
                }
                return connection.getResponseCode();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return -1;
            }
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Toast.makeText(SoftwareDetailActivity.this, "Комментарий добавлен", Toast.LENGTH_SHORT).show();

                ServiceApi.fetchUpdatedComments(SoftwareDetailActivity.software.getId(), new Callback<List<Comments>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<Comments>> call, retrofit2.Response<List<Comments>> response) {

                        if (response.isSuccessful()) {
                            List<Comments> updatedComments = response.body();
                            commentsAdapter.setComments(updatedComments);
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<List<Comments>> call, Throwable t) {

                    }
                });
            } else {
                Toast.makeText(SoftwareDetailActivity.this, "Ошибка при добавлении комментария", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
