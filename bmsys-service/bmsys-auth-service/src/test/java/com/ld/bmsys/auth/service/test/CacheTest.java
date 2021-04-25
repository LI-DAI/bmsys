package com.ld.bmsys.auth.service.test;

import com.google.common.cache.*;
import com.ld.bmsys.auth.service.security.vo.OnlineUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * @author LD
 * @date 2021/4/24 12:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {

    /**
     * 使用CacheLoader方式实现guava cache
     * CacheLoader : 当检索不存在时候,会自动加载默认信息
     * <p>
     * <p>
     * <p>
     * 统计功能是Guava cache一个非常实用的特性。可以通过CacheBuilder.recordStats() 方法启动了 cache的数据收集：
     * 1. Cache.stats(): 返回了一个CacheStats对象， 提供一些数据方法
     * 2. hitRate(): 请求点击率
     * 3. averageLoadPenalty(): 加载new value，花费的时间， 单位nanosecondes
     * 4. evictionCount(): 清除的个数 
     * <p>
     * 除取自动淘汰策略之外,还可以自定义清除方式
     * 1. 单独移除用: Cache.invalidate(key)
     * 2. 批量移除用 :Cache.invalidateAll(keys)
     * 3. 移除所有用 :Cache.invalidateAll()
     */
    @Test
    public void test1() {
        CacheLoader<String, OnlineUser> cacheLoader = new CacheLoader<String, OnlineUser>() {
            @Override
            public OnlineUser load(String key) throws Exception {
                System.out.println("no cache ,load data from db...");
                return new OnlineUser(0, "default", "1.1.1.1", LocalDateTime.now());
            }
        };

        //设置权重
        Weigher<String, OnlineUser> weigher = (String key, OnlineUser value) -> {
            if (key.equals("beijing")) {
                return 2;
            }
            return 0;
        };

        LoadingCache<String, OnlineUser> loadingCache = CacheBuilder.newBuilder()
                //当cache内元素数临近或超过最大值时,cache会自动释放最近没用或不经常使用的元素
//                .maximumSize(3)
                //失效时间,最后一次访问(读或者写)开始计时，过了这段指定的时间就会释放掉该元素
                .expireAfterAccess(Duration.ofMinutes(10))
                //从元素被创建或者最后一次被修改值的点来计时的,如果超过了指定时间,元素就会被删除
//                .expireAfterWrite(Duration.ofMinutes(10))
                //设置最大权重值
                .maximumWeight(3)
                .weigher(weigher)
                //过weak reference存储keys,如果keys没有被strong或者soft引用，那么元素会被垃圾回收
//                .weakKeys()
                //过weak reference存储values,如果values没有被strong或者soft引用，那么元素会被垃圾回收
//                .weakValues()
                .build(cacheLoader);

        //如果key相同,则该方法将覆盖key对应的value
        loadingCache.put("ming", new OnlineUser(1, "ming", "2.2.2.2", LocalDateTime.now()));
        loadingCache.put("hong", new OnlineUser(2, "hong", "3.3.3.3", LocalDateTime.now()));
        loadingCache.put("beijing", new OnlineUser(2, "beijing", "3.3.3.3", LocalDateTime.now()));

        System.out.println(loadingCache.getIfPresent("ming"));
        System.out.println(loadingCache.getIfPresent("hong"));

        //移除
        loadingCache.invalidate("hong");

        ConcurrentMap<String, OnlineUser> userConcurrentMap = loadingCache.asMap();
        //和userCache.put都可以将数据存入缓存,但是此操作非原子操作
        userConcurrentMap.put("dai", new OnlineUser(3, "dai", "3.3.3.3", LocalDateTime.now()));
        userConcurrentMap.forEach((k, v) -> System.out.println(k + "=" + v.toString()));

        System.out.println(loadingCache.getIfPresent("beijing"));
        System.out.println();

        //这里使用 getUnchecked 如果没有获取到数据,就会加载 CacheLoader中的数据
        System.out.println(loadingCache.getUnchecked("beijing"));

        //无论是否是 cacheLoader 模式 都支持 get(K key, Callable<? extends V> loader) 方法
        try {
            OnlineUser onlineUser = loadingCache.get("noValueCall", () -> new OnlineUser(3, "noValueCall", "3.3.3.3", LocalDateTime.now()));
            System.out.println(onlineUser);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        System.out.println(loadingCache.asMap());


        Cache<String, OnlineUser> cache = CacheBuilder.newBuilder().maximumSize(10).build();
        cache.put("beijing", new OnlineUser(2, "beijing", "3.3.3.3", LocalDateTime.now()));
        System.out.println(cache.getIfPresent("beijing"));


    }


}
