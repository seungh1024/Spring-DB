package com.seungh1024.repository;

import com.seungh1024.domain.Member;

public interface MemberRepositoryEx {
    Member save(Member member);
    Member findById(String memberId);
    void update(String memberId, int money);
    void delete(String memberId);
}
