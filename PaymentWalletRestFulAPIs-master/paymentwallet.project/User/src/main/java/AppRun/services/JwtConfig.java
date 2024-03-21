package AppRun.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;
import java.util.function.Function;
@Service
public class JwtConfig {
	
	
	private static final String SECRET = "secret";

	public String generateToken(UserDetails userdetails)
	{
		Map<String,Object> claims=new HashMap<>();
		return createToken(claims,userdetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String username) {
		// TODO Auto-generated method stub
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(SignatureAlgorithm.HS256, SECRET)
				.compact();
	}
	private Claims extractAllClaims(String token)
	{
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}
	public <T> T extractClaim(String token,Function<Claims,T> claimsresolver)
	{
		Claims claims=extractAllClaims(token);
		return claimsresolver.apply(claims);
	}
	
	public String extractUsername(String token)
	{
		return extractClaim(token,Claims::getSubject);
	}
	public Date extractExpiration(String token)
	{
		return extractClaim(token,Claims::getExpiration);
	}
	private Boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date(System.currentTimeMillis()));
	}
	public Boolean validateToken(String token,UserDetails userdetails)
	{
        return (extractUsername(token).equals(userdetails.getUsername()) && !isTokenExpired(token));
	}

}
