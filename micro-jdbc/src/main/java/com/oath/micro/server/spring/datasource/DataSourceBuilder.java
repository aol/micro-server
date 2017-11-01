package com.oath.micro.server.spring.datasource;

import javax.sql.DataSource;

public interface DataSourceBuilder {

    public DataSource buildDataSource(String classname, String url, String username, String password, int maxPoolsize);
}
