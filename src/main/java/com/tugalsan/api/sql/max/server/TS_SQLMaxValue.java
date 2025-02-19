package com.tugalsan.api.sql.max.server;

import com.tugalsan.api.function.client.maythrow.uncheckedexceptions.TGS_FuncMTUCE_OutBool_In1;
import com.tugalsan.api.log.server.*;
import com.tugalsan.api.time.client.*;

public class TS_SQLMaxValue {

    final private static TS_Log d = TS_Log.of(TS_SQLMaxValue.class);

    public TS_SQLMaxValue(TS_SQLMaxExecutor executor) {
        this.executor = executor;
    }
    final private TS_SQLMaxExecutor executor;

    public TGS_Time time(TGS_FuncMTUCE_OutBool_In1<Long> optionalValidator) {
        var val = val();
        if (val == null) {
            return null;
        }
        if (optionalValidator != null && !optionalValidator.validate(val)) {
            return null;
        }
        return TGS_Time.ofTime(val);
    }

    public TGS_Time date(TGS_FuncMTUCE_OutBool_In1<Long> optionalValidator) {
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

    public long nextIdDated(boolean slimToZeroDateYear_defultTrue) {
        var now = TGS_Time.of();
        var year = now.getYear();
        if (slimToZeroDateYear_defultTrue) {//IT CAN STAY, In 3000 ids will be longer
            year = TGS_TimeUtils.slimToZeroDateYear_OnlyIf2XXX(year);
        }
        var first = year * 1000000L + now.getMonth() * 10000L + 1L;//YYYYMMSSSS
        var next = nextId();
        return next > first ? next : first;
    }

    public long nextIdDated() {
        return nextIdDated(true);
    }
}
