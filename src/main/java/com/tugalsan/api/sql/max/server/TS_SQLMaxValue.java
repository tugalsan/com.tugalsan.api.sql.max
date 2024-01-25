package com.tugalsan.api.sql.max.server;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.time.client.*;
import com.tugalsan.api.validator.client.*;

public class TS_SQLMaxValue {

    final private static TS_Log d = TS_Log.of(TS_SQLMaxValue.class);

    public TS_SQLMaxValue(TS_SQLMaxExecutor executor) {
        this.executor = executor;
    }
    final private TS_SQLMaxExecutor executor;

    public TGS_Time time(TGS_ValidatorType1<Long> optionalValidator) {
        var val = val();
        if (val == null) {
            return null;
        }
        if (optionalValidator != null && !optionalValidator.validate(val)) {
            return null;
        }
        return TGS_Time.ofTime(val);
    }

    public TGS_Time date(TGS_ValidatorType1<Long> optionalValidator) {
        var val = val();
        if (val == null) {
            d.ci("date", "val == null");
            return null;
        }
        if (optionalValidator != null && !optionalValidator.validate(val)) {
            d.ci("date", "optionalValidator != null && !optionalValidator.validate(val)", optionalValidator.validate(val));
            return null;
        }
        var date = TGS_Time.ofDate(val);
        d.ci("date", () -> date.toString_dateOnly());
        return date;
    }

    public Long val() {
        var val = executor.run();
        d.ci("val", val);
        return val;
    }

    public long nextId() {
        var val = val();
        return val == null ? 1L : val + 1L;
    }

    public long nextIdDated(boolean slim2000_defultTrue) {
        var now = TGS_Time.of();
        var year = now.getYear();
        if (slim2000_defultTrue) {
            if (year >= 2000 && year <= 2999) {
                year -= 2000;
            }
        }
        var first = year * 1000000L + now.getMonth() * 10000L + 1L;//YYYYMMSSSS
        var next = nextId();
        return next > first ? next : first;
    }

    public long nextIdDated() {
        return nextIdDated(true);
    }
}
