package stock.biz.learn.multiThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class SlowRunnableTask implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(SlowRunnableTask.class);


    public void run(){
        for(int i=0;i<10;i++){
            logger.info("running task .... " + i + " times");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("task finished.... ");

    }
}
