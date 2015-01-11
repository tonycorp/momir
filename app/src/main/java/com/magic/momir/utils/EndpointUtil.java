package com.magic.momir.utils;

public class EndpointUtil {
    public static final String ROOT = "https://api.mtgdb.info";
    private static final String CARD_ENDPOINT = "type m 'Creature' and convertedmanacost eq %s and setId not 'UGL' and setId not 'UNH'";
    private static final String IMAGE_ENDPOINT = "https://api.mtgdb.info/content/hi_res_card_images/%s.jpg";

    public static String getCardQuery(final String cmc) {
        return String.format(CARD_ENDPOINT, cmc);
    }

    public static String getImageEndpoint(final String multiverseId) {
        return String.format(IMAGE_ENDPOINT, multiverseId);
    }
}
