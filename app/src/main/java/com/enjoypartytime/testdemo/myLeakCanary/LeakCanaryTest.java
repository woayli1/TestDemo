package com.enjoypartytime.testdemo.myLeakCanary;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/10
 */
public class LeakCanaryTest {

    public static void main(String[] args) {

        Watcher watcher = new Watcher();

        Object object = new Object();
        System.out.println("obj:" + object);
        watcher.watch(object, "");
//        LeakCanaryUtils.sleep(500);

        //释放对象
//        object = null;
//        LeakCanaryUtils.gc();

//        LeakCanaryUtils.sleep(6000);
//        System.out.println("查看是否存在怀疑列表：" + watcher.getRetainedReferences().size());

    }
}
