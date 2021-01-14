# eGovFramewokr 기반의 Redis CF Java Sample App 

## 1. Framework
- eGovFramework 3.9.0
- maven 3.6.3
- tomcat 9.0.37


## 2. 특징
1) Spring에서 Redis를 사용하기위한 라이브러리
- lettuce
- jedis

>  jedis는 Multi Thread 환경에서 thread-safe 하지 않기 때문에 jedis-pool을 사용해야한다. 그러나 jedis-pool은 대기 비용이 증가하기 때문에 본 어플리케이션에서는 lettuce를 사용한다.

2) RedisTemplate을 통한 Redis 접근

- 메서드 종류
  - opsForValue	: Strings를 쉽게 Serialize / Deserialize 해주는 Interface
  - opsForList  : List를 쉽게 Serialize / Deserialize 해주는 Interface
  - opsForSet	: Set를 쉽게 Serialize / Deserialize 해주는 Interface
  - opsForZSet	: ZSet를 쉽게 Serialize / Deserialize 해주는 Interface
  - opsForHash	: Hash를 쉽게 Serialize / Deserialize 해주는 Interface

> 본 어플리케이션에서는 String 타입의 데이터로만 테스트하였다.(opsForValue)



## 사용법(API Endpoint)
- GET /
> main 페이지로 이동

- POST /keys?kn={kn:.+}&kv={kv:.+}
> Key, Value를 저장.
 >> Param1 : kn(key 이름)  
 >> Param2 : kv(key value)

- GET /{key:.+}
> value 값을 조회하는 페이지로 이동
 >> Param1 : kn(key 이름)

- GET /keys/{key:.+}
> value 값을 조회
 >> Param1 : kn(key 이름)  