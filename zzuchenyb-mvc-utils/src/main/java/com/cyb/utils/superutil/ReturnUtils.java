package com.cyb.utils.superutil;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;


public class ReturnUtils {
  public static void main(String[] args) {
	Pair<Integer, Integer> pair = new ImmutablePair<Integer,Integer>(1,2);
	System.out.println(pair.getKey()+","+pair.getValue());
	System.out.println(pair.getLeft()+","+pair.getRight());
	Triple<Integer,Integer,Integer> a = new ImmutableTriple<Integer,Integer,Integer>(1, 2, 3);
	System.out.println(a.getLeft()+","+a.getMiddle()+","+a.getRight());
  }
}
