package org.noahsark.auth.config;

import org.noahsark.constant.CacheConstants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;


public class CustomClientDetailsService extends JdbcClientDetailsService {

    public CustomClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    @Cacheable(value = {CacheConstants.OAUTH_CLIENT_DETAILS_KEY}, key = "#clientId")
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {

        return super.loadClientByClientId(clientId);
    }

    @CachePut(value = {CacheConstants.OAUTH_CLIENT_DETAILS_KEY}, key = "#clientDetails.clientId")
    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        super.addClientDetails(clientDetails);
    }

    @CachePut(value = {CacheConstants.OAUTH_CLIENT_DETAILS_KEY}, key = "#clientDetails.clientId")
    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        super.updateClientDetails(clientDetails);
    }

    @CachePut(value = {CacheConstants.OAUTH_CLIENT_DETAILS_KEY}, key = "#clientId")
    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        super.updateClientSecret(clientId, secret);
    }

    @CacheEvict(value = {CacheConstants.OAUTH_CLIENT_DETAILS_KEY}, key = "#clientId")
    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        super.removeClientDetails(clientId);
    }
}
