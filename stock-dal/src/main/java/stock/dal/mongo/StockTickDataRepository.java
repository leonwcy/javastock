/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stock.dal.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Collation;
import com.mongodb.util.JSON;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import stock.dal.mongo.pojo.StockCompanyObj;
import stock.dal.mongo.pojo.StockCompanyUnitObj;
import stock.dal.mongo.pojo.TicketDataObj;

import java.util.List;
import java.util.Optional;

@Component
public class StockTickDataRepository {
    @Autowired
    @Qualifier("MongoTemplate_db")
    private MongoTemplate mongoTemplate;

    private static String tickdata_hk_collectionName = "tickdata_hk";

    public void save(TicketDataObj obj) {
        mongoTemplate.save(obj);
    }

    public List<TicketDataObj> get(TicketDataObj obj) {
        Query q = new Query();
        if(StringUtils.isEmpty(obj.getCode())){
            Assert.isNull(obj.getCode(),"code can't be empty");
        }
        q.addCriteria(Criteria.where("code").is(obj.getCode()));
        if(!StringUtils.isEmpty(obj.getDate())) {
            q.addCriteria(Criteria.where("date").is(obj.getDate()));
        }

        return mongoTemplate.find(q,TicketDataObj.class,tickdata_hk_collectionName );
    }

    public List<TicketDataObj> get_hk(String code,String date) {
        Query q = new Query();
        if(StringUtils.isEmpty(code)){
            Assert.isNull(code,"code can't be empty");
        }
        if(code.startsWith("HK")){
            code = "hk0"+ code.substring(2);
        }
        q.addCriteria(Criteria.where("code").is(code));
        if(!StringUtils.isEmpty(date)) {
            q.addCriteria(Criteria.where("date").is(date));
        }

        return mongoTemplate.find(q,TicketDataObj.class,tickdata_hk_collectionName );
    }

    public Optional<TicketDataObj> get_day_hk(String code, String date) {
        Query q = new Query();
        if(StringUtils.isEmpty(code)){
            Assert.isNull(code,"code can't be empty");
        }
        if(StringUtils.isEmpty(date)){
            Assert.isNull(code,"date can't be empty");
        }
        if(code.startsWith("HK")){
            code = "hk0"+ code.substring(2);
        }
        //TODO: 这里是一周的时间，不是一天的时间
        q.addCriteria(Criteria.where("code").is(code));
        q.addCriteria(Criteria.where("date").all());


        return mongoTemplate.find(q,TicketDataObj.class,tickdata_hk_collectionName ).stream().findFirst();
    }

}
