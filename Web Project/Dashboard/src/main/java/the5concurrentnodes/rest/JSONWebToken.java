package the5concurrentnodes.rest;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWebToken {

    private RsaJsonWebKey rsaJsonWebKey = null;
    private static JSONWebToken instance;


    private JSONWebToken(){}

    public static JSONWebToken getInstance(){

        if(instance == null) {

            instance = new JSONWebToken();

        }

        return instance;
    }

    private RsaJsonWebKey getRsaJsonWebKey() {

        if(rsaJsonWebKey == null) {

            try {
                 rsaJsonWebKey =  RsaJwkGenerator.generateJwk(2048);
            }catch(JoseException e){}
        }

        return rsaJsonWebKey;
    }

    private JwtClaims getClaims(int uId, String diD, String role, boolean expire) {

        JwtClaims claims = new JwtClaims();
        claims.setIssuer(Constants.KEY_JWT_ISSUER);  // who creates the token and signs it
        claims.setAudience(Constants.KEY_JWT_AUDIENCE); // to whom the token is intended to be sent
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setSubject(Constants.KEY_JWT_SUBJECT); // the subject/principal is whom the token is about
        claims.setClaim(Constants.KEY_JWT_USER_ID, String.valueOf(uId));
        claims.setClaim(Constants.KEY_JWT_DEVICE_ID, diD);
        claims.setClaim(Constants.KEY_JWT_ROLE, role);

        if(expire) {

            claims.setExpirationTimeMinutesInTheFuture(3);
        }



        return claims;
    }

    public String createJWT(int uId, String diD, String role, boolean expire) {

        JsonWebSignature jsonWebSignature = new JsonWebSignature();
        jsonWebSignature.setPayload(getClaims(uId, diD, role, expire).toJson());
        jsonWebSignature.setKey(getRsaJsonWebKey().getPrivateKey());
        jsonWebSignature.setKeyIdHeaderValue(getRsaJsonWebKey().getKeyId());
        jsonWebSignature.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        String jsonWebToken = null;
        try {

            jsonWebToken = jsonWebSignature.getCompactSerialization();
        }catch(JoseException e) {}

        return jsonWebToken;
    }

    public JSONObject validateToken(String token) {

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireSubject()
                .setExpectedIssuer(Constants.KEY_JWT_ISSUER)
                .setExpectedAudience(Constants.KEY_JWT_AUDIENCE)
                .setVerificationKey(getRsaJsonWebKey().getKey())
                .build();

        JSONObject jsonObject = null;

        try{

            JwtClaims jwtClaims = jwtConsumer.processToClaims(token);

            try{

                jsonObject = new JSONObject();
                jsonObject.put(Constants.KEY_JWT_USER_ID, jwtClaims.getClaimValue(Constants.KEY_JWT_USER_ID));
                jsonObject.put(Constants.KEY_JWT_DEVICE_ID, jwtClaims.getClaimValue(Constants.KEY_JWT_DEVICE_ID));
                jsonObject.put(Constants.KEY_JWT_ROLE, jwtClaims.getClaimValue(Constants.KEY_JWT_ROLE));

            }catch(JSONException e){}

        }catch (InvalidJwtException e) {}

        return jsonObject;
    }
}
