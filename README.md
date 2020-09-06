# Quantxt Theia Java client library


The official [Quantxt][quantxt] Java client library.

Theia is a fully managed document extraction software. User needs to first configure the fields that they want to extract. Theia guarantees correct extraction of content if the fields are configured properly. Fields can be embedded in plain text, within tables or within form documents.

Theia can process documents in various formats including PDF, TIFF, PNG, TXT and Ms Excel. Scanned documents are automatically detected and run through OCR before extraction. 


## Installation

### Requirements

- Java 11 or later


### Maven users

Add this dependency to your project's POM:


```xml
<repositories>
  <repository>
    <id>oss-snapshots</id>
    <name>Sonatype OSS Releases</name>
    <url>https://oss.sonatype.org/content/repositories/releases/</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.quantxt.sdk</groupId>
  <artifactId>qtcurate</artifactId>
  <version>2.2.0</version>
</dependency>
```

[quantxt]: http://quantxt.com

Look at the [Samples](src/main/java/com/quantxt/sdk/sample) for end to end examples

Contact us at <support@quantxt.com> for API key or technical questions.

