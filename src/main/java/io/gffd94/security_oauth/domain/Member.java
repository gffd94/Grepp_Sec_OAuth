package io.gffd94.security_oauth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nickname;
    private String email;
    private String role = "USER";

    // 구글 네이버 카카오 구분
    private String provider;

    private LocalDateTime signedAt = LocalDateTime.now();

    @Builder
    public Member(String name, String nickname, String email, String provider) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.provider = provider;
    }


}
