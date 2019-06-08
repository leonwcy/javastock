package stock.algo.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class HashSetTest {
    private static final Logger logger = LoggerFactory.getLogger(HashSetTest.class);


    public static void main(String[] args) {
        String[] strs = new String[]{"1","2","b","3","4","a","5","6","7"};

        Set<String> sets = new HashSet();
        sets.addAll(CollectionUtils.arrayToList(strs));
        sets.add("1");

        logger.info("for loop");
        for(String l : sets){
            logger.info(String.valueOf(l.hashCode()));
        }

        logger.info("iterator");
        Iterator<String> iterator = sets.iterator();
        while (iterator.hasNext()){
            logger.info( iterator.next());
        }

        logger.info("lamda");
        sets.stream().forEach(s->{logger.info(s);});



    }
}
