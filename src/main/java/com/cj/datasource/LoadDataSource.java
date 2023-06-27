package com.cj.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**加载数据源
 * @Date 2023/6/27 23:21
 * @Author cc
 */
@Component
@EnableConfigurationProperties(DruidProperties.class)
public class LoadDataSource {

    @Autowired
    DruidProperties druidProperties;
    public Map<String, DataSource> loadAllDataSource() throws Exception {

        Map<String, Map<String, String>> allDs = druidProperties.getDs();
        HashMap<String, DataSource> map = new HashMap<>();
        for (String dsName : allDs.keySet()) {
            Map<String, String> ds = allDs.get(dsName);
//            DruidDataSource druidDataSource = new DruidDataSource();
//            druidDataSource.setUrl(ds.get("url"));
//            druidDataSource.setUsername(ds.get("username"));
//            druidDataSource.setPassword(ds.get("password"));
            DruidDataSource druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(ds);
            map.put(dsName,druidProperties.dataSource(druidDataSource));
        }

        return map;
    }
}
