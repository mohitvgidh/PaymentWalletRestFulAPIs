package AppRun.configs;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import AppRun.services.JwtConfig;
import AppRun.services.UserDetailsDBService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Configuration
public class JwtFilterConfig extends OncePerRequestFilter{
	
	@Autowired
	private JwtConfig jwtcfg;
	
	@Autowired
	private UserDetailsDBService userdetailsdbservice;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String authheader=request.getHeader("Authorization");
		String username=null;
		String jwt=null;
		if(authheader!=null && authheader.startsWith("Bearer "))
		{
				jwt=authheader.substring(7);
				username=jwtcfg.extractUsername(jwt);
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails usrdetails=userdetailsdbservice.loadUserByUsername(username);
			if(jwtcfg.validateToken(jwt, usrdetails))
			{
				UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(usrdetails,null,usrdetails.getAuthorities());
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(token);
			}
			
					
		}
		filterChain.doFilter(request, response);
		
	}

}
