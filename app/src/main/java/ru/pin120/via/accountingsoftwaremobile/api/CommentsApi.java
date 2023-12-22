package ru.pin120.via.accountingsoftwaremobile.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.pin120.via.accountingsoftwaremobile.models.Comments;

public interface CommentsApi {
    @GET("comments/for-software/{softwareId}")
    Call<List<Comments>> getCommentsForSoftware(@Path("softwareId") long softwareId);
}

