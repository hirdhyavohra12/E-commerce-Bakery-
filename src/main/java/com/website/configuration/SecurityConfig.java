package com.website.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.website.service.CustomUserDetailService;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig{
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
//	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests().requestMatchers("/","/shop/**","/register","/login/**").permitAll()		
		.requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated()
		.and().formLogin().loginPage("/login").permitAll()
		.failureUrl("/login?error=true").defaultSuccessUrl("/shop")
		.usernameParameter("email")
		.passwordParameter("password").and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
		.invalidateHttpSession(true).deleteCookies("JSESSIONID").and().exceptionHandling().and().csrf().disable();
		
		
		httpSecurity.headers().frameOptions().disable();
//		
		return httpSecurity.build();
	}	
	
	@Bean
	protected BCryptPasswordEncoder bcryptEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider daoAuthProvider =new DaoAuthenticationProvider();
		daoAuthProvider.setUserDetailsService(customUserDetailService);
		daoAuthProvider.setPasswordEncoder(bcryptEncoder());
		return daoAuthProvider;
		
	}
	
	 @Bean
	    public WebSecurityCustomizer webSecurityCustomizer() {
	        return (web) -> web.ignoring().requestMatchers("/resources/**","/static/**","/images/**","/productImages/**","/css/**","/js/**");
	    }
}
