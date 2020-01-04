package cn.org.ferry.core.components;

import cn.org.ferry.core.dto.FerryRequest;
import cn.org.ferry.core.utils.ConstantUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * <p>记录帮助类，完成数据的增删改查
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/19 14:51
 */

@Component
public final class RecordHelper {
    @Autowired
    private FerryRequest ferryRequest;
    @Autowired
    private SqlSession sqlSession;

    public int insert(String tableName, Map<String, Object> record){
        String sql = initSql(tableName, record, Type.INSERT);
        Connection connection = sqlSession.getConnection();
        return -1;
    }

    private String initSql(String tableName, @NotNull Map<String, Object> record, Type type){
        Objects.requireNonNull(tableName, "表名不能为空");
        Objects.requireNonNull(record, "数据或条件不能为空");
        StringBuffer sql = new StringBuffer();
        switch (type){
            case INSERT:
                sql.append("insert into ").append(tableName).append("(");
                StringBuffer sb = new StringBuffer("values(");
                for (Map.Entry<String, Object> entry : record.entrySet()) {
                    sql.append(entry.getKey()).append(",");
                    if(entry.getValue() instanceof Date){
                        sb.append("str_to_date(").append(ConstantUtils.DateFormat.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND.format(entry.getValue()))
                                .append(",'%Y-%m-%d %H:%i:%s')").append(",");
                    }else if(entry.getValue() instanceof LocalDate){
                        sb.append("str_to_date(").append(entry.getValue().toString()).append(",'%Y-%m-%d')").append(",");
                    }else {
                        sb.append(entry.getValue()).append(",");
                    }
                }
                sql.append("created_by,creation_date,last_updated_by,last_update_date) ")
                        .append(sb).append(ferryRequest.getFerrySession().getUserId()).append(",")
                        .append(ConstantUtils.DateFormat.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND.format(ferryRequest.getNow())).append(",")
                        .append(ferryRequest.getFerrySession().getUserId()).append(",")
                        .append(ConstantUtils.DateFormat.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND.format(ferryRequest.getNow()))
                        .append(")");
                break;
            default:
        }
        return sql.toString();
    }

    enum Type {
        INSERT,UPDATE,DELETE,SELECT
    }
}
