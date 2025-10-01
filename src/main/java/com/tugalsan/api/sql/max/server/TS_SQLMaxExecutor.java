package com.tugalsan.api.sql.max.server;

import module com.tugalsan.api.log;
import module com.tugalsan.api.tuple;
import module com.tugalsan.api.sql.conn;
import module com.tugalsan.api.sql.sanitize;
import module com.tugalsan.api.sql.select;
import module com.tugalsan.api.sql.where;

public class TS_SQLMaxExecutor {

    private static final TS_Log d = TS_Log.of(TS_SQLMaxExecutor.class);

    public TS_SQLMaxExecutor(TS_SQLConnAnchor anchor, CharSequence tableName, CharSequence columnName) {
        this.anchor = anchor;
        this.tableName = tableName;
        this.columnName = columnName;
    }
    final public TS_SQLConnAnchor anchor;
    final public CharSequence tableName;
    final public CharSequence columnName;

    public TS_SQLWhere where = null;

    @Override
    public String toString() {
        TS_SQLSanitizeUtils.sanitize(columnName);
        TS_SQLSanitizeUtils.sanitize(tableName);
        var sb = new StringBuilder().append(anchor.tagSelectAndSpace()).append("MAX(").append(columnName).append(") ").append(" FROM ").append(tableName);
        if (where != null) {
            sb.append(" ").append(where);
        }
        return sb.toString();
    }

    public Long run() {
        TGS_Tuple1<Long> pack = new TGS_Tuple1();
        TS_SQLSelectStmtUtils.select(anchor, toString(), fillStmt -> {
            if (where != null) {
                where.fill(fillStmt, 0);
            }
        }, rs -> {
            d.ci("run", () -> rs.meta.command());
            if (rs.row.isEmpty()) {
                return;
            }
            pack.value0 = rs.lng.get(0, 0);
        });
        return pack.value0;
    }
}
