package com.bifel.hashmap;

/**
 * Simple hash map with open addressing, integer keys and long values.
 *
 * Note: {@link Integer#MIN_VALUE} cannot be used as a key
 * and {@link Long#MIN_VALUE} cannot be used as a value in this implementation.
 */
public class HashMap {

    /**
     * long is a primitive, so it cannot be null.
     * This constants {@value NULL_LONG} and {@value NULL_INTEGER}
     * indicates that there were no associated value in this map.
     */
    public static final long NULL_LONG = Long.MIN_VALUE;
    public static final int NULL_INTEGER = Integer.MIN_VALUE;

    private int[] keys;
    private long[] values;

    private int capacity = 1;
    private int size;
    private int threshold;
    private float loadFactor;


    /**
     * Creates new map with default start size of 16 and load factor of 0.75
     */
    public HashMap() {
        this(16, 0.75f);
    }

    /**
     * Creates new map with custom starting size and default load factor of 0.75.
     *
     * @param initCapacity starting size of data array which approximate to 2 ^ (some number).
     */
    public HashMap(int initCapacity) {
        this(initCapacity, 0.75f);
    }

    /**
     * Creates new map with custom starting size and load factor.
     * Useful if you already know which number of entries to expect.
     *
     * @param loadFactor determines how much of the array should be filled to double it.
     */
    public HashMap(int initCapacity, float loadFactor) {
        while (capacity < initCapacity) {
            this.capacity = capacity << 1;
        }

        this.loadFactor = loadFactor;
        threshold = (int) (capacity * loadFactor);
        size = 0;

        keys = new int[capacity];
        values = new long[capacity];
        for (int i = 0; i < capacity; i++) {
            keys[i] = NULL_INTEGER;
            values[i] = NULL_LONG;
        }
    }

    /**
     * Bind given value at given key in this hash map.
     *
     * @param key   integer key.
     * @param value long value to be assigned.
     */
    public void put(int key, long value) {
        int index = indexFor(key);
        while (keys[index] != NULL_INTEGER) {
            if (keys[index] == key) {
                values[index] = value;
                return;
            }
            index = index != capacity ? index + 1 : 0;// cycle if overload
        }
        keys[index] = key;
        values[index] = value;
        size++;

        if (size > threshold) {
            resize(capacity * 2);
        }
    }

    /**
     * Use for double storage when {@link #capacity} > {@link #threshold}
     */
    private void resize(int newCapacity) {
        int[] newKeys = new int[newCapacity];
        long[] newValues = new long[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newKeys[i] = NULL_INTEGER;
            newValues[i] = NULL_LONG;
        }

        for (int i = 0; i < capacity; i++) {
            if (keys[i] != NULL_INTEGER) {
                int index = newIndexFor(keys[i], newCapacity);
                while (newKeys[index] != NULL_INTEGER) {
                    if (newKeys[index] == keys[i]) {
                        break;
                    } else {
                        index++;
                    }
                }
                newKeys[index] = keys[i];
                newValues[index] = values[i];
            }
        }
        keys = newKeys;
        values = newValues;
        capacity = newCapacity;
        threshold = (int) (newCapacity * loadFactor);
    }

    /**
     * @return index where this pair will be stored.
     */
    private int indexFor(int key) {
        return key & (capacity - 1);
    }


    /**
     * Use when increasing the storage
     *
     * @return index where this pair will be stored with new capacity.
     */
    private int newIndexFor(int key, int newCapacity) {
        return key & (newCapacity - 1);
    }

    /**
     * Looks for a given key in this map and returns bind value if it contains.
     *
     * @param key key to look for.
     * @return bind value or {@link #NULL_LONG}.
     */
    public long get(int key) {
        for (int index = indexFor(key); true; index++) {
            if (index == capacity) {
                index = 0;//cycle if overload
            }

            if (keys[index] == key) {
                return values[index];
            }
            if (keys[index] == NULL_INTEGER) {
                return NULL_LONG;
            }
        }
    }

    /**
     * Size of this hash map.
     *
     * @return Number of key-value pairs stored in this map.
     */
    public int size() {
        return size;
    }
}
