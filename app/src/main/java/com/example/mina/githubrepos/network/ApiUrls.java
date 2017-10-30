package com.example.mina.githubrepos.network;

/**
 * Created by mina on 26/10/17.
 */

public class ApiUrls {

    public static final String REPO_ORG_KEY = "repo_name";
    public static final String PAGE_KEY = "page";
    public static final String CODE_KEY = "code";

    public static final String CALLBACK_IRL = "repoexplore://callback";
    public static final String GITHUB_CLIENT_ID = "71b7fef7d0597166c486";
    public static final String GITHUB_CLIENT_SECRET = "946c6b589cef1f73473246e2f4069593068bd812";

    public static final String BASE_URL = "https://api.github.com";
    public static final String REPO_URL = "/users/{" + REPO_ORG_KEY + "}/repos?";
    public static final String LOGIN_URL = "https://github.com/login/oauth/authorize" +
            "?client_id=" + GITHUB_CLIENT_ID + "&redirect_uri=" + CALLBACK_IRL;

}
