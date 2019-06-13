package com.example.gamedemo.common.resource;

import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.utils.ApplicationContextProvider;
import com.example.gamedemo.common.utils.ExcelUtils;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description
 * @date 2019/5/20
 */
public class ResourceManager {
  /** 存放静态资源文件 */
  public static ConcurrentHashMap<Class, ConcurrentHashMap> resourceMap = new ConcurrentHashMap<>();

  /**
   * 通过id获取静态资源的项
   *
   * @param clazz
   * @param key
   * @return
   */
  public static <K, V> V getResourceItemById(Class<V> clazz, K key) {
    return (V) resourceMap.get(clazz).get(key);
  }

  /**
   * 获取resource对应的map
   *
   * @param clazz
   * @param <V>
   * @param <K>
   * @return
   */
  public static <K, V> ConcurrentMap<K, V> getResourceMap(Class<V> clazz) {
    return resourceMap.get(clazz);
  }

  /**
   * 新增resource项
   *
   * @param clazz
   * @param key
   * @param value
   * @param <V>
   * @param <K>
   */
  public static <K, V> void putResourceItem(Class clazz, K key, V value) {
    if (resourceMap.get(clazz) == null) {
      ConcurrentHashMap<K, V> resourceItemMap = new ConcurrentHashMap<>(16);
      resourceMap.put(clazz, resourceItemMap);
    }
    resourceMap.get(clazz).put(key, value);
  }

  /** 初始化静态资源 */
  // FIXME 此处只有测试模块用到
  public static void initResource() {
    // logger.info("开始初始化静态资源");
    ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
    Map<String, Object> beansWithAnnotation =
        applicationContext.getBeansWithAnnotation(Resource.class);
    Set<Map.Entry<String, Object>> entries = beansWithAnnotation.entrySet();
    for (Map.Entry<String, Object> entry : entries) {
      Class<?> aClass = entry.getValue().getClass();
      List<?> list = ExcelUtils.importExcel((entry.getValue().getClass()));
      for (Object object : list) {
        ResourceInterface resourceItem = (ResourceInterface) object;
        // 加载完成进行处理，如字符串装换成特殊格式的数据
        resourceItem.postInit();
        ResourceManager.putResourceItem(aClass, resourceItem.getId(), object);
      }
    }
    // logger.info("完成初始化静态资源");
  }
}
