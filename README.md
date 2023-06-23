# CodeReady Dependency Analytics Java API<br/>![latest-no-snapshot][0] ![latest-snapshot][1]

> This project is still a WIP. Currently, only Java's Maven ecosystem is implemented.

The _Crda Java API_ module is deployed to _GitHub Package Registry_.

* Looking for our JavaScript/TypeScript API? Try [Crda JavaScript API](https://github.com/RHEcosystemAppEng/crda-javascript-api).
* Looking for our Backend implementation? Try [Crda Backend](https://github.com/RHEcosystemAppEng/crda-backend).

<details>
<summary>Click here for configuring <em>GHPR</em> and gaining access to the <em>crda-java-api</em> module.</summary>

<h3>Create your token</h3>
<p>
Create a
<a href="https://docs.github.com/en/packages/learn-github-packages/introduction-to-github-packages#authenticating-to-github-packages">token</a>
with the <strong>read:packages</strong> scope<br/>

> Based on
> <a href="https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry">GitHub documentation</a>,
> In <em>Actions</em> you can use <em>GITHUB_TOKEN</em>

</p>

<details>
<summary>Click here for <em>Maven</em> instructions</summary>

<h3>Configure <em>GHPR</em> for <em>Maven</em></h3>
<ol>
<li>Encrypt your token:

```shell
$ mvn --encrypt-password your-ghp-token-goes-here

encrypted-token-will-appear-here
```

</li>
<li>Add a <em>server</em> definition in your <em>$HOME/.m2/settings.xml</em> (note the <em>id</em>):

```xml
<servers>
    ...
    <server>
        <id>github</id>
        <username>github-userid-goes-here</username>
        <password>encrypted-token-goes-here-including-curly-brackets</password>
    </server>
    ...
</servers>
```
</li>
<li> Add a <em>repository</em> definition in your <em>pom.xml</em> (note the <em>id</em>):

```xml
  <repositories>
    ...
    <repository>
      <id>github</id>
      <url>https://maven.pkg.github.com/RHEcosystemAppEng/crda-java-api</url>
      <snapshots>
        <enabled>true</enabled> <!-- omit or set to false if not using snapshots -->
      </snapshots>
    </repository>
    ...
  </repositories>
```

</li>
</ol>
</details>

<details>
<summary>Click here for <em>Gradle</em> instructions</summary>

<h3>Configure GHPR for <em>Gradle</em></h3>
<ol>
<li>Save your token and username as environment variables:
<ul>
<li><em>GITHUB_USERNAME</em></li>
<li><em>GITHUB_TOKEN</em></li>
</ul>
</li>
<li> Add a <em>maven-type repository</em> definition in your <em>build.gradle</em>:

```groovy
repositories {
    ...
    maven {
        url 'https://maven.pkg.github.com/RHEcosystemAppEng/crda-java-api'
        credentials {
            username System.getenv("GITHUB_USERNAME")
            password System.getenv("GITHUB_TOKEN")
        }
    }
    ...
}
```

</li>

</ol>
</details>

</details>

<h3>Usage</h3>
<ol>
<li>Declare the dependency:
<ul>
<li>For <em>Maven</em> in <em>pom.xml</em>:

```xml
<dependency>
    <groupId>com.redhat.crda</groupId>
    <artifactId>crda-java-api</artifactId>
    <version>${crda-java-api.version}</version>
</dependency>
```
</li>

<li>For <em>Gradle</em> in <em>build.gradle</em>:

```groovy
implementation 'com.redhat.crda:crda-java-api:${crda-java-api.version}'
```

</li>
</ul>

</li>
<li>If working with modules, configure module read:

```java
module x { // module-info.java
    requires com.redhat.crda;
}
```
</li>
<li>Code example:

```java
import com.redhat.crda.impl.CrdaApi;
import com.redhat.crda.backend.AnalysisReport;

import java.util.concurrent.CompletableFuture;

public class CrdaExample {
    public static void main(String... args) throws Exception {
        // instantiate the Crda API implementation
        var crdaApi = new CrdaApi();

        // get a byte array future holding a html report
        CompletableFuture<byte[]> htmlReport = crdaApi.stackAnalysisHtmlAsync("/path/to/pom.xml");
        // get a AnalysisReport future holding a deserialized report
        CompletableFuture<AnalysisReport> analysisReport = crdaApi.stackAnalysisAsync("/path/to/pom.xml");
    }
}
```
</li>
</ol>

<h3>Excluding Packages</h3>
<p>
Excluding a package from any analysis can be achieved by marking the package for exclusion.
</p>

<ul>
<li>Java Maven (pom.xml)</li>

```xml
<dependency> <!--crdaignore-->
  <groupId>...</groupId>
  <artifactId>...</artifactId>
  <version>...</version>
</dependency>
```

</ul>

<h3>Tokens</h3>
<p>
For including extra vulnerability data and resolutions, otherwise only available to vendor registered users. You can
set the various vendor tokens as environment variables.

Available token environment variables:
</p>

<table>
<tr>
<th>Vendor</th>
<th>Token Environment Variable</th>
</tr>
<tr>
<td><a href="https://app.snyk.io/redhat/snyk-token">Snyk</a></td>
<td>CRDA_SNYK_TOKEN</td>
</tr>
</table>

<h3>Custom Executables</h3>
<p>
This project uses each ecosystem's executable for creating dependency trees. These executables are expected to be
present on the system PATH. If they are not, or perhaps you want to use custom ones. Use can use the following
environment variables for setting custom paths for the said executables.
</p>

<table>
<tr>
<th>Ecosystem</th>
<th>Default</th>
<th>Environment Variable</th>
</tr>
<tr>
<td><a href="https://maven.apache.org/">Maven</a></td>
<td><em>mvn</em></td>
<td>CRDA_MVN_PATH</td>
</tr>
</table>

<h3>Used By</h3>

<ul>
<li><a href="https://github.com/redhat-developer/intellij-dependency-analytics">IntelliJ Dependency Analytics Plugin</a></li>
</ul>

<!-- Badge links -->
[0]: https://img.shields.io/github/v/release/RHEcosystemAppEng/crda-java-api?color=green&label=latest
[1]: https://img.shields.io/github/v/release/RHEcosystemAppEng/crda-java-api?color=yellow&include_prereleases&label=snapshot
