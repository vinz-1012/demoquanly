package com.tutorialspoint.jdbc.com.quanlygara.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataChangeNotifier {
    private static final List<DataChangeListener> listeners = new CopyOnWriteArrayList<>();

    private DataChangeNotifier() {}

    public static void addListener(DataChangeListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(DataChangeListener listener) {
        listeners.remove(listener);
    }

    public static void notifyDataChanged(String entityName) {
        for (DataChangeListener listener : listeners) {
            listener.onDataChanged(entityName);
        }
    }
}
