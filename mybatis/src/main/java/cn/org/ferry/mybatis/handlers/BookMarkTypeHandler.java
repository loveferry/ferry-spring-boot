package cn.org.ferry.mybatis.handlers;

import cn.org.ferry.mybatis.enums.BookMarkType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 书签类型处理器
 */
public class BookMarkTypeHandler extends BaseTypeHandler<BookMarkType> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BookMarkType bookMarkType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, bookMarkType.name());
    }

    @Override
    public BookMarkType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.getString(s) == null ? null : BookMarkType.valueOf(resultSet.getString(s));
    }

    @Override
    public BookMarkType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString(i) == null ? null : BookMarkType.valueOf(resultSet.getString(i));
    }

    @Override
    public BookMarkType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getString(i) == null ? null : BookMarkType.valueOf(callableStatement.getString(i));
    }
}
