package ru.pin120.via.accountingsoftwaremobile.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.pin120.via.accountingsoftwaremobile.models.Screens;

public interface ScreensApi {
    @GET("screens")
    Call<List<Screens>> getScreens();
}
