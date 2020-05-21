package com.zxk175.notify.config.mbp;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

/**
 * @author zxk175
 * @since 2019-10-12 16:20
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.zxk175.notify.**.dao.**")
public class MyBatisPlusConfig {


    @Bean(name = "hikariDataSource")
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariDataSource hikariDataSource() {
        return new HikariDataSource();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        List<ISqlParser> sqlParserList = Lists.newArrayList();
        // 攻击SQL 阻断解析器、加入解析链
        sqlParserList.add(new MyBlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);

        return paginationInterceptor;
    }

    @Bean("transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("hikariDataSource") HikariDataSource hikariDataSource) {
        return new DataSourceTransactionManager(hikariDataSource);
    }

}
