package stock.job;


import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import stock.job.grabdata.FetchStockShortDataJob;

@SpringBootApplication
@ImportResource({"classpath:spring/applicationContext-*.xml"})
public class JobApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(JobApplicationTest.class, args);
    }


    @Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder.newJob(FetchStockShortDataJob.class).withIdentity("FetchStockShortDataJobTest")
                .usingJobData("name", "World").storeDurably().build();
    }

    //https://www.quartz-scheduler.net/documentation/quartz-2.x/tutorial/crontriggers.html
    @Bean
    public Trigger sampleJobTrigger() {
        return TriggerBuilder.newTrigger().forJob(sampleJobDetail())
               .withIdentity("sampleTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?")).build();

    }
}
