package lawyerku.customer.api;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import lawyerku.customer.BuildConfig;
import lawyerku.customer.api.model.CreatePerkaraModel;
import lawyerku.customer.api.model.CredentialModel;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.api.model.PerkaraModel;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface LawyerkuService {
    //    ==============================================================
    //                      CREDENTIAL
    //    ==============================================================

//    @Headers("Accept:application/json")
//    @POST("api/authenticate?api_token=b3b45k4n")
//    Observable<CredentialModel.LoginResponse> login(
//            @Body CredentialModel.Request body);

//    @Headers("Accept:application/json")
    @Headers("Content-Type:application/json")
    @POST("api/login")
    Observable<CredentialModel.LoginResponse> login(
            @Body CredentialModel.Request body);

    @Headers("Accept:application/json")
    @POST("api/register/")
    Observable<CredentialModel.RegistrationResponse> register(
//            @Path("type") String type,
            @Body CredentialModel.Request body);

    //    ==============================================================
    //                      PROJECT
    //    ==============================================================


    @Headers("Content-Type:application/json")
    @GET("api/lawyers/search")
    Observable<LawyerModel.Response> searchLawyer(
            @Header("Authorization") String header,
            @Query("languageskill_id") String language,
            @Query("jobskill_id") String skill,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude);

//    @Headers("Accept:application/json")
//    @GET("api/languages-and-skills?api_token=b3b45k4n")
//    Observable<LanguageModel.Response> getLanguageAndSkill();
//
    @Headers("Accept:application/json")
    @POST("api/projects")
    Observable<CreatePerkaraModel.Response> createProject(
            @Header("Authorization") String header,
            @Body CreatePerkaraModel.Response.Data body);

    @Headers("Content-Type:application/json")
    @GET("api/projects/{idProject}")
    Observable<PerkaraModel.Response> getProject(
            @Header("Authorization") String header,
            @Path ("idProject") String idProject );

    @Headers("Content-Type:application/json")
    @GET("api/projects/new/get")
    Observable<PerkaraModel.Response> getProjectNew(
            @Header("Authorization") String header );

    @Headers("Content-Type:application/json")
    @GET("api/projects")
    Observable<PerkaraModel.Response> getListProject(
            @Header("Authorization") String header );
//
//    @Headers("Accept:application/json")
//    @PUT("api/project/{projectId}")
//    Observable<CreateProjectModel.Response> updateProject(
//            @Header("Authorization") String header,
//            @Path("projectId") String projectId,
//            @Body CreateProjectModel.Request body);
//
//    @Headers("Accept:application/json")
//    @GET("api/project/check")
//    Observable<CheckModel.Response> checkAvailableProject(
//            @Header("Authorization") String header);
//
//    @Headers("Accept:application/json")
//    @GET("api/search")
//    Observable<WorkmanModel.Response> searchWorkman(
//            @Header("Authorization") String header,
//            @Query("langs") String language,
//            @Query("skills") String skill,
//            @Query("lat") String latitude,
//            @Query("lng") String longitude,
//            @Query("ignore") String ignore);
//
//    @Headers("Accept:application/json")
//    @PUT("api/project/add-workman/{projectId}")
//    Observable<WorkmanModel.ResponseAddWorkman> addWorkman(
//            @Header("Authorization") String header,
//            @Path("projectId") String projectId,
//            @Body WorkmanModel.Request body);
//
//    /**
//     * Status: deal, nodeal, completed, uncompleted
//     */
//    @Headers("Accept:application/json")
//    @PUT("api/project/{projectId}/{status}")
//    Observable<StatusModel.Response> changeStatus(
//            @Header("Authorization") String header,
//            @Path("projectId") String projectId,
//            @Path("status") String status);

    //    ==============================================================
    //                      RATE
    //    ==============================================================

//    @Headers("Accept:application/json")
//    @POST("api/rate/{type}/{projectId}")
//    Observable<RateModel.Response> rate(
//            @Header("Authorization") String header,
//            @Path("type") String type,
//            @Path("projectId") String projectId,
//            @Body RateModel.Request request);

    //    ==============================================================
    //                      HISTORY
    //    ==============================================================

//    @Headers("Accept:application/json")
//    @GET("api/projects")
//    Observable<HistoryModel.Response> getHistory(
//            @Header("Authorization") String header,
//            @Query("page") int page);
//
//    @Headers("Accept:application/json")
//    @GET("api/projects")
//    Observable<HistoryModel.Response> getHistory(
//            @Header("Authorization") String header,
//            @Query("page") int page,
//            @Query("has_rating") int hasRating);
//
//    @Headers("Accept:application/json")
//    @GET("api/projects?on_going=1")
//    Observable<HistoryModel.Response> getOnProgress(
//            @Header("Authorization") String header,
//            @Query("page") int page);
//
//    @Headers("Accept:application/json")
//    @GET("api/projects?on_going=1")
//    Observable<HistoryModel.Response> getOnProgress(
//            @Header("Authorization") String header);
//
////    @Headers("Accept:application/json")
////    @GET("/api/projects?on_going=1")
////    Observable<HistoryModel.Response> getOnProgress(
////            @Header("Authorization") String header,
////            @Query("page") int page,
////            @Query("has_rating") int hasRating);
//
//    @Headers("Accept:application/json")
//    @GET("api/projects")
//    Observable<HistoryModel.Response> getMitraHistory(
//            @Header("Authorization") String header,
//            @Query("page") int page,
//            @Query("status") String status);
//
//    @Headers("Accept:application/json")
//    @GET("api/projects")
//    Observable<HistoryModel.Response> getMitraHistory(
//            @Header("Authorization") String header,
//            @Query("page") int page,
//            @Query("has_rating") int hasRating,
//            @Query("status") String status);
//
//    @Headers("Accept:application/json")
//    @GET("api/projects")
//    Observable<HistoryModel.Response> getNoRateHistory(
//            @Header("Authorization") String header,
//            @Query("has_rating") int hasRating,
//            @Query("status") String status);

    //    ==============================================================
    //                      PROFILE
    //    ==============================================================

    @Headers("Accept:application/json")
    @POST("api/me")
    Observable<LawyerModel.ResponseProfile> getProfile(@Header("Authorization") String header);

    @Headers("Accept:application/json")
    @GET("api/lawyers/{idlawyer}")
    Observable<LawyerModel.Response> getLawyer(
            @Header("Authorization") String header,
            @Path("idlawyer") int idlawyer);

    @Headers("Accept:application/json")
    @GET("api/language_skills")
    Observable<LawyerModel.Response> getLaw(@Header("Authorization") String header);
//
//    @Multipart
//    @Headers("Accept:application/json")
//    @POST("api/image/upload")
//    Observable<ProfileModel.Response> uploadProfilePicture(
//            @Header("Authorization") String header,
//            @Part MultipartBody.Part image);
//
//    @Headers("Accept:application/json")
//    @PUT("api/{type}/update")
//    Observable<ProfileModel.Response> updateProfile(
//            @Header("Authorization") String header,
//            @Path("type") String type,
//            @Body ProfileModel.Request request);
//    @Headers("Accept:application/json")
//    @PUT("api/workman/update-fcm-token")
//    Observable<FcmToken.Response> updateFcmToken(
//            @Header("Authorization") String header,
//            @Body BodyToken fcmToken);

    //    ==============================================================
    //                      SERVICE
    //    ==============================================================

    class Factory {
        public static LawyerkuService create() {
            return getRetrofitConfig().create(LawyerkuService.class);
        }
        private static Retrofit retrofit = null;
        public static Retrofit getRetrofit(){
            return retrofit;
        }
        public static Retrofit getRetrofitConfig() {
            return new Retrofit.Builder()
                    .baseUrl(BuildConfig.LawyerkuUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client())
                    .build();
        }

        private static OkHttpClient client() {
            return new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build();
        }
    }
}
