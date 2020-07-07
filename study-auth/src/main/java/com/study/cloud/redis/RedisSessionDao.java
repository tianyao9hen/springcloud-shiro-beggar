package com.study.cloud.redis;

import com.study.cloud.utils.RedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author jay.zhou
 * @date 2019/1/15
 * @time 13:29
 * 源码参考来源：https://blog.csdn.net/lishehe/article/details/45223823
 */
public class RedisSessionDao extends AbstractSessionDAO {

    private static final Logger LOGGER = getLogger(RedisSessionDao.class);
    /**
     * 此编码需要与 RedisServiceImpl 类中编码一致
     * 用于解析每个session的Key
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 过期时间
     */
    private static final Long EXPIRE_SECONDS = 60000L;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * shiro-redis的session对象前缀
     */
    private static final String KEY_PREFIX = "shiro_redis_session:";

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("更新Session:{}", session.getId());
        }
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            LOGGER.error("session对象(或者sessionId)为空.");
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("删除Session:{}", session.getId());
        }
        //通过sessionId删除session
        redisUtil.del(getStrKey(session.getId()));
    }

    /**
     * 统计当前活动的session TODO
     *
     * @return 当前活动的session
     */
    @Override
    public Collection<Session> getActiveSessions() {
        /*final Set<Session> sessions = newHashSet();
        //获取缓存中匹配key值的所有键
        final Set<byte[]> keys = redisService.keys(this.keyPrefix + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            for (byte[] key : keys) {
                //添加到set集合中
                byte[] bytes = redisService.get(key);
                Session session = SerializerUtil.deserialize(bytes);
                sessions.add(session);
            }
        }
        //shiro的session为我们提供了大量的API接口
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("==========>>统计活动Session（开始）总计活动Session:{}条.<<==========", sessions.size());
            for (Session session : sessions) {
                LOGGER.debug("ID:{}", session.getId());
                LOGGER.debug("有效期:{}秒", session.getTimeout() / 1000);
                LOGGER.debug("创建时间:{}", dateToDateStr(session.getStartTimestamp(), DEFAULT_FORMAT));
                LOGGER.debug("上次使用时间:{}", dateToDateStr(session.getStartTimestamp(), DEFAULT_FORMAT));
                LOGGER.debug(".......................................................................");
            }
            LOGGER.debug("==========>>统计活动Session（结束）总计活动Session:{}条.<<==========", sessions.size());
        }
        return sessions;*/
        return null;
    }

    @Override
    protected Serializable doCreate(Session session) {
        //分配sessionId
        final Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        //保存session并存储到Redis集群中
        this.saveSession(session);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("创建Session:{}", sessionId);
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            LOGGER.error("sessionId为空.");
            return null;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("读取Session:{}", sessionId);
        }
        //与saveSession是反操作，通过sessionId获取Key的字节数据
        //final byte[] key = this.getByteKey(sessionId);
        String key = KEY_PREFIX + sessionId;
        //再通过key的字节数据找到value的字节数据
        Session session = (Session) redisUtil.get(key);
        //最后再反序列化得到session对象
        return session;
    }

    /**
     * 保存session
     * sessionId -> key[]
     * session   -> value[]
     *
     * @param session Session对象
     * @throws UnknownSessionException 未知Session异常
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            LOGGER.error("session对象(或者sessionId)为空.");
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("保存Session:{}", session.getId());
        }
        //sessionId -> key[]
        //final byte[] key = getByteKey(session.getId());
        String key = KEY_PREFIX + session.getId();
        //session   -> value[]
        //final byte[] value = SerializerUtil.serialize(session);
        session.setTimeout(EXPIRE_SECONDS);
        //save To Redis
        //this.redisService.setEx(key, value, getExpireSeconds());
        //redisUtil.set(key,session,EXPIRE_SECONDS);
        redisUtil.set(key,session);
    }

    private byte[] getByteKey(Serializable sessionId) {
        final String preKey = KEY_PREFIX + sessionId;
        return preKey.getBytes(Charset.forName(DEFAULT_CHARSET));
    }

    private String getStrKey(Serializable sessionId){
        String key = KEY_PREFIX + sessionId;
        return key;
    }
}