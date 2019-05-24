package com.example.gamedemo.server.common.ramcache.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author: wengj
 * @date: 2019/5/24
 * @description: 实体操作数据库
 */
//@Repository
@NoRepositoryBean
public interface EntityRepository<T, ID> extends JpaRepository<T, ID> {
}
