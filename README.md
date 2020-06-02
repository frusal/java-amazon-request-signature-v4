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

<details><summary>Example running trace</summary>

```http
POST https://route53.amazonaws.com/2013-04-01/hostedzone/Z08118721NNU878C4PBNA/rrset
Host: route53.amazonaws.com
X-Amz-Content-Sha256: 46c7521da55bcf9e99fa6e12ec83997fab53128b5df0fb12018a6b76fb2bf891
X-Amz-Date: 20200602T035618Z
Authorization: AWS4-HMAC-SHA256 Credential=AKIAJTOUYS27JPVRDUYQ/20200602/us-east-1/route53/aws4_request, SignedHeaders=host;x-amz-content-sha256;x-amz-date, Signature=6a59090f837cf71fa228d2650e9b82e9769e0ec13e9864e40bd2f81c682ef8cb
Content-Type: text/xml; charset=utf-8
<?xml version="1.0" encoding="UTF-8"?>
<ChangeResourceRecordSetsRequest xmlns="https://route53.amazonaws.com/doc/2013-04-01/">
<ChangeBatch>
   <Changes>
      <Change>
         <Action>UPSERT</Action>
         <ResourceRecordSet>
            <Name>c001cxxx.frusal.com.</Name>
            <Type>A</Type>
            <TTL>300</TTL>
            <ResourceRecords>
               <ResourceRecord>
                  <Value>157.245.232.185</Value>
               </ResourceRecord>
            </ResourceRecords>
         </ResourceRecordSet>
      </Change>
   </Changes>
</ChangeBatch>
</ChangeResourceRecordSetsRequest>
connection.getResponseCode()=200
responseContentType=text/xml
Response BODY:
<?xml version="1.0"?>
<ChangeResourceRecordSetsResponse xmlns="https://route53.amazonaws.com/doc/2013-04-01/"><ChangeInfo><Id>/change/C011827119UYGF04GVIP6</Id><Status>PENDING</Status><SubmittedAt>2020-06-02T03:56:25.822Z</SubmittedAt></ChangeInfo></ChangeResourceRecordSetsResponse>
```

</details>

It is a single java file utility class implementation. No other then java core class library dependencies (OpenJDK).

For now, I would suggest you just copy its content straight into your source code, if there will be any demand, I might publish it on [maven central].

Have a look at  "Example" class above as an example of how you can use it with core java to make a real request.

Not sure if you can run it on Android. It might have some dependencies on OpenJDK which Android is missing.

[maven central]: https://mvnrepository.com/repos/central

[AmazonRequestSignatureV4Utils.java]: src/main/java/com/frusal/amazonsig4/AmazonRequestSignatureV4Utils.java
[AmazonRequestSignatureV4Example.java]: src/main/java/com/frusal/amazonsig4/AmazonRequestSignatureV4Example.java
