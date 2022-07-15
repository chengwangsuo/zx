package com.zx.hoperun.common.entity;

import lombok.Data;

import java.util.Map;

@Data
public class BaseParams {
    private String url;
    private Map<String,String> map;
}
