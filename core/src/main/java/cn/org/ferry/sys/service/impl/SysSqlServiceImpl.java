package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.SysSql;
import cn.org.ferry.sys.mapper.SysSqlMapper;
import cn.org.ferry.sys.service.SysSqlService;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SysSqlServiceImpl extends BaseServiceImpl<SysSql> implements SysSqlService {
    @Resource
    private SysSqlMapper sysSqlMapper;
    @Autowired
    private DataSource dataSource;

    @Override
    public String querySqlBySqlCode(String sqlCode) {
        return sysSqlMapper.querySqlBySqlCode(sqlCode);
    }

    @Override
    public Object execute(String sql, Map<String, Object> params) throws SQLException {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(sql);
        String sqlPrep = matcher.replaceAll("?");
        Connection conn = dataSource.getConnection();
        PreparedStatement prep = conn.prepareStatement(sqlPrep);
        matcher = pattern.matcher(sql);
        int i = 0;
        while (matcher.find()){
            String key = matcher.group(1);
            Object value = params.get(key);
            prep.setObject(++i, value);
        }
        ResultSet rs = prep.executeQuery();
        if(!rs.last()){
            close(rs, prep, conn);
            return null;
        }
        // 总行数
        int rowCount = rs.getRow();
        // 将指针指向最前面
        rs.beforeFirst();

        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();

        if((1 == rowCount) && (1 == columnCount)){
            rs.next();
            return rs.getObject(columnCount);
        }
        List<Map<String, Object>> list = new LinkedList<>();
        while (rs.next()){
            Map<String, Object> row = new LinkedHashMap<>(columnCount);
            for(int index = 1; index <= columnCount; index++){
                row.put(md.getColumnName(index), rs.getObject(index));
            }
            list.add(row);
        }
        close(rs, prep, conn);
        return list;
    }

    /**
     * 关闭数据库连接
     * 先打开的后关闭
     */
    private void close(ResultSet rs, PreparedStatement prep, Connection conn) throws SQLException {
        try {
            rs.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }finally {
            try {
                prep.close();
            } catch (SQLException e) {
                throw new SQLException(e);
            }finally {
                conn.close();
            }
        }


    }
}
