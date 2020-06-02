package com.frusal.amazonsig4;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AmazonRequestSignatureV4Example {

    public static void main(String[] args) throws Exception {
        String route53HostedZoneId = "Z08118721NNU878C4PBNA";
        String awsIdentity = "AKIAJTOUYS27JPVRDUYQ";
        String awsSecret = "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";
        String awsRegion = "us-east-1";
        String awsService = "route53";

        URL url = new URL("https://route53.amazonaws.com/2013-04-01/hostedzone/" + route53HostedZoneId + "/rrset");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        System.out.println(connection.getRequestMethod() + " " + url);

        String body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ChangeResourceRecordSetsRequest xmlns=\"https://route53.amazonaws.com/doc/2013-04-01/\">\n" +
                "<ChangeBatch>\n" +
                // " <Comment>optional comment about the changes in this change batch request</Comment>\n" +
                "   <Changes>\n" +
                "      <Change>\n" +
                "         <Action>UPSERT</Action>\n" +
                "         <ResourceRecordSet>\n" +
                "            <Name>c001cxxx.frusal.com.</Name>\n" +
                "            <Type>A</Type>\n" +
                "            <TTL>300</TTL>\n" +
                "            <ResourceRecords>\n" +
                "               <ResourceRecord>\n" +
                "                  <Value>157.245.232.185</Value>\n" +
                "               </ResourceRecord>\n" +
                "            </ResourceRecords>\n" +
                // " <HealthCheckId>optional ID of a Route 53 health check</HealthCheckId>\n" +
                "         </ResourceRecordSet>\n" +
                "      </Change>\n" +
                "   </Changes>\n" +
                "</ChangeBatch>\n" +
                "</ChangeResourceRecordSetsRequest>";
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);

        Map<String, String> headers = new LinkedHashMap<>();
        String isoDate = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'").format(ZonedDateTime.now(ZoneOffset.UTC));
        AmazonRequestSignatureV4Utils.calculateAuthorizationHeaders(
                connection.getRequestMethod(),
                connection.getURL().getHost(),
                connection.getURL().getPath(),
                connection.getURL().getQuery(),
                headers,
                bodyBytes,
                isoDate,
                awsIdentity,
                awsSecret,
                awsRegion,
                awsService);

        // Unsigned headers
        headers.put("Content-Type", "text/xml; charset=utf-8"); // I guess it get modified somewhere on the way... Let's just leave it out of the signature.

        // Log headers and body
        System.out.println(headers.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue()).collect(Collectors.joining("\n")));
        System.out.println(body);

        // Send
        headers.forEach((key, val) -> connection.setRequestProperty(key, val));
        connection.setDoOutput(true);
        connection.getOutputStream().write(bodyBytes);
        connection.getOutputStream().flush();

        int responseCode = connection.getResponseCode();
        System.out.println("connection.getResponseCode()=" + responseCode);

        String responseContentType = connection.getHeaderField("Content-Type");
        System.out.println("responseContentType=" + responseContentType);

        System.out.println("Response BODY:");
        if (connection.getErrorStream() != null) {
            System.out.println(new String(connection.getErrorStream().readAllBytes(), StandardCharsets.UTF_8));
        } else {
            System.out.println(new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
        }
    }
}
