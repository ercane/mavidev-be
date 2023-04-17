package com.mree.demo.mavidev.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Stream;

/**
 * Helper methods to avoid null values
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NullHelper {

    /**
     * To get null Safe list
     *
     * @param inputList input list that is possible null
     * @param <T>       type of input list elements
     * @return null safe list
     */
    public static <T> List<T> nullSafeList(List<T> inputList) {
        return CollectionUtils.isEmpty(inputList) ? Collections.emptyList() : inputList;
    }

    /**
     * To get nullSafe set
     *
     * @param inputSet input set that is possible null
     * @param <T>      type of input set elements
     * @return null safe set
     */
    public static <T> Set<T> nullSafeSet(Set<T> inputSet) {
        return CollectionUtils.isEmpty(inputSet) ? Collections.emptySet() : inputSet;
    }

    /**
     * To get null safe Stream, if input collection is empty returns an empty Stream
     *
     * @param collection input collection that is possible null
     * @param <T>        type of input collection elements
     * @return null safe stream
     */
    public static <T> Stream<T> nullSafeStream(Collection<T> collection) {
        return CollectionUtils.isEmpty(collection) ? Stream.empty() : collection.stream();
    }

    /**
     * To get null safe Map.Entry Stream, if input map  is empty returns an empty Stream
     *
     * @param map input map taht is possible null
     * @param <T> type of map key
     * @param <V> type of map value
     * @return null safe Map.Entry stream
     */
    public static <T, V> Stream<Map.Entry<T, V>> nullSafeStream(Map<T, V> map) {
        return CollectionUtils.isEmpty(map) ? Stream.empty() : map.entrySet().stream();
    }
}
