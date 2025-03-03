package com.pavelgo.userservice.config;

import com.pavelgo.userservice.config.properties.DataSourceProperties;
import com.pavelgo.userservice.config.properties.DataSourcePropertiesToJdbcTemplatePropertiesMapping;
import com.pavelgo.userservice.config.properties.JdbcTemplateProperties;
import com.pavelgo.userservice.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSourceConfig {

    @Bean
    public List<JdbcTemplateProperties> getJdbcTemplates(DataSourceProperties dataSourceProperties,
                                                         DataSourcePropertiesToJdbcTemplatePropertiesMapping dataSourceMapping) {
        return dataSourceProperties
                .stream().map(dataSourceMapping::toJdbcTemplateProperties)
                .toList();
    }

    @Bean
    public List<UserRepository> getUserRepositories(List<JdbcTemplateProperties> jdbcTemplateProperties) {
        return jdbcTemplateProperties.stream()
                .map(UserRepository::new)
                .toList();
    }
}
