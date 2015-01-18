package com.magic.momir.utils;

public class EndpointUtil {
    public static final String ROOT = "https://momir.herokuapp.com";
    private static final String IMAGE_ENDPOINT = "https://api.mtgdb.info/content/hi_res_card_images/%s.jpg";

    public static String getImageEndpoint(final String multiverseId) {
        return String.format(IMAGE_ENDPOINT, multiverseId);
    }
}
