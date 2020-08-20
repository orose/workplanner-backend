package no.roseweb.workplanner.controllers;

public final class RestPath {

    public static final String API = "/api";
    public static final String API_V1 = "/api/v1";

    static final String USERINFO = "/userinfo";
    static final String INVITE = "/invite";
    static final String ORGANIZATION_ID = "/organization/{id}";
    static final String ORGANIZATION_USER = "/organization/{id}/user";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    static final String WORKORDER = "/workorder";
    static final String WORKORDER_ID = "/workorder/{id}";
    static final String WORKORDER_ASSIGN = "/workorder/{id}/assign/{userId}";

    private RestPath() {
    }

}
