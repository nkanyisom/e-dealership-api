# E-Dealership API - AWS Amplify Deployment Checklist

## Pre-Deployment Checklist

### ✅ 1. Code Quality
- [x] Application compiles successfully
- [x] All controllers have proper error handling
- [x] API documentation is configured (Swagger/OpenAPI)
- [ ] Unit tests written and passing
- [ ] Integration tests written and passing

### ✅ 2. Configuration Files
- [x] `pom.xml` configured with all dependencies
- [x] `application.properties` configured for development
- [x] `application-production.properties` created for production
- [x] Spring Boot Actuator added for health checks
- [x] Proper logging configuration

### ✅ 3. Deployment Files Created
- [x] `Dockerfile` - Multi-stage Docker build
- [x] `.dockerignore` - Optimized Docker builds
- [x] `Procfile` - Elastic Beanstalk process definition
- [x] `.ebextensions/01_setup.config` - EB environment configuration
- [x] `buildspec.yml` - AWS CodeBuild specification
- [x] `amplify.yml` - AWS Amplify build configuration
- [x] `.gitignore` - Git ignore rules

### ⚠️ 4. Database Configuration (IMPORTANT)

#### Current State: H2 In-Memory Database
- ✅ Works for development and testing
- ⚠️ **NOT suitable for production** (data loss on restart)

#### For Production Deployment:

1. **Create RDS Database Instance**
   ```bash
   # Using AWS CLI
   aws rds create-db-instance \
     --db-instance-identifier e-dealership-db \
     --db-instance-class db.t3.micro \
     --engine postgres \
     --master-username admin \
     --master-user-password YOUR_SECURE_PASSWORD \
     --allocated-storage 20
   ```

2. **Add PostgreSQL Driver to pom.xml**
   ```xml
   <dependency>
       <groupId>org.postgresql</groupId>
       <artifactId>postgresql</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```

3. **Update application-production.properties**
   ```properties
   spring.datasource.url=jdbc:postgresql://your-rds-endpoint:5432/dealership_db
   spring.datasource.username=admin
   spring.datasource.password=${RDS_PASSWORD}
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   ```

4. **Set Environment Variables in AWS**
   - `RDS_HOSTNAME`: RDS endpoint
   - `RDS_PORT`: 5432
   - `RDS_DB_NAME`: dealership_db
   - `RDS_USERNAME`: admin
   - `RDS_PASSWORD`: Your secure password
   - `SPRING_PROFILES_ACTIVE`: production

### 🔐 5. Security Considerations

- [ ] Remove H2 console in production (`spring.h2.console.enabled=false`)
- [ ] Use environment variables for sensitive data (never hardcode)
- [ ] Set up AWS Secrets Manager for credentials
- [ ] Configure security groups (RDS should only accept traffic from application)
- [ ] Enable HTTPS/SSL certificate
- [ ] Implement API authentication (Spring Security + JWT)
- [ ] Add CORS configuration if needed
- [ ] Enable SQL injection protection (use parameterized queries)

### 📦 6. Build Verification

Run these commands before deploying:

```powershell
# Set JAVA_HOME
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-17.0.13.11-hotspot"

# Clean build
mvn clean package -DskipTests

# Verify JAR was created
ls target/*.jar

# Test the application locally
java -jar target/e-dealership-api-1.0.0.jar
```

### 🚀 7. Deployment Options

#### Option A: AWS Elastic Beanstalk (Recommended)
```bash
eb init -p corretto-17 e-dealership-api
eb create e-dealership-env
eb deploy
```

#### Option B: Docker + AWS App Runner
```bash
docker build -t e-dealership-api .
# Push to ECR and deploy via App Runner console
```

#### Option C: AWS Amplify + CodeBuild
- Connect GitHub repository to Amplify
- Amplify will use `amplify.yml` automatically
- Configure environment variables in Amplify console

### 📊 8. Post-Deployment Verification

Test these endpoints after deployment:

1. **Health Check**
   ```bash
   curl https://your-app-url/actuator/health
   ```
   Expected: `{"status":"UP"}`

2. **API Test - Get Dealerships**
   ```bash
   curl https://your-app-url/api/dealerships
   ```

3. **Swagger UI**
   Visit: `https://your-app-url/swagger-ui.html`

4. **API Documentation**
   Visit: `https://your-app-url/v3/api-docs`

### 🔍 9. Monitoring & Logging

- [ ] Set up CloudWatch Logs
- [ ] Configure CloudWatch Alarms for:
  - High error rate
  - High response time
  - Memory/CPU usage
- [ ] Enable AWS X-Ray for distributed tracing
- [ ] Set up log aggregation

### 🔄 10. CI/CD Pipeline (Optional but Recommended)

- [ ] Set up GitHub Actions or AWS CodePipeline
- [ ] Automated testing on pull requests
- [ ] Automated deployment on merge to main
- [ ] Blue/Green deployment strategy
- [ ] Automated rollback on failure

### 📝 11. Documentation

- [x] API documentation via Swagger
- [x] Deployment guide created (`DEPLOYMENT.md`)
- [ ] Architecture diagram
- [ ] Runbook for common operations
- [ ] Incident response procedures

### 💰 12. Cost Optimization

- [ ] Use appropriate instance sizes (start with t3.micro)
- [ ] Enable auto-scaling
- [ ] Set up billing alarms
- [ ] Use Reserved Instances for production
- [ ] Implement caching to reduce database calls

---

## Quick Start Commands

### Local Development
```powershell
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-17.0.13.11-hotspot"
mvn spring-boot:run
```

### Build for Production
```powershell
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-17.0.13.11-hotspot"
mvn clean package -DskipTests
```

### Docker Build & Run
```bash
docker build -t e-dealership-api .
docker run -p 9091:9091 e-dealership-api
```

### Deploy to Elastic Beanstalk
```bash
eb init -p corretto-17 e-dealership-api --region us-east-1
eb create e-dealership-prod-env
eb deploy
eb open
```

---

## Important Notes

1. **Current JAR location**: `target/e-dealership-api-1.0.0.jar`
2. **All deployment files are ready** in the project root
3. **Production database setup is REQUIRED** before going live
4. **Environment variables must be set** in AWS console
5. **Test thoroughly** in a staging environment first

---

## Support & Resources

- AWS Documentation: https://docs.aws.amazon.com/
- Spring Boot on AWS: https://spring.io/guides/gs/spring-boot-aws/
- Elastic Beanstalk: https://docs.aws.amazon.com/elasticbeanstalk/
- RDS Setup: https://docs.aws.amazon.com/rds/

---

**Ready to Deploy!** 🚀

Choose your deployment method from the options above and follow the steps in `DEPLOYMENT.md`.
