package stacs.estate.cs5031p3code.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * A util class for operating JSON Web Token.
 *
 * @author 220032952
 * @version 0.0.1
 */
public class JwtUtil {

    /**
     * The expiry date of token.
     * 60 * 60 * 1000 one hour.
     */
    public static final Long JWT_TTL = 60 * 60 * 1000L;

    /**
     * Setting the secret key in plaintext
     */
    public static final String JWT_KEY = "EstateAgencySystem";

    /**
     * The method for getting UUID.
     *
     * @return Return a UUID.
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * Generate JWT.
     *
     * @param subject The data to be stored in the token (in json format).
     * @return Return a JWT.
     */
    public static String createJWT(String subject) {
        var builder = getJwtBuilder(subject, null, getUUID());// set expired time.
        return builder.compact();
    }

    /**
     * Generate JWT.
     *
     * @param subject   The data to be stored in the token (in json format).
     * @param ttlMillis The timeout.
     * @return Return a JWT.
     */
    public static String createJWT(String subject, Long ttlMillis) {
        var builder = getJwtBuilder(subject, ttlMillis, getUUID());// set expired time.
        return builder.compact();
    }

    /**
     * The method for getting JWT builder.
     *
     * @param subject   The storage object.
     * @param ttlMillis The timeout.
     * @param uuid      The UUID.
     * @return Return a JWT builder.
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        var signatureAlgorithm = SignatureAlgorithm.HS256;
        var secretKey = generalKey();
        var nowMillis = System.currentTimeMillis();
        var now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        var expMillis = nowMillis + ttlMillis;
        var expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)              // UUID
                .setSubject(subject)   // JSON data
                .setIssuer("EstateAgencySystem")     // Issuer
                .setIssuedAt(now)      // The time of issuing
                .signWith(signatureAlgorithm, secretKey) // Signed using the HS256 symmetric encryption algorithm, the second parameter is the secret key
                .setExpiration(expDate);
    }

    /**
     * Generate JWT.
     *
     * @param id        The id.
     * @param subject   The data.
     * @param ttlMillis The timeout.
     * @return Return a JWT.
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        var builder = getJwtBuilder(subject, ttlMillis, id);// set expired time.
        return builder.compact();
    }

    /**
     * Generate the encrypted secret key.
     *
     * @return Return the secret key.
     */
    public static SecretKey generalKey() {
        var encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * Parsing the JWT.
     *
     * @param jwt The JWT.
     * @return Return the parsing result.
     * @throws Exception The Exception object.
     */
    public static Claims parseJWT(String jwt) throws Exception {
        var secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}