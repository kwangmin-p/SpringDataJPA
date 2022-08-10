package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy();
    List<Member> findTop3By();

    @Query(name = "Member.findByUsername")  // 사실 여기에선 spring data jpa에서는 @Query 를 주석쳐도 된다. findByUsername이라는 namedQuery를 먼저 찾는것이 관계이기 때문이다. NamedQuery가 없다면 메소드 이름으로 쿼리를 생성한다. 즉 우선순위 1.NamedQuery 2.Method명으로 쿼리 생성
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

//    @query 어노테이션에 jpql 을 사용해서 dto로 반환할 경우 패키지경로를 다 적어줘야해서 불편하다.
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findMemberListByUsername(String username); // 컬렉션 조회
    Member findMemberByUsername(String username); // 단건 조회
    Optional<Member> findOptionalMemberByUsername(String username); // 단건 Optional 조회

    @Query(value="select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m") // left outer join 같은 경우는 join 한 결과와 안한 결과의 count 개수가 같으므로 count query를 분리, 지정할 수 있다.
    Page<Member> findByAge(int age, Pageable pageable);
}
