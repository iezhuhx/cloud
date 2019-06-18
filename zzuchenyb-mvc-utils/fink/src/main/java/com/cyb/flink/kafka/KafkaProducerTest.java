package com.cyb.flink.kafka;

import org.apache.commons.lang3.RandomUtils;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * 描述：
 *
 * @author iechenyb
 * @create --
 */
public class KafkaProducerTest {
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
        int totalMessageCount = 100;
        for (int i = 0; i < totalMessageCount; i++) {

            String value = String.format("%d,%s,%d", System.currentTimeMillis(), "machine-1", currentMemSize());
            System.out.println("msg idx:"+i+"#"+value);
            producer.send(new ProducerRecord<>("test-0921", value), new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        System.out.println("Failed to send message with exception " + exception);
                    }
                }
            });
            Thread.sleep(100L);
        }
        producer.close();
    }

    private static long currentMemSize() {
        //return MemoryUsageExtrator.currentFreeMemorySizeInBytes();
        return RandomUtils.nextLong(1000,9000000);
    }
}
