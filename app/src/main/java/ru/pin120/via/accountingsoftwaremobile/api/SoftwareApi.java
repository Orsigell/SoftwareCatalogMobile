package ru.pin120.via.accountingsoftwaremobile.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.pin120.via.accountingsoftwaremobile.models.Software;

public interface SoftwareApi {
    @GET("software")
    Call<List<Software>> getSoftwares();
}
