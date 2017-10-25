package com.example.mina.githubrepos.network;

import com.example.mina.githubrepos.models.RepoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Mina on 5/31/2017.
 */

public class ApiInterfaces {

    /*public interface LoginApi {
        @Headers("Content-Type: application/json")
        @POST(ApiUrls.API_URL + ApiUrls.LOGIN_URL)
        Call<String> getApiData(@Body String data);
    }*/

    public interface GetOrgRepo {
        @GET("/users/{repo_name}/repos?")
        Observable<List<RepoModel>> getApiData(@Path(value = "repo_name", encoded = true) String repoName,
                                               @Query("page") String page);
    }

    /*public interface FieldApi {
        @GET(ApiUrls.API_URL + ApiUrls.FIELD_URL)
        Call<String> getApiData();
    }

    public interface UploadApi {
        @Multipart
        @POST(ApiUrls.API_URL + ApiUrls.UPLOAD_IMAGE_URL)
        Call<String> uploadImage(@Query(ApiUrls.IMAGE_TYPE_KEY) String imageType,
                                 @Part MultipartBody.Part file);
    }*/

}
