package stock1.job.anontation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by chenye.wu on 2018-03-13.
 */
@Configuration
public class ScanConfig {

    @Autowired
    private Environment env;

    @Bean
    public String[] profiles() {
        return env.getActiveProfiles();
    }

}
