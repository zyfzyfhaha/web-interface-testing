package com.ssc.ssgm.fx.refdata.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ssc.ssgm.fx.refdata.util.FileUtil;
import com.ssc.ssgm.fx.refdata.base.Config;
import com.statestr.fx.ffo.util.RequestUtils;

/**
 * @author e532258
 */
@Repository
public class BaseDBUtil {
    protected static final Logger LOGGER = Logger.getLogger(BaseDBUtil.class);
    /**
     * JdbcTemplate jdbcTemplate.
     */
    protected JdbcTemplate jdbcTemplate;
    protected JdbcTemplate jdbcGFXTemplate;
    protected JdbcTemplate jdbcNBATemplate;
    /**
     * @param template
     *            is created by spring container.
     */
    public void setJdbcTemplate(final JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    /**
     * @param dataSource
     *            is created by spring container.
     */
    @Resource(name = "testDataSource")
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Resource(name = "testGFXDataSource")
    public void setGFXDataSource(final DataSource dataSource) {
        jdbcGFXTemplate = new JdbcTemplate(dataSource);
    }
    
    @Resource(name = "testNBADataSource")
    public void setNBADataSource(final DataSource dataSource) {
        jdbcNBATemplate = new JdbcTemplate(dataSource);
    }
    /**
     * query sql to return result.
     * @param sqlState
     *                sql used to query.
     * @return query result list
     */
    public List<Map<String, Object>> querySQL(final String sqlState) {
        return jdbcTemplate.queryForList(sqlState);
    }
    
    /**
     * Execute update sql.
     * @param sqlState
     *                sql used to query.
     */
    public void executeSQL(final String sqlState) {
        jdbcTemplate.update(sqlState);
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
        jdbcTemplate.batchUpdate(arraySqls);
    }

    /**
     * Read and batch execute update sqls.
     * @param sqls
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
    
    public void executeSQLForQAUAT(final List<String> sqls) {
        String[] arraySqls = new String[sqls.size()];
        for (int i = 0; i < arraySqls.length; i++) {
            arraySqls[i] = sqls.get(i);
        }
        requestCleanRequest(arraySqls);
    }
    
    private void requestCleanRequest(String[] sqls){
    	Config config = new Config();
    	String url  = config.getProperty("cleanService.url");
    	String dbName = config.getProperty("cleanService.dbName");
    	String dbUser = config.getProperty("cleanService.dbUser");
    	if ("notready".equals(dbName)||dbName==null) {
			return;
		}
    	String data = RequestUtils.newDatabaseRequest(dbName,dbUser,sqls);  
        boolean result = RequestUtils.executePostRequest(url,data);
        if (!result) {
			LOGGER.error("Failed to requestCleanRequest: " + sqls.toString());
		}
    }
    
    
    public void executeSqlFile(String fileName){
    	String content = FileUtil.readFile(fileName).trim(); 
		String[] sql_ar = content.split(";#\n");
        jdbcNBATemplate.batchUpdate(sql_ar);
    }
    
	public void addSql(String StoryName) {
        executeSqlFile("sql/" + StoryName + "AddTestData.sql");
	}
	
	public void deleteSql(String StoryName){
		 executeSqlFile("sql/" + StoryName + "DeleteTestData.sql");
	}
}
