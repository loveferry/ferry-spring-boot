package cn.org.ferry.system.mybatis.version;

import cn.org.ferry.system.exception.VersionException;

/**
 * @author ferry
 */
public interface NextVersion<T> {

    /**
     * 返回下一个版本
     *
     * @param current
     * @return
     */
    T nextVersion(T current) throws VersionException;

}
