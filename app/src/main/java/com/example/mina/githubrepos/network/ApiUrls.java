package com.example.mina.githubrepos.network;

/**
 * Created by mina on 26/10/17.
 */

public class ApiUrls {

    public static final String REPO_ORG_KEY = "repo_name";
    public static final String PAGE_KEY = "page";

    public static final String BASE_URL = "https://api.github.com";
    public static final String REPO_URL = "/users/{" + REPO_ORG_KEY + "}/repos?";

}
