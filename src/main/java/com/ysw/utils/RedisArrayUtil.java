package com.ysw.utils;

import java.util.Random;

import redis.clients.jedis.Jedis;

/**
 * 连接工具类，防止redis连接次数过多报错
 */
public class RedisArrayUtil {

    private static Jedis[] jedisArray;
    private static int jedisArraySize;
    private static String jedisHost;
    private static int jedisPort;
    private static Random random = new Random();

    public RedisArrayUtil(int jedisArraySize, String jedisHost, int jedisPort) {
        this.jedisArraySize = jedisArraySize;
        this.jedisHost = jedisHost;
        this.jedisPort = jedisPort;
        jedisPool();
    }

    @SuppressWarnings("resource")
    public static void jedisPool() {
        jedisArray = new Jedis[jedisArraySize];
        for (int i = 0; i < jedisArraySize; i++) {
            Jedis jedis = new Jedis(jedisHost, jedisPort);
            jedis.auth("123456");
            if (jedis.ping().equals("PONG")) {
                jedisArray[i] = jedis;
            } else {
                jedis = new Jedis(jedisHost, jedisPort);
                jedisArray[i] = jedis;
            }
        }
    }

    public Jedis getConnection() {
        int randomCount;
        Jedis jedis;
        while (true) {
            randomCount = random.nextInt(jedisArraySize - 1);
            jedis = jedisArray[randomCount];
            if (jedis.ping().equals("PONG")) {
                break;
            } else {
                delete(randomCount, jedisArray);
                addJedis();
            }
        }
        return jedis;
    }

    //删除数组指定位
    public Jedis[] delete(int index, Jedis array[]) {
        Jedis[] jedisArrayNew = new Jedis[array.length - 1];
        for (int i = 0; i < jedisArrayNew.length - 1; i++) {
            if (i < index) {
                jedisArrayNew[i] = array[i];
            } else {
                jedisArrayNew[i] = array[i + 1];
            }
        }
        return jedisArrayNew;
    }

    public void addJedis() {
        @SuppressWarnings("resource")
        Jedis jedis = new Jedis(jedisHost, jedisPort);
        while (true) {
            if (jedis.ping().equals("PONG")) {
                jedisArray[jedisArraySize] = jedis;
                break;
            }
        }
    }

//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            System.out.println(redisArrayUtil.getConnection());
//        }
//    }

}
