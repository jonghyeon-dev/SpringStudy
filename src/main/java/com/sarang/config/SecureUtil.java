package com.sarang.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SecureUtil {
    private Logger logger = LoggerFactory.getLogger(SecureUtil.class);

    public String encryptSHA256(String text) throws NoSuchAlgorithmException {
        logger.info("SecureSHA256 encryptSHA256");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());

        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] bytes) {
        logger.info("SecureSHA256 bytesToHex");
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public String getClientIP(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    logger.info("> X-FORWARDED-FOR : " + ip);
    if (ip == null) {
        ip = request.getHeader("Proxy-Client-IP");
        logger.info("> Proxy-Client-IP : " + ip);
    }
    if (ip == null) {
        ip = request.getHeader("WL-Proxy-Client-IP");
        logger.info(">  WL-Proxy-Client-IP : " + ip);
    }
    if (ip == null) {
        ip = request.getHeader("HTTP_CLIENT_IP");
        logger.info("> HTTP_CLIENT_IP : " + ip);
    }
    if (ip == null) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        logger.info("> HTTP_X_FORWARDED_FOR : " + ip);
    }
    if (ip == null) {
        ip = request.getRemoteAddr();
        logger.info("> getRemoteAddr : "+ip);
    }
    logger.info("> Result : IP Address : "+ip);

    return ip;
}
}
