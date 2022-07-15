package com.zx.hoperun.tc.vo;

import com.zx.hoperun.tc.entity.TcData;
import lombok.Data;

@Data
public class TcResult {
    private String gid;
    private String seqNum;
    private String message;
    private String status;
    private TcData data;

    public TcResult(TcData t) {
        this.data = t;
    }
}
