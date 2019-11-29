package com.cyb.flink.kafka;

import com.cyb.utils.random.RandomUtils;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * 描述：
 *
 * @author iechenyb
 * @create --
 */
public class KafkaProducerStringTest {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");//同步所有节点
        props.put("retries", 0);//失败，则不尝试
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 1024*1024);//33554432
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        int totalMessageCount = 10000;
        for (int i = 0; i < totalMessageCount; i++) {
            String value = RandomUtils.getPassWord(5);
            Future<RecordMetadata> send = producer.send(new ProducerRecord<>("string-key", value), new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        System.out.println("Failed to send message with exception " + exception);
                    }
                }
            });
            long time = 100L*RandomUtils.getNum(1,10);
            System.out.println(value+" sleep:"+time);
            Thread.sleep(time);
        }
        producer.close();
    }


}
