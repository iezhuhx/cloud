package com.cyb.app.seo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.cyb.unit.SynonymAnalyzerTest;
import com.cyb.utils.ik.AnalyzerStratory;
import com.cyb.utils.random.RandomUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 新闻搜索<br>
 * 创建时间: 2019年1月30日
 */
public class SearcherUtils {
	Log log = LogFactory.getLog(SearcherUtils.class);
	static String key="name";
	public static void main(String[] args) throws Exception {
		searcher("中国小儿强");// 特殊符号可能报错！ 
		for (int i = 0; i < 100; i++) {
		  searcher(RandomUtils.getRandomChineseNormal());// 特殊符号可能报错！ 
		}
	}

	static IndexSearcher searcher = null;

	public static void searcher(String queryString) {
		try {
			FSDirectory dir = FSDirectory.open(new File(SynonymAnalyzerTest.fsDirFile));
			IndexReader reader = DirectoryReader.open(dir);
			searcher = new IndexSearcher(reader);
			QueryParser parser = new QueryParser(
					Version.LUCENE_47, 
					key, 
					AnalyzerStratory.getAnalyzer());
			Query query = parser.parse(queryString);
			// 分词内容
			System.out.println(query.toString(key));
			TopDocs tds = searcher.search(query, 5);
			show(tds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void show(TopDocs docs) throws IOException {
		// 相当于将真个doc都查询出来了！
		for (ScoreDoc scoreDoc : docs.scoreDocs) {
			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println(doc.get("content") + "\t" + doc.get(key));
		}
	}
}
