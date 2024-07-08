package com.rs.ecommerceapi.security;

public interface Iconstant {
    String GOOGLE_CLIENT_ID = "19893606297-4brfjr83ip6ct7hean250kofkkqqmcmn.apps.googleusercontent.com";

    String GOOGLE_CLIENT_SECRET = "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";

    String GOOGLE_REDIRECT_URI = "http://localhost:3000/login";

    String GOOGLE_GRANT_TYPE = "authorization_code";

    String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";

    String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
}
