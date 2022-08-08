package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// test 내에서도 spring bean injection을 위해 spring container가 필요하다. -> SpringBootTest 사용 이유
// 예전에는 RunWith(SpringRunner.class) 를 썼어야하는데 Jnuit5부터는 쓰지 않아도 된다.
@SpringBootTest
@Transactional //test의 Transactional은 테스트 끝나고 rollback 해서 콘솔에 쿼리를 확인 할 수 없다. 또한 디비도 초기화됨. 확인하고싶으면 @Rollback(false) 를 붙인다.
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member); // 하나의 트랜잭션 안에서 영속성 컨텍스트 안에서 같은 인스턴스임을 보장. equalHashcode 를 오버라이드했다면 false 나왔을 것.
    }
}