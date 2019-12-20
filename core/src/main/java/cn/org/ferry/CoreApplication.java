package cn.org.ferry;

import cn.org.ferry.system.mybatis.annotation.MapperScan;
import cn.org.ferry.system.utils.ConfigUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan(basePackages = "cn.org.ferry.**.mapper")
public class CoreApplication {

	public static void main(String[] args) {
        // SpringApplication.run(FerryApplication.class, args);
		SpringApplication springApplication = new SpringApplication(CoreApplication.class);
		// 关闭 logo 显示效果
        // springApplication.setBannerMode(Banner.Mode.OFF);
		ConfigurableApplicationContext applicationContext = springApplication.run(args);
		ConfigUtil.init(applicationContext);
	}
}

