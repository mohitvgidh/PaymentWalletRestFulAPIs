package AppRun.configs;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import AppRun.services.UserDetailsDBService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtFilterConfig jwtfilter;
	
	@Bean
	public UserDetailsService userdetailsservice()
	{
		/*UserDetails Admin=User.withUsername("mohit").password(encoder.encode("gidh")).roles("ADMIN").build();
		UserDetails User1=User.withUsername("rohit").password(encoder.encode("kul")).roles("USER").build();
		return new InMemoryUserDetailsManager(Admin,User1);*/
		return new UserDetailsDBService();
	}
	
	@Bean
	public PasswordEncoder getEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securitychain(HttpSecurity http) throws Exception
	{
		http.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
		return http.csrf().disable().authorizeHttpRequests().requestMatchers("/get/welcome", "/create","/authenticate","/get/usernameservice/*").permitAll()
		.and().authorizeHttpRequests().requestMatchers("/get/**").authenticated().and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().formLogin().and().build();
		
	}
	@Bean
	public AuthenticationProvider authprovider()
	{
		DaoAuthenticationProvider pd=new DaoAuthenticationProvider();
		pd.setUserDetailsService(userdetailsservice());
		pd.setPasswordEncoder(getEncoder());
		return pd;
	}
	@Bean
	public AuthenticationManager authenticationManagerBean()
	{
		return new ProviderManager(authprovider());
	}


}
