package cn.org.ferry.system.mapper;

import cn.org.ferry.system.dto.BaseDTO;

import java.util.List;

/**
 * <p>通用的基础mybatis接口
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/17 10:22
 */

public interface BaseMapper<T extends BaseDTO> {
    /**
     * 查询所有数据
     * @param t 参数对象
     * @return 返回查询到的集合
     */
    List<T> selectAll(T t);

    /**
     * 查询数据，并分页
     * @param t 参数对象
     * @param page 当前页
     * @param pageSize 页面大小
     * @return 返回查询到的数据集
     */
    List<T> select(T t, int page, int pageSize);

    /**
     * 查询记录
     * @param primaryKey 记录主键
     * @return 返回记录
     */
    T selectByPrimaryKey(Object primaryKey);

    /**
     * 查询数量
     * @param t 参数对象
     * @return 返回查询到的记录数量
     */
    long selectCount(T t);

    /**
     * 插入单条记录并将插入的记录返回(空值也会插入)
     * @param t 待插入的记录
     * @return 将插入的数据返回
     */
    T insert(T t);

    /**
     * 插入单条记录并将插入的记录返回(空值不会插入)
     * @param t 待插入的记录
     * @return 将插入的数据返回
     */
    T insertSelective(T t);

    /**
     * 更新记录（包含空值）
     * @param t 记录
     * @return 返回更新后的记录
     */
    T updateByPrimaryKey(T t);

    /**
     * 更新记录（不更新空值）
     * @param t 记录
     * @return 返回更新后的记录
     */
    T updateByPrimaryKeySelective(T t);

    /**
     * 删除单条记录
     * @param o 表主键
     * @return 返回删除的记录
     */
    T deleteByPrimaryKey(Object o);

    /**
     * 删除记录
     * @param t 参数对象
     * @return 返回删除的记录数
     */
    long deleteRecords(T t);
}
