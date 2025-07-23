package com.rdlbe.foundations.utils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectionUtils {

    private CollectionUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> Map<String, T> filterOut(Map<String,T> map, Set<String> keys) {
        if (map == null || keys == null) {
            return map;
        }

        return map.entrySet().stream()
                .filter(v -> !keys.contains(v.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
