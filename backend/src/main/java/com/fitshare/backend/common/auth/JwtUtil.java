package com.fitshare.backend.common.auth;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
@NoArgsConstructor
public class JwtUtil {

    // 현재 접속해 있는 사용자의 아이디를 반환하는 메소드
    public static Optional<Long> getCurrentId(){
        // Security Context에 Authentication 객체가 저장되는 시점은
        // JwtFilter의 doFilter메소드에서 Request가 들어올 때 SecurityContext에 Authentication 객체를 저장해서 사용
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            log.debug("Security Context에 인증 정보가 없습니다");
            return Optional.empty();
        }

        Long id = null;
        if(authentication.getPrincipal() instanceof UserDetails){
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            id = Long.valueOf(springSecurityUser.getUsername());
        }else if(authentication.getPrincipal() instanceof Long){
            id = (Long) authentication.getPrincipal();
        }

        return Optional.ofNullable(id);
    }


}
