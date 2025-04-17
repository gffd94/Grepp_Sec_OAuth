package io.gffd94.security_oauth.app;

import io.gffd94.security_oauth.dto.MemberDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;


public class MemberDetailsFactory {

    public static MemberDetails memberDetails(String provider , OAuth2User oAuth2User) {

        Map<String, Object> attributes = oAuth2User.getAttributes();

        switch (provider.toUpperCase()){
            case "KAKAO" -> {
                Map<String, String> properties = (Map<String, String>) attributes.get("properties");
                return MemberDetails.builder()
                        .name(properties.get("nickname"))
                        .email(attributes.get("id")+"@kakao.com")
                        .attributes(attributes)
                        .build();
            }

            case "GOOGLE" -> {
                return MemberDetails.builder()
                        .name(attributes.get("name").toString())
                        .email(attributes.get("email").toString())
                        .attributes(attributes)
                        .build();
            }

            case "NAVER" -> {
                Map<String, String> properties = (Map<String, String>) attributes.get("response");
                return MemberDetails.builder()
                        .name(properties.get("name"))
                        .email(properties.get("id")+"@naver.com")
                        .attributes(attributes)
                        .build();
            }
            
            default -> throw new IllegalArgumentException("제원하지 않는 제공자: " + provider);
            
        }

    }



}
