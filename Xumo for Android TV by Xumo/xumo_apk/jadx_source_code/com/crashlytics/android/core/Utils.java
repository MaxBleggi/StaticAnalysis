package com.crashlytics.android.core;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

final class Utils {
    private static final FilenameFilter ALL_FILES_FILTER = new FilenameFilter() {
        public boolean accept(File file, String str) {
            return true;
        }
    };

    private Utils() {
    }

    static int capFileCount(File file, int i, Comparator<File> comparator) {
        return capFileCount(file, ALL_FILES_FILTER, i, comparator);
    }

    static int capFileCount(File file, FilenameFilter filenameFilter, int i, Comparator<File> comparator) {
        file = file.listFiles(filenameFilter);
        filenameFilter = null;
        if (file == null) {
            return 0;
        }
        int length = file.length;
        Arrays.sort(file, comparator);
        FilenameFilter length2 = file.length;
        while (filenameFilter < length2) {
            File file2 = file[filenameFilter];
            if (length <= i) {
                return length;
            }
            file2.delete();
            length--;
            filenameFilter++;
        }
        return length;
    }
}
