package com.limonnana.be01.config;


import com.limonnana.be01.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProviderImp implements  CustomAuthenticationProvider{


    private final UserService userService;

    public CustomAuthenticationProviderImp(@Lazy UserService userService) {
        this.userService = userService;
    }
/*
    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {


        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

     //  if (shouldAuthenticateAgainstThirdPartySystem()) {

            // use the credentials
            // and authenticate against the third-party system
            return new UsernamePasswordAuthenticationToken(
               name, password, new ArrayList<>());
      //  } else {
      //      return null;
     //  }
    }
*/


    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();
        com.limonnana.be01.entity.User u = new com.limonnana.be01.entity.User();
        u.setUsername(name);
        u.setPassword(password);
        if (userService.userLogin(u).isPresent()) {
            final List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            final UserDetails principal = new User(name, password, grantedAuths);
            final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
            return auth;
        } else {
            return null;
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

