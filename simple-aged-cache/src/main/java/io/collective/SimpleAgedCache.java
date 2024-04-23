package io.collective;

import java.time.Clock;

public class SimpleAgedCache {

    private final Clock clock;
    private Object[] keys;
    private Object[] values;
    private long[] expirationTimes;
    private int indexSize;
    private static final int MAX_SIZE = 100; // Arbitrary maximum number of entries in the cache


    public SimpleAgedCache(Clock clock) {
        this.clock = clock;
        this.keys = new Object[MAX_SIZE];
        this.values = new Object[MAX_SIZE];
        this.expirationTimes = new long[MAX_SIZE];
        this.indexSize = 0;
    }

    public SimpleAgedCache() {
        // Constructor chaining - Set default zone and set other variables by using the other constructor SimpleAgedCache(Clock clock)
        this(Clock.systemDefaultZone());
    }

    public void put(Object key, Object value, int retentionInMillis) {
        this.keys[indexSize] = key;
        this.values[indexSize] = value;
        this.expirationTimes[indexSize] = clock.millis() + retentionInMillis;;
        indexSize++;
    }

    public boolean isEmpty() {
        // Validate size before returning
        validateSize();
        return indexSize == 0;
    }

    public int size() {
        // Validate size before returning
        validateSize();
        return indexSize;
    }

    public Object get(Object key) {
        long currentTime = clock.millis();
        for (int i = 0; i < indexSize; i++) {
            if (this.keys[i].equals(key)){
                if (this.expirationTimes[i] >= currentTime) {
                    return this.values[i];
                }
                else{
                    return null; // Exit the loop after removing an expired entry
                }
            }
        }
        // If the key is not found or the entry has expired, return null
        return null;
    }

    private void validateSize(){
        long currentTime = clock.millis();
        for (int i = 0; i < indexSize; i++) {
            if (expirationTimes[i] < currentTime) {
                removeEntryAt(i);
                i--; // After removal, recheck the same index
            }
        }
    }

    private void removeEntryAt(int index) {
        // Shift elements to fill the gap caused by removal
        for (int i = index; i < indexSize - 1; i++) {
            this.keys[i] = this.keys[i + 1];
            this.values[i] = this.values[i + 1];
            this.expirationTimes[i] = this.expirationTimes[i + 1];
        }
        // Clear the last element to prevent duplicates
        this.keys[indexSize - 1] = null;
        this.values[indexSize - 1] = null;
        this.expirationTimes[indexSize - 1] = 0;
        indexSize--; // Update the size after removal
    }

}

