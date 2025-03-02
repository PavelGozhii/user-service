package com.pavelgo.userservice.config.properties;

import com.pavelgo.userservice.config.DataSourceDriverClassResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSourcePropertiesToJdbcTemplatePropertiesMapping {

    public JdbcTemplateProperties toJdbcTemplateProperties(DataSourceProperties.DataSource property) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                DataSourceDriverClassResolver.getDriverClassByName(property.getStrategy()));
        dataSource.setUrl(property.getUrl());
        dataSource.setUsername(property.getUser());
        dataSource.setPassword(property.getPassword());
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return JdbcTemplateProperties.builder()
                .jdbcTemplate(jdbcTemplate)
                .mapping(UserDataSourceMapping.builder()
                        .id(property.getMapping().getId())
                        .username(property.getMapping().getUsername())
                        .name(property.getMapping().getName())
                        .surname(property.getMapping().getSurname())
                        .tableName(property.getTable())
                        .build())
                .build();

    }
}
