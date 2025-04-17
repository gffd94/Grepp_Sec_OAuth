package io.gffd94.security_oauth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

// 커스텀한 OAuth2User로 사용자가 입력한 데이터를 기반으로 security에 넘겨주는 역할
@Getter
@Accessors(chain = true) // setter가 void -> chain ( chain은 return을 this로 해주기 때문에 가능 )
public class MemberDetails implements OAuth2User {

    private String name;
    private String email;
    private Map<String, Object> attributes;

    @Setter
    private String role;

    // 권한을 넘겨주는 부분
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Builder
    public MemberDetails(String name,String email, Map<String, Object> attributes) {
        this.name = name;
        this.email = email;
        this.attributes = attributes;
    }

}
