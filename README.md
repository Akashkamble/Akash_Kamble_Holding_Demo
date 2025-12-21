# Holding Demo

## Libraries used
1. [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for Dependency Injection.
2. [Retrofit](https://github.com/square/retrofit) for API Call.
3. [Room](https://developer.android.com/jetpack/androidx/releases/room) for Database
4. [Compose](https://developer.android.com/develop/ui/compose/setup) for UI.
5. [MockK](https://mockk.io/) for testing

## Project Structure

```
├──src
|   ├── main
|   |   ├── java
|   |   |   ├── holdingdemo
|   |   |   |   ├── data
|   |   |   |   |   ├── db (dao, db, entity)
|   |   |   |   |   ├── mapper (Response DTO to entity and entity to ui model mapper)
|   |   |   |   |   ├── remote (ResponseDTO, API interface)
|   |   |   |   |   ├── repo (Repo Implementation)
|   |   |   |   ├── di (Hilt modules)
|   |   |   |   ├── domain
|   |   |   |   |   ├── model (Holding)
|   |   |   |   |   ├── (Repo Interface)
|   |   |   |   |   ├── result (Wrapper for Success and Error)
|   |   |   |   ├── ui
|   |   |   |   |   ├── holding (Composables and ViewModel)
|   |   |   |   |   ├── model (Compose Screen UI state)
|   |   |   |   |   ├── theme (Colors and Theme for application)
├──test
|   ├── java
|   |   ├── holdingdemo
|   |   |   ├── fake (Repository Fake)
|   |   |   ├── model (Model related tests of property calculations)
|   |   |   ├── ui
|   |   |   |   ├── viewmodel (ViewModel Tests)
```

## ScreenShots

| Light   |      Dark      |
|:----------:|:-------------:
| ![Image](https://github.com/user-attachments/assets/96afcda2-117c-4907-9419-c749092bbd33) | ![Image](https://github.com/user-attachments/assets/d88deab4-151b-48aa-8ba1-245fbfb741c4)  |
| ![Image](https://github.com/user-attachments/assets/3208e328-d19e-4895-950b-9e8490119c57) | ![Image](https://github.com/user-attachments/assets/65463f34-071c-46ff-b981-8f15bbef1f0c)  |
| ![Image](https://github.com/user-attachments/assets/ee61bf4b-6fdd-4a2f-8483-fbf570a9e451) | ![Image](https://github.com/user-attachments/assets/4ee2a957-464a-4950-a513-f97e97066d97) |
