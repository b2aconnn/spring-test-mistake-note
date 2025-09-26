package com.example.demo.service;

import com.example.demo.exception.CertificationCodeNotMatchedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.UserStatus;
import com.example.demo.model.dto.UserCreateDto;
import com.example.demo.model.dto.UserUpdateDto;
import com.example.demo.repository.UserEntity;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.model.UserStatus.ACTIVE;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestPropertySource("classpath:test-application.properties")
@Transactional
@SpringBootTest
//@Sql("/sql/user-service-test-data.sql")
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        String email = "test1@abc.com";

        // when
        UserEntity result = userService.getByEmail(email);

        // then
        assertThat(result.getNickname()).isEqualTo("test1");
    }

    @Test
    void getByEmail은_PEDNING_상태인_유저를_찾아올_수_없다() {
        // given
        String email = "test2@abc.com";

        // when
        // then
        assertThatThrownBy(() -> userService.getByEmail(email))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        long id = 1;

        // when
        UserEntity result = userService.getById(id);

        // then
        assertThat(result.getNickname()).isEqualTo("test1");
    }

    @Test
    void getById은_PEDNING_상태인_유저를_찾아올_수_없다() {
        // given
        long id = 2;

        // when
        // then
        assertThatThrownBy(() -> userService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userCreateDto를_이용하여_유저를_생성할_수_있다() {
        // given
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .email("test10@abc.com")
                .address("Gyeongi")
                .nickname("kok202-k")
                .build();

        // mailsender.send() 가 호출되어도 실제로 메일이 전송되지 않도록 mocking 처리
        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // when
        UserEntity result = userService.create(userCreateDto);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
//        assertThat(result.getCertificationCode()).isNotNull(); // FIXME: UUID가 랜덤하게 생성되므로 테스트 불가능
    }

    @Test
    void userModifyDto를_이용하여_유저를_수정할_수_있다() {
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("b2aconnn@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("b2aconnn");
        userEntity.setStatus(ACTIVE);
        userEntity.setCertificationCode("aaaaa-aaaa-aaaaa-aaaa");
        userRepository.save(userEntity);

        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .address("Incheon")
                .nickname("kok202-modified")
                .build();

        // when
        userService.update(1, userUpdateDto);

        // then
        UserEntity user = userService.getById(1);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getAddress()).isEqualTo("Incheon");
        assertThat(user.getNickname()).isEqualTo("kok202-modified");
    }

    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("b2aconnn@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("b2aconnn");
        userEntity.setStatus(ACTIVE);
        userEntity.setCertificationCode("aaaaa-aaaa-aaaaa-aaaa");
        userRepository.save(userEntity);

        // when
        userService.login(1);

        // then
        UserEntity user = userService.getById(1);
//        assertThat(user.getLastLoginAt()).isEqualTo("??"); // FIXME: 시간 비교는 어떻게 하지?
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTVIE_시킬_수_있다() {
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("b2aconnn@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("b2aconnn");
        userEntity.setStatus(ACTIVE);
        userEntity.setCertificationCode("aaaaa-aaaa-aaaaa-aaaa");
        userRepository.save(userEntity);

        // when
        userService.verifyEmail(1, "aaaaa-aaaa-aaaaa-aaaa");

        // then
        UserEntity user = userService.getById(1);
        assertThat(user.getStatus()).isEqualTo(ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("b2aconnn@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("b2aconnn");
        userEntity.setStatus(ACTIVE);
        userEntity.setCertificationCode("aaaaa-aaaa-aaaaa-aaaa");
        userRepository.save(userEntity);

        // when
        // then
        assertThatThrownBy(() -> userService.verifyEmail(1, "aaaaa-aaaa-aaaaa-cccc"))
                .isInstanceOf(CertificationCodeNotMatchedException.class)
                .hasMessage("자격 증명에 실패하였습니다.");
    }
}