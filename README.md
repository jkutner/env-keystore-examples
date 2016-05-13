# EnvKeyStore Examples

Examples of [EnvKeyStore](https://github.com/jkutner/env-keystore)

## Usage

Creating a TrustStore:

```sh-session
$ mvn package
$ java -cp target/app.jar TrustStoreExample
Exception in thread "main" javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathB\
uilderException: unable to find valid certification path to requested target
        at sun.security.ssl.Alerts.getSSLException(Alerts.java:192)
        at sun.security.ssl.SSLSocketImpl.fatal(SSLSocketImpl.java:1949)
        at sun.security.ssl.Handshaker.fatalSE(Handshaker.java:302)
        ...

$ git checkout truststore
$ export TRUSTED_CERT="$(curl http://www.selfsigned.xyz/server.crt)"
$ mvn package
$ java -cp target/app.jar TrustStoreExample
```

Creating a KeyStore

```sh-session
$ openssl genrsa -des3 -passout pass:x -out server.pass.key 2048
...
$ openssl rsa -passin pass:x -in server.pass.key -out server.key
writing RSA key
$ rm server.pass.key
$ openssl req -new -key server.key -out server.csr
...
Country Name (2 letter code) [AU]:US
State or Province Name (full name) [Some-State]:California
...
A challenge password []:
...
$ openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
$ export KEYSTORE_KEY="$(cat server.key)"
$ export KEYSTORE_CERT="$(cat server.crt)"
$ export KEYSTORE_PASSWORD="password"
$ mvn package
$ java -cp target/app.jar KeyStoreExample
[main] INFO ratpack.server.RatpackServer - Starting server...
[main] INFO ratpack.server.RatpackServer - Building registry...
[main] INFO ratpack.server.RatpackServer - Ratpack started for https://localhost:5050
```