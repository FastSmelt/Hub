package gg.pots.data.redis;

import redis.clients.jedis.Jedis;

public class JedisPublisher {

    private Jedis jedis;
    private final JedisHandler jedisManager;

    /**
     * Constructor to initialise the JedisPublisher.
     *
     * @param jedisManager the jedisManager.
     */

    public JedisPublisher(JedisHandler jedisManager) {
        this.jedisManager = jedisManager;
    }

    public void publishData(String message) {
        try {
            this.jedis = this.jedisManager.getJedisPool().getResource();

            if (this.jedisManager.isJedisAuth()) {
                this.jedis.auth(this.jedisManager.getJedisPassword());
            }

            this.jedis.publish(this.jedisManager.getJedisChannel(), message);
        } finally {
            this.jedis.close();
        }
    }
}
