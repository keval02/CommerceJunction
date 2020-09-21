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

    @POST(ApiURLs.MEDIUM_LIST_URL)
    Call<ResponseBody> MediumList(@Body JsonObject jsonRequest);

    @POST(ApiURLs.STANDARD_LIST_URL)
    Call<ResponseBody> StandardList(@Body JsonObject jsonRequest);

    @POST(ApiURLs.SUBJECT_LIST_URL)
    Call<ResponseBody> SubjectList(@Body JsonObject jsonRequest);

    @POST(ApiURLs.CHAPTERS_LIST_URL)
    Call<ResponseBody> ChaptersList(@Body JsonObject jsonRequest);

    @POST(ApiURLs.VIDEO_LIST_URL)
    Call<ResponseBody> VideoList(@Body JsonObject jsonRequest);
}
