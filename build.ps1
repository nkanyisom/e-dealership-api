# Build Script - Workaround for SSL Certificate Issue
# This script creates a Spring Boot executable JAR without using Maven package

Write-Host "Setting JAVA_HOME..." -ForegroundColor Green
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-17.0.13.11-hotspot"

Write-Host "Compiling the project..." -ForegroundColor Green
mvn -o clean compile

if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed!" -ForegroundColor Red
    exit 1
}

Write-Host "Creating JAR using Spring Boot Repackage..." -ForegroundColor Green
mvn -o spring-boot:repackage

if ($LASTEXITCODE -eq 0) {
    Write-Host "`nBuild successful!" -ForegroundColor Green
    Write-Host "JAR file created at: target\e-dealership-api-1.0.0.jar" -ForegroundColor Cyan
    Write-Host "`nTo run the application:" -ForegroundColor Yellow
    Write-Host "  java -jar target\e-dealership-api-1.0.0.jar" -ForegroundColor White
} else {
    Write-Host "`nSpring Boot repackage failed. Using alternative method..." -ForegroundColor Yellow
    
    # Alternative: Create executable JAR manually
    Write-Host "Extracting Spring Boot dependencies..." -ForegroundColor Green
    
    $targetDir = "target"
    $classesDir = "$targetDir\classes"
    $libDir = "$targetDir\lib"
    
    # Create lib directory
    New-Item -ItemType Directory -Force -Path $libDir | Out-Null
    
    # Copy dependencies (they should be in local .m2 repo)
    Write-Host "Copying dependencies from local Maven repository..." -ForegroundColor Green
    mvn -o dependency:copy-dependencies -DoutputDirectory=target/lib -DincludeScope=runtime
    
    # Create MANIFEST.MF
    $manifest = @"
Manifest-Version: 1.0
Main-Class: org.springframework.boot.loader.JarLauncher
Start-Class: com.dealership.api.EDealershipApiApplication
Spring-Boot-Version: 3.2.0
Spring-Boot-Classes: BOOT-INF/classes/
Spring-Boot-Lib: BOOT-INF/lib/
"@
    
    $manifest | Out-File -FilePath "$targetDir\MANIFEST.MF" -Encoding ASCII
    
    Write-Host "Building final JAR..." -ForegroundColor Green
    
    # Create directory structure
    $bootInfClasses = "$targetDir\jar-temp\BOOT-INF\classes"
    $bootInfLib = "$targetDir\jar-temp\BOOT-INF\lib"
    $metaInf = "$targetDir\jar-temp\META-INF"
    
    New-Item -ItemType Directory -Force -Path $bootInfClasses | Out-Null
    New-Item -ItemType Directory -Force -Path $bootInfLib | Out-Null
    New-Item -ItemType Directory -Force -Path $metaInf | Out-Null
    
    # Copy compiled classes
    Copy-Item -Path "$classesDir\*" -Destination $bootInfClasses -Recurse -Force
    
    # Copy dependencies
    if (Test-Path $libDir) {
        Copy-Item -Path "$libDir\*" -Destination $bootInfLib -Force
    }
    
    # Copy manifest
    Copy-Item -Path "$targetDir\MANIFEST.MF" -Destination "$metaInf\MANIFEST.MF" -Force
    
    # Create JAR
    Set-Location "$targetDir\jar-temp"
    jar cfm "..\e-dealership-api-1.0.0.jar" META-INF\MANIFEST.MF *
    Set-Location ..\..
    
    # Cleanup
    Remove-Item -Path "$targetDir\jar-temp" -Recurse -Force
    Remove-Item -Path "$targetDir\MANIFEST.MF" -Force
    
    Write-Host "`nJAR file created: target\e-dealership-api-1.0.0.jar" -ForegroundColor Green
    Write-Host "Note: This is a basic JAR. For full Spring Boot executable JAR, fix the SSL issue." -ForegroundColor Yellow
}

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "Build Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
