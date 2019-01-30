package com.cyb.utils.ik;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月30日
 */
public class AnalyzerStratory {
	Log log = LogFactory.getLog(AnalyzerStratory.class);
	static Version version = Version.LUCENE_47;
	public static String TYPE_IK="ik";
	public static String TYPE_SYN="syn";
	public static String TYPE_STD="std";
	static Map<String,Analyzer> analyzers=new HashMap<>();
	static {
		analyzers.put(TYPE_IK, new IKAnalyzer());
		analyzers.put(TYPE_SYN, new SynonymsAnalyzer());
		analyzers.put(TYPE_STD, new StandardAnalyzer(version));
	}
	
	public static Analyzer getAnalyzer(){
		return analyzers.get(TYPE_IK);
	}
	
	public static Analyzer getSynAnalyzer(){
		return analyzers.get(TYPE_SYN);
	}
	
	public static Analyzer getStdAnalyzer(){
		return analyzers.get(TYPE_STD);
	}
	
	public static Analyzer getAnalyzer(String type){
		if(analyzers.containsKey(type)){
			return analyzers.get(type);
		}else{
			return getAnalyzer();
		}
	}
}
