/*
package me.parkdonggyu.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.parkdonggyu.springbootdeveloper.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

// 시큐리티 설정을 위한 파일
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {


    private final UserDetailService userService;


    // 스프링 시큐리티의 기능을 모든 경우에 사용하지 않게 비활성화. 일반적으로 정적 리소스에 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console()) // h2데이터베이스 데이터를 확인하는 콘솔에 시큐리티 미사용
                .requestMatchers(new AntPathRequestMatcher("/static/**")); // static 하위 경로에 있는 파일들에 시큐리티 미사용
    }


    // 특정 HTTP 요청에 대한 보안을 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> auth // 인증,인가 설정
                        .requestMatchers( // requestMatchers : 특정 요청과 일치하는 url에 대한 엑세스 설정
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/user")
                        ).permitAll() // permitAll : /login, /signup, /user로 요청이 오면 인증,인가 없이 접근할 수 있게 설정
                        .anyRequest().authenticated()) // anyRequest : 위에서 설정 URL 이외의 요청에 대한 설정, authenticated : 인가는 필요X but 인증은 되어야 접근 가능(article도 로그인 해야만 볼 수 있음)
                .formLogin(formLogin -> formLogin // 폼 기반 로그인 설정
                        .loginPage("/login") // loginPage : 로그인 페이지 경로 설정
                        .defaultSuccessUrl("/articles") // defaultSuccessUrl : 로그인 완료 후 이동할 경로 설정
                )
                .logout(logout -> logout // 로그아웃 설정
                        .logoutSuccessUrl("/login") // logoutSuccessUrl : 로그아웃이 완료되었을 때 이동할 경로 설정
                        .invalidateHttpSession(true) // invalidateHttpSession : 로그아웃 이후 세션을 삭제할지 여부 설정
                )
                .csrf(AbstractHttpConfigurer::disable) // csrf 설정을 비활성화. 실습 편의를 위해
                .build();
    }

    // 인증 관리자 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService); // 사용자 정보를 가져올 서비스 설정, 서비스는 반드시 UserDetailsService를 상속받는 클래스여야함
        authProvider.setPasswordEncoder(bCryptPasswordEncoder); // passwordEncoder는 비밀번호 암호화 인코더 설정
        return new ProviderManager(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}*/
