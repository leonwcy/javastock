package stock.biz.learn.multiThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class SlowTask implements Callable<Integer> {
    private final Logger logger = LoggerFactory.getLogger(SlowTask.class);


    public void run() {
        for(;;){
            logger.info("running task ....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public Integer call() throws Exception {

        for(int i=0;i<10;i++){
            logger.info("running task .... " + i + " times");
            Thread.sleep(1000);
        }
        logger.info("task finished.... ");
        return null;
    }
}
