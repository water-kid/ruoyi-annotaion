package com.cj.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @Date 2023/6/27 23:12
 * @Author cc
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidProperties {
    private String type;
    private String driverClassName;
    private Map<String,Map<String,String>> ds;

    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private Integer maxWait;

    /**
     * 设置datasource 公共属性
     * @param druidDataSource   只包含核心属性的 datasource ：  url，username,password
     * @return
     */
    public DataSource dataSource(DruidDataSource druidDataSource){
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);
        return druidDataSource;
    }
}
