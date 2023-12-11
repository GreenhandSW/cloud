package io.github.greenhandsw.sync.common;

import lombok.Data;

import java.util.List;

@Data
public class CanalEntity<ENTITY> {
    private List<ENTITY> data;
    private String database;
    private long es;
    private int id;
    private boolean isDdl;
    private MysqlType mysqlType;
    private String old;
    private List<String> pkNames;
    private String sql;
    private SqlType sqlType;
    private String table;
    private long ts;
    private String type;
}
