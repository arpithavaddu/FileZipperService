
# FILEZIPPER SERVICE
This Filezipper is a microservice with one rest endpoint /files/zip which takes list of files(form-data) as input and gives a zip containing those input files.

- To build this service: ``` ./gradlew clean build ```
- To run this service locally : ```./gradlew clean bootrun ```
This service is dockerized .
 - To build the image run: ``` docker build -t filezipper . ```
- To run the docker image :
  - docker run -p8080:8080 filezipper
  - This will spin up the service
  - On postman do a post to http://localhost:8080/files/zip with a list of files selected as a parameter as shown in below
 <img width="1287" alt="Screenshot 2022-05-05 at 02 54 54" src="https://user-images.githubusercontent.com/22766940/166853940-6f46a023-f1b8-4f1f-95f4-9c65d158a14f.png">
<img width="1285" alt="Screenshot 2022-05-05 at 02 55 20" src="https://user-images.githubusercontent.com/22766940/166853945-4055fefb-b82d-47e1-ae22-80d2af0bd714.png">
