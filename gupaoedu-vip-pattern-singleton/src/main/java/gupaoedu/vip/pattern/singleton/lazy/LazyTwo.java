package gupaoedu.vip.pattern.singleton.lazy;

/**
 * Created by Maxiaohong on 2019-01-27.
 * 懒汉式单例2：
 * 使用synchronized来解决线程不安全的问题。
 */
public class LazyTwo {

    private LazyTwo(){

    }
    private static LazyTwo lazy = null;
    public static synchronized  LazyTwo  getInstance(){
         if(lazy==null){
             lazy = new LazyTwo();
         }
         return lazy;
    }
}
