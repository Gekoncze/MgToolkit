package cz.mg.toolkit.impl;


public class Impl {
    private static ImplApi implApi = null;

    public static ImplApi getImplApi() {
        return implApi;
    }

    public static void setImplApi(ImplApi implApi) {
        Impl.implApi = implApi;
    }
}
