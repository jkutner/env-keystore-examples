import com.github.jkutner.EnvKeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;

public class TrustStoreExample {
  public static void main(String[] args) throws Exception {
    registerCertificate("TRUSTED_CERT");

    String urlStr = "https://ssl.selfsigned.xyz";
    URL url = new URL(urlStr);
    HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
    conn.setDoInput(true);
    conn.setRequestMethod("GET");
    conn.getInputStream().close();
  }

  private static void registerCertificate(String trustedCert)
      throws NoSuchAlgorithmException, KeyStoreException, IOException,
      CertificateException, KeyManagementException {
    KeyStore ts = EnvKeyStore.create(trustedCert).keyStore();

    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
    tmf.init(ts);

    SSLContext sc = SSLContext.getInstance("TLSv1.2");
    sc.init(null, tmf.getTrustManagers(), new SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
  }
}