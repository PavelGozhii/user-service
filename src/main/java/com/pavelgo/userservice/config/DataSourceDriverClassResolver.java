package com.pavelgo.userservice.config;

public class DataSourceDriverClassResolver {

    public static String getDriverClassByName(String name){
        return switch (name) {
            case "oracle" -> "oracle.jdbc.driver.OracleDriver";
            case "mysql" -> "com.mysql.jdbc.Driver";
            case "postgres" -> "org.postgresql.Driver";
            case "sqlserver" -> "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            case "h2" -> "org.h2.Driver";
            default -> throw new RuntimeException("Unknown data source strategy: " + name);
        };
    }
}
