package cn.org.ferry.mybatis.handlers;

import cn.org.ferry.mybatis.enums.Sex;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 性别处理器
 */

public class SexHandler extends BaseTypeHandler<Sex> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Sex sex, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, sex.name());
    }

    @Override
    public Sex getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.getString(s) == null ? null : Sex.valueOf(resultSet.getString(s));
    }

    @Override
    public Sex getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString(i) == null ? null : Sex.valueOf(resultSet.getString(i));
    }

    @Override
    public Sex getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getString(i) == null ? null : Sex.valueOf(callableStatement.getString(i));
    }
}
