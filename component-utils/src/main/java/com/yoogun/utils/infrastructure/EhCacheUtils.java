package com.yoogun.utils.infrastructure;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

/**
 * Cache工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class EhCacheUtils {

	private static EhCacheCacheManager cacheManager ;

	public static final String SYSTEM_CACHE = "systemCache";

	public static final String MENU_CACHE = "menuCache";

	/**
	 *  注入静态对象
	 * @param cacheManager 缓存服务器
	 */
	@Autowired
	public void setCacheManager(EhCacheCacheManager cacheManager) {
		EhCacheUtils.cacheManager = cacheManager;
	}

	public static EhCacheCacheManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * 获取系统缓存
	 * @param key 缓存KEY
	 * @return 系统缓存值
	 */
	public static Object get(String key) {
		return get(SYSTEM_CACHE, key);
	}

	/**
	 * 写入系统缓存
	 * @param key 缓存KEY
	 */
	public static void put(String key, Object value) {
		put(SYSTEM_CACHE, key, value);
	}

	/**
	 * 从系统缓存中移除
	 * @param key 缓存KEY
	 */
	public static void remove(String key) {
		remove(SYSTEM_CACHE, key);
	}

	/**
	 * 获取缓存
	 * @param cacheName 缓存名称
	 * @param key 缓存KEY
	 * @return 缓存
	 */
	public static Object get(String cacheName, String key) {
		return getCache(cacheName).get(key).get();
	}

	/**
	 * 写入缓存
	 * @param cacheName 缓存名称
	 * @param key 缓存KEY
	 * @param value 缓存值
	 */
	public static void put(String cacheName, String key, Object value) {
		getCache(cacheName).put(key, value);
	}

	/**
	 * 从缓存中移除
	 * @param cacheName 缓存名称
	 * @param key 缓存KEY
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).evict(key);
	}
	
	/**
	 * 获得一个Cache，没有则创建一个。
	 * @param cacheName 缓存名称
	 * @return 缓存
	 */
	public static Cache getCache(String cacheName) {
		return cacheManager.getCache(cacheName);
	}

}
