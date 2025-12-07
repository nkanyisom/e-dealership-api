# E-Dealership API - AWS Deployment Guide

## Deployment Options

This project is packaged and ready for deployment to AWS using multiple methods:

### Option 1: AWS Elastic Beanstalk (Recommended for Spring Boot)

#### Prerequisites
- AWS CLI installed and configured
- EB CLI installed (`pip install awsebcli`)

#### Steps:

1. **Initialize Elastic Beanstalk**
   ```bash
   eb init -p corretto-17 e-dealership-api --region us-east-1
   ```

2. **Create an environment**
   ```bash
   eb create e-dealership-env
   ```

3. **Deploy the application**
   ```bash
   # First, build the JAR
   mvn clean package -DskipTests
   
   # Deploy to Elastic Beanstalk
   eb deploy
   ```

4. **Open the application**
   ```bash
   eb open
   ```

5. **Access Swagger UI**
   ```
   http://your-app.elasticbeanstalk.com/swagger-ui.html
   ```

#### Configuration Files Included:
- `Procfile` - Tells Elastic Beanstalk how to run the application
- `.ebextensions/01_setup.config` - EB configuration for environment variables

---

### Option 2: AWS App Runner (Container-based)

#### Prerequisites
- Docker installed
- AWS CLI configured

#### Steps:

1. **Build Docker image locally (optional testing)**
   ```bash
   docker build -t e-dealership-api .
   docker run -p 9091:9091 e-dealership-api
   ```

2. **Push to Amazon ECR**
   ```bash
   # Create ECR repository
   aws ecr create-repository --repository-name e-dealership-api --region us-east-1
   
   # Login to ECR
   aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin YOUR_AWS_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com
   
   # Tag and push
   docker tag e-dealership-api:latest YOUR_AWS_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/e-dealership-api:latest
   docker push YOUR_AWS_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/e-dealership-api:latest
   ```

3. **Create App Runner service from AWS Console**
   - Go to AWS App Runner
   - Create service from container registry
   - Select your ECR image
   - Configure port: 9091
   - Deploy

---

### Option 3: AWS CodeBuild + AWS Amplify Hosting

While AWS Amplify is primarily for frontend apps, you can use it with CodeBuild for backend APIs.

#### Prerequisites
- GitHub repository connected to AWS Amplify

#### Steps:

1. **Connect your GitHub repository to AWS Amplify**
   - Go to AWS Amplify Console
   - Connect repository
   - Select backend branch

2. **Amplify will automatically detect `amplify.yml`**
   The included `amplify.yml` file configures the build process.

3. **Configure environment variables** (if using RDS):
   - `RDS_HOSTNAME`
   - `RDS_PORT`
   - `RDS_DB_NAME`
   - `RDS_USERNAME`
   - `RDS_PASSWORD`

4. **Deploy**
   Amplify will automatically build and deploy on every git push.

---

### Option 4: AWS CodeBuild + S3 + EC2/ECS

Use `buildspec.yml` for AWS CodeBuild pipelines.

#### Steps:

1. **Create CodeBuild project**
   - Source: Your GitHub repository
   - Environment: Managed image - Amazon Linux 2
   - Runtime: Standard
   - Buildspec: Use `buildspec.yml` from repository

2. **Artifacts**
   - Upload to S3 bucket
   - Deploy to EC2 or ECS

---

## Database Configuration

### Development (Current)
- Uses H2 in-memory database
- Data is lost on restart

### Production (Recommended)

Update `application-production.properties` with RDS credentials:

```properties
spring.datasource.url=jdbc:postgresql://${RDS_HOSTNAME}:${RDS_PORT}/${RDS_DB_NAME}
spring.datasource.username=${RDS_USERNAME}
spring.datasource.password=${RDS_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

Don't forget to add PostgreSQL dependency to `pom.xml`:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## Health Check Endpoint

The application includes Spring Boot Actuator for health monitoring:

- Health: `http://your-app/actuator/health`
- Info: `http://your-app/actuator/info`

---

## API Documentation

Once deployed, access Swagger UI at:
```
http://your-app-url/swagger-ui.html
```

OpenAPI JSON specification:
```
http://your-app-url/v3/api-docs
```

---

## Environment Variables

Set these in your AWS environment:

- `PORT` - Application port (default: 8080 for production, 9091 for dev)
- `SPRING_PROFILES_ACTIVE` - Set to `production` for production deployments
- `RDS_HOSTNAME` - RDS database hostname
- `RDS_PORT` - RDS database port
- `RDS_DB_NAME` - Database name
- `RDS_USERNAME` - Database username
- `RDS_PASSWORD` - Database password

---

## Files Included for Deployment

- ✅ `Procfile` - Elastic Beanstalk process file
- ✅ `.ebextensions/01_setup.config` - EB configuration
- ✅ `buildspec.yml` - AWS CodeBuild specification
- ✅ `Dockerfile` - Multi-stage Docker build
- ✅ `.dockerignore` - Docker build optimization
- ✅ `amplify.yml` - AWS Amplify build configuration
- ✅ `application-production.properties` - Production configuration

---

## Testing the Deployment

After deployment, test these endpoints:

1. **Health Check**
   ```bash
   curl http://your-app-url/actuator/health
   ```

2. **Get all dealerships**
   ```bash
   curl http://your-app-url/api/dealerships
   ```

3. **Swagger UI**
   Visit: `http://your-app-url/swagger-ui.html`

---

## Troubleshooting

### Application won't start
- Check logs: `eb logs` (for Elastic Beanstalk)
- Verify Java 17 runtime is selected
- Check database connectivity

### Database connection errors
- Verify RDS security group allows inbound traffic
- Check environment variables are set correctly
- Ensure RDS is in the same VPC as your application

### Build failures
- Ensure Maven can download dependencies (internet access)
- Check Java version compatibility
- Review build logs in CodeBuild/Amplify console

---

## Next Steps

1. Set up a production database (RDS PostgreSQL)
2. Configure HTTPS/SSL certificate
3. Set up CI/CD pipeline
4. Configure monitoring and alerts
5. Implement authentication/authorization
6. Set up auto-scaling policies

---

For more information, refer to:
- [AWS Elastic Beanstalk Documentation](https://docs.aws.amazon.com/elasticbeanstalk/)
- [AWS App Runner Documentation](https://docs.aws.amazon.com/apprunner/)
- [Spring Boot on AWS](https://spring.io/guides/gs/spring-boot-aws/)
