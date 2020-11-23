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

## JPA 연동
1. 일단 의존성부터 추가 한다.
    ```groovy
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'com.h2database:h2'
    ```
2. `Account`Entity를 만든다.
    유저 정보와 Id, Password, username, role 등을 포함한다. 

3. Spring Security가 User 정보를 읽는 방법을 설정
   ```java
   @Service
   public class AccountService implements UserDetailsService {
   
       @Autowired AccountRepository accountRepository;
   
       @Override
       public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
           Account account = accountRepository.findByUsername(username);
           if (account == null) {
               throw new UsernameNotFoundException(username);
           }
           return User.builder()
                   .username(account.getUsername())
                   .password(account.getPassword())
                   .roles(account.getRole())
                   .build();
       }
   
       public Account createNew(Account account) {
           return accountRepository.save(account);
       }
   }
   ```    
    
    `SecurityConfig`에 다음과 같이 명시적으로 설정해도 되지만 Bean으로 등록하는 것 만으로도 자동으로 읽어간다. 
    ```java
    @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(accountService);
        }
    ```

3. 회원가입하는 URL 혹은 페이지를 만들어서 Account를 등록하면 로그인 할 수 있다.
    
## PasswordEncoder
비밀번호를 저장할때 사용되는 password이다.
다음과 같이 등록하면 되는데 `NoOpPasswordEncoder`은 아무것도 안해주는 인코더이다. 
(실에서는 사용하지 말것, Security 5 이전에 쓰던 방법)
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
}
```

### Security5부터 Password 기본적략이 바뀐 이유
`NoOp`에서 `{인코딩}비밀번호`방식으로 바뀌었다.
바뀐 이유는 기존에 비밀번호는 평문으로 저장되어 있을 수도 있고 다른 인증 알고리즘을 사용하고 싶어할 수도 있기에
앞에 인코딩 방식을 정의해주는 방식으로 갔다.
spring에서 추천하는 방식은 다음과 같다.
```java
@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
```

encoding은 다음과 같이 하면 된다.
```java
public void encodingPassword(PasswordEncoder passwordEncoder) {
    password = passwordEncoder.encode(password);
}
```


