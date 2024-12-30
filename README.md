# Resume Builder
Resume Builder is a web application that allows users to create and manage their resumes online. It provides a user-friendly interface for creating, editing, and downloading resumes in PDF format. The application utilizes technologies such as Java, Thymeleaf, Spring Boot, and iText for HTML to PDF conversion.

<br>

## Features
- **User Registration**: Users can create an account to access the application.

- **Resume Creation**: Users can create a new resume by filling out a form with their personal information, projects, work experience, and skills.

- **Resume Editing**: Users can edit their existing resumes, update information, add or remove sections.

- **HTML to PDF Conversion**: The application uses iText to convert the resume HTML content into PDF format.

- **Download Resume**: Users can download their resumes in PDF format to their local devices.

- **Visibility Settings**: Users can manage the visibility of sections on their resume, such as hiding skills or contact information.

- **Logging**: The application logs incoming requests to endpoints and provides helpful information for debugging and analysis.

- **Admin Panel**: Administrators have access to an admin panel with additional privileges.
  
    - **Grant/Revoke Admin Privileges**: Administrators can grant or revoke admin privileges for other users.
      
    - **Ban/Unban Users**: Administrators can ban or unban users, restricting or restoring their access to the application.
      
    - **View User Resume**: Administrators can view any user's Resume. (They cannot see any data that the user has chosen to hide using their visibility settings.)

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

## Configuration
MongoDB & Firebase Storage needs to be configured before you can start using ResumeBuilder Application.

### MongoDB
ResumeBuilder uses MongoDB as its database. Application.yaml has already been configured. If you don't have it installed you can [download it from here.](https://www.mongodb.com/try/download/community)

### MongoDB Compass
You may need this for editing data on the database directly so I strongly suggest having this installed on your local environment as well. You can [download the application from here.](https://www.mongodb.com/try/download/compass)

### Firebase Storage
ResumeBuilder Application uses Firebase for storing images like profile pictures & project photos.

To configure Firebase Storage:

- Create a new project from [Firebase Console.](https://console.firebase.google.com/)

- Go to Project Settings -> Service Accounts -> Firebase Admin SDK -> Generate new private key.

- Replace `src/main/resources/cloud/firebase.json` with your downloaded JSON file. Keep in mind that it should be named as `firebase.json`.

- Enable Firebase Storage.

- Update the `BUCKET_NAME` constant in `src/main/java/com/sefaunal/resumebuilder/Utils/ImageUtils.java` to match your Firebase project's bucket name.

```java
private static final String BUCKET_NAME = "resumebuilder-f10a4.appspot.com";
```

<br>

## Accessing Admin Panel
If you would like to access admin panel you'll need to:

- Run the project.

- Access the application via browser from `http://localhost:8080/`.

- Create an account.

- Change user's role to `ADMIN` from MongoDB Compass.

<img width="1544" alt="1" src="https://github.com/user-attachments/assets/09905cbd-2cb0-4af0-9be9-5128556127f7" />

<br>

## Screenshots

### <p align="center">Landing Page</p>
<img width="1840" alt="2" src="https://github.com/user-attachments/assets/7dc120a0-f941-4f33-a06f-46ab0b9324ba" />

<img width="1840" alt="3" src="https://github.com/user-attachments/assets/cf30249a-dbdc-4b99-b0e2-46ce2b54799f" />

### <p align="center">Login Page</p>
<img width="1840" alt="4" src="https://github.com/user-attachments/assets/fef7d95e-ac6b-4e18-bc21-72efb082bd56" />

### <p align="center">Register Page</p>
<img width="1840" alt="5" src="https://github.com/user-attachments/assets/bf002e00-4b1c-422b-b61b-257f1be1dcac" />

### <p align="center">Under Maintenance Page</p>
<img width="1840" alt="6" src="https://github.com/user-attachments/assets/3f480047-34a1-4be5-953a-8587a53dc6bc" />

### <p align="center">404 Page</p>
<img width="1840" alt="7" src="https://github.com/user-attachments/assets/2d8e20e0-2b8b-4b9a-936d-27954d46011b" />

### <p align="center">Settings Pages</p>
<img width="1840" alt="8" src="https://github.com/user-attachments/assets/b97abae5-1b24-4871-9384-300a7206e102" />

<img width="1840" alt="9" src="https://github.com/user-attachments/assets/51d65319-f4cf-4bcb-b480-9b1e8558dbb2" />

<img width="1840" alt="10" src="https://github.com/user-attachments/assets/9549dccc-f735-4c14-90b1-fd6b6e35fcae" />

<img width="1840" alt="11" src="https://github.com/user-attachments/assets/9e96890e-6cb5-410e-8d0b-184b773d84a0" />

### <p align="center">Resume Pages</p>
<img width="1840" alt="12" src="https://github.com/user-attachments/assets/9fa9d0bb-faee-41c0-8d95-70234598d2f6" />

<img width="1840" alt="13" src="https://github.com/user-attachments/assets/9d779c5b-8ce2-4975-bc1c-c1126710696b" />

<img width="1840" alt="14" src="https://github.com/user-attachments/assets/b5bd15ab-76f2-4d2e-be4a-f92f77598b54" />

### <p align="center">Admin Page</p>
<img width="1840" alt="15" src="https://github.com/user-attachments/assets/462ab706-3f28-4054-aeea-682135641513" />

### <p align="center">Downloaded PDF</p>
<img width="1840" alt="16" src="https://github.com/user-attachments/assets/c6848c01-ced8-4ebc-9e55-ef60619a165b" />
