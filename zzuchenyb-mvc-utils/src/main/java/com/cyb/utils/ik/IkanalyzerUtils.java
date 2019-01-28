package com.cyb.utils.ik;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.util.FilesystemResourceLoader;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2019年1月28日
 */
public class IkanalyzerUtils {
	Log log = LogFactory.getLog(IkanalyzerUtils.class);

	public static List<String> ikString(String text) {
		StringReader re = new StringReader(text);
		IKSegmenter ik = new IKSegmenter(re, true);
		Lexeme lex = null;
		List<String> words = new ArrayList<>();
		try {
			while ((lex = ik.next()) != null) {
				words.add(lex.getLexemeText());
			}
		} catch (Exception e) {
		}
		return words;
	}

	private static List<String> displayTokens(TokenStream ts) throws IOException {
		CharTermAttribute termAttr = ts.addAttribute(CharTermAttribute.class);
		@SuppressWarnings("unused")
		OffsetAttribute offsetAttribute = ts.addAttribute(OffsetAttribute.class);
		ts.reset();
		List<String> words = new ArrayList<>();
		while (ts.incrementToken()) {
			String token = termAttr.toString();
			words.add(token);
			// System.out.print(offsetAttribute.startOffset() + "-" +
			// offsetAttribute.endOffset() + "[" + token + "] ");
		}
		ts.end();
		ts.close();
		return words;
	}

	static String file = "C:\\Users\\DHUser\\workspace\\zzuchenyb-mvc-utils\\src\\main\\resources\\ik\\synonyms.txt";

	@SuppressWarnings("resource")
	public static List<String> synonymsAnalyzer(String text) throws IOException {
		Version ver = Version.LUCENE_47;
		Map<String, String> filterArgs = new HashMap<String, String>();
		filterArgs.put("luceneMatchVersion", ver.toString());
		filterArgs.put("synonyms", file);
		filterArgs.put("expand", "true");
		SynonymFilterFactory factory = new SynonymFilterFactory(filterArgs);
		factory.inform(new FilesystemResourceLoader());
		WhitespaceAnalyzer whitespaceAnalyzer = new WhitespaceAnalyzer(ver);
		//StandardAnalyzer standardAaalyzer = new StandardAnalyzer(ver);
		TokenStream ts = factory.create(whitespaceAnalyzer.tokenStream("someField", text));
		return displayTokens(ts);
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 请注意扩展词汇！<br>
	 * 创建时间: 2017年7月15日
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String text = "通知基于java语言开发的轻量级的中文分词工具包";
		System.out.println(ikString(text));

		text = "JAVA是一个好语言，从到2015年12月1日它已经有20年的历史了";
		System.out.println(ikString(text));
		text = "其实 远报 似 好人";
		System.out.println(synonymsAnalyzer(text));
	}
}
