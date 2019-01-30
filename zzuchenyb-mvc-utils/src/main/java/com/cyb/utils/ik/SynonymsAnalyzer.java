package com.cyb.utils.ik;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.apache.lucene.analysis.util.FilesystemResourceLoader;
import org.apache.lucene.util.Version;
import org.springframework.util.ResourceUtils;
import org.wltea.analyzer.lucene.IKTokenizer;

import com.cyb.utils.file.FileUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2019年1月28日
 */
public class SynonymsAnalyzer extends Analyzer {
	Log log = LogFactory.getLog(SynonymsAnalyzer.class);
	private  String synonymsPath="";

	public SynonymsAnalyzer()   {
		try {
			File cfgFile = ResourceUtils.getFile("classpath:ik/synonyms.txt");
			cfgFile = FileUtils.classPathFile2("ik/synonyms.txt"); 
			this.synonymsPath = cfgFile.getAbsolutePath();
			System.out.println(this.synonymsPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SynonymFilterFactory getSynonymFilterFactory() throws IOException {
		Version ver = Version.LUCENE_47;
		if (synonymsPath.contains("classpath:")) {
			String path = synonymsPath.replace("classpath:", "");
			Map args = new HashMap<>();
			args.put("synonyms", path);
			args.put("luceneMatchVersion", ver.toString());
			args.put("synonyms", synonymsPath);
			args.put("expand", "true");
			SynonymFilterFactory factory = new SynonymFilterFactory(args);
			factory.inform(new ClasspathResourceLoader());
			return factory;
		}
		int index = synonymsPath.lastIndexOf(File.separator);
		String dir = synonymsPath.substring(0, index);
		String name = synonymsPath.substring(index + 1);
		Map args = new HashMap<>();
		args.put("synonyms", name);
		args.put("luceneMatchVersion", ver.toString());
		args.put("synonyms", synonymsPath);
		args.put("expand", "true");
		SynonymFilterFactory factory = new SynonymFilterFactory(args);
		@SuppressWarnings("unused")
		Path baseDirectory = Paths.get(dir);
		FilesystemResourceLoader loader = new FilesystemResourceLoader();// baseDirectory
		factory.inform(loader);
		return factory;
	}

	@Override
	protected TokenStreamComponents createComponents(String arg0, Reader reader) {
		SynonymFilterFactory factory = null;
		try {
			factory = getSynonymFilterFactory();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Tokenizer tokenizer = new IKTokenizer(reader, true);
		if (factory != null) {
			TokenStream tokenStream = factory.create(tokenizer);
			return new TokenStreamComponents(tokenizer, tokenStream);
		}
		return new TokenStreamComponents(tokenizer);
	}
}
