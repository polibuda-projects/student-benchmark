package com.example.studentbenchmark;

import com.google.gson.JsonObject;

public class TestConstants {

    public static final String USER_NICKNAME = "nickname";
    public static final String USER_EMAIL = "email@email.com";
    public static final String USER_PASSWORD = "P@ssw0rd";
    public static final String USER_NEW_PASSWORD = "P@ssw0rdNEW";

    public static final JsonObject CHANGE_USER_PASSWORD_REQUEST = createChangeUserPasswordRequest();

    private static JsonObject createChangeUserPasswordRequest() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("oldPassword", USER_PASSWORD);
        jsonObject.addProperty("newPassword", USER_NEW_PASSWORD);
        jsonObject.addProperty("newPasswordRepeated", USER_NEW_PASSWORD);
        return jsonObject;
    }

}
