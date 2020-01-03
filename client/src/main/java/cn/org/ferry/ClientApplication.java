package cn.org.ferry;


import cn.org.ferry.core.listeners.ConfigInitListener;
import cn.org.ferry.mybatis.annotations.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan(basePackages = "cn.org.ferry.**.mapper")
public class ClientApplication {

	public static void main(String[] args) {
        // SpringApplication.run(FerryApplication.class, args);
		SpringApplication springApplication = new SpringApplication(ClientApplication.class);
		// 设置监听
		springApplication.addListeners(new ConfigInitListener());
		// 关闭 logo 显示效果
        // springApplication.setBannerMode(Banner.Mode.OFF);
		ConfigurableApplicationContext applicationContext = springApplication.run(args);
//		ConfigUtil.init(applicationContext);
	}
}

