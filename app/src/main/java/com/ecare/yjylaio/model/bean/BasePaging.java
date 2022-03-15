package com.ecare.yjylaio.model.bean;

import java.util.List;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.model.bean
 * ClassName: BasePaging
 * Description:
 * Author:
 * CreateDate: 2021/6/18 14:30
 * Version: 1.0
 */
public class BasePaging<T> {

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
