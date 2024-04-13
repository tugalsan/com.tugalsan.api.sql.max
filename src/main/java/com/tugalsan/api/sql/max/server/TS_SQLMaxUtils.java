package com.tugalsan.api.sql.max.server;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.sql.conn.server.*;
import com.tugalsan.api.union.client.TGS_UnionExcuse;

public class TS_SQLMaxUtils {

    final private static TS_Log d = TS_Log.of(TS_SQLMaxUtils.class);

    public static TGS_UnionExcuse<TS_SQLMax> max(TS_SQLConnAnchor anchor, CharSequence tableName, int colIdx) {
        var u_names = TS_SQLConnColUtils.names(anchor, tableName);
        if (u_names.isExcuse()) {
            return u_names.toExcuse();
        }
        var columnName = u_names.value().get(colIdx);
        d.ci("max", "columnName", columnName);
        return TGS_UnionExcuse.of(new TS_SQLMax(anchor, tableName, columnName));
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
