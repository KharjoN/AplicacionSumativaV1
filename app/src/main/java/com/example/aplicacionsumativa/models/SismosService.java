package com.example.aplicacionsumativa.models;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface SismosService {
    @GET("sismos")
    Call<List<Sismos>> getSismo();
}