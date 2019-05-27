package cn.org.ferry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.org.ferry.**.mapper")
public class CoreApplication {

	public static void main(String[] args) {
//        SpringApplication.run(FerryApplication.class, args);
		SpringApplication springApplication = new SpringApplication(CoreApplication.class);
		// 关闭 logo 显示效果
//        springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);
	}

}

