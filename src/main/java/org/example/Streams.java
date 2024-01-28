package org.example;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<T> {
    private final Collection<T> collection;

    private Streams(Collection<T> collection) {
        this.collection = collection;
    }

    public static <T> Streams<T> of(Collection<T> collection) {
        return new Streams<>(collection);
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        Iterator<T> iterator = collection.iterator();
        Collection<T> filteredCollection = new ArrayList<>();
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (predicate.test(item)) filteredCollection.add(item);
        }

        return new Streams<T>(filteredCollection);
    }

    public Streams<T> transform(Function<? super T, ? extends T> function) {
        Collection<T> transformed = new ArrayList<>();
        for (T item : collection) {
            transformed.add(function.apply(item));
        }
        return new Streams<T>(transformed);
    }

    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        Map<K, V> map = new HashMap<>();
        for (T item : collection) {
            map.put(keyMapper.apply(item), valueMapper.apply(item));
        }
        return map;
    }

    public List<T> toList() {
        return new ArrayList<>(collection);
    }
}
