package nl.wilbrink.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class IAMClientDetailsService implements ClientDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails clientDetails = new ClientDetails() {

            @Override
            public String getClientId() {
                return clientId;
            }

            @Override
            public Set<String> getResourceIds() {
                return null;
            }

            @Override
            public boolean isSecretRequired() {
                return false;
            }

            @Override
            public String getClientSecret() {
                return passwordEncoder.encode("password");
            }

            @Override
            public boolean isScoped() {
                return false;
            }

            @Override
            public Set<String> getScope() {
                Set<String> scopes = new HashSet<>();
                scopes.add("READ");
                scopes.add("WRITE");

                return scopes;
            }

            @Override
            public Set<String> getAuthorizedGrantTypes() {
                Set<String> grantTypes = new HashSet<>();
                grantTypes.add("password");

                return grantTypes;
            }

            @Override
            public Set<String> getRegisteredRedirectUri() {
                return new HashSet<>();
            }

            @Override
            public Collection<GrantedAuthority> getAuthorities() {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("auth"));
                return authorities;
            }

            @Override
            public Integer getAccessTokenValiditySeconds() {
                return 60000;
            }

            @Override
            public Integer getRefreshTokenValiditySeconds() {
                return 60000;
            }

            @Override
            public boolean isAutoApprove(String s) {
                return false;
            }

            @Override
            public Map<String, Object> getAdditionalInformation() {
                return null;
            }
        };

        return clientDetails;
    }
}
