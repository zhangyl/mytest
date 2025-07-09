package com.zyl.mytest.test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        String path = "C:\\Users\\snail\\compare\\";
        String online = path + "online.txt";

        List<String> itemList = getConfigItem(online);

        Collections.sort(itemList);

        for (String item : itemList) {
            System.out.println(item);
        }
        /*
        Map<String, String> onlineMap = getMap(online);



        String offline = path + "offline.txt";
        Map<String, String> offlineMap = getMap(offline);

        List<String> needList = new LinkedList<>();
        for (Map.Entry<String, String> entry : onlineMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            String offlineItem = offlineMap.get(key);

            if(offlineItem == null) {
                needList.add(key + "=" + value);
            }
        }
        Collections.sort(needList);
        for(String s : needList) {
            System.out.println(s);
        }
        */
    }

    public static  Map<String, String> getMap(String filePath) {

        List<String> itemList = getConfigItem(filePath);
        return convertToMap(itemList);
    }

    public static List<String> getConfigItem(String filePath) {

        List<String> destList = new LinkedList<>();
        try {


            List<String> templateFile = Files.readAllLines(Paths.get(filePath));

            for(String p : templateFile) {
                if(p == null || p.trim().isEmpty() || p.trim().startsWith("#")) {
                    continue;
                }
                destList.add(p);
            }


//            System.out.println("######################################################################################");
//            for(String s : destList) {
//                System.out.println(s);
//            }
//            System.out.println("######################################################################################");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return destList;
    }

    public static Map<String, String> convertToMap(List<String> list) {

        Map<String, String> map = new HashMap<>();
        for (String item : list) {
            String[] kv = item.split("=");
            map.put(kv[0].trim(), kv[1]);
        }
        return map;
    }

}
