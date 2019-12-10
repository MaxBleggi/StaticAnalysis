package se.emilsjolander.stickylistheaders;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

class DistinctMultiHashMap<TKey, TItemValue> {
    private IDMapper<TKey, TItemValue> mIDMapper;
    LinkedHashMap<Object, List<TItemValue>> mKeyToValuesMap;
    LinkedHashMap<Object, TKey> mValueToKeyIndexer;

    interface IDMapper<TKey, TItemValue> {
        TKey keyIdToKey(Object obj);

        Object keyToKeyId(TKey tKey);

        TItemValue valueIdToValue(Object obj);

        Object valueToValueId(TItemValue tItemValue);
    }

    DistinctMultiHashMap() {
        this(new IDMapper<TKey, TItemValue>() {
            public TKey keyIdToKey(Object obj) {
                return obj;
            }

            public Object keyToKeyId(TKey tKey) {
                return tKey;
            }

            public TItemValue valueIdToValue(Object obj) {
                return obj;
            }

            public Object valueToValueId(TItemValue tItemValue) {
                return tItemValue;
            }
        });
    }

    DistinctMultiHashMap(IDMapper<TKey, TItemValue> iDMapper) {
        this.mKeyToValuesMap = new LinkedHashMap();
        this.mValueToKeyIndexer = new LinkedHashMap();
        this.mIDMapper = iDMapper;
    }

    public List<TItemValue> get(TKey tKey) {
        return (List) this.mKeyToValuesMap.get(this.mIDMapper.keyToKeyId(tKey));
    }

    public TKey getKey(TItemValue tItemValue) {
        return this.mValueToKeyIndexer.get(this.mIDMapper.valueToValueId(tItemValue));
    }

    public void add(TKey tKey, TItemValue tItemValue) {
        Object keyToKeyId = this.mIDMapper.keyToKeyId(tKey);
        if (this.mKeyToValuesMap.get(keyToKeyId) == null) {
            this.mKeyToValuesMap.put(keyToKeyId, new ArrayList());
        }
        keyToKeyId = getKey(tItemValue);
        if (keyToKeyId != null) {
            ((List) this.mKeyToValuesMap.get(this.mIDMapper.keyToKeyId(keyToKeyId))).remove(tItemValue);
        }
        this.mValueToKeyIndexer.put(this.mIDMapper.valueToValueId(tItemValue), tKey);
        if (!containsValue((List) this.mKeyToValuesMap.get(this.mIDMapper.keyToKeyId(tKey)), tItemValue)) {
            ((List) this.mKeyToValuesMap.get(this.mIDMapper.keyToKeyId(tKey))).add(tItemValue);
        }
    }

    public void removeKey(TKey tKey) {
        if (this.mKeyToValuesMap.get(this.mIDMapper.keyToKeyId(tKey)) != null) {
            for (Object valueToValueId : (List) this.mKeyToValuesMap.get(this.mIDMapper.keyToKeyId(tKey))) {
                this.mValueToKeyIndexer.remove(this.mIDMapper.valueToValueId(valueToValueId));
            }
            this.mKeyToValuesMap.remove(this.mIDMapper.keyToKeyId(tKey));
        }
    }

    public void removeValue(TItemValue tItemValue) {
        if (getKey(tItemValue) != null) {
            List list = (List) this.mKeyToValuesMap.get(this.mIDMapper.keyToKeyId(getKey(tItemValue)));
            if (list != null) {
                list.remove(tItemValue);
            }
        }
        this.mValueToKeyIndexer.remove(this.mIDMapper.valueToValueId(tItemValue));
    }

    public void clear() {
        this.mValueToKeyIndexer.clear();
        this.mKeyToValuesMap.clear();
    }

    public void clearValues() {
        for (Entry entry : entrySet()) {
            if (entry.getValue() != null) {
                ((List) entry.getValue()).clear();
            }
        }
        this.mValueToKeyIndexer.clear();
    }

    public Set<Entry<Object, List<TItemValue>>> entrySet() {
        return this.mKeyToValuesMap.entrySet();
    }

    public Set<Entry<Object, TKey>> reverseEntrySet() {
        return this.mValueToKeyIndexer.entrySet();
    }

    public int size() {
        return this.mKeyToValuesMap.size();
    }

    public int valuesSize() {
        return this.mValueToKeyIndexer.size();
    }

    protected boolean containsValue(List<TItemValue> list, TItemValue tItemValue) {
        for (TItemValue valueToValueId : list) {
            if (this.mIDMapper.valueToValueId(valueToValueId).equals(this.mIDMapper.valueToValueId(tItemValue))) {
                return true;
            }
        }
        return null;
    }

    public TItemValue getValueByPosition(int i) {
        Object[] toArray = this.mValueToKeyIndexer.keySet().toArray();
        if (i <= toArray.length) {
            return this.mIDMapper.valueIdToValue(toArray[i]);
        }
        throw new IndexOutOfBoundsException();
    }
}
