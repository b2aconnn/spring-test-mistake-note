package com.example.demo.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.demo.model.UserStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource("classpath:test-application.properties")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다() {
        // given
        UserEntity userEntity = new UserEntity();
//        userEntity.setId(1L);
        userEntity.setEmail("b2aconnn@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("b2aconnn");
        userEntity.setStatus(ACTIVE);
        userEntity.setCertificationCode("aaaaa-aaaa-aaaaa-aaaa");
        UserEntity saveUserEntity = userRepository.save(userEntity);

        // when
        Optional<UserEntity> result = userRepository.findByIdAndStatus(saveUserEntity.getId(), ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        // given
        UserEntity userEntity = new UserEntity();
//        userEntity.setId(1L);
        userEntity.setEmail("b2aconnn@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("b2aconnn");
        userEntity.setStatus(ACTIVE);
        userEntity.setCertificationCode("aaaaa-aaaa-aaaaa-aaaa");
        UserEntity saveUserEntity = userRepository.save(userEntity);

        // when
        Optional<UserEntity> result = userRepository.findByIdAndStatus(saveUserEntity.getId(), PENDING);

        // then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다() {
        // given
        UserEntity userEntity = new UserEntity();
//        userEntity.setId(1L);
        userEntity.setEmail("b2aconnn@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("b2aconnn");
        userEntity.setStatus(ACTIVE);
        userEntity.setCertificationCode("aaaaa-aaaa-aaaaa-aaaa");
        userRepository.save(userEntity);

        // when
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("b2aconnn@gmail.com", ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByEmailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        // given
        UserEntity userEntity = new UserEntity();
//        userEntity.setId(1L);
        userEntity.setEmail("b2aconnn@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("b2aconnn");
        userEntity.setStatus(ACTIVE);
        userEntity.setCertificationCode("aaaaa-aaaa-aaaaa-aaaa");
        userRepository.save(userEntity);

        // when
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("b2aconnn@gmail.com", PENDING);

        // then
        assertThat(result.isEmpty()).isTrue();
    }
}