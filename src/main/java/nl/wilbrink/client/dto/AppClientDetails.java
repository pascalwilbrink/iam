package nl.wilbrink.client.dto;

import nl.wilbrink.client.entity.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class AppClientDetails implements ClientDetails {

    private Client client;

    public AppClientDetails(Client client) {
        this.client = client;
    }

    @Override
    public String getClientId() {
        return client.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        Set<String> resourceIds = new HashSet<>();

        return resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return false;
    }

    @Override
    public String getClientSecret() {
        return client.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        Set<String> scope = new HashSet<>();

        return scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> grantTypes = new HashSet<>();

        grantTypes.add("password");
        grantTypes.add("refresh_token");

        return grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return new HashSet<>();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority("Client"));

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
    public boolean isAutoApprove(String clientId) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return new HashMap<>();
    }
}
