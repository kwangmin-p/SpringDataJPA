package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy();
    List<Member> findTop3By();

    @Query(name = "Member.findByUsername")  // 사실 여기에선 spring data jpa에서는 @Query 를 주석쳐도 된다. findByUsername이라는 namedQuery를 먼저 찾는것이 관계이기 때문이다. NamedQuery가 없다면 메소드 이름으로 쿼리를 생성한다. 즉 우선순위 1.NamedQuery 2.Method명으로 쿼리 생성
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);
}
