package cn.org.ferry.mybatis.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum IdentityDialect {
    DB2("VALUES IDENTITY_VAL_LOCAL()"),
    MYSQL("SELECT LAST_INSERT_ID()"),
    ORACLE("SEQUENCE"),
    SQLSERVER("SELECT SCOPE_IDENTITY()"),
    CLOUDSCAPE("VALUES IDENTITY_VAL_LOCAL()"),
    DERBY("VALUES IDENTITY_VAL_LOCAL()"),
    HSQLDB("CALL IDENTITY()"),
    SYBASE("SELECT @@IDENTITY"),
    DB2_MF("SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1"),
    INFORMIX("select dbinfo('sqlca.sqlerrd1') from systables where tabid=1");

    private static final Logger logger = LoggerFactory.getLogger(IdentityDialect.class);


    private String identityRetrievalStatement;

    IdentityDialect(String identityRetrievalStatement) {
        this.identityRetrievalStatement = identityRetrievalStatement;
    }

    public static String getDataBaseIdentityByDialect(String dbType) {
        try {
            IdentityDialect identityDialect = IdentityDialect.valueOf(dbType);
            return identityDialect.getIdentityRetrievalStatement();
        }catch (IllegalArgumentException e){
            logger.error("Can not get identity for "+dbType, e);
            return null;
        }

    }

    public String getIdentityRetrievalStatement() {
        return identityRetrievalStatement;
    }
}
