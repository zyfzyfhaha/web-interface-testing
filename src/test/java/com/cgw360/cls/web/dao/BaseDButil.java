package com.cgw360.cls.web.dao;

import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;  

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cgw360.cls.web.dao.SQLReader;
import com.cgw360.cls.web.util.FileUtil;
import com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor;

	/** 
	 * 数据库工具类 
	 *  
	 * @author zyf 
	 *  
	 */
	@Repository
	public class BaseDButil{  
		
		protected static final Logger LOGGER = Logger.getLogger(BaseDButil.class);
		protected static JdbcTemplate jdbcTemplate;
		protected static JdbcTemplate jdbcTemplateZaAdmin;
		protected static JdbcTemplate jdbcTemplateAuto;
		 
		 public void setJdbcTemplate(final JdbcTemplate template) {
		        this.jdbcTemplate = template;
		    }

		    public JdbcTemplate getJdbcTemplate() {
		        return jdbcTemplate;
		    }
		
		    @Resource(name = "clsDataSource")
		    public void setClsdataSource(final DataSource dataSource) {
		        jdbcTemplate = new JdbcTemplate(dataSource);
		    }
		    
		    @Resource(name = "zaAdminDataSource")
		    public void setZaAdminDataSource(final DataSource dataSource) {
		        jdbcTemplateZaAdmin = new JdbcTemplate(dataSource);
		    }
		    
		    @Resource(name = "autoDataSource")
		    public void setAutoDataSource(final DataSource dataSource) {
		    	jdbcTemplateAuto = new JdbcTemplate(dataSource);
		    }
		    
		    
		    /**
		     * query sql to return result.
		     * @param sqlState
		     *                sql used to query.
		     * @return query result list
		     */
		    public List<Map<String, Object>> querySQL(final String sqlState) {
		        return getJdbcTemplate().queryForList(sqlState);
		    }
		    
		    /**
		     * Execute update sql.
		     * @param sqlState
		     *                sql used to query.
		     */
		    public void executeSQL(final String sqlState) {
		        getJdbcTemplate().update(sqlState);
		    }
		    
		    /**
		     * Batch execute update sqls.
		     * @param sqls
		     *                all the sqls used to query.
		     */
		    public void executeSQL(final List<String> sqls) {
		        String[] arraySqls = new String[sqls.size()];
		        for (int i = 0; i < arraySqls.length; i++) {
		            arraySqls[i] = sqls.get(i);
		        }
		        getJdbcTemplate().batchUpdate(arraySqls);
		    }

		    /**
		     * Read and batch execute update sqls.
		     * @param keys
		     *                all the sql used to query.
		     */
		    public void fetchAndRunSQLs(String[] keys) {
		        if (keys.length == 0) {
		            LOGGER.warn("Skiping the sql execution as the keys is empty.");
		        }
		        List<String> consolidatedSqls = new ArrayList<String>();

		        for (String key : keys) {
		            List<String> sqls = SQLReader.getInstance().getSql(key);
		            if (sqls != null) {
		                consolidatedSqls.addAll(sqls);
		            } else {
		                LOGGER.warn("Skipping the sql key {"+key+"} as the sql doesn't not exists.");
		            }
		        }

		        if (consolidatedSqls.size() > 0) {
		            executeSQL(consolidatedSqls);
		        } else {
		            LOGGER.warn("Skiping the sql execution as the sqls for keys {"+keys.toString()+"} does not exist.");
		        }
		    }
		    
		    public String generateInsertPreSql(String tableName, List<String> columnHeaders){
		        StringBuilder sqlBuilder = new StringBuilder("Insert into ");
		        StringBuilder parameterBuilder = new StringBuilder("");
		        sqlBuilder.append(tableName).append("(");
		        for(String column : columnHeaders){
		            sqlBuilder.append("").append(column).append(",");
		            parameterBuilder.append("?").append(",");
		        }
		        sqlBuilder.deleteCharAt(sqlBuilder.length()-1);
		        parameterBuilder.deleteCharAt(parameterBuilder.length()-1);
		        sqlBuilder.append(") values(").append(parameterBuilder).append(")");
		        return sqlBuilder.toString();
		    }
		    
		    public String generateDeletePreSql(String tableName, List<String> columnHeaders){
		        StringBuilder sqlBuilder = new StringBuilder("Delete from ");
		        sqlBuilder.append(tableName).append(" where ");
		        for(String column : columnHeaders){
		            sqlBuilder.append(column).append("=? and ");
		        }
		        sqlBuilder.delete(sqlBuilder.lastIndexOf("and"), sqlBuilder.length());
		        return sqlBuilder.toString();
		    }
		    
		    public void executeSqlFile(String fileName){
		    	String content = FileUtil.readFile(fileName).trim(); 
				String[] sql_ar = content.split(";#\n");
		        getJdbcTemplate().batchUpdate(sql_ar);
		    }
		    
			public void addSql(String StoryName) {
		        executeSqlFile("sql/" + StoryName + "AddTestData.sql");
			}
			
			public void deleteSql(String StoryName){
				 executeSqlFile("sql/" + StoryName + "DeleteTestData.sql");
			}
			
			  
		    /**
		     * query sql to return only top result by field.
		     * @param sqlState
		     *                sql used to query.
		     * @return query result list
		     */
		    public String getUniqueResultByQuerySQL(final String sqlState, String field) {
		    	List<Map<String, Object>> tmpResult = getJdbcTemplate().queryForList(sqlState);
		    	if(tmpResult.size() == 0 || tmpResult.get(0).get(field) == null){
		    		return null;
		    	}else{
		    		return tmpResult.get(0).get(field).toString();
		    	}
		    }
		    
		    public Map<String, Object> getTopResultByQuerySQL(final String sqlState){
		    	List<Map<String, Object>> tmpResult = getJdbcTemplate().queryForList(sqlState);
		    	if(tmpResult.size() == 0) return null;
		    	return tmpResult.get(0);
		    }
		    
		    public List<String> getResultListBuQuerySQL(final String sqlState, String field){
		    	List<Map<String, Object>> tmpResult = getJdbcTemplate().queryForList(sqlState);
		    	List<String> tmpList = new ArrayList<String>();
		    	for(Map<String, Object> res: tmpResult){
		    		tmpList.add(res.get(field)==null?null:res.get(field).toString());
		    	}
		       return tmpList;
		    }
		    
		    
	    /** 
	     * 连接数据库 
	     *  
	     * @return 数据库连接对象 
	     */  
	    public static Connection connect() {  
	        Properties pro = new Properties();  
	        String driver = null;  
	        String url = null;  
	        String username = null;  
	        String password = null;  
	  
	        try {  
	            InputStream is = BaseDButil.class.getClassLoader()  
	                    .getResourceAsStream("db.properties");  
	            // System.out.println(is.toString());  
	            pro.load(is);  
	            driver = pro.getProperty("jdbc.mysql.driver");  
	            url = pro.getProperty("jdbc.cls.url");  
	            username = pro.getProperty("jdbc.cls.username");  
	            password = pro.getProperty("jdbc.cls.password");  
	              
//	           System.out.println(driver + ":" + url + ":" + username + ":"  
//	           + password);  
	            Class.forName(driver);  
	            Connection conn = DriverManager.getConnection(url, username,  
	                    password);  
	            return conn;  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    }  
	  
	    /** 
	     * 关闭数据库 
	     *  
	     */  
	    public static void close(Connection con) {  
	        if (con != null) {  
	            try {  
	                con.close();  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	  
//	  public static void main(String[] args) {  
//	      Connection con = new BaseDButil().connect();  
//	      System.out.println(con);  
//	  }  
	}  


