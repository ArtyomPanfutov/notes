package com.panfutov.notes.service;

import com.google.common.cache.CacheLoader;
import com.panfutov.notes.dto.auth0.UserInfo;
import com.panfutov.notes.entity.User;
import com.panfutov.notes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserCacheLoader extends CacheLoader<UserCacheKey, UserInfo> {

    private final UserRepository userRepository;

    private final Auth0Service auth0Service;

    public UserCacheLoader(UserRepository userRepository, Auth0Service auth0Service) {
        this.userRepository = userRepository;
        this.auth0Service = auth0Service;
    }

    @Override
    public UserInfo load(UserCacheKey userCacheKey) throws Exception {
        var userFromDb = userRepository.findBySub(userCacheKey.subject());
        if (userFromDb.isPresent()) {
            var user = userFromDb.get();
            return new UserInfo(user.getSub(), user.getEmail());
        }

        log.info("Fetching user info from auth0");
        var userInfo = auth0Service.fetchUserInfo(userCacheKey.accessToken());

        if (!userRepository.existsBySub(userInfo.sub())) {
            saveUser(userInfo);
        }

        return userInfo;
    }

    private void saveUser(UserInfo userInfo) {
        var newUser = new User();
        newUser.setSub(userInfo.sub());
        newUser.setEmail(userInfo.email());
        userRepository.save(newUser);
    }
}

