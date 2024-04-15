# ExchangeRate-Service

## How to Start the Application

1. Navigate to the root directory exchangerate-service.
2. Run the following command to build the application image:
    
    ./gradlew bootBuildImage 
    
3. Change directory to the docker folder.
    
    cd docker
    
4. Build the Docker containers using Docker Compose.
    
    docker-compose build
    
5. Start the application using Docker Compose.
    
    docker-compose up
    

Note: If you want to use mock data, follow step 5 with the PROFILE=mock environment variable.

set PROFILE=mock && docker-compose up (Windows Command Promt)

## How to Call Endpoints

1. Import the rates.json collection into Postman.
2. Use Postman to make requests to each endpoint to verify their behavior.
