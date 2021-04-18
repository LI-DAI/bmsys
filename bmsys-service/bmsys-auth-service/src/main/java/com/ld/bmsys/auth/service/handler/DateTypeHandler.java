package com.ld.bmsys.auth.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * @Author ld
 * @Date 2020/9/1 16:19
 * <p>
 * 将数据库中 TIMESTAMP 类型转化为 LocalDateTime
 */
@MappedJdbcTypes(JdbcType.TIMESTAMP)
@MappedTypes(LocalDateTime.class)
@Slf4j
public class DateTypeHandler extends BaseTypeHandler<LocalDateTime> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        log.info("设置timestamp值");
        ps.setTimestamp(i, Timestamp.valueOf(parameter));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        log.info("获取 localDateTime 测试 1111");
        Timestamp timestamp = rs.getTimestamp(columnName);
        return LocalDateTime.from(timestamp.toInstant());
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        log.info("获取 localDateTime 测试 2222");
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        return LocalDateTime.from(timestamp.toInstant());
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        log.info("获取 localDateTime 测试 3333");
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        return LocalDateTime.from(timestamp.toInstant());
    }
}
