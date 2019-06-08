package stock.algo.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class ArrayListTest {
    private static final Logger logger = LoggerFactory.getLogger(ArrayListTest.class);


    public static void main(String[] args) {
        String[] strs = new String[]{"1","2","3"};
        ArrayList<String> lists = new ArrayList();
        lists.addAll(CollectionUtils.arrayToList(strs));
        lists.add(1,"4");

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
    }
}
