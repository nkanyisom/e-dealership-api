# Build Issue Resolution

## Problem
Your Maven build is failing due to an SSL certificate error:
```
PKIX path building failed: unable to find valid certification path to requested target
```

This prevents Maven from downloading plugins and dependencies from Maven Central.

## Root Cause
This is typically caused by:
1. **Corporate firewall/proxy** intercepting HTTPS traffic
2. **Network security software** (antivirus, corporate VPN, etc.)
3. **Java keystore** missing the Maven Central SSL certificate

## Immediate Workaround

A basic JAR has been created at: `target/e-dealership-api-1.0.0.jar`

However, this JAR won't run standalone as it's missing Spring Boot dependencies.

## Solutions

### Solution 1: Fix SSL Certificate (Recommended)

#### Option A: Add Maven Central certificate to Java keystore

```powershell
# Download the certificate (you may need to export it from your browser)
# Visit https://repo.maven.apache.org/maven2/ in browser
# Click the lock icon -> Certificate -> Details -> Export

# Import to Java keystore
cd "C:\Program Files\Microsoft\jdk-17.0.13.11-hotspot\lib\security"
keytool -import -alias mavencent -file path\to\maven-central.cer -keystore cacerts -storepass changeit
```

#### Option B: Disable SSL verification (NOT RECOMMENDED for production)

Add to `pom.xml`:
```xml
<repositories>
    <repository>
        <id>central</id>
        <url>http://repo1.maven.org/maven2</url>
        <releases><enabled>true</enabled></releases>
    </repository>
</repositories>
<pluginRepositories>
    <pluginRepository>
        <id>central</id>
        <url>http://repo1.maven.org/maven2</url>
        <releases><enabled>true</enabled></releases>
    </pluginRepository>
</pluginRepositories>
```

### Solution 2: Configure Maven to use HTTP (Temporary)

Create/edit `C:\Users\nkmalunga\.m2\settings.xml`:

```xml
<settings>
    <mirrors>
        <mirror>
            <id>central-http</id>
            <mirrorOf>central</mirrorOf>
            <name>Maven Central HTTP</name>
            <url>http://insecure.repo1.maven.org/maven2</url>
        </mirror>
    </mirrors>
</settings>
```

### Solution 3: Use a Different Network

- Try from a different WiFi network (e.g., mobile hotspot)
- Temporarily disable VPN
- Build from outside corporate network

### Solution 4: Work Offline with Cached Dependencies

If you've built this project successfully before on another machine:
1. Copy the `.m2/repository` folder from that machine
2. Build with `-o` (offline mode): `mvn clean package -o`

### Solution 5: Deploy as-is (Current Compiled Code)

Since your code compiles successfully, you can:

1. **Deploy to AWS and let it build there** (AWS has proper certificates)
   - Push to GitHub
   - Use AWS Amplify/CodeBuild to build (they won't have this SSL issue)
   - The `buildspec.yml` is ready

2. **Use Docker** (Docker builds won't have this local SSL issue)
   ```bash
   docker build -t e-dealership-api .
   ```
   The `Dockerfile` will download dependencies during the build inside the container.

## Quick Fix to Test Locally

You can run the app with Maven without building:

```powershell
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-17.0.13.11-hotspot"
mvn spring-boot:run
```

This should work if the Spring Boot plugin is already cached.

## Recommended Next Steps

1. **For AWS Deployment**: Push to GitHub and deploy via AWS Amplify - it will build successfully there
2. **For Local Development**: Fix the SSL certificate issue using Solution 1
3. **Quick Test**: Use Docker to build and run locally

## Test if SSL is the issue

```powershell
# Try to connect to Maven Central
curl https://repo.maven.apache.org/maven2/
```

If this fails with SSL error, it confirms the issue is system-wide, not just Maven.

## Alternative: Use Gradle

If Maven continues to have issues, you could convert to Gradle which might handle SSL differently.

---

**Bottom line**: Your code is fine and compiles successfully. The issue is with Maven accessing the repository due to SSL/network restrictions. The easiest path forward is to deploy via AWS which won't have this local network issue.
