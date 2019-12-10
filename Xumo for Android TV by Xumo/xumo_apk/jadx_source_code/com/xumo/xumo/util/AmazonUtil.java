package com.xumo.xumo.util;

import com.amazon.device.ads.aftv.AmazonFireTVAdsKeyValuePair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmazonUtil {
    public static final String AD_BREAK_PSEUDO_LIVE_ID = "6c690eac-fb11-47f5-a5fb-a3dc5304a3ec";
    public static final String AD_BREAK_TRUE_LIVE_ID = "b187c68c-9a6f-4d25-9a9c-b95b42d440af";
    public static final String AD_BREAK_VOD_ID = "1307eea2-dee8-4392-a779-7099ea8e05f7";
    public static final String APP_ID = "25266728e8334df4b414395377bf9d10";

    public static String getAmazonRating(String str) {
        Map hashMap = new HashMap();
        hashMap.put("UNRATED", "Unrated");
        hashMap.put("Unrated", "Unrated");
        hashMap.put("TV-Y7", "TV-Y7");
        hashMap.put("8+", "TV-Y7");
        hashMap.put("tv-y", "TV-Y");
        hashMap.put("TV-Y", "TV-Y");
        hashMap.put("Age 4", "TV-Y");
        hashMap.put("tv-pg l,v", "TV-PG");
        hashMap.put("tv-pg d,l,s,v", "TV-PG");
        hashMap.put("tv-pg v", "TV-PG");
        hashMap.put("tv-pg l", "TV-PG");
        hashMap.put("tv-pg", "TV-PG");
        hashMap.put("Age 10", "TV-PG");
        hashMap.put("TV-PG", "TV-PG");
        hashMap.put("mature", "TV-MA");
        hashMap.put("MA", "TV-MA");
        hashMap.put("GMA", "TV-MA");
        hashMap.put("tv-ma", "TV-MA");
        hashMap.put("16+", "TV-MA");
        hashMap.put("explicit", "TV-MA");
        hashMap.put("adult", "TV-MA");
        hashMap.put("b-15", "TV-MA");
        hashMap.put("Age 18", "TV-MA");
        hashMap.put("mature-pg13", "TV-MA");
        hashMap.put("Adult", "TV-MA");
        hashMap.put("c", "TV-MA");
        hashMap.put("18+", "TV-MA");
        hashMap.put("d", "TV-MA");
        hashMap.put("16 +", "TV-MA");
        hashMap.put("TV-MA", "TV-MA");
        hashMap.put("TV-G", "TV-G");
        hashMap.put("All Ages", "TV-G");
        hashMap.put("general", "TV-G");
        hashMap.put("nonadult", "TV-G");
        hashMap.put("a", "TV-G");
        hashMap.put("g", "TV-G");
        hashMap.put("tv-g", "TV-G");
        hashMap.put("GT", "TV-G");
        hashMap.put("C8+", "TV-G");
        hashMap.put("exempt", "TV-G");
        hashMap.put("", "TV-14");
        hashMap.put("TV-14", "TV-14");
        hashMap.put("tv-14 v", "TV-14");
        hashMap.put("aa", "TV-14");
        hashMap.put("0", "TV-14");
        hashMap.put("1", "TV-14");
        hashMap.put("2", "TV-14");
        hashMap.put("13 +", "TV-14");
        hashMap.put("C", "TV-14");
        hashMap.put("13+", "TV-14");
        hashMap.put("T", "TV-14");
        hashMap.put("tv-14", "TV-14");
        hashMap.put("b", "TV-14");
        hashMap.put("14+", "TV-14");
        hashMap.put("non-adult", "TV-14");
        hashMap.put("pg-13", "TV-13");
        hashMap.put("R", "R");
        hashMap.put("r", "R");
        hashMap.put("PG-13", "PG-13");
        hashMap.put("pg13", "PG-13");
        hashMap.put("PG", "PG");
        hashMap.put("pg", "PG");
        hashMap.put("TV-NR", "NR");
        hashMap.put("Not Rated", "NR");
        hashMap.put("nr", "NR");
        hashMap.put("NR", "NR");
        hashMap.put("E", "NR");
        hashMap.put("nc-17", "NC-17");
        hashMap.put("G", "G");
        return (String) hashMap.get(str);
    }

    public static String getAmazonGenre(String str) {
        Map hashMap = new HashMap();
        hashMap.put("A", "Adventure");
        hashMap.put("AP", "Audience Participation");
        hashMap.put("AC", "Award Ceremonies & Pageants");
        hashMap.put("CP", "Children’s Programming");
        hashMap.put("CV", "Comedy Variety");
        hashMap.put("CM", "Concert Music");
        hashMap.put("CC", "Conversation, Colloquies");
        hashMap.put("DD", "Daytime Drama");
        hashMap.put("D", "Devotional");
        hashMap.put("DO", "Documentary, General");
        hashMap.put("DN", "Documentary, News");
        hashMap.put("EA", "Evening Animation");
        hashMap.put("FF", "Feature Film");
        hashMap.put("GD", "General Drama");
        hashMap.put("GV", "General Variety");
        hashMap.put("IA", "Instructions, Advice ");
        hashMap.put("MD", "Musical Drama");
        hashMap.put("N", "News");
        hashMap.put("OP", "Official Police");
        hashMap.put("P ", "Paid Political");
        hashMap.put("PV", "Participation Variety");
        hashMap.put("PM", "Popular Music");
        hashMap.put("PD", "Private Detective");
        hashMap.put("QG", "Quiz -Give Away");
        hashMap.put("QP", "Quiz -Panel");
        hashMap.put("SF", "Science Fiction");
        hashMap.put("CS", "Situation Comedy");
        hashMap.put("SA", "Sports Anthology ");
        hashMap.put("SC", "Sports Commentary");
        hashMap.put("SE ", "Sports Event");
        hashMap.put("SN", "Sports News");
        hashMap.put("SM", "Suspense/Mystery");
        hashMap.put("EW", "Western Drama");
        return (String) hashMap.get(str);
    }

    public static String getAmazonAdUrl(List<AmazonFireTVAdsKeyValuePair> list) {
        String str = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (AmazonFireTVAdsKeyValuePair amazonFireTVAdsKeyValuePair : list) {
            if (!(amazonFireTVAdsKeyValuePair.getKey().isEmpty() || amazonFireTVAdsKeyValuePair.getValue().isEmpty())) {
                stringBuilder.append(amazonFireTVAdsKeyValuePair.getKey());
                stringBuilder.append("=");
                stringBuilder.append(amazonFireTVAdsKeyValuePair.getValue());
                stringBuilder.append("&");
            }
        }
        return stringBuilder.length() > null ? stringBuilder.substring(null, stringBuilder.length() - 1) : str;
    }
}
