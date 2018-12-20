package com.kiiik.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月14日
 */
@Component
@ConfigurationProperties(prefix = "system.switch")
public class SwitchProperties {
	private boolean showControllerTime;
	private long showOverExeTime;//显示controller层执行超过的毫秒数日志
    private boolean returnTimeToView;//是否返回时间给请求
	public boolean isShowControllerTime() {
		return showControllerTime;
	}

	public void setShowControllerTime(boolean showControllerTime) {
		this.showControllerTime = showControllerTime;
	}

	public long getShowOverExeTime() {
		return showOverExeTime;
	}

	public void setShowOverExeTime(long showOverExeTime) {
		this.showOverExeTime = showOverExeTime;
	}

	public boolean isReturnTimeToView() {
		return returnTimeToView;
	}

	public void setReturnTimeToView(boolean returnTimeToView) {
		this.returnTimeToView = returnTimeToView;
	}
	
}
