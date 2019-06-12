package com.cyb.app.bms;

import com.cyb.app.commondb.ConnectionUtils;
import com.cyb.app.h2.H2DBConnectionPool;
import com.cyb.app.h2.H2DBInfor;
import com.cyb.utils.bean.RMap;
import com.cyb.utils.date.DateUnsafeUtil;
import com.cyb.utils.text.ELUtils;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 描述：sql
 *
 * @author iechenyb
 * @create 2019年6月3日15:04:52
 */
public class BMSSql {
    public static String CREATESQL="create table sendlog(rq varchar,sj varchar)";//日期和时间
    public static String QUERYSQL="select count(1) as total from sendlog where rq=${rq}";
    public static String DELETESQL="DELETE FROM SENDLOG";
    public static String INSERTSQL ="insert into sendlog values('${rq}','${sj}')";
    static H2DBInfor dbInfo = new H2DBInfor();
    static JdbcConnectionPool pool = H2DBConnectionPool.getJDBCConnectionPool(dbInfo);
    public static void main(String[] args) throws SQLException {
        ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
        dbUtil.update(DELETESQL);
        /*dbUtil.update(ELUtils.el(INSERTSQL,RMap.build()
                .put("rq",DateUnsafeUtil.date2long8())
                .put("sj",DateUnsafeUtil.timeToMilis())));*/
        String sql = ELUtils.el(QUERYSQL, RMap.
                build().put("rq", DateUnsafeUtil.date2long8().toString()));
       List<Map<String,Object>> rss = dbUtil.queryForListMap(sql);
        System.out.println(rss.get(0).get("TOTAL"));
    }
}
