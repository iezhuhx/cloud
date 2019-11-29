package com.cyb.utils.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * 描述：任意数据库连接测试
 *
 * @author iechenyb
 * @create --
 */
@Slf4j
public class ConnectionUtils {

    public static boolean testConnection(String driver,String url,String username,String password){
        try {
            Class.forName(driver);
            Connection conn =getConnection(url,username,password);
            return conn!=null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static Connection getConnection(String url,String username,String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 单节点connection获取
     * @param dbtype
     * @param linktype
     * @param ip
     * @param port
     * @param dbname
     * @param username
     * @param password
     * @return
     */
    public static Connection connection(String dbtype,String linktype,
                                        String ip,String port,
                                        String dbname,String username,String password){
        return connection(dbtype,"单节点",ip,port,dbname,username,password);
    }
    public static Connection connection(String dbtype,String linktype,
                                        String tns,String ip,String port,
                                        String dbname,String username,String password){
        Connection conn = null;
        try {
            String jdbcurl = "";
            if ("ORACLE".equals(dbtype.toUpperCase())) {
                if("单节点".equals(linktype)){
                    jdbcurl = "jdbc:oracle:thin:@" + ip + ":"
                            + port + ":" + dbname;
                }else{//集群
                    jdbcurl = "jdbc:oracle:thin:@ "+ tns;
                }
                conn =initOracle(jdbcurl,username,password, false);

            } else if ("MYSQL".equals(dbtype.toUpperCase())) {
                jdbcurl = "jdbc:mysql://" + ip + ":"
                        + port + "/" + dbname
                        + "?useUnicode=true&characterEncoding=utf-8";
                conn =initMysql(jdbcurl, username,password, false);

            } else if ("DB2".equals(dbtype.toUpperCase())) {
                jdbcurl = "jdbc:db2://" + ip + ":"
                        + port + "/" + dbname;
                conn =initDB2(jdbcurl, username,password, false);

            } else if ("SQLSERVER".equals(dbtype.toUpperCase())) {
                jdbcurl = "jdbc:sqlserver://" + ip + ":"
                        + port + ";DatabaseName=" + dbname;
                conn =initSQLServer(jdbcurl, username,password, false);

            }else if("GREENPLUM".equals(dbtype.toUpperCase())){
                /*jdbcurl = "jdbc:pivotal:greenplum://" + ip + ":"
                        + port + ";DatabaseName=" + dbname;*/
                jdbcurl = tns;
                conn = initGreenPlum(jdbcurl, username, password, false);
            }else if("HIVE".equals(dbtype.toUpperCase())){
                /*jdbcurl = "jdbc:hive2://" + ip + ":"+port + "/default";*/
                jdbcurl = tns;
                conn = initHIVE(jdbcurl, username, password, false);
            }else if("H2".equals(dbtype.toUpperCase())){
                /*jdbcurl = "jdbc:hive2://" + ip + ":"+port + "/default";*/
                jdbcurl = tns;
                conn = initHIVE(jdbcurl, username, password, false);
            }else {
                return null;
            }
        } catch (Exception e) {
            log.error("数据源连接失败",e);
            return null;
        }

        return conn;
    }
    /**
     * @author Liyg
     * @description 创建ORACLE连接信息
     * @param jdbcurl
     * @param username
     * @param password
     * @param autoCommit
     * @return
     */
    public static Connection initOracle(String jdbcurl, String username, String password, boolean autoCommit) {
        Connection conn=null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //连接数据库
            conn = DriverManager.getConnection(jdbcurl, username, password);
            conn.setAutoCommit(autoCommit);
//            logger.debug(jdbcurl);
//            logger.debug("username=【"+username+"】");
//            logger.debug("Oracle DATABASE: Connection success!");
        }catch(Exception se) {
            //连接失败
//            logger.debug("initOra："+jdbcurl + "\r\n" + " Connection failed! " + se.getMessage());
            //System.exit(0);
            log.error("ORACLE连接失败",se);
            return null;
        }
        return conn;
    }

    /**
     * @author Liyg
     * @description 创建DB2连接信息
     * @param jdbcurl
     * @param username
     * @param password
     * @param autoCommit
     * @return
     */
    public static Connection initDB2(String jdbcurl, String username, String password, boolean autoCommit) {
        Connection conn=null;
        try {
            Driver driver=(Driver) Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
            //连接数据库
            DriverManager.registerDriver(driver);
            conn = DriverManager.getConnection(jdbcurl, username, password);
            conn.setAutoCommit(autoCommit);
//            logger.info("db2 DATABASE:" + jdbcurl + " Connection success!");
        }catch(Exception se) {
//            logger.debug("initDB2："+jdbcurl + "\r\n" + " Connection failed! " + se.getMessage());
            log.error("DB2连接失败",se);
            return null;
        }
        return conn;
    }

    /**
     * @author Liyg
     * @description 创建MYSQL连接信息
     * @param jdbcurl
     * @param username
     * @param password
     * @param autoCommit
     * @return
     */
    public static Connection initMysql(String jdbcurl, String username, String password, boolean autoCommit) {
        Connection conn=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //连接数据库
            conn = DriverManager.getConnection(jdbcurl, username, password);
            conn.setAutoCommit(autoCommit);
//            logger.debug(jdbcurl);
//            logger.debug("username=【"+username+"】");
//            logger.debug("MYSQL DATABASE: Connection success!");
        }catch(Exception se) {
            //连接失败
            conn = null;
//            logger.debug("initMYSQL："+jdbcurl + "\r\n" + " Connection failed! " + se.getMessage());
            log.error("MYSQL连接失败",se);
            return null;
        }
        return conn;
    }

    /**
     * @author Liyg
     * @description 创建SQLServer连接信息
     * @param jdbcurl
     * @param username
     * @param password
     * @param autoCommit
     * @return
     */
    public static Connection initSQLServer(String jdbcurl, String username, String password, boolean autoCommit) {
        Connection conn=null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // 连接数据库
            conn = DriverManager.getConnection(jdbcurl, username, password);
            conn.setAutoCommit(autoCommit);
//            logger.debug(jdbcurl);
//            logger.debug("username=【" + username + "】");
//            logger.debug("SQLSERVER DATABASE: Connection success!");
        } catch (Exception se) {
            // 连接失败
//            logger.debug("initSQLServer："+jdbcurl + "\r\n" + " Connection failed! " + se.getMessage());
//            System.exit(0);
            log.error("SQLSERVER连接失败",se);
            return null;
        }
        return conn;
    }


