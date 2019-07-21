package cn.org.ferry.system.mybatis.handler;

import cn.org.ferry.system.sysenum.IfOrNotFlag;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 是否标志类型处理器
 */
public class IfOrNotFlagHandler extends BaseTypeHandler<IfOrNotFlag> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, IfOrNotFlag ifOrNotFlag, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, ifOrNotFlag.name());
    }

    @Override
    public IfOrNotFlag getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.getString(s) == null ? null : IfOrNotFlag.valueOf(resultSet.getString(s));
    }

    @Override
    public IfOrNotFlag getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString(i) == null ? null : IfOrNotFlag.valueOf(resultSet.getString(i));
    }

    @Override
    public IfOrNotFlag getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getString(i) == null ? null : IfOrNotFlag.valueOf(callableStatement.getString(i));
    }
}
