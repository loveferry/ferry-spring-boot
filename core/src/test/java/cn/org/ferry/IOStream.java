package cn.org.ferry;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/29 11:43
 */

public class IOStream {
    @Test
    public void cbdjsc(){
        File file = new File("/Users/ferry/Documents/学习资料/安装expect");
        try (InputStream is = new FileInputStream(file);
             OutputStream os = new FileOutputStream("/Users/ferry/Documents/学习资料/安装expect2");
             OutputStream os2 = new FileOutputStream("/Users/ferry/Documents/学习资料/安装expect3")){
            int len;
            byte[] bs = new byte[4096];
            while (-1 != (len=is.read(bs))){
                os.write(bs, 0, len);
            }
            os.flush();
            bs = new byte[4096];
            while (-1 != (len=is.read(bs))){
                os2.write(bs, 0, len);
            }
            os2.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
