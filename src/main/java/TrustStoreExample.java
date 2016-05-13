import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

public class TrustStoreExample {
  public static void main(String[] args) throws Exception {
    String urlStr = "https://ssl.selfsigned.xyz";
    URL url = new URL(urlStr);
    HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
    conn.setDoInput(true);
    conn.setRequestMethod("GET");
    conn.getInputStream().close();
  }
}