package com.cyb.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.util.List;

/**
 * 描述：
 *
 * @author iechenyb
 * @create --
 */

/**
 * 从本地文件读取字符串，按空格分割单词，统计每个分词出现的次数并输出
 */
public class StaticsLocalFile {
    public static void main(String[] args) {
        //获取执行环境 ExecutionEnvironment （批处理用这个对象）
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //加载数据源到 DataSet
        DataSet<String> text = env.readTextFile("d:/data/bigdata/test.txt");
        DataSet<Tuple2<String, Integer>> counts =
                text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                        //s 即从文本中读取到一行字符串，按空格分割后得到数组tokens
                        String[] tokens = s.toLowerCase().split("\\s+");
                        for (String token : tokens) {
                            if (token.length() > 0) {
                                //初始化每一个单词，保存为元祖对象
                                collector.collect(new Tuple2<String, Integer>(token, 1));
                            }
                        }
                    }
                })
                        .groupBy(0) //0表示Tuple2<String, Integer> 中的第一个元素，即分割后的单词
                        .aggregate(Aggregations.SUM, 1); //同理，1表示Tuple2<String, Integer> 中的第二个元素，即出现次数

        try {
            //从DataSet 中获得集合，并遍历
            System.out.println("print result:");
            List<Tuple2<String,Integer>> list = counts.collect();
            for (Tuple2<String,Integer> tuple2:list){
                System.out.println(tuple2.f0 + ":" + tuple2.f1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }/*finally{
            System.exit(0);
        }*/
    }
}
