package com.gupaoedu.vip.spring.demo.service.impl;

import com.gupaoedu.vip.spring.demo.service.IModifyService;
import com.gupaoedu.vip.spring.framework.annotation.GPService;

/**
 * Created by Maxiaohong on 2019-03-02.
 */
@GPService
public class ModifyService implements IModifyService {
    @Override
    public String add(String name, String addr) {
        return "modifyService add,name=" + name + ",addr=" + addr;
    }

    @Override
    public String edit(Integer id, String name) {
        return "modifyService edit,id=" + id + ",name=" + name;
    }

    @Override
    public String remove(Integer id) {
        return "modifyService id=" + id;
    }
}
