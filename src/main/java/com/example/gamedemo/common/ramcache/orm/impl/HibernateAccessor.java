package com.example.gamedemo.common.ramcache.orm.impl;

import com.example.gamedemo.common.ramcache.Entity;
import com.example.gamedemo.common.ramcache.orm.Accessor;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/5/24
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class HibernateAccessor extends HibernateDaoSupport implements Accessor {
  @Autowired private SessionFactory sessionFactory;

  @Resource(name = "sessionFactory")
  public void setSuperSessionFactory(SessionFactory sessionFactory) {
    super.setSessionFactory(sessionFactory);
  }

  @Override
  public <PK extends Serializable, T extends Entity> T load(Class<T> clazz, PK id) {
    return getHibernateTemplate().get(clazz, id);
  }

  @Override
  public <PK extends Serializable, T extends Entity> PK save(Class<T> clazz, T entity) {
    return (PK) getHibernateTemplate().save(entity);
  }

  @Override
  public <PK extends Serializable, T extends Entity> void remove(Class<T> clazz, PK id) {
    T entity = load(clazz, id);
    if (null == entity) {
      return;
    }
    getHibernateTemplate().delete(entity);
  }

  @Override
  public <PK extends Serializable, T extends Entity> void saveOrUpdate(Class<T> clazz, T entity) {
    getHibernateTemplate().saveOrUpdate(entity);
  }

  @Override
  public <T extends Entity> List<T> getEntityList(Class<T> clazz) {
    return (List<T>) getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(clazz));
  }
}
