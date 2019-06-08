package stock.algo;

import java.util.Map;

/**
 * Created by chenye.wu on 2018-03-15.
 */
public class HashTest {

    public static void main(String[] args) {

        //Map<String,String> map = new HashMap<String,String>();
        Map<String,String> map = new java.util.HashMap<String,String>();
        map.put("1","cat");
        map.put("2","dog");
        map.put("3","a");
        map.put("4","b");
        map.put("5","c");
        map.put("6","d");



        String t =map.get("5");

    }


}
