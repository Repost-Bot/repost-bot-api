package com.pluhin.repostbot.config;

import org.cfg4j.provider.ConfigurationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final Integer YEAR_IN_SECONDS = 31_556_926;

  private final UserDetailsService userDetailsService;
  private final ConfigurationProvider configurationProvider;

  public SecurityConfig(UserDetailsService defaultUserDetailsService,
      ConfigurationProvider configurationProvider) {
    this.userDetailsService = defaultUserDetailsService;
    this.configurationProvider = configurationProvider;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailsService);
    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(4);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .formLogin()
        .loginProcessingUrl("/authenticate")
        .usernameParameter("j_username")
        .passwordParameter("j_password")
        .permitAll()
        .and()
        .logout()
        .logoutUrl("/logout")
        .deleteCookies("JSESSIONID")
        .and()
        .rememberMe()
        .alwaysRemember(true)
        .key(configurationProvider.getProperty("remember.me.key", String.class))
        .tokenValiditySeconds(YEAR_IN_SECONDS)
        .and()
        .authorizeRequests()
        .antMatchers("/queue/post/**").authenticated()
        .antMatchers("/queue/**").permitAll()
        .antMatchers("/users/confirm").permitAll()
        .antMatchers("/**").authenticated();
  }
}
