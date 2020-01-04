package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.exceptions.CommonException;
import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.sys.dto.SysSql;
import cn.org.ferry.sys.mapper.SysSqlMapper;
import cn.org.ferry.sys.service.SysSqlService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

@Service
public class SysSqlServiceImpl extends BaseServiceImpl<SysSql> implements SysSqlService {
    private static final Logger logger = LoggerFactory.getLogger(SysSqlServiceImpl.class);
    @Resource
    private SysSqlMapper sysSqlMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String querySqlBySqlCode(String sqlCode) {
        if(StringUtils.isEmpty(sqlCode)){
            throw new CommonException("数据源编码为空");
        }
        return sysSqlMapper.querySqlBySqlCode(sqlCode);
    }

    /**
     * 注入 dataSource 获取连接有时会连接超时，不知是什么参数没设置好，这里采取注入 jdbcTemplate 进行查询
     * @param sql 给定的sql语句
     * @param params sql语句对应的查询参数
     * @return
     * @throws SQLException
     */
    @Override
    public Object execute(String sql, Map<String, Object> params) throws SQLException {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(sql);
        String sqlPrep = matcher.replaceAll("?");
        logger.info("execute sql: {}", sqlPrep);
        PreparedStatementCallback callback = (PreparedStatement prep) -> {
            Matcher m = pattern.matcher(sql);
            int i = 0;
            String[] ss = new String[m.groupCount()];
            while (m.find()){
                String key = m.group(1);
                Object value = params.get(key);
                ss[i] = "["+value.getClass().getSimpleName()+"]"+value;
                prep.setObject(++i, value);
            }
            logger.info("execute sql parameters: {}", StringUtils.join(ss, ","));
            ResultSet rs = prep.executeQuery();
            // 如果结果集为空，那么关闭连接返回空
            if(!rs.last()){
                return null;
            }
            // 最后一行的行号，即总行数
            int rowCount = rs.getRow();
            // 将指针指向最前面
            rs.beforeFirst();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            if((1 == rowCount) && (1 == columnCount)){
                rs.next();
                Object o = rs.getObject(rowCount);
                if(o instanceof java.sql.Date){
                    return new Date(((java.sql.Date)o).getTime());
                }else{
                    return o;
                }
            }
            List<Map<String, Object>> list = new LinkedList<>();
            while (rs.next()){
                Map<String, Object> row = new LinkedHashMap<>(columnCount);
                for(int index = 1; index <= columnCount; index++){
                    row.put(md.getColumnName(index), rs.getObject(index));
                }
                list.add(row);
            }
            return list;
        };
        return jdbcTemplate.execute(sqlPrep, callback);
    }

    /*@Override
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
        // 如果结果集为空，那么关闭连接返回空
        if(!rs.last()){
            close(rs, prep, conn);
            return null;
        }
        // 最后一行的行号，即总行数
        int rowCount = rs.getRow();
        // 将指针指向最前面
        rs.beforeFirst();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        if((1 == rowCount) && (1 == columnCount)){
            rs.next();
            Object o = rs.getObject(rowCount);
            if(o instanceof java.sql.Date){
                return new Date(((java.sql.Date)o).getTime());
            }else{
                return o;
            }
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
    }*/

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
