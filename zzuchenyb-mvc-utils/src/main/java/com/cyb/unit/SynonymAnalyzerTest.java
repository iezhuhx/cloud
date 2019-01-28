package com.cyb.unit;
import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

import com.cyb.utils.ik.SynonymsAnalyzer;	

/*  FieldType	说明
	TextField.TYPE_STORED	被分词索引且存储
	TextField.TYPE_STORED	被分词索引但不存储
	StringField.TYPE_STORED	不被分词，它作为一个整体被搜索，索引且存储
	StringField.TYPE_NOT_STORED	不被分词，它作为一个整体被搜索，索引但不存储
	StoredField.TYPE	这是不能被搜索的，它只是被搜索内容的附属物。如URL等
*/
public class SynonymAnalyzerTest {

	Directory dir;
	FSDirectory fsDir;
	IndexSearcher searcher;
	Analyzer analyzer = new SynonymsAnalyzer();
	Version version = Version.LUCENE_47;
	@Before
	public void setUp() throws Exception {
		dir = new RAMDirectory();
		fsDir=FSDirectory.open(new File("d:/data/"));
		IndexWriterConfig conf = new IndexWriterConfig(
				version,
				analyzer);
		IndexWriter writer = new IndexWriter(dir, conf);
		Document doc = new Document();
		doc.add(new TextField("content", "我来自中国广州", Field.Store.YES));
		doc.add(new TextField("name", "iechenyb是csdn博客活跃者", Field.Store.YES));
		writer.addDocument(doc);
		
		Document doc1 = new Document();
		doc1.add(new TextField("content", "远豹是csdn博客活跃者", Field.Store.YES));
		doc1.add(new TextField("name", "我来自中国天朝", Field.Store.YES));
		writer.addDocument(doc1);
		
		writer.close();
		IndexReader reader = DirectoryReader.open(dir);
		searcher = new IndexSearcher(reader);
	}
	
	@Test
	public void test1() throws IOException {
		Term term = new Term("content", "天朝");//天朝
		Query query = new TermQuery(term);
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs docs = searcher.search(query, 10);
		show(docs);
	}
	
	@Test
	public void test2() throws IOException {
		Term term = new Term("content", "俺");
		Query query = new TermQuery(term);
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs docs = searcher.search(query, 10);
		show(docs);
	}
	
	@Test
	public void test3() throws IOException {
		Term term = new Term("content", "五羊城");
		Query query = new TermQuery(term);
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs docs = searcher.search(query, 10);
		show(docs);
	}
	
	public void show(TopDocs docs) throws IOException{
		//相当于将真个doc都查询出来了！
		for(ScoreDoc scoreDoc:docs.scoreDocs){
			Document doc=searcher.doc(scoreDoc.doc);
			System.out.println(doc.get("content")+"\t"+doc.get("name"));
		}
	}
	@Test
    public void searchByTerm() throws IOException {
        // 搜索特定的项
        Query query = new TermQuery(new Term("content", "天朝"));
        showQueryResult(query, 5);
    }
 
    @Test
    public void searchByTermRange() {
        Query query = new TermRangeQuery("id", new BytesRef("1".getBytes()), new BytesRef("5".getBytes()), true, true);
        showQueryResult(query, 5);
    }
 
    @Test//PrefixQuery 
    public void searchByPrefix() {
        Query query = new PrefixQuery(new Term("content", "Apache"));
        showQueryResult(query, 5);
    }
 
    @Test//通配符搜索
    public void searchByWildcard() {
        Query query = new WildcardQuery(new Term("content", "Apache"));
        showQueryResult(query, 5);
    }
 
    @Test//多个条件的查询
    public void searchByBoolean() {
        BooleanQuery booleanQuery = new BooleanQuery();
        Query query1 = new TermQuery(new Term("name", "天朝"));
        Query query2 = new TermQuery(new Term("content", "广州"));
        booleanQuery.add(query1, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
        showQueryResult(booleanQuery, 5);
    }
 
    @Test//模糊匹配搜索
    public void searchByFuzzy() {
       /* FuzzyQuery query = new FuzzyQuery(new Term("name", "天朝"), 20, 10);
        showQueryResult(query, 5);*/
    }
 
    @Test
    public void go_queryParser() throws IOException, ParseException, QueryNodeException{
        String key = "天朝";
    	String defaultFiled = "content";
        //用法1 传统解析器-单默认字段 QueryParser：只能查询一个字段
        QueryParser parser = new QueryParser(
        		version,
        		defaultFiled,
        		analyzer);
        Query query = parser.parse(key);
        showQueryResult(query, 10);
        
        //用法2  传统解析器-多默认字段  MultiFieldQueryParser：
        String[] multiDefaultFields = {"name", defaultFiled};
        MultiFieldQueryParser multiFieldQueryParser = 
        		new MultiFieldQueryParser(version,multiDefaultFields, analyzer);
        multiFieldQueryParser.setDefaultOperator(QueryParser.Operator.OR);// 设置默认的组合操作，默认是 OR
        query = multiFieldQueryParser.parse(key);
        showQueryResult(query, 10);
        //用法3  新解析框架的标准解析器 单字段查询
        StandardQueryParser qpHelper = new StandardQueryParser(analyzer);
        //qpHelper.setAllowLeadingWildcard(true);// 开启第一个字符的通配符匹配，默认关闭因为效率不高
        //qpHelper.setDefaultOperator(Operator.AND);// 改变空格的默认操作符，以下可以改成AND
        qpHelper.setMultiFields(multiDefaultFields);
        query = qpHelper.parse(key, defaultFiled);
        showQueryResult(query, 10);
    }
 
    /**
     * @param query
     * @param num   取回前N个文档
     */
    private void showQueryResult(Query query, Integer num) {
        TopDocs topDocs = null;
        try {
            topDocs = searcher.search(query, num);
            System.out.println("====================");
            show(topDocs);
            System.out.println("====================");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}