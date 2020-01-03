package cn.org.ferry;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataBaseApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DataBaseApplication.class);
        // 关闭 logo 显示效果
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
    }

}
