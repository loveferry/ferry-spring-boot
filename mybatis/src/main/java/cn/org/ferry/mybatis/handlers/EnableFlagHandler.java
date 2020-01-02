package cn.org.ferry.mybatis.handlers;

import cn.org.ferry.mybatis.enums.EnableFlag;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 启用标志类型处理器
 */
public class EnableFlagHandler extends BaseTypeHandler<EnableFlag> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, EnableFlag enableFlag, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, enableFlag.name());
    }

    @Override
    public EnableFlag getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.getString(s) == null ? null : EnableFlag.valueOf(resultSet.getString(s));
    }

    @Override
    public EnableFlag getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString(i) == null ? null : EnableFlag.valueOf(resultSet.getString(i));
    }

    @Override
    public EnableFlag getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getString(i) == null ? null : EnableFlag.valueOf(callableStatement.getString(i));
    }
}
