package com.mm.android.deviceaddmodule.openapi;

import com.mm.android.deviceaddmodule.openapi.data.SystemData;

import java.io.Serializable;
import java.util.UUID;

public class BaseRequestParam implements Serializable {
    public String id = UUID.randomUUID().toString();
    public SystemData system = new SystemData();
}
