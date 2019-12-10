package com.google.ads.interactivemedia.v3.internal;

import java.util.List;
import java.util.RandomAccess;

/* compiled from: IMASDK */
public final class li {
    static boolean a(List<?> list, Object obj) {
        if (obj == kv.a(list)) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        List list2 = (List) obj;
        int size = list.size();
        if (size != list2.size()) {
            return false;
        }
        if (!(list instanceof RandomAccess) || !(list2 instanceof RandomAccess)) {
            return lh.a(list.iterator(), list2.iterator());
        }
        for (int i = 0; i < size; i++) {
            if (!ks.a(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    static int b(List<?> list, Object obj) {
        if (list instanceof RandomAccess) {
            return d(list, obj);
        }
        list = list.listIterator();
        while (list.hasNext()) {
            if (ks.a(obj, list.next())) {
                return list.previousIndex();
            }
        }
        return -1;
    }

    private static int d(List<?> list, Object obj) {
        int size = list.size();
        int i = 0;
        if (obj == null) {
            while (i < size) {
                if (list.get(i) == null) {
                    return i;
                }
                i++;
            }
        } else {
            while (i < size) {
                if (obj.equals(list.get(i))) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    static int c(List<?> list, Object obj) {
        if (list instanceof RandomAccess) {
            return e(list, obj);
        }
        list = list.listIterator(list.size());
        while (list.hasPrevious()) {
            if (ks.a(obj, list.previous())) {
                return list.nextIndex();
            }
        }
        return -1;
    }

    private static int e(List<?> list, Object obj) {
        if (obj == null) {
            for (obj = list.size() - 1; obj >= null; obj--) {
                if (list.get(obj) == null) {
                    return obj;
                }
            }
        } else {
            for (int size = list.size() - 1; size >= 0; size--) {
                if (obj.equals(list.get(size))) {
                    return size;
                }
            }
        }
        return -1;
    }
}
