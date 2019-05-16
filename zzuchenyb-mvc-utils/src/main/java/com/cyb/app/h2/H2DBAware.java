package com.cyb.app.h2;

public interface H2DBAware {
	public  String getDbUrl(H2DBInfor db);
	public String getUrl(String prix,H2DBInfor db);
}
