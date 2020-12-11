package com.tpw.goo.test;

import com.alibaba.fastjson.JSONObject;
import com.tpw.goo.bean.UrlInfo;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;
import java.util.stream.Collectors;

public class ProducerDemo {
    private final KafkaProducer<String, String> producer;

//    public final static String TOPIC = "test1";
//    public final static String TOPIC = "rebate_yugu_new";
    //public final static String TOPIC = "trade_id_uid";
//    public final static String TOPIC = "user_mingixi_mq";
//    public final static String TOPIC = "kafkaReadStorm";
    public final static String TOPIC = "flinkKafka";
    public final static String WORD_PREFIX = "kafka_";
    protected List<String> wordList = Arrays.asList("aa","bb","cc","dd","ee");
    protected List<String> newWordList = new ArrayList<>();

    protected String generateWordLine()
    {
        Collections.shuffle(this.newWordList);
        int randNum = RandomUtils.nextInt(  0,newWordList.size()-1);
        return StringUtils.join(newWordList.toArray()," ",0,randNum);

    }

    private ProducerDemo() {
        Properties props = new Properties();
//        props.put("zk.connect", "47.107.246.243:2181");
//        props.put("bootstrap.servers", "47.107.246.243:9092");//xxx服务器ip

        props.put("zk.connect", "47.112.111.193:2181");
        props.put("bootstrap.servers", "47.112.111.193:9092");//xxx服务器ip

//        props.put("zk.connect", "localhost:2181");
//        props.put("bootstrap.servers", "localhost:9092");//xxx服务器ip

        props.put("acks", "all");//所有follower都响应了才认为消息提交成功，即"committed"
        props.put("retries", 0);//retries = MAX 无限重试，直到你意识到出现了问题:)
       // props.put("batch.size", 16384);//producer将试图批处理消息记录，以减少请求次数.默认的批量处理消息字节数
        //batch.size当批量的数据大小达到设定值后，就会立即发送，不顾下面的linger.ms
       // props.put("linger.ms", 1);//延迟1ms发送，这项设置将通过增加小的延迟来完成--即，不是立即发送一条记录，producer将会等待给定的延迟时间以允许其他消息记录发送，这些消息记录可以批量处理
       // props.put("buffer.memory", 33554432);//producer可以用来缓存数据的内存大小。
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String, String>(props);

        for (String s:
             wordList) {
            newWordList.add(WORD_PREFIX+s);
        }


    }

    public void produce() {
        int messageNo = 1;
        final int COUNT = 30000;

        UrlInfo urlInfo = new UrlInfo();

        while(messageNo < COUNT) {
            String key = String.valueOf(messageNo);
            JSONObject obj1 = new JSONObject();
            String data = "";
//            if(messageNo%3 !=0)
//            {
//                //data = String.format("hello KafkaProducer message %s from hubo 06291018 ", key);
//                data = String.format("http://www.sina.com/%s/index.html", key);
//            }
//            else
//            {
//                data = String.format("http://www.baidu.com/%s/article.php", key);
//            }

            data = generateWordLine();

       /*     obj1.put("name",data);
            String sendData = JSONObject.toJSONString(obj1);
            System.out.println(sendData);*/

            urlInfo.setUrl(data);

            urlInfo.setCurrentTime(new Date());

            String sendData = JSONObject.toJSONString(urlInfo);
            System.out.println(sendData);

            try {
                producer.send(new ProducerRecord<String, String>(TOPIC, key + "_lpy",/*sendData*/ data));
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            messageNo++;

            System.out.println("send ok messageNo:" + messageNo + " topic:" + TOPIC);
        }

        producer.close();
    }

    public void produce2() {
           // String sendData = "[{\"money\":39,\"uid\":11706825,\"trade_uid\":18693768,\"shijian\":\"zg\",\"platform\":\"jd\",\"create_time\":\"2020-09-10 08:05:40\",\"trade_id\":14843208},{\"money\":7,\"uid\":6682981,\"trade_uid\":18693768,\"shijian\":\"bd\",\"platform\":\"jd\",\"create_time\":\"2020-09-11 12:05:40\",\"trade_id\":14843208},{\"money\":3,\"uid\":519406,\"trade_uid\":18693768,\"shijian\":\"bd\",\"platform\":\"jd\",\"create_time\":\"2020-09-11 13:58:40\",\"trade_id\":14843208}]";

           String sendData = "[{\"money\":39,\"uid\":11706825,\"trade_uid\":18693768,\"shijian\":\"zg\",\"platform\":\"jd\",\"create_time\":\"2020-09-14 22:24:40\",\"trade_id\":14843208}]";
            sendData = sendData.replaceAll("","");

            try {
                producer.send(new ProducerRecord<String, String>(TOPIC, sendData));
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("send ok topic:" + TOPIC);

            producer.close();
    }

    public void produce3() {
        String sendData = "{\"uid\":\"6177508\",\"trade_id\":\"237955\"}";

        sendData = sendData.replaceAll("","");

        try {
            producer.send(new ProducerRecord<String, String>(TOPIC, sendData));
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("send ok topic:" + TOPIC);

        producer.close();
    }

    public static void main(String[] args) {
        new ProducerDemo().produce();
    }


}
