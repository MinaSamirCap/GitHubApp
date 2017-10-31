package com.example.mina.githubrepos.models;

/**
 * Created by mina on 31/10/17.
 */

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessTokenModel implements Serializable {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("scope")
    @Expose
    private String scope;

    /**
     * No args constructor for use in serialization
     */
    public AccessTokenModel() {
    }

    /**
     * @param scope
     * @param tokenType
     * @param accessToken
     */
    public AccessTokenModel(String accessToken, String tokenType, String scope) {
        super();
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.scope = scope;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}
