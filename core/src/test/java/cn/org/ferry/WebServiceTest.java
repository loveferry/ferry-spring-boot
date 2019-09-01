package cn.org.ferry;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/08/31 11:35
 */

@WebService
public class WebServiceTest {
    @WebMethod
    public String say(String msg){
        return "get"+msg;
    }

    public static void main(String[] args) {
        Endpoint.publish("http://10.8.20.15:8082/ferry/webservice", new WebServiceTest());
    }
}
