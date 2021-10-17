package com.example.buscacep;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostmonService {
    @GET("{id}")
    Call<Endereço> getEndereco(@Path("id") String cep);
}
