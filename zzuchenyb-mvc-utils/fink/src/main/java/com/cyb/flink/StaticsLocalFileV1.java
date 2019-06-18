package com.cyb.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
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
public class StaticsLocalFileV1 {
    public static void main(String[] args) {
        //获取执行环境 ExecutionEnvironment （批处理用这个对象）
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //加载数据源到 DataSet
        DataSet<String> text = env.readTextFile("d:/data/bigdata/test.txt");
        //用 WordWithCount 保存单词和次数信息
        DataSet<WordWithCount> counts =
                text.flatMap(new FlatMapFunction<String, WordWithCount>() {
                    @Override
                    public void flatMap(String s, Collector<WordWithCount> collector) throws Exception {
                        String[] tokens = s.toLowerCase().split("\\s+");
                        for (String token : tokens) {
                            if (token.length() > 0) {
                                collector.collect(new WordWithCount(token, 1));
                            }
                        }
                    }
                })
                        .groupBy("word")//直接指定字段名称
                        .reduce(new ReduceFunction<WordWithCount>() {
                            @Override
                            public WordWithCount reduce(WordWithCount wc, WordWithCount t1) throws Exception {
                                return new WordWithCount(wc.word, wc.count + t1.count);
                            }
                        });
        try {
            System.out.println("print result:");
            List<WordWithCount> list = counts.collect();
            for (WordWithCount wc: list) {
                System.out.println(wc.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // pojo
    public static class WordWithCount {
        public String word;
        public long count;

        public WordWithCount() {
        }

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return word + " : " + count;
        }
    }
}
