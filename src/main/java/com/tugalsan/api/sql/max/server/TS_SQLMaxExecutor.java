package com.tugalsan.api.sql.max.server;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.sql.conn.server.*;
import com.tugalsan.api.sql.sanitize.server.*;
import com.tugalsan.api.sql.select.server.*;
import com.tugalsan.api.sql.where.server.*;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;

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
        var sb = new StringBuilder().append("SELECT MAX(").append(columnName).append(") ").append(" FROM ").append(tableName);
        if (where != null) {
            sb.append(" ").append(where);
        }
        return sb.toString();
    }

    public TGS_UnionExcuse<Long> run() {
        var wrap = new Object() {
            TGS_UnionExcuse<Integer> u_fill;
            TGS_UnionExcuse<Boolean> u_isEmpty;
            TGS_UnionExcuse<Long> u_rs_lng_get;
            TGS_UnionExcuseVoid u_select;
        };
        wrap.u_select = TS_SQLSelectStmtUtils.select(anchor, toString(), fillStmt -> {
            if (where != null) {
                wrap.u_fill = where.fill(fillStmt, 0);
            }
        }, rs -> {
            d.ci("run", () -> rs.meta.command());
            wrap.u_isEmpty = rs.row.isEmpty();
            if (wrap.u_isEmpty.isExcuse() || wrap.u_isEmpty.value()) {
                return;
            }
            wrap.u_rs_lng_get = rs.lng.get(0, 0);
        });
        if (wrap.u_fill != null && wrap.u_fill.isExcuse()){
            wrap.u_fill.toExcuse();
        }
        if (wrap.u_isEmpty != null && wrap.u_isEmpty.isExcuse()){
            wrap.u_isEmpty.toExcuse();
        }
        if (wrap.u_rs_lng_get != null && wrap.u_rs_lng_get.isExcuse()){
            wrap.u_rs_lng_get.toExcuse();
        }
        if (wrap.u_select != null && wrap.u_select.isExcuse()){
            wrap.u_select.toExcuse();
        }
        return wrap.u_rs_lng_get;
    }
}
