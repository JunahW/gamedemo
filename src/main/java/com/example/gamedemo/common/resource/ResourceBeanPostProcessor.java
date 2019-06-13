package com.example.gamedemo.common.resource;

import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.utils.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wengj
 * @description：配置文件加载
 * @date 2019/6/13
 */
@Component
public class ResourceBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter
    implements Ordered {
  private static final Logger logger = LoggerFactory.getLogger(ResourceBeanPostProcessor.class);

  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {

    Class<?> clazz = bean.getClass();
    Resource annotation = clazz.getAnnotation(Resource.class);
    if (annotation != null) {
      logger.info("加载静态资源[{}]", beanName);
      List<?> list = ExcelUtils.importExcel(clazz);
      for (Object object : list) {
        ResourceInterface resourceItem = (ResourceInterface) object;
        // 加载完成进行处理，如字符串装换成特殊格式的数据
        resourceItem.postInit();
        ResourceManager.putResourceItem(clazz, resourceItem.getId(), object);
      }
    }
    return super.postProcessAfterInstantiation(bean, beanName);
  }

  @Override
  public int getOrder() {
    return Integer.MAX_VALUE;
  }
}
