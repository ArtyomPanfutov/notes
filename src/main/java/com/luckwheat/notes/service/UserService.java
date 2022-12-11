package com.luckwheat.notes.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.luckwheat.notes.dto.auth0.UserInfo;
import com.luckwheat.notes.entity.User;
import com.luckwheat.notes.repository.UserRepository;
import com.luckwheat.notes.service.exception.UserInfoCacheException;
import com.luckwheat.notes.service.exception.UserNotFoundException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Singleton
@Slf4j
public class UserService {

    private final Auth0Service auth0Service;

    private final UserRepository userRepository;

    private final TokenExtractor tokenExtractor;

    private final Cache<String, UserInfo> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofHours(1))
            .build();

    @Inject
    public UserService(Auth0Service auth0Service, UserRepository userRepository, TokenExtractor tokenExtractor) {
        this.auth0Service = auth0Service;
        this.userRepository = userRepository;
        this.tokenExtractor = tokenExtractor;
    }

    public UserInfo getUserByBearerToken(String bearer) {
        var token = tokenExtractor.extract(bearer);
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token cannot be empty or null");
        }

        return fetchUserFromAuth0(token);
    }

    public User getUserByUserInfo(UserInfo userInfo) {
        return userRepository.findBySub(userInfo.sub())
                .orElseThrow(UserNotFoundException::new);
    }

    private UserInfo fetchUserFromAuth0(String token) {
        try {
            return cache.get(token, () -> getUserInfo(token));
        } catch (ExecutionException e) {
            log.error("Error occurred while getting user info", e);
            throw new UserInfoCacheException(e);
        }
    }

    private UserInfo getUserInfo(String token) {
       var userInfo = auth0Service.fetchUserInfo(token);

       if (!userRepository.existsBySub(userInfo.sub())) {
           var user = new User();
           user.setSub(userInfo.sub());
           userRepository.save(user);
       }

       return userInfo;
    }

}
