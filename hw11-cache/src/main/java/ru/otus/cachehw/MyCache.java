package ru.otus.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.putIfAbsent(key, value);
        sendNotify(key, value, "put");
    }

    @Override
    public void remove(K key) {
        V existedValue = cache.get(key);
        if (existedValue != null) {
            cache.remove(key);
        }
        sendNotify(key, existedValue, "remove");
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        sendNotify(key, value, "get");
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void sendNotify(K key, V value, String operationName) {
        listeners.forEach(listener -> listener.notify(key, value, operationName));
    }
}
