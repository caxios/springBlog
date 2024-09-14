package me.parkdonggyu.springbootdeveloper.config.jwt;
// 토큰 생성, 유효성 검사, 토큰에서 필요한 정보를 가져오는 클래스

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import me.parkdonggyu.springbootdeveloper.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // JWT 생성 메서드
    private String makeToken(Date expiry, User user){

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 타입(typ) : JWT
                .setIssuer(jwtProperties.getIssuer()) // 내용(페이로드,is) : properties 파일에서 설정한 값, ajufresh@gmail.com
                .setIssuedAt(now) // 내용 iat : 현재 시간
                .setExpiration(expiry) // 내용 exp : expiry 멤버 변숫값
                .setSubject(user.getEmail()) // 내용 sub : 유저의 이메일
                .claim("id",user.getId()) // 클레임(키-값 페어로 이뤄진 내용의 단위) id : 유저 ID
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret()) // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .compact(); // 위에서 구성된 JWT 객체를 하나의 JWT 문자열로 변환하는 직렬화 함수
    }

    // JWT 유효성 검증 메서드
    public boolean validToken(String token){
        try{
            Jwts.parser() // JWT 파서(해석) 객체를 생성하고
                    .setSigningKey(jwtProperties.getSecret()) // 서명 검증에 사용할 비밀 키를 설정하고
                    .parseClaimsJws(token); // 토큰의 서명이 설정된 비밀 키로 검증하여 유효하면 claim(내용, 페이로드)에 들어있는 사용자 정보(ID, 이메일 등)를 반환,
            return true;
        }catch (Exception e){ // 유효하지 않아 복호화 과정에서 에러가 나면 false 반환
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);

    }

    // 토큰 기반으로 ID 가져오기
    public Long getUserId(String token){
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    // 토큰 기반으로 클레임(페이로드) 가져오기ㄷ
    public Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }
}
