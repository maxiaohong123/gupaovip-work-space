package gupaoedu.vip.pattern.singleton.hungry;

/**
 * Created by Maxiaohong on 2019-01-27.
 * 饿汉式单例：
 * 优点：在访问之前就已经实例化，线程安全。
 * 缺点：占用内存，即使不用，也要一直占着内存。
 */
public class Hungry {
    //1、构造函数私有化
    private Hungry(){

    }
    //2、先静态，后动态；先属性，后方法；先上后下
    private static final  Hungry hungry = new Hungry();
    public static  Hungry getInstance(){
        System.out.println(System.currentTimeMillis()+":"+hungry);
        return  hungry;
    }



}
