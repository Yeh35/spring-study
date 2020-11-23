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
  
## 인메모리 유저 추가
* 이 방법은 좋은 방법은 아니다. (실습을 위한..)
*  properties를 이용한 설정 방법
기본 유저를 등록해주는 `UserDetailsServiceAutoConfiguration`로 가면 
기본 유저에 정보를 `SecurityProperties`를 통해서 가져온다는 걸 알 수 있다.
우리가 Properties를 통해서 인메모리 유저의 Name과 password를 정의해줄 수 있다.
    ```
    spring.security.user.name=admin
    spring.security.user.password=1234
    spring.security.user.roles=ADMIN
    ```
   
*  `SecurityConfig`를 이용한 설정 방법
    위에서 만든 `SecurityConfig` Class에 가서 다음과 같이 설정하면 된다.
    ```java
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication()
               .withUser("sangoh").password("{noop}123").roles("USER")
           .and()
               .withUser("admin").password("{noop}!@#").roles("ADMIN");
   }
    ```

