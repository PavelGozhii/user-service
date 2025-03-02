package com.pavelgo.userservice.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties("data-sources")
public class DataSourceProperties {

    private List<DataSource> dataSources;

    @Data
    public static class DataSource {
        private String name;
        private String strategy;
        private String url;
        private String table;
        private String user;
        private String password;
        private Mapping mapping;

        @Data
        public static class Mapping {
            private String id;
            private String username;
            private String name;
            private String surname;
        }
    }
}
