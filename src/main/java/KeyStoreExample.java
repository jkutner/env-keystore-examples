import com.github.jkutner.EnvKeyStore;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.ssl.SSLContexts;

public class KeyStoreExample {
  public static void main(String[] args) throws Exception {
    EnvKeyStore eks = EnvKeyStore.create("KEYSTORE_KEY", "KEYSTORE_CERT", "KEYSTORE_PASSWORD");
    RatpackServer.start(s -> s
      .serverConfig(c -> {
        c.baseDir(BaseDir.find());
        c.ssl(SSLContexts.sslContext(eks.toInputStream(), eks.password()));
      })
      .handlers(chain -> chain
        .all(ctx -> ctx.render("Hello from Ratpack!"))
      )
    );
  }
}