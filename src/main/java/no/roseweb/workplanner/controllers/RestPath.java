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
    static final String WORKORDERS = "/workorders";
    static final String WORKORDERS_ID = "/workorders/{id}";
    static final String WORKORDERS_ASSIGN = "/workorders/{id}/assign/{userId}";

    private RestPath() {
    }

}
