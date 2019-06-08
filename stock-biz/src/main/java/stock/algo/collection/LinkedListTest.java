package stock.algo.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

public class LinkedListTest {
    private static final Logger logger = LoggerFactory.getLogger(LinkedListTest.class);


    public static void main(String[] args) {
        String[] strs = new String[]{"1","2","3"};
        LinkedList<String> lists = new LinkedList();
        lists.addAll(CollectionUtils.arrayToList(strs));

        logger.info("for loop");
        for(int i =0;i<lists.size();i++){
            logger.info(lists.get(i));
        }
        logger.info("for loop");
        for(String l : lists){
            logger.info(l);
        }
        logger.info("iterator");
        ListIterator<String> iterator = lists.listIterator();
        while (iterator.hasNext()){
            logger.info( iterator.next());
        }
        logger.info("previous iterator");
        while (iterator.hasPrevious()){
            logger.info( iterator.previous());
        }
        logger.info("lamda");
        lists.stream().forEach(s->{logger.info(s);});
        logger.info("queue");
        while (!StringUtils.isEmpty(lists.peek())){
            logger.info(lists.pop());
        }
    }
}
