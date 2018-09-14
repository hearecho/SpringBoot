package com.echo.springboot206security.config;

import com.echo.springboot206security.bean.UrlAuthBean;
import com.echo.springboot206security.bean.UserRoleBean;
import com.echo.springboot206security.repository.UrlRepository;
import com.echo.springboot206security.service.CustomUserService;
import com.echo.springboot206security.service.SetUrlAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
    private String loginPage = "/login";
    private String faileUrl = "/login?error=T";
    private String successUrl = "/home";
    private String applyCheckCode = "/applyCheckCode";

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    CustomUserService customUserService;

    @Autowired
    SetUrlAuthService setUrlAuthService;

    @Autowired
    SessionRegistry sessionRegistry;


    /**
     * 设置对应的url和对应的权限也可以设置从数据库中读取
     * HttpSecurity : 主要配置路径，资源访问权限（是否需要权限，需要什么角色）
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //从数据库中读取url及其对应的权限
        List<UrlAuthBean> urlAuthBeans = setUrlAuthService.getUrlAuthBeanList();
        for (UrlAuthBean urlAuthBean:urlAuthBeans) {
            List<String> list = urlAuthBean.getRols();
            http.authorizeRequests()
                    .antMatchers(urlAuthBean.getUrl())
                    .hasAnyRole(list.toArray(new String[list.size()]));
        }
        http.authorizeRequests().antMatchers("/hello").hasAnyRole("ADMIN","VIP");
        http.authorizeRequests().anyRequest().permitAll();

        http.formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").defaultSuccessUrl(getSuccessUrl())
                .and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry)
                .and().and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .httpBasic()
                .and().csrf().disable();

    }

    /**
     *
     * @return 封装身份认证提供者
     */
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


    @Bean
    public SessionRegistry getSessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }
    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getFaileUrl() {
        return faileUrl;
    }

    public void setFaileUrl(String faileUrl) {
        this.faileUrl = faileUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getApplyCheckCode() {
        return applyCheckCode;
    }

    public void setApplyCheckCode(String applyCheckCode) {
        this.applyCheckCode = applyCheckCode;
    }
}
