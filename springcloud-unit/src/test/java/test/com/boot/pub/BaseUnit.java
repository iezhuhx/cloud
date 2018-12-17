package test.com.boot.pub;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.kiiik.TaskPlatformStarter;
import com.kiiik.pub.bean.BaseResult;
import com.kiiik.pub.mybatis.service.GenericService;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年5月28日下午2:15:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskPlatformStarter.class)
@ActiveProfiles("dev")
@WebAppConfiguration
@ContextConfiguration(locations = {})
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class BaseUnit {
	public final String SKIP = "1";// 是否跳过请求参数测试
	public final boolean showRQ = true;// 是否显示请求参数
	protected final String GET = "get";
	protected final String PUT = "put";
	protected final String DELETE = "delete";
	protected final String POST = "post";
	@Autowired
	public WebApplicationContext context;

	public MockMvc mockMvc;

	@Autowired
	protected GenericService genericService;
	

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	public void exeAjaxRq(String uri,Object data,String type) throws Exception {
		switch(type){
		case GET: exeGet(uri);break;
		case POST: exePost(uri, JSON.toJSONString(data));break;
		case DELETE:exeDelete(uri);break;
		case PUT: exePut(uri, JSON.toJSONString(data));break;
		}
	}
	public void exeAjaxRq(String uri,String data,String type) throws Exception {
		switch(type){
		case GET: exeGet(uri);break;
		case POST: exePost(uri, data);break;
		case DELETE:exeDelete(uri);break;
		case PUT: exePut(uri, data);break;
		}
	}
	public void exeAjaxRq(String uri,Object data) throws Exception {
		if(StringUtils.isEmpty(data))	{
			exePost(uri,JSON.toJSONString(data));
		}else{
			exeGet(uri);
		}	
	}
	
	public void exeAjaxRq(String uri,String data) throws Exception {
		if(StringUtils.isEmpty(data))	{
			exePost(uri,data);
		}else{
			exeGet(uri);
		}	
	}
	
	public void exePost(String uri,String data) throws UnsupportedEncodingException, Exception{
		String responseString = mockMvc
				.perform(post(uri).accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON_UTF8).content(data.getBytes()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.ec", is(BaseResult.SUCCESS)))
				.andReturn().getResponse().getContentAsString();
		System.out.println("result: " + responseString);
	}
	public void exePut(String uri,String data) throws UnsupportedEncodingException, Exception{
		String responseString = mockMvc
				.perform(put(uri).accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON_UTF8).content(data.getBytes()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.ec", is(BaseResult.SUCCESS)))
				.andReturn().getResponse().getContentAsString();
		System.out.println("result: " + responseString);
	}
	public void exeGet(String uri) throws UnsupportedEncodingException, Exception{
		String responseString = mockMvc
				.perform(get(uri).accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.ec", is(BaseResult.SUCCESS)))
				.andReturn().getResponse().getContentAsString();
		System.out.println("result: " + responseString);
	}
	public void exeDelete(String uri) throws UnsupportedEncodingException, Exception{
		String responseString = mockMvc
				.perform(delete(uri).accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.ec", is(BaseResult.SUCCESS)))
				.andReturn().getResponse().getContentAsString();
		System.out.println("result: " + responseString);
	}

}
