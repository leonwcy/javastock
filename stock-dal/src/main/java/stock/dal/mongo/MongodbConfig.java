package stock.dal.mongo;

import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongodbConfig {
    @Bean("MongoTemplate_db")
    public MongoTemplate MongoClient(){
        return new MongoTemplate(new SimpleMongoDbFactory( new MongoClientURI("mongodb://localhost:27017/db")));
    }

}
