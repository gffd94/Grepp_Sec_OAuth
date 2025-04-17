package io.gffd94.security_oauth.app;

import io.gffd94.security_oauth.dao.MemberRepository;
import io.gffd94.security_oauth.domain.Member;
import io.gffd94.security_oauth.dto.MemberDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 어떤 플랫폼에서 사용하는지 플랫폼
        /*  security:
            oauth2:
            client:
            registration: 
            여기에 해당하는 정보가 담겨있다
        * */

        // 로그인한 사람의 정보
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User = {}", oAuth2User);

        String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        MemberDetails memberDetails = MemberDetailsFactory.memberDetails(provider, oAuth2User);

        //DB에서 가입된 회원인지 확인이 필요!(기준 - 이메일)
        Optional<Member> memberOptional = memberRepository.findByEmail(memberDetails.getEmail());

        // DB에 회원 정보가 없다면 회원가입을 시킨다.
        Member findMember = memberOptional.orElseGet(
                () -> {
                    Member saved = Member.builder()
                            .name(memberDetails.getName())
                            .email(memberDetails.getEmail())
                            .provider(provider)
                            .build();

                    return memberRepository.save(saved);
                });


        if(findMember.getProvider().equals(provider)){
            return memberDetails.setRole(findMember.getRole());
        }else {
            throw new RuntimeException();
        }


    }
}
