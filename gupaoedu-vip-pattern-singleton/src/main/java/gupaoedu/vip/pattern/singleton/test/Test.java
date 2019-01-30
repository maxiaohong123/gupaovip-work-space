package gupaoedu.vip.pattern.singleton.test;

import gupaoedu.vip.pattern.singleton.lazy.LazyOne;
import gupaoedu.vip.pattern.singleton.lazy.LazyThree;
import gupaoedu.vip.pattern.singleton.lazy.LazyTwo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Maxiaohong on 2019-01-27.
 *
 */
public class Test {
    public static void main(String[] args) throws InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        int count=200;
//       final CountDownLatch countDownLatch = new CountDownLatch(count);
//      //  final Set<Hungry> syncSet = Collections.synchronizedSet(new HashSet<Hungry>());
//        long start = System.currentTimeMillis();
//        for(int i=0;i<count;i++){
//            new Thread(){
//                @Override
//                public void run() {
//                    try {
//                        countDownLatch.await();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(System.currentTimeMillis()+":"+ LazyThree.getInstance());
//                }
//            }.start();
//            countDownLatch.countDown();
//            //System.out.println(LazyTwo.getInstance());
//        }
//        long end = System.currentTimeMillis();
//      //  System.out.println("总耗时："+(end-start));

        //-------------------------静态内部类单例被侵犯测试---------------------------
        Class<?> clazz = LazyThree.class;
//        java.lang.reflect.Constructor<?>[] c =  clazz.getDeclaredConstructors();
//        for(java.lang.reflect.Constructor cc: c){
//            try {
//                //用反射强制访问
//                cc.setAccessible(true);
//                cc.newInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        Constructor s = clazz.getDeclaredConstructor();
        s.setAccessible(true);
        LazyThree ll = (LazyThree) s.newInstance();
        System.out.println("无参构造函数");
    }
}
