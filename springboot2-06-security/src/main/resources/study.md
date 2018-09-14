# 使用Spring Security进行安全认证

### 1.引入依赖，这个没话说

~~~xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
</dependency>
~~~



### 2.创建MySecurityConfig配置类，该类继承WebSecurityConfigurerAdapter;
> 这个类主要是继承两个方法
>
> 1. protected void configure(HttpSecurity http)
> 2. protected void configure(AuthenticationManagerBuilder auth)

#### 1.protected void configure(HttpSecurity http)

> 这个方法作用：(同样可以从数据库中读取并进行设置)
>
> 1. 设置资源路径的访问需要的权限
>
> 2. 设置登陆用的登陆界面的一些信息，默认使用的是自带的登陆界面，可以自定义；
>
>    不过自定义时间需要注意处理csrf_token的提交；

~~~java
protected void configure(HttpSecurity http) throws Exception {
        //从数据库中读取url及其对应的权限
        List<UrlAuthBean> urlAuthBeans = setUrlAuthService.getUrlAuthBeanList();
        for (UrlAuthBean urlAuthBean:urlAuthBeans) {
            List<String> list = urlAuthBean.getRols();
            http.authorizeRequests()
                    .antMatchers(urlAuthBean.getUrl())
                    .hasAnyRole(list.toArray(new String[list.size()]));
        }
    	//hasAnyRole会在后续处理中将权限变为 ROLE_ADMIN 后面自定义用户UserDetails时间应该注意
        http.authorizeRequests().antMatchers("/hello").hasAnyRole("ADMIN","VIP");
        http.authorizeRequests().anyRequest().permitAll();

        http.formLogin()
                .permitAll().successForwardUrl(getSuccessUrl())
                .and()
                .logout().permitAll();

    }
~~~

#### 2.protected void configure(AuthenticationManagerBuilder auth)

> 将DaoAuthenticationProvider进行注册；
>
> 同时也可以直接在这个方法中进行注册调用，不使用public DaoAuthenticationProvider authenticationProvider()这个方法进行设置；
>
> 作用：
>
> 1. 设置自定义用户信息CustomUserDetails;
> 2. 设置密码解析器
> 3. 。。。等等

~~~java
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        //这里使用了密码不进行加密验证，正式项目还是必须要用加密验证方式
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
~~~

### 3.创建CustomUserDetails

> 实现的UserDetailsService接口.
>
> 需要创建UserRoleBean类实现UserDetails(用来存储角色信息，和数据库存储用的实体类不一样)
>
> 作用：
>
> 1. 实现UserDetails loadUserByUsername(String username)方法，返回UserDetail实例，并对用户是否在数据库中进行判断

~~~java
@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository
                .findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("can not found username " + username);
        }
        return new UserRoleBean(userEntity);
    }
}
~~~

### 4.创建UserRoleBean

> 实现UserDetails接口，实现其中的方法；（按需要实现，其中username，password，和Authority是必须要的）
>
> CustomUserService调用这个类

~~~java
public class UserRoleBean implements UserDetails {
    private UserEntity userEntity;
    private List<String> roles;
    public UserRoleBean(UserEntity userEntity) {
        this.userEntity = userEntity;
        this.roles = userEntity.getRoles();
    }
    /**
     * 设置对象集合
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        for (String role:roles) {
            //对应后续权限认证比对，数据库中存储的是  ADMIN等等
            list.add(new SimpleGrantedAuthority("ROLE_"+role));
        }
        return list;
    }
    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }
    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
~~~

