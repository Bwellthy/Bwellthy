package com.bwellthy.app.api;

import com.bwellthy.app.api.response.Vocabulary;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequest {
    @GET(ApiURL.VOCABULARY)
    Call<Vocabulary> getVocabulary();
}
