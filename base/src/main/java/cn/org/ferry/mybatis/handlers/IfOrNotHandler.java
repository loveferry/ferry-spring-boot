package cn.org.ferry.mybatis.handlers;

import cn.org.ferry.mybatis.enums.IfOrNot;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 是否标志类型处理器
 */
public class IfOrNotHandler extends BaseTypeHandler<IfOrNot> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, IfOrNot ifOrNotFlag, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, ifOrNotFlag.name());
    }

    @Override
    public IfOrNot getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.getString(s) == null ? null : IfOrNot.valueOf(resultSet.getString(s));
    }

    @Override
    public IfOrNot getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString(i) == null ? null : IfOrNot.valueOf(resultSet.getString(i));
    }

    @Override
    public IfOrNot getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getString(i) == null ? null : IfOrNot.valueOf(callableStatement.getString(i));
    }
}
