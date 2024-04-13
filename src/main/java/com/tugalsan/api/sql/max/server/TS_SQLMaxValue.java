package com.tugalsan.api.sql.max.server;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.sql.count.server.TS_SQLCountUtils;
import com.tugalsan.api.time.client.*;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import com.tugalsan.api.validator.client.*;

public class TS_SQLMaxValue {

    final private static TS_Log d = TS_Log.of(TS_SQLMaxValue.class);

    public TS_SQLMaxValue(TS_SQLMaxExecutor executor) {
        this.executor = executor;
    }
    final private TS_SQLMaxExecutor executor;

    public TGS_UnionExcuse<TGS_Time> time(TGS_ValidatorType1<Long> optionalValidator) {
        var u_val = val();
        if (u_val.isExcuse()) {
            u_val.toExcuse();
        }
        var val = u_val.value();
        if (optionalValidator != null && !optionalValidator.validate(val)) {
            return TGS_UnionExcuse.ofExcuse(d.className, "date", "optionalValidator != null && !optionalValidator.validate(val)");
        }
        var time = TGS_Time.ofTime(val);
        d.ci("time", () -> time.toString_timeOnly());
        return TGS_UnionExcuse.of(time);
    }

    public TGS_UnionExcuse<TGS_Time> date(TGS_ValidatorType1<Long> optionalValidator) {
        var u_val = val();
        if (u_val.isExcuse()) {
            u_val.toExcuse();
        }
        var val = u_val.value();
        if (optionalValidator != null && !optionalValidator.validate(val)) {
            return TGS_UnionExcuse.ofExcuse(d.className, "date", "optionalValidator != null && !optionalValidator.validate(val)");
        }
        var date = TGS_Time.ofDate(val);
        d.ci("date", () -> date.toString_dateOnly());
        return TGS_UnionExcuse.of(date);
    }

    public TGS_UnionExcuse<Long> val() {
        var val = executor.run();
        if (val.isExcuse()) {//if count 0 return 1;
            var count = TS_SQLCountUtils.count(executor.anchor, executor.tableName).where(executor.where);
            if (count.isExcuse()) {
                return count;
            }
            if (count.value() == 0L) {
                return TGS_UnionExcuse.of(1L);
            }
        }
        d.ci("val", val);
        return val;
    }

    public TGS_UnionExcuse<Long> nextId() {
        var u_val = val();
        if (u_val.isExcuse()) {
            u_val.toExcuse();
        }
        return TGS_UnionExcuse.of(u_val.value() + 1L);
    }

    public TGS_UnionExcuse<Long> nextIdDated(boolean slim2000_defultTrue) {
        var now = TGS_Time.of();
        var year = now.getYear();
        if (slim2000_defultTrue) {
            if (year >= 2000 && year <= 2999) {
                year -= 2000;
            }
        }
        var first = year * 1000000L + now.getMonth() * 10000L + 1L;//YYYYMMSSSS
        var next = nextId();
        if (next.isExcuse()) {
            return next.toExcuse();
        }
        return next.value() > first ? next : TGS_UnionExcuse.of(first);
    }

    public TGS_UnionExcuse<Long> nextIdDated() {
        return nextIdDated(true);
    }
}
