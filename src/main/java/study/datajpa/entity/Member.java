package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

// 실제로는 Entity에는 Setter를 가급적 사용하지 않음
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //    jpa는 기본생성자가 있어야 한다. 하지만 사용하지 못하도록 protected로 선언했다. jpa는 프록시 기술을 쓰므로 private 으로 쓸 경우 오류 발생.
@ToString(of = {"id", "username", "age"}) // ToString 에 Team이 있으면 안된다. 무한 loop 에 빠진다.
@NamedQuery(
        name="Member.findByUsername",
        query="select m from Member m where m.username = :username"
)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id") // db column 명 지정
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) // many to one 관계는 기본이 Eager 라서 lazy 로 설정해줘야한다.
    @JoinColumn(name="team_id")
    private Team team;

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username){
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }
    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}
