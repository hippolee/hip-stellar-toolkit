package org.hippo.toolkit.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RedisClientTemplate
 * <p>
 * Created by litengfei on 2017/6/5.
 */
public class RedisClientTemplate {

    /** logger */
    private static final Logger logger = (Logger) LoggerFactory.getLogger(RedisClientTemplate.class);

    /** pool */
    private ShardedJedisPool pool;

    public RedisClientTemplate(ShardedJedisPool pool) {
        this.pool = pool;
    }

    /**
     * 取得ShardedJedis实例
     *
     * @return
     */
    public ShardedJedis getRedisClient() {
        ShardedJedis shardJedis = null;
        try {
            shardJedis = pool.getResource();
        } catch (Exception e) {
            logger.error("get redis client error", e);
        }
        return shardJedis;
    }

    /**
     * SET key value
     * 将字符串值 value 关联到 key
     * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型
     * 时间复杂度:O(1)
     *
     * @param key
     * @param value
     * @return 总是返回 OK ，因为 SET 不可能失败
     */
    public String set(String key, String value) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.set(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * GET key
     * 返回 key 所关联的字符串值
     * 如果 key 不存在那么返回特殊值 nil
     * 假如 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 当 key 不存在时，返回 nil ，否则，返回 key 的值
     */
    public String get(String key) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * GETSET key value
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     * 当 key 存在但不是字符串类型时，返回一个错误
     * 时间复杂度:O(1)
     *
     * @param key
     * @param value
     * @return 返回给定 key 的旧值，当 key 没有旧值时，也即是 key 不存在时，返回 nil
     */
    public String getSet(String key, String value) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.getSet(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SETNX key value
     * 将 key 的值设为 value ，当且仅当 key 不存在
     * 若给定的 key 已经存在，则 SETNX 不做任何动作
     * SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写
     * 时间复杂度:O(1)
     *
     * @param key
     * @param value
     * @return 设置成功，返回 1，设置失败，返回 0
     */
    public Long setnx(String key, String value) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.setnx(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SETEX key seconds value
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)
     * 如果 key 已经存在， SETEX 命令将覆写旧值
     * 这个命令类似于以下两个命令：
     * SET key value
     * EXPIRE key seconds  # 设置生存时间
     * 不同之处是， SETEX 是一个原子性(atomic)操作，关联值和设置生存时间两个动作会在同一时间内完成，该命令在 Redis 用作缓存时，
     * 非常实用。
     * 时间复杂度:O(1)
     *
     * @param key
     * @param seconds
     * @param value
     * @return 设置成功时返回 OK，当 seconds 参数不合法时，返回一个错误
     */
    public String setex(String key, int seconds, String value) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * EXISTS key
     * 检查给定 key 是否存在
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 若 key 存在，返回 1 ，否则返回 0
     */
    public Boolean exists(String key) {
        Boolean result = false;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.exists(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * TYPE key
     * 返回 key 所储存的值的类型
     * 时间复杂度:O(1)
     *
     * @param key
     * @return none (key不存在) string (字符串) list (列表) set (集合) zset (有序集) hash (哈希表)
     */
    public String type(String key) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.type(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * DEL key
     * 删除给定 key
     * 不存在的 key 会被忽略
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 若 key 删除成功，返回 1 ，否则返回 0
     */
    public Long del(String key) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.del(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * EXPIRE key seconds
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除
     * 在 Redis 中，带有生存时间的 key 被称为『可挥发』(volatile)的
     * 生存时间可以通过使用 DEL 命令来删除整个 key 来移除，或者被 SET 和 GETSET 命令覆写(overwrite)，这意味着，如果一个命
     * 令只是修改(alter)一个带生存时间的 key 的值而不是用一个新的 key 值来代替(replace)它的话，那么生存时间不会被改变
     * 可以对一个已经带有生存时间的 key 执行 EXPIRE 命令，新指定的生存时间会取代旧的生存时间
     * 时间复杂度:O(1)
     *
     * @param key
     * @param seconds
     * @return 设置成功返回 1，当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的生存时间)，返回 0
     */
    public Long expire(String key, int seconds) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * EXPIREAT key timestamp
     * EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置生存时间
     * 不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp)
     * 时间复杂度:O(1)
     *
     * @param key
     * @param timestamp
     * @return 如果生存时间设置成功，返回 1，当 key 不存在或没办法设置生存时间，返回 0
     */
    public Long expireAt(String key, long timestamp) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.expireAt(key, timestamp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * TTL key
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 当 key 不存在或没有设置生存时间时，返回 -1，否则，返回 key 的剩余生存时间(以秒为单位)
     */
    public Long ttl(String key) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.ttl(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * KEYS pattern
     * 查找所有符合给定模式 pattern 的 key
     * KEYS * 匹配数据库中所有 key
     * KEYS h?llo 匹配 hello ， hallo 和 hxllo 等
     * KEYS h*llo 匹配 hllo 和 heeeeello 等
     * KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo
     * 特殊符号用 \ 隔开
     * 时间复杂度:O(N)， N 为数据库中 key 的数量
     *
     * @param pattern
     * @return 符合给定模式的 key 列表
     */
    public Set<String> keys(String pattern) {
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            Jedis j = shardedJedis.getShard(pattern);
            result = j.keys(pattern);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SETBIT key offset value
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)
     * 位的设置或清除取决于 value 参数，可以是 0 也可以是 1
     * 当 key 不存在时，自动生成一个新的字符串值
     * 字符串会进行伸展(grown)以确保它可以将 value 保存在指定的偏移量上。当字符串值进行伸展时，空白位置以 0 填充
     * offset 参数必须大于或等于 0 ，小于 2^32 (bit 映射被限制在 512 MB 之内)
     * 对使用大的 offset 的 SETBIT 操作来说，内存分配可能造成 Redis 服务器被阻塞。
     * 时间复杂度:O(1)
     *
     * @param key
     * @param offset
     * @param value
     * @return 指定偏移量原来储存的位
     */
    public boolean setbit(String key, long offset, boolean value) {
        boolean result = false;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.setbit(key, offset, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * GETBIT key offset
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
     * 当 offset 比字符串值的长度大，或者 key 不存在时，返回 0
     * 时间复杂度:O(1)
     *
     * @param key
     * @param offset
     * @return 字符串值指定偏移量上的位(bit)
     */
    public boolean getbit(String key, long offset) {
        ShardedJedis shardedJedis = getRedisClient();
        boolean result = false;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.getbit(key, offset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SETRANGE key offset value
     * 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始
     * 不存在的 key 当作空白字符串处理
     * SETRANGE 命令会确保字符串足够长以便将 value 设置在指定的偏移量上，如果给定 key 原来储存的字符串长度比偏移量小(比如
     * 字符串只有 5 个字符长，但你设置的 offset 是 10 )，那么原字符和偏移量之间的空白将用零字节(zerobytes, "\x00" )来填充
     * 注意你能使用的最大偏移量是 2^29-1(536870911) ，因为 Redis 字符串的大小被限制在 512 兆(megabytes)以内。如果你需要
     * 使用比这更大的空间，你可以使用多个 key
     * 当生成一个很长的字符串时，Redis 需要分配内存空间，该操作有时候可能会造成服务器阻塞(block)。在2010年的Macbook Pro上，
     * 设置偏移量为 536870911(512MB 内存分配)，耗费约 300 毫秒， 设置偏移量为 134217728(128MB 内存分配)，耗费约 80 毫秒，
     * 设置偏移量 33554432(32MB 内存分配)，耗费约 30 毫秒，设置偏移量为 8388608(8MB 内存分配)，耗费约 8 毫秒。 注意若首次
     * 内存分配成功之后，再对同一个 key 调用 SETRANGE 操作，无须再重新内存
     * 时间复杂度:对小(small)的字符串，平摊复杂度O(1)，否则为O(M)， M 为 value 参数的长度
     *
     * @param key
     * @param offset
     * @param value
     * @return 被 SETRANGE 修改之后，字符串的长度
     */
    public long setrange(String key, long offset, String value) {
        ShardedJedis shardedJedis = getRedisClient();
        long result = 0;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.setrange(key, offset, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * GETRANGE key start end
     * 返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
     * 负数偏移量表示从字符串最后开始计数， -1 表示最后一个字符， -2 表示倒数第二个，以此类推。
     * GETRANGE 通过保证子字符串的值域(range)不超过实际字符串的值域来处理超出范围的值域请求。
     * 时间复杂度:O(N)， N 为要返回的字符串的长度，复杂度最终由字符串的返回值长度决定，但因为从已有字符串中取出
     * 子字符串的操作非常廉价(cheap)，所以对于长度不大的字符串，该操作的复杂度也可看作O(1)
     *
     * @param key
     * @param startOffset
     * @param endOffset
     * @return 截取得出的子字符串
     */
    public String getrange(String key, long startOffset, long endOffset) {
        ShardedJedis shardedJedis = getRedisClient();
        String result = null;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.getrange(key, startOffset, endOffset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * INCR key
     * 将 key 中储存的数字值增一
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误
     * 本操作的值限制在 64 位(bit)有符号数字表示之内
     * 这是一个针对字符串的操作，因为 Redis 没有专用的整数类型，所以 key 内储存的字符串被解释为十进制 64 位有符号整数
     * 来执行 INCR 操作
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 执行 INCR 命令之后 key 的值
     */
    public Long incr(String key) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.incr(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * INCRBY key increment
     * 将 key 所储存的值加上增量 increment
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误
     * 本操作的值限制在 64 位(bit)有符号数字表示之内
     * 关于递增(increment) / 递减(decrement)操作的更多信息，参见 INCR 命令
     * 时间复杂度:O(1)
     *
     * @param key
     * @param increment
     * @return 加上 increment 之后， key 的值
     */
    public Long incrBy(String key, long increment) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.incrBy(key, increment);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * DECR key
     * 将 key 中储存的数字值减一
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误
     * 本操作的值限制在 64 位(bit)有符号数字表示之内
     * 关于递增(increment) / 递减(decrement)操作的更多信息，请参见 INCR 命令
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 执行 DECR 命令之后 key 的值
     */
    public Long decr(String key) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.decr(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * DECRBY key decrement
     * 将 key 所储存的值减去减量 decrement
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误
     * 本操作的值限制在 64 位(bit)有符号数字表示之内
     * 关于更多递增(increment) / 递减(decrement)操作的更多信息，请参见 INCR 命令
     * 时间复杂度:O(1)
     *
     * @param key
     * @param decrement
     * @return 减去 decrement 之后， key 的值
     */
    public Long decrBy(String key, long decrement) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.decrBy(key, decrement);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * APPEND key value
     * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾
     * 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样
     * 时间复杂度:平摊O(1)
     *
     * @param key
     * @param value
     * @return 追加 value 之后， key 中字符串的长度
     */
    public Long append(String key, String value) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.append(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SORT key [BY pattern] [LIMIT offset count] [GET pattern [GET pattern ...]] [ASC | DESC] [ALPHA] [STORE destination]
     * 返回或保存给定列表、集合、有序集合 key 中经过排序的元素
     * 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较
     * 时间复杂度:O(N+M*log(M))， N 为要排序的列表或集合内的元素数量， M 为要返回的元素数量
     * 如果只是使用 SORT 命令的 GET 选项获取数据而没有进行排序，时间复杂度 O(N)
     *
     * @param key
     * @return 没有使用 STORE 参数，返回列表形式的排序结果。使用 STORE 参数，返回排序结果的元素数量
     */
    public List<String> sort(String key) {
        List<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.sort(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SORT key [BY pattern] [LIMIT offset count] [GET pattern [GET pattern ...]] [ASC | DESC] [ALPHA] [STORE destination]
     * 返回或保存给定列表、集合、有序集合 key 中经过排序的元素
     * 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较
     * 时间复杂度:O(N+M*log(M))， N 为要排序的列表或集合内的元素数量， M 为要返回的元素数量
     * 如果只是使用 SORT 命令的 GET 选项获取数据而没有进行排序，时间复杂度 O(N)
     *
     * @param key
     * @param sortingParameters
     * @return 没有使用 STORE 参数，返回列表形式的排序结果。使用 STORE 参数，返回排序结果的元素数量
     */
    public List<String> sort(String key, SortingParams sortingParameters) {
        List<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.sort(key, sortingParameters);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * HSET key field value
     * 将哈希表 key 中的域 field 的值设为 value
     * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作
     * 如果域 field 已经存在于哈希表中，旧值将被覆盖
     * 时间复杂度:O(1)
     *
     * @param key
     * @param field
     * @param value
     * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1，如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0
     */
    public Long hset(String key, String field, String value) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * HGET key field
     * 返回哈希表 key 中给定域 field 的值
     * 时间复杂度:O(1)
     *
     * @param key
     * @param field
     * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil
     */
    public String hget(String key, String field) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.hget(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * HSETNX key field value
     * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在
     * 若域 field 已经存在，该操作无效
     * 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令
     * 时间复杂度:O(1)
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hsetnx(String key, String field, String value) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.hsetnx(key, field, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * HMSET key field value [field value ...]
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     * 此命令会覆盖哈希表中已存在的域
     * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作
     * 时间复杂度:O(N)， N 为 field-value 对的数量
     *
     * @param key
     * @param hash
     * @return 如果命令执行成功，返回 OK，当 key 不是哈希表(hash)类型时，返回一个错误
     */
    public String hmset(String key, Map<String, String> hash) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.hmset(key, hash);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * HMGET key field [field ...]
     * 返回哈希表 key 中，一个或多个给定域的值
     * 如果给定的域不存在于哈希表，那么返回一个 nil 值
     * 因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表
     * 时间复杂度:O(N)， N 为给定域的数量
     *
     * @param key
     * @param fields
     * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样
     */
    public List<String> hmget(String key, String... fields) {
        List<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.hmget(key, fields);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * HINCRBY key field increment
     * 为哈希表 key 中的域 field 的值加上增量 increment
     * 增量也可以为负数，相当于对给定域进行减法操作
     * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令
     * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0
     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误
     * 本操作的值被限制在 64 位(bit)有符号数字表示之内
     * 时间复杂度:O(1)
     *
     * @param key
     * @param field
     * @param increment
     * @return 执行 HINCRBY 命令之后，哈希表 key 中域 field 的值
     */
    public Long hincrBy(String key, String field, long increment) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.hincrBy(key, field, increment);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * HEXISTS key field
     * 查看哈希表 key 中，给定域 field 是否存在
     * 时间复杂度:O(1)
     *
     * @param key
     * @param field
     * @return 如果哈希表含有给定域，返回 1，如果哈希表不含有给定域，或 key 不存在，返回 0
     */
    public Boolean hexists(String key, String field) {
        Boolean result = false;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.hexists(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * HDEL key field [field ...]
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略
     * 时间复杂度:O(N)， N 为要删除的域的数量
     *
     * @param key
     * @param fields
     * @return 被成功移除的域的数量，不包括被忽略的域
     */
    public Long hdel(String key, String... fields) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.hdel(key, fields);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * HLEN key
     * 返回哈希表 key 中域的数量
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 哈希表中域的数量，当 key 不存在时，返回 0
     */
    public Long hlen(String key) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.hlen(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * HKEYS key
     * 返回哈希表 key 中的所有域
     * 时间复杂度:O(N)， N 为哈希表的大小
     *
     * @param key
     * @return 一个包含哈希表中所有域的表，当 key 不存在时，返回一个空表。
     */
    public Set<String> hkeys(String key) {
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.hkeys(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * HVALS key
     * 返回哈希表 key 中所有域的值
     * 时间复杂度:O(N)， N 为哈希表的大小
     *
     * @param key
     * @return 一个包含哈希表中所有值的表，当 key 不存在时，返回一个空表
     */
    public List<String> hvals(String key) {
        List<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.hvals(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * HGETALL key
     * 返回哈希表 key 中，所有的域和值
     * 在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍
     * 时间复杂度:O(N)， N 为哈希表的大小
     *
     * @param key
     * @return 以列表形式返回哈希表的域和域的值，若 key 不存在，返回空列表
     */
    public Map<String, String> hgetAll(String key) {
        Map<String, String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.hgetAll(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * LPUSH key value [value ...]
     * 将一个或多个值 value 插入到列表 key 的表头
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头：
     * 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a
     * 这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令
     * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作
     * 当 key 存在但不是列表类型时，返回一个错误
     * 时间复杂度:O(1)
     *
     * @param key
     * @param values
     * @return 执行 LPUSH 命令后，列表的长度
     */
    public Long lpush(String key, String... values) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.lpush(key, values);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * RPUSH key value [value ...]
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾：
     * 比如对一个空列表 mylist 执行 RPUSH mylist a b c ，得出的结果列表为 a b c
     * 等同于执行命令 RPUSH mylist a 、 RPUSH mylist b 、 RPUSH mylist c
     * 如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作
     * 当 key 存在但不是列表类型时，返回一个错误
     * 时间复杂度:O(1)
     *
     * @param key
     * @param values
     * @return 执行 RPUSH 操作后，表的长度
     */
    public Long rpush(String key, String... values) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.rpush(key, values);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * LPOP key
     * 移除并返回列表 key 的头元素
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 列表的头元素，当 key 不存在时，返回 nil
     */
    public String lpop(String key) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.lpop(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * RPOP key
     * 移除并返回列表 key 的尾元素
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 列表的尾元素，当 key 不存在时，返回 nil
     */
    public String rpop(String key) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.rpop(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * LLEN key
     * 返回列表 key 的长度
     * 如果 key 不存在，则 key 被解释为一个空列表，返回 0
     * 如果 key 不是列表类型，返回一个错误
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 列表 key 的长度
     */
    public Long llen(String key) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.llen(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * LRANGE key start stop
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推
     * LRANGE 命令的取值范围为闭区间
     * 时间复杂度:O(S+N)， S 为偏移量 start ， N 为指定区间内元素的数量
     *
     * @param key
     * @param start
     * @param stop
     * @return 一个列表，包含指定区间内的元素
     */
    public List<String> lrange(String key, long start, long stop) {
        List<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.lrange(key, start, stop);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * LREM key count value
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素
     * count 的值可以是以下几种：
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值
     * count = 0 : 移除表中所有与 value 相等的值
     * 时间复杂度:O(N)
     *
     * @param key
     * @param count
     * @param value
     * @return 被移除元素的数量，因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM 命令总是返回 0
     */
    public Long lrem(String key, long count, String value) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.lrem(key, count, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * LSET key index value
     * 将列表 key 下标为 index 的元素的值设置为 value
     * 当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误
     * 关于列表下标的更多信息，请参考 LINDEX 命令
     * 时间复杂度:对头元素或尾元素进行 LSET 操作，复杂度为 O(1)。其他情况下，为 O(N)， N 为列表的长度
     *
     * @param key
     * @param index
     * @param value
     * @return 操作成功返回 ok ，否则返回错误信息
     */
    public String lset(String key, long index, String value) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.lset(key, index, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * LTRIM key start stop
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推
     * 也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推
     * 当 key 不是列表类型时，返回一个错误
     * LTRIM 命令通常和 LPUSH 命令或 RPUSH 命令配合使用（在 LPUSH 或 RPUSH 操作后，使列表保留指定数量）
     * LTRIM 命令的取值范围为闭区间
     * 时间复杂度:O(N)， N 为被移除的元素的数量
     *
     * @param key
     * @param start
     * @param stop
     * @return 命令执行成功时，返回 ok
     */
    public String ltrim(String key, long start, long stop) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.ltrim(key, start, stop);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * LINDEX key index
     * 返回列表 key 中，下标为 index 的元素
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推
     * 也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推
     * 如果 key 不是列表类型，返回一个错误
     * 时间复杂度:O(N)， N 为到达下标 index 过程中经过的元素数量。因此，对列表的头元素和尾元素执行 LINDEX 命令，复杂度为O(1)
     *
     * @param key
     * @param index
     * @return 列表中下标为 index 的元素，如果 index 参数的值不在列表的区间范围内(out of range)，返回 nil
     */
    public String lindex(String key, long index) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.lindex(key, index);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * LINSERT key BEFORE|AFTER pivot value
     * 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后
     * 当 pivot 不存在于列表 key 时，不执行任何操作
     * 当 key 不存在时， key 被视为空列表，不执行任何操作
     * 如果 key 不是列表类型，返回一个错误
     * 时间复杂度:O(N)， N 为寻找 pivot 过程中经过的元素数量
     *
     * @param key
     * @param where
     * @param pivot
     * @param value
     * @return 如果命令执行成功，返回插入操作完成之后，列表的长度。
     * 如果没有找到 pivot ，返回 -1
     * 如果 key 不存在或为空列表，返回 0
     */
    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.linsert(key, where, pivot, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SADD key member [member ...]
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略
     * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合
     * 当 key 不是集合类型时，返回一个错误
     * 时间复杂度:O(N)， N 是被添加的元素的数量
     *
     * @param key
     * @param members
     * @return 被添加到集合中的新元素的数量，不包括被忽略的元素
     */
    public Long sadd(String key, String... members) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.sadd(key, members);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SMEMBERS key
     * 返回集合 key 中的所有成员
     * 不存在的 key 被视为空集合
     * 时间复杂度:O(N)， N 为集合的基数
     *
     * @param key
     * @return 集合中的所有成员
     */
    public Set<String> smembers(String key) {
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.smembers(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SREM key member [member ...]
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
     * 当 key 不是集合类型，返回一个错误
     * 时间复杂度:O(N)， N 为给定 member 元素的数量
     *
     * @param key
     * @param members
     * @return 被成功移除的元素的数量，不包括被忽略的元素
     */
    public Long srem(String key, String members) {
        ShardedJedis shardedJedis = getRedisClient();

        Long result = null;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.srem(key, members);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SPOP key
     * 移除并返回集合中的一个随机元素
     * 如果只想获取一个随机元素，但不想该元素从集合中被移除的话，可以使用 SRANDMEMBER 命令
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 被移除的随机元素。当 key 不存在或 key 是空集时，返回 nil
     */
    public String spop(String key) {
        ShardedJedis shardedJedis = getRedisClient();
        String result = null;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.spop(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SCARD key
     * 返回集合 key 的基数(集合中元素的数量)
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 如果 member 元素被成功移除，返回 1 。
     * 如果 member 元素不是 source 集合的成员，并且没有任何操作对 destination 集合执行，那么返回 0
     */
    public Long scard(String key) {
        ShardedJedis shardedJedis = getRedisClient();
        Long result = null;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.scard(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SISMEMBER key member
     * 判断 member 元素是否集合 key 的成员
     * 时间复杂度:O(1)
     *
     * @param key
     * @param member
     * @return 如果 member 元素是集合的成员，返回 1，如果 member 元素不是集合的成员，或 key 不存在，返回 0
     */
    public Boolean sismember(String key, String member) {
        ShardedJedis shardedJedis = getRedisClient();
        Boolean result = null;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.sismember(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * SRANDMEMBER key [count]
     * 返回集合中的一个随机元素
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 返回一个元素；如果集合为空，返回 nil
     */
    public String srandmember(String key) {
        ShardedJedis shardedJedis = getRedisClient();
        String result = null;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.srandmember(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZADD key score member [[score member] [score member] ...]
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，
     * 来保证该 member 在正确的位置上
     * score 值可以是整数值或双精度浮点数
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作
     * 当 key 存在但不是有序集类型时，返回一个错误
     *
     * @param key
     * @param score
     * @param member
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员
     * @see <p>https://redis.io/topics/data-types#sorted-sets</p>
     * 时间复杂度:O(M*log(N))， N 是有序集的基数， M 为成功添加的新成员的数量
     */
    public Long zadd(String key, double score, String member) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zadd(key, score, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZREM key member [member ...]
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
     * 当 key 存在但不是有序集类型时，返回一个错误
     * 时间复杂度:O(M*log(N))， N 为有序集的基数， M 为被成功移除的成员的数量
     *
     * @param key
     * @param members
     * @return 被成功移除的成员的数量，不包括被忽略的成员
     */
    public Long zrem(String key, String... members) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrem(key, members);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZCARD key
     * 返回有序集 key 的基数
     * 时间复杂度:O(1)
     *
     * @param key
     * @return 当 key 存在且是有序集类型时，返回有序集的基数。当 key 不存在时，返回 0
     */
    public Long zcard(String key) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zcard(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZCOUNT key min max
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量
     * 关于参数 min 和 max 的详细使用方法，请参考 ZRANGEBYSCORE 命令
     * 时间复杂度:O(log(N)+M)， N 为有序集的基数， M 为值在 min 和 max 之间的元素的数量
     *
     * @param key
     * @param min
     * @param max
     * @return score 值在 min 和 max 之间的成员的数量
     */
    public Long zcount(String key, double min, double max) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zcount(key, min, max);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZSCORE key member
     * 返回有序集 key 中，成员 member 的 score 值
     * 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 nil
     * 时间复杂度:O(1)
     *
     * @param key
     * @param member
     * @return member 成员的 score 值，以字符串形式表示
     */
    public Double zscore(String key, String member) {
        Double result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zscore(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZINCRBY key increment member
     * 为有序集 key 的成员 member 的 score 值加上增量 increment
     * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member
     * 就是让 member 的 score 值减去 5
     * 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key increment member
     * 当 key 不是有序集类型时，返回一个错误
     * score 值可以是整数值或双精度浮点数
     * 时间复杂度:O(log(N))
     *
     * @param key
     * @param increment
     * @param member
     * @return member 成员的新 score 值，以字符串形式表示
     */
    public Double zincrby(String key, double increment, String member) {
        Double result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zincrby(key, increment, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZRANGE key start stop [WITHSCORES]
     * 返回有序集 key 中，指定区间内的成员
     * 其中成员的位置按 score 值递增(从小到大)来排序
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列
     * 如果你需要成员按 score 值递减(从大到小)来排列，请使用 ZREVRANGE 命令
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推
     * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推
     * <p>
     * 超出范围的下标并不会引起错误
     * 比如说，当 start 的值比有序集的最大下标还要大，或是 start > stop 时， ZRANGE 命令只是简单地返回一个空列表
     * 另一方面，假如 stop 参数的值比有序集的最大下标还要大，那么 Redis 将 stop 当作最大下标来处理
     * 时间复杂度:O(log(N)+M)， N 为有序集的基数，而 M 为结果集的基数
     *
     * @param key
     * @param start
     * @param stop
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<String> zrange(String key, int start, int stop) {
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrange(key, start, stop);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZRANGE key start stop [WITHSCORES]
     *
     * @param key
     * @param start
     * @param stop
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<Tuple> zrangeWithScores(String key, int start, int stop) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrangeWithScores(key, start, stop);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
     * 有序集成员按 score 值递增(从小到大)次序排列
     * 具有相同 score 值的成员按字典序(lexicographical order)来排列(该属性是有序集提供的，不需要额外的计算)
     * 可选的 LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )
     * 注意当 offset 很大时，定位 offset 的操作可能需要遍历整个有序集，此过程最坏复杂度为 O(N) 时间
     * 可选的 WITHSCORES 参数决定结果集是单单返回有序集的成员，还是将有序集成员及其 score 值一起返回
     * 时间复杂度:O(log(N)+M)， N 为有序集的基数， M 为被结果集的基数
     *
     * @param key
     * @param min
     * @param max
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<String> zrangeByScore(String key, double min, double max) {
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
     *
     * @param key
     * @param min
     * @param max
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZREVRANGE key start stop [WITHSCORES]
     * 返回有序集 key 中，指定区间内的成员
     * 其中成员的位置按 score 值递减(从大到小)来排列
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列
     * 除了成员按 score 值递减的次序排列这一点外， ZREVRANGE 命令的其他方面和 ZRANGE 命令一样
     * 时间复杂度:O(log(N)+M)， N 为有序集的基数，而 M 为结果集的基数
     *
     * @param key
     * @param start
     * @param stop
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<String> zrevrange(String key, int start, int stop) {
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrevrange(key, start, stop);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZREVRANGE key start stop [WITHSCORES]
     *
     * @param key
     * @param start
     * @param end
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrevrangeWithScores(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员
     * 有序集成员按 score 值递减(从大到小)的次序排列
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order )排列
     * 除了成员按 score 值递减的次序排列这一点外， ZREVRANGEBYSCORE 命令的其他方面和 ZRANGEBYSCORE 命令一样
     * 时间复杂度:O(log(N)+M)， N 为有序集的基数， M 为结果集的基数
     *
     * @param key
     * @param max
     * @param min
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
     *
     * @param key
     * @param max
     * @param min
     * @param offset
     * @param count
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrevrangeByScore(key, max, min, offset, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
     *
     * @param key
     * @param max
     * @param min
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrevrangeByScoreWithScores(key, max, min);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
     *
     * @param key
     * @param max
     * @param min
     * @param offset
     * @param count
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZRANK key member
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列
     * 排名以 0 为底，也就是说， score 值最小的成员排名为 0
     * 使用 ZREVRANK 命令可以获得成员按 score 值递减(从大到小)排列的排名
     * 时间复杂度:O(log(N))
     *
     * @param key
     * @param member
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名。如果 member 不是有序集 key 的成员，返回 nil
     */
    public Long zrank(String key, String member) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrank(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZREVRANK key member
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序
     * 排名以 0 为底，也就是说， score 值最大的成员排名为 0
     * 使用 ZRANK 命令可以获得成员按 score 值递增(从小到大)排列的排名
     * 时间复杂度:O(log(N))
     *
     * @param key
     * @param member
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名。如果 member 不是有序集 key 的成员，返回 nil
     */
    public Long zrevrank(String key, String member) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zrevrank(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZREMRANGEBYRANK key start stop
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员
     * 区间分别以下标参数 start 和 stop 指出，包含 start 和 stop 在内
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推
     * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推
     * 时间复杂度:O(log(N)+M)， N 为有序集的基数，而 M 为被移除成员的数量
     *
     * @param key
     * @param start
     * @param stop
     * @return 被移除成员的数量
     */
    public Long zremrangeByRank(String key, int start, int stop) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zremrangeByRank(key, start, stop);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * ZREMRANGEBYSCORE key min max
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
     * 时间复杂度:O(log(N)+M)， N 为有序集的基数，而 M 为被移除成员的数量
     * 时间复杂度:O(log(N)+M)， N 为有序集的基数，而 M 为被移除成员的数量
     *
     * @param key
     * @param min
     * @param max
     * @return 被移除成员的数量
     */
    public Long zremrangeByScore(String key, double min, double max) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.zremrangeByScore(key, min, max);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * getShard
     *
     * @param key
     * @return
     */
    public Jedis getShard(String key) {
        ShardedJedis shardedJedis = getRedisClient();
        Jedis result = null;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.getShard(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * getShardInfo
     *
     * @param key
     * @return
     */
    public JedisShardInfo getShardInfo(String key) {
        ShardedJedis shardedJedis = getRedisClient();
        JedisShardInfo result = null;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.getShardInfo(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * getKeyTag
     *
     * @param key
     * @return
     */
    public String getKeyTag(String key) {
        ShardedJedis shardedJedis = getRedisClient();
        String result = null;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.getKeyTag(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * getAllShardInfo
     *
     * @return
     */
    public Collection<JedisShardInfo> getAllShardInfo() {
        ShardedJedis shardedJedis = getRedisClient();
        Collection<JedisShardInfo> result = null;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.getAllShardInfo();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * getAllShards
     *
     * @return
     */
    public Collection<Jedis> getAllShards() {
        ShardedJedis shardedJedis = getRedisClient();
        Collection<Jedis> result = null;
        if (shardedJedis == null) {
            return result;
        }

        try {
            result = shardedJedis.getAllShards();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

}