    /**
     * @author zk
     * @description 创建GreenPlum连接信息
     * @param jdbcurl
     * @param username
     * @param password
     * @param autoCommit
     * @return
     */
    public static Connection initGreenPlum(String jdbcurl, String username, String password, boolean autoCommit) {
        Connection conn=null;
        try {
            Class.forName("com.pivotal.jdbc.GreenplumDriver");
            //conn = DriverManager.getConnection("jdbc:pivotal:greenplum://192.168.229.146:5432;DatabaseName=database", "gpadmin", "111");
            conn = DriverManager.getConnection(jdbcurl,username, password);
            conn.setAutoCommit(autoCommit);
//            logger.debug("GreenPlum DATABASE: Connection success!");
        } catch (Exception se) {
            // 连接失败
//            logger.debug("GreenPlum："+jdbcurl + "\r\n" + " Connection failed! " + se.getMessage());
            log.error("GREENPLUM连接失败",se);
            return null;
        }
        return conn;
    }

    /**
     * @author zk
     * @description 创建HIVE连接信息
     * @param jdbcurl
     * @param username
     * @param password
     * @param autoCommit
     * @return
     */
    public static Connection initHIVE(String jdbcurl, String username, String password, boolean autoCommit) {
        Connection conn=null;
        try {
            Class.forName("org.apache.hadoop.hive.jdbc.HiveDriver");
            //conn = DriverManager.getConnection("jdbc:pivotal:greenplum://192.168.229.146:5432;DatabaseName=database", "gpadmin", "111");
            conn = DriverManager.getConnection(jdbcurl,username, password);
            conn.setAutoCommit(autoCommit);
//            logger.debug("HIVE: Connection success!");
        } catch (Exception se) {
            // 连接失败
//            logger.debug("HIVE："+jdbcurl + "\r\n" + " Connection failed! " + se.getMessage());
            log.error("HIVE连接失败",se);
            return null;
        }
        return conn;
    }
    /**
     * @author Liyg
     * @description 根据传入的数据源类型，创建Connection连接信息
     * @param jdbcurl
     * @param username
     * @param password
     * @param autoCommit
     * @param dbtype
     * @return
     */
    public static Connection intiConnection(String jdbcdriver,String jdbcurl, String username, String password, boolean autoCommit, String dbtype) {
        Connection conn = null;
        try {
            Class.forName(jdbcdriver);
            // 连接数据库
            conn = DriverManager.getConnection(jdbcurl, username, password);
            conn.setAutoCommit(autoCommit);
//            logger.debug(jdbcurl);
//            logger.debug("username=【" + username + "】");
//            logger.debug("SQLSERVER DATABASE: Connection success!");
        } catch (Exception se) {
            // 连接失败
//            logger.debug("initSQLServer："+jdbcurl + "\r\n" + " Connection failed! " + se.getMessage());
//            System.exit(0);
            log.error("连接失败",se);
            return null;
        }
        return conn;
    }

    /**
     * 关闭数据库
     * @author Liyg
     * @param conn
     * @param pstmt
     */
    public static void closeDB(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            rs = null;

            if (pstmt != null) {
                pstmt.close();
            }
            pstmt = null;

            if (conn != null) {
                conn.close();
            }
            conn = null;
        } catch (Exception ee) {
            log.error("数据源关闭失败",ee);
        }
    }
}
