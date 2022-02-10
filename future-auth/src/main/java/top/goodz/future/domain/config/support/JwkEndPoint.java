package top.goodz.future.domain.config.support;

import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * jwk 接入点
 */
//@FrameworkEndpoint
public class JwkEndPoint {

/*
    @GetMapping("/.well-known/jwks.json")
    public Map<String,Object> getKey(){

        RSAPublicKey publicKey = (RSAPublicKey)this.keyPair.getPublic();

        RSAKey ra = new RSAKey() {
            @Override
            public BigInteger getModulus() {
                return publicKey.getPublicExponent();
            }
        }

        return null;
    }*/

}
