package cn.org.ferry.mybatis.version;

import cn.org.ferry.mybatis.exceptions.VersionException;

/**
 * @author ferry
 */
public interface NextVersion<T> {

    /**
     * 返回下一个版本
     */
    T nextVersion(T current) throws VersionException;

}
