package com.gupaoedu.vip.spring.framework.context;

/**
 * Created by Maxiaohong on 2019-03-17.
 */
public abstract class GPAbstractApplicationContext {
    //提供给子类重写的。
    protected  void onRefresh(){

    }

    protected abstract void refreshBeanFactory();
}
