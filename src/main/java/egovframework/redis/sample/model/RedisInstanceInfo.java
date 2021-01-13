package egovframework.redis.sample.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Redis Instance Info Model 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.12
 **/
@Data
public class RedisInstanceInfo implements Serializable {
    private static final long serialVersionUID = -7353484588260422449L;

    private String host;
    private int port;
    private String password;

    public RedisInstanceInfo() {
    }
}
