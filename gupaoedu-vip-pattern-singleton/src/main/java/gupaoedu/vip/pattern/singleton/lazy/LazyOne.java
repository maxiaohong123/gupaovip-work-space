package gupaoedu.vip.pattern.singleton.lazy;

/**
 * Created by Maxiaohong on 2019-01-27.
 * 懒汉式单例1：
 * 优点：用的时候再实例化，不占用内存。
 * 缺点：用到的时候才实例化，延时加载 ，线程不安全
 */
public class LazyOne {
    private LazyOne(){}
    private static LazyOne lazy = null;
    public static LazyOne getInstance(){
         if(lazy==null){
             lazy = new LazyOne();
         }
         return lazy;
    }
}
