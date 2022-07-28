package pers.krpc.core.registry;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;

/**
 * krpc
 * 2022/7/28 15:40
 *
 * @author lanhaifeng
 * @since
 **/
@Slf4j
public class RegistryListener {

    public void addListener() {
        try {
            //1. 创建一个PathChildrenCache
            PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, shardingPath, true);
            //2. 添加目录监听器
            pathChildrenCache.getListenable().addListener((curatorFramework1, pathChildrenCacheEvent) -> {
                switch (pathChildrenCacheEvent.getType()) {
                    case CHILD_ADDED:
                        log.info("[{}]监听到新增/移除节点,进行重新计算分片", nodeKey);
                        shardingAlgorithm(curatorFramework1);
                        log.info("[{}]本机计算分片配置[{}]", nodeKey, getShardingRand());
                        break;
                    case CHILD_REMOVED:
                    default:
                        log.info("[{}]监听到节点变更,[{}]", nodeKey, pathChildrenCacheEvent);
                }
            });
            //3. 启动监听器
            pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            log.info("[{}]注册监听异常[{}]", nodeKey, e.toString());
            throw new RuntimeException();
        }
    }

}
