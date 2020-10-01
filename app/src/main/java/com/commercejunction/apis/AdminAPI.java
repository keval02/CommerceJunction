package com.commercejunction.apis;

import com.commercejunction.model.BaseModel;
import com.commercejunction.model.BoardModel;
import com.commercejunction.model.CheapterModel;
import com.commercejunction.model.LoginModel;
import com.commercejunction.model.ProfileModel;
import com.commercejunction.model.StandardModel;
import com.commercejunction.model.SubjectModel;
import com.commercejunction.model.VideoModel;
import com.google.gson.JsonObject;

import kotlin.reflect.KCallable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface AdminAPI {

    @POST(ApiURLs.REGISTRATION_URL)
    Call<BaseModel> RegisterUserMobileNumber(@Body JsonObject jsonRequest);

    @POST(ApiURLs.LOGIN_URL)
    Call<LoginModel> LoginUserName(@Body JsonObject jsonRequest);

    @GET(ApiURLs.MEDIUM_LIST_URL)
    Call<BoardModel> MediumList();

    @GET(ApiURLs.STANDARD_LIST_URL)
    Call<StandardModel> StandardList(@Path("MEDIUMID") int mediumId);

    @GET(ApiURLs.SUBJECT_LIST_URL)
    Call<SubjectModel> SubjectList(@Path("STANDARDID") int standardId);

    @GET(ApiURLs.CHAPTERS_LIST_URL)
    Call<CheapterModel> ChaptersList(@Path("SUBJECTID") int standardId);

    @GET(ApiURLs.VIDEO_LIST_URL)
    Call<VideoModel> VideoList(@Path("CHAPTERID") int standardId);

    @GET(ApiURLs.PDF_LIST_URL)
    Call<ResponseBody> PDFList(@Path("CHAPTERID") int standardId);

    @GET(ApiURLs.CHANGE_PASSWORD_URL)
    Call<ResponseBody> ChangePassword(@Body JsonObject jsonRequest);

    @GET(ApiURLs.EDIT_PROFILE_URL)
    Call<ProfileModel> EditProfile(@Path("STUDENTID") int studentId);
}
