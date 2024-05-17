package hotel.hotelsearch.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import hotel.hotelsearch.user.model.myUser;
import hotel.hotelsearch.user.repository.userRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

  @Autowired
  private jwtService jwtservice;
  @Autowired
  private userRepository userRepository;
  private int apiCalled = 0;

  @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }


        String jwt = authHeader.split(" ")[1];

        String refreshedJwt = jwtservice.refreshToken(jwt);
        if (!refreshedJwt.equals(jwt)) {
            
            response.setHeader("Authorization", "Bearer " + refreshedJwt);
        }

        
        String username = jwtservice.extractUsername(refreshedJwt);
        myUser user = userRepository.findUserByUsername(username).orElse(null);
        
        if (user != null) {

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        System.err.println("["+ apiCalled + "]Api called by user: " + username);  
        apiCalled++;
        filterChain.doFilter(request, response);
    }

}
