package com.panfutov.notes.service;

import com.auth0.jwt.JWT;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.panfutov.notes.dto.auth0.UserInfo;
import com.panfutov.notes.entity.User;
import com.panfutov.notes.repository.UserRepository;
import com.panfutov.notes.service.exception.UserInfoCacheException;
import com.panfutov.notes.service.exception.UserNotFoundException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Singleton
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final TokenExtractor tokenExtractor;

    private final LoadingCache<UserCacheKey, UserInfo> cache;

    @Inject
    public UserService(Auth0Service auth0Service, UserRepository userRepository, TokenExtractor tokenExtractor) {
        this.userRepository = userRepository;
        this.tokenExtractor = tokenExtractor;
        this.cache = CacheBuilder.newBuilder()
            .expireAfterWrite(7, TimeUnit.DAYS) // TODO: make adjustable from props
            .build(new UserCacheLoader(userRepository, auth0Service));
    }

    public UserInfo getUserByAuthorizationHeader(String bearer) {
        var token = tokenExtractor.extractBearerToken(bearer);
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token cannot be empty or null");
        }

        return fetchUser(token);
    }

    /**
     * Retrieves user from DB by user info.
     * @param userInfo user info dto object.
     * @throws UserNotFoundException if user such user does not exist.
     * @return User entity instance.
     */
    public User getUserByUserInfo(UserInfo userInfo) {
        return userRepository.findBySub(userInfo.sub())
                .orElseThrow(UserNotFoundException::new);
    }

    private UserInfo fetchUser(String token) {
        final var subject = JWT.decode(token).getSubject();

        try {
            return cache.get(new UserCacheKey(subject, token));
        } catch (ExecutionException e) {
            log.error("Error occurred while getting user info", e);
            throw new UserInfoCacheException(e);
        }
    }
}
