package cn.org.ferry.system.mybatis.version;

import cn.org.ferry.system.exception.VersionException;

import java.sql.Timestamp;

/**
 * @author ferry
 */
public class DefaultNextVersion implements NextVersion {

    @Override
    public Object nextVersion(Object current) throws VersionException {
        if (current == null) {
            throw new VersionException("当前版本号为空!");
        }
        if (current instanceof Integer) {
            return (Integer) current + 1;
        } else if (current instanceof Long) {
            return (Long) current + 1L;
        } else if (current instanceof Timestamp) {
            return new Timestamp(System.currentTimeMillis());
        } else {
            throw new VersionException("默认的 NextVersion 只支持 Integer, Long 和 java.sql.Timestamp 类型的版本号，如果有需要请自行扩展!");
        }
    }

}
