package com.example.gamedemo.common.resource;

import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.utils.ApplicationContextProvider;
import com.example.gamedemo.common.utils.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wengj
 * @description
 * @date 2019/6/6
 */
@Component
@Order(value = 1)
public class StartInit implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(StartInit.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("===============init resource==============");
        logger.info("开始初始化静态资源");
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Resource.class);
        Set<Map.Entry<String, Object>> entries = beansWithAnnotation.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Class<?> aClass = entry.getValue().getClass();
            List<?> list = ExcelUtils.importExcel((entry.getValue().getClass()));
            for (Object object : list) {
                ResourceInterface resourceItem = (ResourceInterface) object;
                //加载完成进行处理，如字符串装换成特殊格式的数据
                resourceItem.postInit();
                ResourceManager.putResourceItem(aClass, resourceItem.getId(), object);
            }
        }
        logger.info("完成初始化静态资源");
    }
}
