package com.gupaoedu.vip.spring.demo.service.impl;

import com.gupaoedu.vip.spring.demo.service.IQueryService;
import com.gupaoedu.vip.spring.framework.annotation.GPService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Maxiaohong on 2019-03-02.
 */
@GPService
public class QueryService implements IQueryService {
    @Override
    public String  query(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        String json = "{name:\"" + name + "\",time:\"" + time + "\"}";
        return json;

    }
}
