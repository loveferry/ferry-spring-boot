package cn.org.ferry.core.dto;

import java.util.List;

public class ResponseData {
    /**
     * 状态码
     */
    private Integer code = 200;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 调用接口成败标识
     */
    private Boolean success = true;

    /**
     * 返回数据
     */
    private List<?> maps;

    public ResponseData(){}

    public ResponseData(List<?> maps){
        this.maps = maps;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<?> getMaps() {
        return maps;
    }

    public void setMaps(List<?> maps) {
        this.maps = maps;
    }
}
