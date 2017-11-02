package com.example.mina.githubrepos.network;

/**
 * Created by mina on 26/10/17.
 */

public class ApiUrls {

    public static final String REPO_ORG_KEY = "repo_name";
    public static final String PAGE_KEY = "page";
    public static final String CODE_KEY = "code";
    public static final String ACCESS_TOKEN_KEY = "access_token";
    public static final String CLIENT_ID_KEY = "client_id";
    public static final String CLIENT_SECRET_KEY = "client_secret";

    public static final String CALLBACK_URL = "repoexplore://callback";
    public static final String GITHUB_CLIENT_ID = "71b7fef7d0597166c486";
    public static final String GITHUB_CLIENT_SECRET = "946c6b589cef1f73473246e2f4069593068bd812";

    public static final String BASE_URL = "https://api.github.com";
    public static final String BASE_URL2 = "https://github.com";
    public static final String APPLICATION_JSON_HEADER = "Accept: application/json";

    public static final String REPO_URL = "/users/{" + REPO_ORG_KEY + "}/repos?";
    public static final String PROFILE_URL = "/user?";
    public static final String USER_PRIVATE_REPOS_URL = "/user/repos?";
    public static final String ACCESS_TOKEN_URL = "login/oauth/access_token?";
    public static final String O_AUTH_URL = "https://github.com/login/oauth/authorize" +
            "?scope=user:email repo&client_id=" + GITHUB_CLIENT_ID + "&redirect_uri=" + CALLBACK_URL;

}
