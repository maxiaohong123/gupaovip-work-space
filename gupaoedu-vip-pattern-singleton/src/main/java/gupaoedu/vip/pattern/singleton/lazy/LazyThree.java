package gupaoedu.vip.pattern.singleton.lazy;

/**
 * Created by Maxiaohong on 2019-01-27.
 * 懒汉式单例3：
 * 优点：采用静态内部类，即不占用内存，又是线程安全的。
 * 缺点：通过强制访问构造函数，也可以实例化，所以需要通过init值来控制私有构造函数被侵犯。
 */
public class LazyThree {
    private static boolean init = false;
    private LazyThree(){
        synchronized (LazyThree.class){
            if(init==false){
                init = !init;
            }else{
                throw new RuntimeException("单例已被侵犯");
            }
        }
    }
    public static LazyThree getInstance(){
        return LazyHolder.LAZY;
    }
    private static class LazyHolder{
        private static final LazyThree LAZY = new LazyThree();
    }
}
