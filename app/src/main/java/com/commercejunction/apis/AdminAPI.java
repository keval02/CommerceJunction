package com.commercejunction.apis;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AdminAPI {

    @POST(ApiURLs.REGISTRATION_URL)
    Call<ResponseBody> RegisterUserMobileNumber(@Body JsonObject jsonRequest);

    @POST(ApiURLs.LOGIN_URL)
    Call<ResponseBody> LoginUserName(@Body JsonObject jsonRequest);
}
