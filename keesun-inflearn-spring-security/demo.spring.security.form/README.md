# Spring Security 폼 인증 구현해보기
[인프런에 백기선님의 Security 강좌](https://www.inflearn.com/course/%EB%B0%B1%EA%B8%B0%EC%84%A0-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0) 를 따라하면서 만든 프로젝트입니다.
* Spring Boot 환경
* 작동방법에 대해서는 설명하지 않는다. (나중에 함)

## Spring Security 연동 
```groovy
implementation 'org.springframework.boot:spring-boot-starter-security'
```
의존성만 추가해줘도 Spring Security 자동설정이 붙는다.
 * 모든 요청은 인증을 필요로 한다.
 * 기본 유자가 생성
    * Username : user
    * password : 실행시 로그에 남음


## Spring Security 설정하기
* java 설정파일 기본 구성은 다음과 같다.
    ```java
    @Configuration
    @EnableWebSecurity
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
    }
    ```
* 필요한 설정을 `WebSecurityConfigurerAdapter`에서 Override해서 사용한다.
    ```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .mvcMatchers("/", "/info").permitAll()
              .mvcMatchers("/admin").hasRole("ADMIN")
              .anyRequest().authenticated()
      ;
    
      http.formLogin();
      http.httpBasic();
    }
    ```