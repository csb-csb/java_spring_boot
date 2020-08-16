package com.sfac.javaSpringBoot.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
/**
 * @Description RedisUtils
 * @Author HymanHu
 * @Date 2020/8/16 10:29
 */
@Component
public class RedisUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * count > 0：从左到右删除等于value的第一个元素
     * count = 0：删除等于value的所有元素
     * count < 0：从右到左删除等于value的第一个元素
     */
    public long removeListItem(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return 0;
        }
    }

    public boolean updateListItem(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return false;
        }
    }

    public boolean setList(String key, List<Object> list) {
        try {
            redisTemplate.opsForList().rightPushAll(key, list);
            return true;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return false;
        }
    }

    public boolean setListItem(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return false;
        }
    }

    public Object getListItem(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return null;
        }
    }

    public long getListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return 0;
        }
    }

    public List<Object> getListRang(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return null;
        }
    }

    public long removeSetMembers(String key, Object... setValues) {
        try {
            return redisTemplate.opsForSet().remove(key, setValues);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return 0;
        }
    }

    public long setSetMembers(String key, long timeOut, Object... values) {
        try {
            long count = redisTemplate.opsForSet().add(key, values);
            if (timeOut > 0) {
                expire(key, timeOut);
            }
            return count;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return 0;
        }
    }

    public long setSetMembers(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return 0;
        }
    }

    public long getSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return 0;
        }
    }

    public boolean isSetMember(String key, Object setValue) {
        return redisTemplate.opsForSet().isMember(key, setValue);
    }

    public Set<Object> getSet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return null;
        }
    }

    public boolean putHash(String key, Map<String, Object> hashObject,
                           long timeOut) {
        try {
            redisTemplate.opsForHash().putAll(key, hashObject);
            if (timeOut > 0) {
                expire(key, timeOut);
            }
            return true;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return false;
        }
    }

    public boolean putHash(String key, Map<String, Object> hashObject) {
        try {
            redisTemplate.opsForHash().putAll(key, hashObject);
            return true;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return false;
        }
    }

    public Map<Object, Object> getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @SuppressWarnings("all")
    public void deleteHashItems(String key, String... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    public boolean haveHahsKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public double decrementHash(String key, String hashKey, double delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    public double incrementHash(String key, String hashKey, double delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    public boolean putHashValue(String key, String hashKey, Object hashValue, long timeOut) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, hashValue);
            if (timeOut > 0) {
                expire(key, timeOut);
            }
            return true;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return false;
        }
    }

    public boolean putHashValue(String key, String hashKey, Object hashValue) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, hashValue);
            return true;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return false;
        }
    }

    public Object getHashValue(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    // 设置value递减
    public long decrement(String key, long delta) {
        if (delta < 1) {
            throw new RuntimeException("Increment delta must great than 0.");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    // 设置value递增
    public long increment(String key, long delta) {
        if (delta < 1) {
            throw new RuntimeException("Increment delta must great than 0.");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public boolean set(String key, Object value, long timeOut) {
        try {
            if (timeOut > 0) {
                redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return false;
        }
    }

    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return false;
        }
    }

    public Object get(String key) {
        return StringUtils.isBlank(key) ? null : redisTemplate.opsForValue().get(key);
    }

    public void delete(String... key) {
        if (null != key && key.length > 0) {
            redisTemplate.delete(Stream.of(key).collect(Collectors.toList()));
        }
    }

    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return false;
        }
    }

    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    // 设置过期时间
    public boolean expire(String key, long timeOut) {
        try {
            if (timeOut > 0) {
                redisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return false;
        }
    }
}