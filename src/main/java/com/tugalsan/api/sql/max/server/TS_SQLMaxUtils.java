package com.tugalsan.api.sql.max.server;

import module com.tugalsan.api.log;
import module com.tugalsan.api.sql.conn;

public class TS_SQLMaxUtils {

    final private static TS_Log d = TS_Log.of(TS_SQLMaxUtils.class);

    public static TS_SQLMax max(TS_SQLConnAnchor anchor, CharSequence tableName, int colIdx) {
        var columnName = TS_SQLConnColUtils.names(anchor, tableName).get(colIdx);
        d.ci("max", "columnName", columnName);
        return new TS_SQLMax(anchor, tableName, columnName);
    }

    public static TS_SQLMax max(TS_SQLConnAnchor anchor, CharSequence tableName, CharSequence columnName) {
        return new TS_SQLMax(anchor, tableName, columnName);
    }

//    public static void test() {
//        var max = TS_SQLMaxUtils
//                .max(null, "tn", "cn")
//                .whereConditionAnd(conditions -> conditions.lngEq("", 0))
//                .val();
//    }
}
