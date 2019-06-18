package com.cyb.flink.model;

import lombok.Data;

/**
 * 描述：
 *
 * @author iechenyb
 * @create --
 */
@Data
public class WordWithCount {
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