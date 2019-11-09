package application.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

import application.constant.CheckResult;
import application.constant.CommonConstant;

/**
 * 
 * jwt加密和解密的工具类
 */
public class JwtUtils {

    /**
     * 签发JWT
     * @param id
     * @param subject 可以是JSON数据 尽可能少
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)   // 主题
                .setIssuer("yiyuankafei")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate); // 过期时间
        }
        return builder.compact();
    }

    /**
     * 验证JWT
     * @param jwtStr
     * @return
     */
    public static CheckResult validateJWT(String jwtStr) {
        CheckResult checkResult = new CheckResult();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrCode(CommonConstant.RES_CODE_TOKEN_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrCode(CommonConstant.RES_CODE_TOKEN_AUTH_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode(CommonConstant.RES_CODE_TOKEN_AUTH_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     * 生成加密Key
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(CommonConstant.JWT_SECRET);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


    /**
     * 解析JWT字符串
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static void main(String[] args) throws InterruptedException {
         // 后端生成token
        String sc=createJWT("1","jack",CommonConstant.JWT_TTL);
        System.out.println(sc);

        // 后端验证token
        CheckResult checkResult = validateJWT(sc);
        System.out.println(checkResult.isSuccess());
        System.out.println(checkResult.getErrCode());
        Claims claims=checkResult.getClaims();
        System.out.println(claims);
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());

        // 刷新token 重新生成token
        Claims claims2=validateJWT(sc).getClaims();
        String sc2=createJWT(claims2.getId(),claims2.getSubject(),CommonConstant.JWT_TTL);
        System.out.println(sc2);
    }

}
