package cn.org.ferry.core.controllers;

import cn.org.ferry.core.annotations.LoginPass;
import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.sys.dto.SysUser;
import cn.org.ferry.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.servlet.http.HttpServletRequest;

/**
 * created by 2018-09-11
 * @author ferry email:ferry_sy@163.com wechat:s1194385796
 * @version 1.0
 */

@Controller
public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private SysUserService sysUserService;

    /**
     * swagger 地址跳转
     */
    @RequestMapping(value = "/swagger")
    public String index() {
        logger.info("redirect:swagger-ui.html");
        return "redirect:swagger-ui.html";
    }

    /**
     * 登陆只能通过员工代码登陆
     * @param sysUser 员工代码和密码为必传项
     * @return 返回登陆信息
     */
    @LoginPass
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData login(HttpServletRequest request, @RequestBody SysUser sysUser){
        return sysUserService.login(request, sysUser);
    }



    @RequestMapping(value = "/test/test", method = RequestMethod.GET)
    @LoginPass
    public void incept2(String a) throws Exception {
//        sslRequestGet("https://59.37.128.138:442/retail_leasing/r/api/finance/document/status/query?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBpLXJlc291cmNlIl0sImNvbXBhbnlJZCI6Mywicm9sZUlkcyI6WzEwMDAxXSwidXNlcl9uYW1lIjoiQURNSU4iLCJzY29wZSI6WyJkZWZhdWx0Il0sImV4cCI6MTU4NTIwNzI0MiwidXNlcklkIjoxMTEzOSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiQURNSU4iXSwianRpIjoiMGNhOWExNGItZWM1MS00ODNmLWJjYTAtNjJkZjNjZWI5MWUyIiwiY2xpZW50X2lkIjoiRmluYW5jZSIsImVtcGxveWVlQ29kZSI6ImFkbWluIn0.27M8Zq9NweDFmz2sDazh60tLJzbnTwcHH2lRc6Ouy58&systemId=YX-SQ&serialNumber=d10a2cbe-e73a-4ea8-9442-4b84073b9516&transactionCode=YX_FINANCE_DOCUMENT_STATUS_OUT&transactionTime=20200321%2009:48:53&version=1&partnersContractNumber=CNZKST0020215323");
        sendPost("https://59.37.128.138:442/retail_leasing/r/api/finance/document/status/query?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBpLXJlc291cmNlIl0sImNvbXBhbnlJZCI6Mywicm9sZUlkcyI6WzEwMDAxXSwidXNlcl9uYW1lIjoiQURNSU4iLCJzY29wZSI6WyJkZWZhdWx0Il0sImV4cCI6MTU4NTIwNzI0MiwidXNlcklkIjoxMTEzOSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiQURNSU4iXSwianRpIjoiMGNhOWExNGItZWM1MS00ODNmLWJjYTAtNjJkZjNjZWI5MWUyIiwiY2xpZW50X2lkIjoiRmluYW5jZSIsImVtcGxveWVlQ29kZSI6ImFkbWluIn0.27M8Zq9NweDFmz2sDazh60tLJzbnTwcHH2lRc6Ouy58&systemId=YX-SQ&serialNumber=d10a2cbe-e73a-4ea8-9442-4b84073b9516&transactionCode=YX_FINANCE_DOCUMENT_STATUS_OUT&transactionTime=20200321%2009:48:53&version=1&partnersContractNumber=CNZKST0020215323");
    }




    /*private final static String PFX_PATH = "/Users/ferry/Documents/client.p12";    //客户端证书路径
    private final static String PFX_PWD = "Yx12345678";    //客户端证书密码

    public String sslRequestGet(String url) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        InputStream instream = new FileInputStream(new File(PFX_PATH));
        try {
            keyStore.load(instream, PFX_PWD.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, PFX_PWD.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext
                , new String[] { "TLSv1" }    // supportedProtocols ,这里可以按需要设置
                , null    // supportedCipherSuites
                , SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpGet httpPost = new HttpGet(url);
            //httpget.addHeader("host", "integration-fred2.fredhuang.com");// 设置一些heander等
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");//返回结果
                EntityUtils.consume(entity);
                logger.info(jsonStr);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }*/










    public String sendPost(String stringUrl) {
        try {
            URL url = new URL(stringUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            //            加载秘钥
            KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
            clientKeyStore.load(new BufferedInputStream(new FileInputStream("/Users/ferry/Documents/client.p12")), "Yx12345678".toCharArray());
//            此类充当基于密钥内容源的密钥管理器的工厂。每个密钥管理器管理特定类型的、由安全套接字所使用的密钥内容。密钥内容是基于 KeyStore 和/或提供者特定的源。
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, "Yx12345678".toCharArray());

            /*KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(new FileInputStream("/Users/ferry/Documents/client.p12"), "Yx12345678".toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);*/

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());




            // 读取服务器要验证的cer证书
            connection.setSSLSocketFactory(sslContext.getSocketFactory());
            // 设置不验证服务器，返回true不验证，false为验证，默认为false；也可以根据arg0的值验证或不验证指定服务器；
            connection.setHostnameVerifier((m1, m2) -> true);
            connection.setConnectTimeout(30000);
            connection.setDoInput(true);// 打开输入流,默认情况下是true
            connection.setDoOutput(true);// 打开输出流 ,需要设为true, 默认情况下是false
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);// 使用post方式不能使用缓存
            // 设置请求体类型为文本类型
            connection.setRequestProperty("Content-Type", "application/json");
            // 设置请求体 的长度
            connection.setRequestProperty("Content-Length", String.valueOf(stringUrl.length()));
            // 获得输出流，向服务器写入

            int response = connection.getResponseCode();// 获取响应码
            String msg;
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();// 获取输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                msg = responseResult(br);// 处理接收到的数据
            }else{
                InputStream inputStream = connection.getErrorStream();// 获取输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                msg = responseResult(br);// 处理接收到的数据
            }
            logger.info(msg);
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"type\":\"error\",\"code\":0,\"message\":\"发送请求出现错误\",\"data\":null}";
        }
    }

    private static String responseResult(BufferedReader br) {
        String line = null;
        StringBuffer sb_response = new StringBuffer();
        try {
            while ((line = br.readLine()) != null) {
                sb_response.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ret = new String(sb_response);
        return ret;
    }


    public static SSLSocketFactory setCertificate(InputStream certificate){
        try{
/*//            加载秘钥
            KeyStore clientKeyStore = KeyStore.getInstance("JKS");
            clientKeyStore.load(new BufferedInputStream(new FileInputStream("/Users/ferry/Downloads/证书/client.key")), "yxzlsq".toCharArray());
//            此类充当基于密钥内容源的密钥管理器的工厂。每个密钥管理器管理特定类型的、由安全套接字所使用的密钥内容。密钥内容是基于 KeyStore 和/或提供者特定的源。
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, "yxzlsq".toCharArray());*/



//            证书工厂。此处指明证书的类型
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            此类表示密钥和证书的存储设施
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(certificate, "yxzlsq".toCharArray());
//            将证书导入证书库
//            keyStore.setCertificateEntry("sqcertificate", certificateFactory.generateCertificate(certificate));
//            此类充当基于信任材料源的信任管理器的工厂
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            用证书授权源和相关的信任材料初始化此工厂
            trustManagerFactory.init(keyStore);


//            取得SSL的SSLContext实例
//            此类的实例表示安全套接字协议的实现，它充当用于安全套接字工厂或 SSLEngine 的工厂。用可选的一组密钥和信任管理器及安全随机字节源初始化此类。
            SSLContext sslContext = SSLContext.getInstance("SSL");
            // 第一个参数是授权的密钥管理器，用来授权验证。TrustManager[]第二个是被授权的证书管理器，用来验证服务器端的证书。第三个参数是一个随机数值，可以填写null
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
//            sslContext.init(null, null, new SecureRandom());
            return sslContext.getSocketFactory();

        } catch (Exception e){
            e.printStackTrace();
        }
        return null ;
    }
}
