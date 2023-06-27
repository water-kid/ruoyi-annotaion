package com.cj.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/6/27 23:33
 * @Author cc
 */
@Component
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 将所有的数据源设置进去
     */
    public DynamicDataSource(LoadDataSource loadDataSource) throws Exception {
        // 设置所有数据源
        Map<String, DataSource> allDs = loadDataSource.loadAllDataSource();
        super.setTargetDataSources(new HashMap<>(allDs));

        // 设置默认数据源,,并不是所有的方法都有@DataSource
        super.setDefaultTargetDataSource(allDs.get(DataSourceType.DEFAULT_DS_NAME));
        super.afterPropertiesSet();

    }

    /**
     * 返回数据源名字，，当系统需要获取数据源的时候，会自动调用这个方法，获取数据源名字
     * @return 拿到这个名字，，再去找这个数据源，，，需要将所有的数据源设置进去
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDsName();

    }
}
