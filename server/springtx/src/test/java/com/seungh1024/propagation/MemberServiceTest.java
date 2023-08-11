package com.seungh1024.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LogRepository logRepository;

    /**
     * memberService    @Transactional : OFF
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON
     */
    @Test
    void outerTxOff_success(){
        //given
        String username = "outerTxOff_success";

        //when
        memberService.joinV1(username);

        //then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService    @Transactional : OFF
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON -> Exception
     */
    @Test
    void outerTxOff_fail(){
        //given
        String username = "로그예외_outerTxOff_success";

        //when
        org.assertj.core.api.Assertions.assertThatThrownBy(()-> memberService.joinV1(username))
                        .isInstanceOf(RuntimeException.class);

        //then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }
    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : OFF
     * logRepository    @Transactional : OFF
     */
    @Test
    void singleTx(){
        //given
        String username = "singleTx_success";

        //when
        memberService.joinV1(username);

        //then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON
     */
    @Test
    void outerTxOn_success(){
        //given
        String username = "outerTxON_success";

        //when
        memberService.joinV1(username);

        //then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON -> Exception 발생
     */
    @Test
    void outerTxOn_fail(){
        //given
        String username = "로그예외_outerTxON_fail";

        //when
        org.assertj.core.api.Assertions.assertThatThrownBy(()-> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        //then
        Assertions.assertTrue(memberRepository.find(username).isEmpty());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON -> Exception 발생
     */
    @Test
    void recoverException_fail(){
        //given
        String username = "로그예외_recoverException_fail";

        //when
        org.assertj.core.api.Assertions.assertThatThrownBy(()-> memberService.joinV2(username))
                .isInstanceOf(UnexpectedRollbackException.class);

        //then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON(REQUIRES_NEW) -> Exception 발생
     */
    @Test
    void recoverException_success(){
        //given
        String username = "로그예외_recoverException_success";

        //when
        memberService.joinV2(username);

        //then member 저장, log 롤백
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }

}