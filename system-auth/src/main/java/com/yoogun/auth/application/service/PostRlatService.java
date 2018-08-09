package com.yoogun.auth.application.service;

import com.yoogun.auth.domain.model.PostRlat;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.utils.infrastructure.EhCacheUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限-岗位上下级关系-业务层
 * @author Liu Jun at 2018-4-2 11:01:00
 * @since v1.0.0
 */
@Service
public class PostRlatService extends BaseAuthService<PostRlat> {

	/**
	 * 获取下级岗位
	 * <p>写入缓存
	 * @param superiorId 上级岗位
	 * @return 下级岗位集合
	 */
	@Cacheable(value = EhCacheUtils.SYSTEM_CACHE, key = "'postRlats|'.concat(#superiorId)")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<PostRlat> searchSubordinate(String superiorId) {
		SQL sql = new SQL();
		sql.SELECT("*").FROM(tableName)
				.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("SUPERIOR_ID = '" + superiorId + "'");
		return this.search(sql);
	}

	/**
	 * 设置岗位的下级岗位
	 * <p>清除缓存
	 * @param superiorId 上级岗位Id
	 * @param postRlats 下级岗位关系对象
	 */
	@CacheEvict(value = EhCacheUtils.SYSTEM_CACHE, key = "'postRlats|'.concat(#superiorId)")
	@Transactional(propagation = Propagation.REQUIRED)
	public void bindSubordinates(String superiorId, List<PostRlat> postRlats) {
		List<PostRlat> removed = this.removeAllRlats(superiorId);	//先删除全部已绑定的数据，并获取
		postRlats.forEach(postRlat -> {	//saveOrUpdate分发
			if(removed.contains(postRlat)) {	//存在则更新
				postRlat.setId(removed.get(removed.indexOf(postRlat)).getId());
				postRlat.setDeleted(false);
				this.modify(postRlat);
			} else {	//不存在则新增
				this.create(postRlat);
			}
		});
	}

	/**
	 * 删除岗位的全部下级岗位
	 * @param superiorId 岗位Id
	 * @return 被删除的下级岗位
	 */
	private List<PostRlat> removeAllRlats(String superiorId) {
		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE("SUPERIOR_ID = '" + superiorId + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		List<PostRlat> list = this.search(sql);
		list.forEach(this::remove);	//循环删除
		return list;
	}

}