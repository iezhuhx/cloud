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
 * 描述：
 *
 * @author iechenyb
 * @create --
 */
public class SendLogService {
    static H2DBInfor dbInfo = new H2DBInfor();
    static JdbcConnectionPool pool = H2DBConnectionPool.getJDBCConnectionPool(dbInfo);
    public static void  insertLog() throws SQLException {
        ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
        dbUtil.update(BMSSql.INSERTSQL);
    }
    public static int queryLog() throws SQLException {
        ConnectionUtils dbUtil = new ConnectionUtils(pool.getConnection());
        String sql = ELUtils.el(BMSSql.QUERYSQL, RMap.
                build().put("rq", DateUnsafeUtil.date2long8().toString()));
        List<Map<String,Object>> rss = dbUtil.queryForListMap(sql);
        return Integer.valueOf(rss.get(0).get("TOTAL").toString());
    }
}
