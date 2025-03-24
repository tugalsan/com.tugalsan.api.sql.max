package com.tugalsan.api.sql.max.server;


import com.tugalsan.api.function.client.maythrowexceptions.unchecked.TGS_FuncMTU_In1;
import com.tugalsan.api.sql.conn.server.*;
import com.tugalsan.api.sql.where.server.*;

public class TS_SQLMax {

    public TS_SQLMax(TS_SQLConnAnchor anchor, CharSequence tableName, CharSequence columnName) {
        this.executor = new TS_SQLMaxExecutor(anchor, tableName, columnName);
    }
    final private TS_SQLMaxExecutor executor;

    public TS_SQLMaxValue whereGroupAnd(TGS_FuncMTU_In1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsAnd(groups);
        return new TS_SQLMaxValue(executor);
    }

    public TS_SQLMaxValue whereGroupOr(TGS_FuncMTU_In1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsOr(groups);
        return new TS_SQLMaxValue(executor);
    }

    public TS_SQLMaxValue whereConditionAnd(TGS_FuncMTU_In1<TS_SQLWhereConditions> conditions) {
        return whereGroupAnd(where -> where.conditionsAnd(conditions));
    }

    public TS_SQLMaxValue whereConditionOr(TGS_FuncMTU_In1<TS_SQLWhereConditions> conditions) {
        return whereGroupOr(where -> where.conditionsOr(conditions));
    }

    public TS_SQLMaxValue whereConditionNone() {
        return new TS_SQLMaxValue(executor);
    }
}
