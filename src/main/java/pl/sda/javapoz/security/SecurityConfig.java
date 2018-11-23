package pl.sda.javapoz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import pl.sda.javapoz.service.CustomUserDetailsService;
import pl.sda.javapoz.service.FacebookConnectionSignup;
import pl.sda.javapoz.service.UserService;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private ConnectionFactoryLocator connectionFactoryLocator;
    private UsersConnectionRepository usersConnectionRepository;
    private FacebookConnectionSignup facebookConnectionSignup;
    private UserService userService;

    @Autowired
    public SecurityConfig(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository, FacebookConnectionSignup facebookConnectionSignup, UserService userService) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.usersConnectionRepository = usersConnectionRepository;
        this.facebookConnectionSignup = facebookConnectionSignup;
        this.userService = userService;
    }

    @Bean
    public CustomLogoutHandler customLogoutHandler() {
        return new CustomLogoutHandler();
    }


    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**", "/static**").permitAll()
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/linkForm").hasRole("ADMIN")
                .antMatchers("/shop", "/beans","/").permitAll()
                .antMatchers("/register","/news/**").permitAll()
                .antMatchers("/contact","/product/**").permitAll()
                .antMatchers("/login*", "/signin/**", "/signup/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").successForwardUrl("/")
                .and()
                .logout()
                .addLogoutHandler(customLogoutHandler())
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }



    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**");
    }

    @Bean
    public ProviderSignInController providerSignInController() {
        ((InMemoryUsersConnectionRepository) usersConnectionRepository)
                .setConnectionSignUp(facebookConnectionSignup);

        return new ProviderSignInController(
                connectionFactoryLocator,
                usersConnectionRepository,
                new FacebookSignInAdapter(userService));
    }

}
