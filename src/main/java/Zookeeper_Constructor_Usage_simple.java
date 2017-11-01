import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class Zookeeper_Constructor_Usage_simple implements Watcher {


    private static CountDownLatch connectedSemaphore  = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181",
                5000,
                new Zookeeper_Constructor_Usage_simple());

        System.out.println(zooKeeper.getState());


        try {
            connectedSemaphore.await();
            System.out.println("在等待");
        } catch (InterruptedException e) {
            System.out.println("出错了: "+e.getMessage());
            e.printStackTrace();

        }
        System.out.println("Zookepper session established.");
    }

    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched event: " + watchedEvent);
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()){
            connectedSemaphore.countDown();
        }
    }
}
