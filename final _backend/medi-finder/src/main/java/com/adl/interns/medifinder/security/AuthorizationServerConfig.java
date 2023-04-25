package com.adl.interns.medifinder.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private String clientid = "medi_finder";
    private String clientSecret = "{noop}secret";
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEA2pP9L63X6Qb/3rCTr5O3Y2cNP6NWGPQ9ljSOsTFW/ARZoP12\n" +
            "GRv6FiE1/Y0xdULKSBjjTSvaC3X9WdjtL7YOeJdXCJBziVsAAbN3Nuvfk7Laxu+h\n" +
            "q4eY8jIPheBiaMOD7ZoLHBKj5eIdVDNiosaS5958JQwaVQKOamso+i2QS8NqbOn/\n" +
            "6nCmGmCu8FJqjm0rgGFjqnCRp208DcQZNBxSvStL9bLHE8hU+HPJbUaAwC4GDPFW\n" +
            "0/WHB0SO2DbgfkRYztKdu/t1fJkbWQjeS/2Qiek6cOmcDbHIIEF3WGhPDfE1dv8/\n" +
            "uiY6EA1zLWcfAE+J+ym9jjY6/30AsvRIVKdEZQIDAQABAoIBAHGX1U3RD9NH0vPd\n" +
            "WnM11mmvZaA794Oycss79yg5FxaUACx9bMGx229ixRdHKHvjQRLk9EPn1UJ9iDQI\n" +
            "W4OfHrgnFpEZ2SF6DG/V38/IF4I/whj+8ksdhivgPlb5/MqrSUXS95EYUtsmfs2C\n" +
            "gvFLN4+O/X+XYZvqXy8JD9uskD/+kdE0ySj0tAsWOWPuWdAsK111uZRlx5b0kT/5\n" +
            "SyVEV3i6/yEk+UkIick+wIp+BxRR88pWE2V2LBD4tekMZ2cdkxshMjhYFP+f0X81\n" +
            "69nuNaBK9yx4zT3cy1YxTXOQxiS9+3rukpqlVoE+ef0TQwzPBt17yx7/XseXqrzT\n" +
            "bq50iqUCgYEA7kwed4cWKJCbpzw2Lh3KK/zR6nM1FzbjQwVquDq+lBsebFJV+C9D\n" +
            "vPjuxfL8s0BYvG5wI//fZYq/cogvpyqaJrd0tPjciBiI93SC0nrCWcOTxnfbHoWF\n" +
            "WSXfUYtTqifxiYMfPCZlmPsSe+jGsNImLevLzzxnjA4kx5cOEjC2P9cCgYEA6tDa\n" +
            "m5wBYFaxpExk1MNFlKQ1+7qjaD6VgFiB0tATOrZyObDQ6QjWnwj6iJsT83/e2PoI\n" +
            "UoNwZf89In4tJexUeEYZ8Y9O5ta78OD83zlLg0XG4vTPXrt8z+L+wuqvmKSTRsaD\n" +
            "bF5k6dIszDPg2izLAlNmb1bv9cMjXZEyzKn+hiMCgYBKiEBXPkct9NzaV7jSf7rG\n" +
            "O1Mks1f9pMs8ISuZ57XH+Rtw4efFsnLLXkjAXRmwrwWSGjC08cjbp6PtnK7a6DZC\n" +
            "ucqk99lyL8pXK3fngUHcdZF41/APAdANbA0HIj7Oz6N6USqp05CfYpgLbWkH0RLj\n" +
            "9gZPESOJ6LqUOqjKA7ZdDQKBgGJ6NsQ9+AHMOfH6q+ri/iJpf85TpsZIQPCzrbaY\n" +
            "1wevjlYNhn/E2iWWYWO7DfH39aCvs5/PSess0RnPKuYVqBNM6wJ4pJGBCw7pfIOt\n" +
            "hrLGJ7P1EabcIGsa+tcKD5yHoNB03pcrO00ofOsi8rDtSjJuga4+GqLKNOG/zDsz\n" +
            "K/BbAoGAVUqthFitYmd4v9pGJN6FAqZ1QpQYkMtO6drlykilz8Po+d9zpkESLKOi\n" +
            "Wuf46uJzyQSPYFzHq1nvPFFGkcaVL3CH4uGHq6e9dLhkQc7qMLzptWQ+gLn1Txjr\n" +
            "Tshrr362pVdTkxu/8oejCNlbV+ZUX0uCGTPeaVXEi7e+5CcbUi0=\n" +
            "-----END RSA PRIVATE KEY-----";
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2pP9L63X6Qb/3rCTr5O3\n" +
            "Y2cNP6NWGPQ9ljSOsTFW/ARZoP12GRv6FiE1/Y0xdULKSBjjTSvaC3X9WdjtL7YO\n" +
            "eJdXCJBziVsAAbN3Nuvfk7Laxu+hq4eY8jIPheBiaMOD7ZoLHBKj5eIdVDNiosaS\n" +
            "5958JQwaVQKOamso+i2QS8NqbOn/6nCmGmCu8FJqjm0rgGFjqnCRp208DcQZNBxS\n" +
            "vStL9bLHE8hU+HPJbUaAwC4GDPFW0/WHB0SO2DbgfkRYztKdu/t1fJkbWQjeS/2Q\n" +
            "iek6cOmcDbHIIEF3WGhPDfE1dv8/uiY6EA1zLWcfAE+J+ym9jjY6/30AsvRIVKdE\n" +
            "ZQIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientid).secret(clientSecret).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000);

    }

}
