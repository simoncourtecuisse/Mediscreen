![Java](https://img.shields.io/badge/made%20with-JAVA-%23C9284D?style=for-the-badge&logo=java&logoColor=#EC1C24)
![Maven](https://img.shields.io/badge/MAVEN-%23005384?style=for-the-badge&logo=maven&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-green?style=for-the-badge&logo=MongoDB&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-cyan?style=for-the-badge&logo=Docker&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-9cf?style=for-the-badge&logo=MySQL&logoColor=white)
![Vue.js](https://img.shields.io/badge/Vue.js-success?style=for-the-badge&logo=Vue&logoColor=#00A865)




____________________

# Mediscreen Application
> -- _Mediscreen is a Spring Boot application that is specialized in detecting risk factors for disease._ --

## Architecture

The architecture Overview of Mediscreen:

![Screenshot](schemaMediscreen.png)


## To install
### Prerequisites
- Java 11 : https://www.oracle.com/java/technologies/downloads/#license-lightbox
- maven (installed with IDE)
- git (installed with IDE)
- Docker : https://docs.docker.com/docker-for-windows/install/

### Steps
- clone repository
`git clone https://github.com/simoncourtecuisse/Mediscreen.git`

- Launch the command for generate the JARs : `mvn clean install`
- Build each image using the DockerFile included in each micro-service folder (must be in the right folder to launch the command) :
```
docker build -t mediscreen-patient .
docker build -t mediscreen-patienthistory .
docker build -t mediscreen-report .
docker build -t mediscreen-ui .
```

## Run the application

To start the application, run the command:
```
docker-compose up
```



