package stock.biz.learn.multiThread;

import java.util.concurrent.*;

public class ExecutorBiz {
    static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);


    public static void main(String[] args) throws Exception {
        //forEachSlowTask();
        ThreadExecuterTask();
        return;
    }

    /*****
     * 使用ThreadPoolExecutor 完成
     * @throws InterruptedException
     */
    private static void ThreadExecuterTask() throws InterruptedException {
        BlockingQueue<Runnable> taskQueues = new LinkedBlockingQueue<Runnable>(10000);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0,100,
                10, TimeUnit.MINUTES,taskQueues);
        for(int i=0;i<100;i++)
        {
            executor.execute(new SlowRunnableTask());
        }

        //BlockingQueue<Runnable> curQueue = executor.getQueue();
        executor.shutdown();
        executor.awaitTermination(10000, TimeUnit.SECONDS);
    }

    /****
     * 使用cachedThreadPool 完成, 查看源码发现实际是根据 ThreadPoolExecutor 实现的.
     * 内部使用SynchronousQueue 完成, 所以有1000个任务，很可能同时起1000个线程。
     * @throws InterruptedException
     */
    private static void forEachSlowTask() throws InterruptedException {
        for(int i=0;i<100;i++)
        {
            cachedThreadPool.submit(new SlowTask());
        }

        final Future<Integer> futureTask = cachedThreadPool.submit(new SlowTask());

        //futureTask.get(1000,TimeUnit.HOURS);
        cachedThreadPool.shutdown();
        //阻塞，直到线程池里所有任务结束 ,注意这个方法需要配合 shutdown 使用。
        cachedThreadPool.awaitTermination(10000, TimeUnit.SECONDS);


        //futureTask.cancel(true);
    }

}
