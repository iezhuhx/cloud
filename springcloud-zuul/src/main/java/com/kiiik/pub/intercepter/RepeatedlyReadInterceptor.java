package com.kiiik.pub.intercepter;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月2日
 */
/*public class RepeatedlyReadInterceptor extends HandlerInterceptorAdapter {
		Log log = LogFactory.getLog(RepeatedlyReadInterceptor.class);

	    @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	        *//**
	         * 对来自后台的请求统一进行日志处理
	         *//*
	    	Object auth = request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
	    	if(auth==null){
		        ResultBean<String> result = new ResultBean<>();
		        result.sessionTimeOut("会话过期，请重新登陆！");
		        ResponseUtils.writeResult(response, result);
		        return false;
	    	}
	        return super.preHandle(request, response, handler);
	    }

	    @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	        RepeatedlyReadRequestWrapper requestWrapper;
	        if (request instanceof RepeatedlyReadRequestWrapper) {
	            // 测试再次获取Body start.... 
	            requestWrapper = (RepeatedlyReadRequestWrapper) request;
	            log.info( getBodyString(requestWrapper));
	            // 测试再次获取Body end....
	        }
	    }

	    @Override
	    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	    }

	    *//**
	     * 获取请求Body
	     *
	     * @param request
	     *
	     * @return
	     *//*
	    public static String getBodyString(final ServletRequest request) {
	        StringBuilder sb = new StringBuilder();
	        InputStream inputStream = null;
	        BufferedReader reader = null;
	        try {
	            inputStream = cloneInputStream(request.getInputStream());
	            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
	            String line = "";
	            while ((line = reader.readLine()) != null) {
	            sb.append(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (inputStream != null) {
	            try {
	                inputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            }
	            if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            }
	        }
	        return sb.toString();
	    }

	    *//**
	     * Description: 复制输入流</br>
	     *
	     * @param inputStream
	     *
	     * @return</br>
	     *//*
	    public static InputStream cloneInputStream(ServletInputStream inputStream) {
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int len;
	        try {
	            while ((len = inputStream.read(buffer)) > -1) {
	            	byteArrayOutputStream.write(buffer, 0, len);
	            }
	            byteArrayOutputStream.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	        return byteArrayInputStream;
	    }
}*/
