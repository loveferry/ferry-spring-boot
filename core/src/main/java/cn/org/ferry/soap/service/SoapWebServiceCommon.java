package cn.org.ferry.soap.service;

/**
 * <p>基于 soap 协议的 web service 接口的通用接口
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/26 20:26
 */

public interface SoapWebServiceCommon {
    /**
     * 接口传输状态
     */
    enum Status{
        SUCCESS("成功"),
        FAILURE("失败");

        private String description;

        Status(String description){
            this.description = description;
        }

        public String description() {
            return description;
        }

    }
}
