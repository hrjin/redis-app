# Redis CF Java Sample App For Session Clustering

## 1. Framework
- eGovFramework 3.9.0
- maven 3.6.3
- tomcat 9.0.37


## 2. 특징
1) Spring에서 Redis Session을 사용하기위한 라이브러리
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


## 3. 목적
- 어플리케이션을 Scale-Out하여 세션 클러스터링이 이루어지는 지 확인


## 4. Build
- Build : mvn clean install
- War File :  /target/redis-app.war


## 5. 사용법(API Endpoint)

### cURL

- POST /keys?kn={kn:.+}&kv={kv:.+}
> Key, Value를 저장.
 >> Param1 : kn(key 이름)  
 >> Param2 : kv(key value)


### Browser Access
- GET /
> main 페이지로 이동

- GET /setSession/{key:.+}
> Cookie에 Session ID 저장 및 Session에 value 값을 저장 후 관련 데이터 출력 페이지로 이동
 >> Param1 : kn(key 이름)

- GET /getSession
> 현재 요청이 온 브라우저의 SESSION ID와 Cookie에 저장돼있는 SESSION ID를 비교하여 데이터 조회 페이지로 이동.<br>
> SESSION ID가 일치할 경우 SESSION에서 데이터 값 정상 조회, 불일치일 경우 데이터 조회 실패.
