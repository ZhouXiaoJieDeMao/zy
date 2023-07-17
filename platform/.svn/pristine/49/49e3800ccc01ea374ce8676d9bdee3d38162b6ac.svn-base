package com.bsoft.framework.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 不同数据库设置不同的方言
 * 执行不同的sql语句
 */

@Component
public class MyDatabaseIdProvider implements DatabaseIdProvider {
    @Override
    public void setProperties(Properties p) {
        DatabaseIdProvider.super.setProperties(p);
        System.out.println(p.getProperty("Oracle"));
    }

    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.put("Oracle","oracle");
        properties.put("MySQL","mysql");
        properties.setProperty("PostgreSQL", "postgresql");
        properties.setProperty("DB2", "db2");
        properties.setProperty("SQL Server", "sqlserver");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }
    @Override
    public String getDatabaseId(DataSource dataSource) throws SQLException {
        Connection conn = dataSource.getConnection();
        String dbName = conn.getMetaData().getDatabaseProductName();
        String dbAlias = "";
        switch (dbName) {
            case "MySQL":
                dbAlias = "mysql";
                break;
            case "Oracle":
                dbAlias = "oracle";
                break;
            default:
                break;
        }
        return dbAlias;
    }
}
