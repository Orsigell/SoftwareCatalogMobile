package ru.pin120.via.accountingsoftwaremobile.api;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.pin120.via.accountingsoftwaremobile.models.Comments;

public class ServiceApi {
    private static final String BASE_URL = "http://192.168.1.145:8080/api/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static CategoriesApi getCategoriesApi() {
        return retrofit.create(CategoriesApi.class);
    }
    public static CommentsApi getCommentsApi() { return retrofit.create(CommentsApi.class); }
    public static ScreensApi getScreensApi() { return retrofit.create(ScreensApi.class); }
    public static SoftwareApi getSoftwareApi() { return retrofit.create(SoftwareApi.class); }
    public static void fetchUpdatedComments(long softwareId, Callback<List<Comments>> callback) {
        CommentsApi commentsApi = retrofit.create(CommentsApi.class);
        Call<List<Comments>> call = commentsApi.getCommentsForSoftware(softwareId);
        call.enqueue(callback);
    }
}
