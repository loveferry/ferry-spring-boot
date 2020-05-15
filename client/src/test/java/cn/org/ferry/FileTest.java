package cn.org.ferry;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/04/30 11:31
 */

public class FileTest {

    @Test
    public void fileTest() throws IOException {
//        BufferedReader br = new BufferedReader(new FileReader("/Users/ferry/Downloads/sss.json"));
        File f = new File("/Users/ferry/Downloads/sssss");
        f.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));

        for (int i = 1; i < 10; i++) {
            bw.newLine();
            bw.append("hwuhiufjbcbdziuhdf");
            bw.flush();
        }

        bw.close();

    }
}
