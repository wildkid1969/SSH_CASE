package com.rollingStones.utils;

import org.apache.http.conn.ssl.TrustStrategy;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class TrustAllStrategy implements TrustStrategy {
    @Override
    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        return true;
    }
}
