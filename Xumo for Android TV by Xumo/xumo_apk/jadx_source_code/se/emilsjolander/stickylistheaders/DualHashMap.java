package se.emilsjolander.stickylistheaders;

import java.util.HashMap;

class DualHashMap<TKey, TValue> {
    HashMap<TKey, TValue> mKeyToValue = new HashMap();
    HashMap<TValue, TKey> mValueToKey = new HashMap();

    DualHashMap() {
    }

    public void put(TKey tKey, TValue tValue) {
        remove(tKey);
        removeByValue(tValue);
        this.mKeyToValue.put(tKey, tValue);
        this.mValueToKey.put(tValue, tKey);
    }

    public TKey getKey(TValue tValue) {
        return this.mValueToKey.get(tValue);
    }

    public TValue get(TKey tKey) {
        return this.mKeyToValue.get(tKey);
    }

    public void remove(TKey tKey) {
        if (get(tKey) != null) {
            this.mValueToKey.remove(get(tKey));
        }
        this.mKeyToValue.remove(tKey);
    }

    public void removeByValue(TValue tValue) {
        if (getKey(tValue) != null) {
            this.mKeyToValue.remove(getKey(tValue));
        }
        this.mValueToKey.remove(tValue);
    }
}
