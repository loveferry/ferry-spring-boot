package cn.org.ferry.system.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseData {
    private Integer code;  // 状态码

    private String message;  // 提示信息

    private Boolean success = true;  // 调用接口成败标识

    private List<?> maps;  // 返回数据

    public ResponseData(){}

    public ResponseData(List<?> maps){
        this.maps = maps;
    }
}
