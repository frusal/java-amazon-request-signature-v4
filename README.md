# java-amazon-request-signature-v4

Java implementation of Amazon Request Signature v4 calculation. Single file and it has no external dependencies.

In a nutshell, it calculates a header like this:

```http
Authorization: AWS4-HMAC-SHA256 Credential=AKIAJTOUYS27JPVRDUYQ/20200602/us-east-1/route53/aws4_request, SignedHeaders=host;x-amz-content-sha256;x-amz-date, Signature=ba85affa19fa4a8735ce952e50d41c8c93406a11d22b88cc98b109b529bcc15e
```

Implements the steps outlined in this AWS document:

- <https://docs.aws.amazon.com/general/latest/gr/signature-version-4.html>

To take home:

- [AmazonRequestSignatureV4Utils.java]
- [AmazonRequestSignatureV4Example.java]

It is a single java file utility class implementation. No other then java core class library dependencies (OpenJDK).

For now, I would suggest you just copy its content straight into your source code, if there will be any demand, I might publish it on [maven central].

Have a look at  "Main" class above as an example of how you can use it with core java to make a real request.

Not sure if you can run it on Android. It might have some dependencies on OpenJDK which Android is missing.

[maven central]: https://mvnrepository.com/repos/central

[AmazonRequestSignatureV4Utils.java]: src/main/java/com/frusal/amazonsig4/AmazonRequestSignatureV4Utils.java
[AmazonRequestSignatureV4Example.java]: src/main/java/com/frusal/amazonsig4/AmazonRequestSignatureV4Example.java
