package me.parkdonggyu.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.parkdonggyu.springbootdeveloper.domain.RefreshToken;
import me.parkdonggyu.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.sql.Ref;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(()-> new IllegalArgumentException("Unexpected token"));
    }
}
