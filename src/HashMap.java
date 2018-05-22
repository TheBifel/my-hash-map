public class HashMap {

    private int[] keys;
    private long[] values;

    private int capacity;
    private int size;
    private int threshold;
    private float loadFactor;

    public static final long NULL_LONG = Long.MIN_VALUE;
    public static final int NULL_INTEGER = Integer.MIN_VALUE;

    public HashMap() {
        this(4, 0.75f);
    }

    public HashMap(int capacity) {
        this(capacity, 0.75f);
    }

    public HashMap(int capacity, float loadFactor) {
        this.loadFactor = loadFactor;

        capacity = 1 << capacity;
        this.capacity = capacity;
        threshold = (int) (capacity * loadFactor);
        size = 0;

        keys = new int[capacity];
        values = new long[capacity];
        for (int i = 0; i < capacity; i++) {
            keys[i] = NULL_INTEGER;
            values[i] = NULL_LONG;
        }
    }

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

        if (size > threshold){
            resize(capacity * 2);
        }
    }

    private void resize(int newCapacity){
        int[] newKeys = new int[newCapacity];
        long[] newValues = new long[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newKeys[i] = NULL_INTEGER;
            newValues[i] = NULL_LONG;
        }

        for (int i = 0; i < capacity; i++){
            if (keys[i] != NULL_INTEGER){
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
        threshold = (int)(newCapacity * loadFactor);
    }

    private int indexFor(int key) {
        return key & (capacity - 1);
    }

    private int newIndexFor(int key, int newCapacity) {
        return key & (newCapacity - 1);
    }

    public long get(int key) {
        int index = indexFor(key);
        while (keys[index] != key && keys[index] != NULL_INTEGER) {
            index = index != capacity ? index + 1 : 0; //cycle if overload
        }
        return keys[index] == NULL_INTEGER ? NULL_LONG : values[index];
    }

    public int size() {
        return size;
    }
}
