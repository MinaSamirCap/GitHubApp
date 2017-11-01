package com.example.mina.githubrepos.network;

import com.example.mina.githubrepos.models.AccessTokenModel;
import com.example.mina.githubrepos.models.RepoModel;
import com.example.mina.githubrepos.models.UserModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Mina on 5/31/2017.
 */

public interface ApiInterfaces {

    /*public interface LoginApi {
        @Headers("Content-Type: application/json")
        @POST(ApiUrls.API_URL + ApiUrls.O_AUTH_URL)
        Call<String> getApiData(@Body String data);
    }*/


    @GET(ApiUrls.REPO_URL)
    Observable<List<RepoModel>> getRepoData(@Path(value = ApiUrls.REPO_ORG_KEY, encoded = true) String repoName,
                                            @Query(ApiUrls.PAGE_KEY) String page);

    @Headers(ApiUrls.APPLICATION_JSON_HEADER)
    @POST(ApiUrls.ACCESS_TOKEN_URL)
    @FormUrlEncoded
    Observable<AccessTokenModel> getAccessToken(@Field(ApiUrls.CLIENT_ID_KEY) String clientId,
                                                @Field(ApiUrls.CLIENT_SECRET_KEY) String clientSecret,
                                                @Field(ApiUrls.CODE_KEY) String code);

    @GET(ApiUrls.PROFILE_URL)
    Observable<UserModel> getProfile(@Query(ApiUrls.ACCESS_TOKEN_KEY) String accessToken);

    @GET(ApiUrls.USER_PRIVATE_REPOS_URL)
    Observable<List<RepoModel>> getPrivateRepos(@Query(ApiUrls.ACCESS_TOKEN_KEY) String accessToken);

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
