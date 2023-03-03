package tw.training.homework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.training.homework.exception.ErrorBody;
import tw.training.homework.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token  = request.getHeader("Authorization");
        if (token == null ||token.isEmpty()) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, "Token is empty.");
            return;
        }
        try {
            Map<String, Object> claims = JwtUtils.getTokenClaims(token);
            String username = String.valueOf(claims.get("username"));
            String authority = String.valueOf(claims.get("authority"));
            Authentication auth = new UsernamePasswordAuthenticationToken(username,null,
                    Arrays.asList(() -> authority));
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, "Invalid Token received.");
            return;
        }
        filterChain.doFilter(request, response);
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, String message){
        response.setStatus(status.value());
        response.setContentType("application/json");

        ErrorBody errorBody = new ErrorBody(status.value(), message);
        try {
            String json = new ObjectMapper().writeValueAsString(errorBody);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}