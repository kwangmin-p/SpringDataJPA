package study.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// 실제로는 Entity에는 Setter를 가급적 사용하지 않음
@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;

//    jpa는 기본생성자가 있어야 한다. 하지만 사용하지 못하도록 protected로 선언했다. jpa는 프록시 기술을 쓰므로 private 으로 쓸 경우 오류 방생.
    protected Member(){
    }

    public Member(String username){
        this.username = username;
    }
}
