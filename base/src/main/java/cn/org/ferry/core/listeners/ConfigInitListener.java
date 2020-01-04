package cn.org.ferry.core.listeners;

import cn.org.ferry.mybatis.utils.ConfigUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/27 22:02
 */

public class ConfigInitListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigUtil.init(event.getApplicationContext());
    }
}
