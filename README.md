# Resume Builder
Resume Builder using Spring Boot, Thymeleaf & MongoDB

<br>

## Description
Resume Builder is a web application that allows users to create and manage their resumes online. It provides a user-friendly interface for creating, editing, and downloading resumes in PDF format. The application utilizes technologies such as Java, Thymeleaf, Spring Boot, and iText for HTML to PDF conversion.

<br>

## Features
- User Registration: Users can create an account to access the application.

- Resume Creation: Users can create a new resume by filling out a form with their personal information, projects, work experience, and skills.

- Resume Editing: Users can edit their existing resumes, update information, add or remove sections.

- HTML to PDF Conversion: The application uses iText to convert the resume HTML content into PDF format.

- Download Resume: Users can download their resumes in PDF format to their local devices.

- Visibility Settings: Users can manage the visibility of sections on their resume, such as hiding skills or contact information.

- Logging: The application logs incoming requests to endpoints and provides helpful information for debugging and analysis.

- Admin Panel: Administrators have access to an admin panel with additional privileges.
    - Grant/Revoke Admin Privileges: Administrators can grant or revoke admin privileges for other users.
    - Ban/Unban Users: Administrators can ban or unban users, restricting or restoring their access to the application.
    - View User Resume: Administrators can view any user's Resume. (They can't see any data user has chosen to hide on their visibility settings)

<br>

## Technologies Used
- Java 21

- iText

- MongoDB

- Thymeleaf

- Spring Boot 3

- Spring Security 6

- Firebase Storage

<br>

## Installation
1. Clone the repository

```
git clone https://github.com/sefaunal/ResumeBuilder.git
```

<br>

2. Navigate to the project directory:

```
cd ResumeBuilder
```

<br>

3. Switch to development branch

```
git checkout development
```

<br>

## Configuration
MongoDB & Firebase Storage needs to be configured before you can start using ResumeBuilder Application.

### MongoDB
ResumeBuilder uses MongoDB as its database. Application.yaml has already been configured. If you don't have it installed you can [download it from here.](https://www.mongodb.com/try/download/community)

### MongoDB Compass
You may need this for editing data on the database directly so i strongly suggest having this installed on your local environment as well. You can [download the application from here.](https://www.mongodb.com/try/download/compass)

### Firebase Storage
ResumeBuilder Application uses Firebase for storing images like profile pictures & project photos.

To configure Firebase Storage:

- Create a new project from [Firebase Console.](https://console.firebase.google.com/)

- Go to Project Settings -> Service Accounts -> Firebase Admin SDK -> Generate new private key.

- Replace src/main/resources/cloud/firebase.json with your downloaded JSON file. Keep in mind that it should be named as "firebase.json".

- Enable Firebase Storage. 

- From src/main/java/com/sefaunal/resumebuilder/Utils/ImageUtils.java change the following field's value with your own Bucket Address.

```java
private static final String BUCKET_NAME = "resumebuilder-f10a4. appspot.com";
```

<br>

## Accessing Admin Panel
If you would like to access admin panel you will need to:

- Run the project.

- Access the application via browser from http://localhost:8080/

- Create an account

- Change user's role to 'ADMIN' from MongoDB Compass

<img width="1544" alt="Screenshot 2024-02-15 at 10 58 25" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/5a0b6a80-b914-4155-9f7f-203be9fc40be">

<br>

## Screenshots

### <p align="center">Landing Page</p>
<img width="1840" alt="Screenshot 2024-02-14 at 16 11 05" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/78120be1-be97-49ab-baba-270677410360">

<img width="1840" alt="Screenshot 2024-02-14 at 16 14 47" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/8427c327-704f-45b8-a893-f71905beff72">

### <p align="center">Login Page</p>
<img width="1840" alt="Screenshot 2024-02-14 at 16 11 14" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/87b5e27c-696a-4191-b712-d9a8c4bc2b82">

### <p align="center">Register Page</p>
<img width="1840" alt="Screenshot 2024-02-14 at 16 11 24" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/439b372b-8955-4f93-b0a4-c65aa5f5b072">

### <p align="center">Under Maintenance Page</p>
<img width="1840" alt="Screenshot 2024-02-14 at 16 11 37" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/f7cf6b72-3e3e-4c72-a3e0-4f74bdaec54b">

### <p align="center">404 Page</p>
<img width="1840" alt="Screenshot 2024-02-14 at 16 11 43" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/7b15581f-dbf8-4b76-89f5-c3b188f6d37f">

### <p align="center">Settings Pages</p>
<img width="1840" alt="Screenshot 2024-02-15 at 14 54 45" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/1e613028-ab82-4ecf-adf1-9bb405261b17">

<img width="1840" alt="Screenshot 2024-02-15 at 14 54 36" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/6477ba5a-0f37-4fa3-a8b8-496fda0ab556">

<img width="1840" alt="Screenshot 2024-02-15 at 14 54 04" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/a7190165-731d-4e78-ae41-70ec11af466c">

<img width="1840" alt="Screenshot 2024-02-15 at 14 54 31" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/7a9d88cf-8721-4d31-b30d-72623c3dab8a">

### <p align="center">Resume Pages</p>
<img width="1840" alt="Screenshot 2024-02-15 at 15 01 43" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/116869bd-4f7c-46f9-bddf-86c16c96a718">

<img width="1840" alt="Screenshot 2024-02-15 at 15 02 21" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/d77b84d3-795d-4c10-83c7-7d1e2ebbd5fa">

<img width="1840" alt="Screenshot 2024-02-15 at 15 02 25" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/ba4655b4-5ea8-48aa-bba5-cc9b575f70c3">

### <p align="center">Admin Page</p>
<img width="1840" alt="Screenshot 2024-02-15 at 15 10 43" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/50dd4af3-6d0f-4242-a28d-8593bfa58995">

### <p align="center">Downloaded PDF</p>
<img width="1840" alt="Screenshot 2024-02-15 at 15 06 30" src="https://github.com/sefaunal/ResumeBuilder/assets/83312431/3069408c-d100-4f91-9c9e-d1ea0b9f5ee0">
