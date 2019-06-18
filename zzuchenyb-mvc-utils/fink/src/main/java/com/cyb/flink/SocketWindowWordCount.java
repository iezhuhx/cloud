package com.cyb.flink;

import com.cyb.flink.model.WordWithCount;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
/**
 * 描述：
 *
 * @author iechenyb
 * @create --
 */
public class SocketWindowWordCount {
    public static void main(String[] args) throws Exception{
        //连接端口号
        final int port;
        try {
            final ParameterTool params = ParameterTool.fromArgs(args);
            port = params.getInt("port");
            System.out.println("port="+port);
        } catch (Exception e) {
            System.out.println("No port specified. Please run 'SocketWindowWordCount --port <port>'");
            return ;
        }
        //获取执行环节
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //获取连接socket输入数据
        DataStream<String> text = env.socketTextStream("localhost", port,"\n");

        //解析数据、对数据进行分组、窗口函数和统计个数

        DataStream<WordWithCount> windowCounts =text.flatMap(new FlatMapFunction<String, WordWithCount>() {

            private static final long serialVersionUID = 6800597108091365154L;

            public void flatMap(String value, Collector<WordWithCount> out) throws Exception {
                for(String word:value.split(" ")) {
                    out.collect(new WordWithCount(word, 1));
                }
            }
        })
                .keyBy("word")
                .timeWindow(Time.seconds(5),Time.seconds(1))
                .reduce(new ReduceFunction<WordWithCount>() {
                    public WordWithCount reduce(WordWithCount value1, WordWithCount value2) throws Exception {
                        return new WordWithCount(value1.word,value1.count+value2.count);
                    }
                });
        windowCounts.print().setParallelism(1);
        env.execute("Socket Window WordCount");
    }
}
