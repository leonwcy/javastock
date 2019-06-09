package stock.job;


import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import stock.job.grabdata.FetchStockCompanyDataJob;
import stock.job.grabdata.FetchStockShortDataJob;
import stock1.job.anontation.EnableScan;

@SpringBootApplication
@ImportResource({"classpath:spring/applicationContext-*.xml"})
@EnableScan //测试下通过注解引入bean
public class JobApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }


    @Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder.newJob(FetchStockCompanyDataJob.class).withIdentity("FetchStockCompanyDataJob")
                .usingJobData("name", "World").storeDurably().build();
    }

    //https://www.quartz-scheduler.net/documentation/quartz-2.x/tutorial/crontriggers.html
    @Bean
    public Trigger sampleJobTrigger() {
        return TriggerBuilder.newTrigger().forJob(sampleJobDetail())
               // .withIdentity("sampleTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?")).build();
                .withIdentity("sampleTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 9-15 ? * MON-FRI")).build();
    }
}
