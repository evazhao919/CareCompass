# Project Design Document - CareCompass Design

## 1. Problem Statement

Care Compass is a healthcare coordination platform designed to empower patients, caregivers, and healthcare providers with seamless access to essential healthcare tools, resources, and support.
Many discharged patients, especially older adults with long-term care needs, require ongoing monitoring and documentation of their health condition after leaving the hospital. Caregivers or family members often play a crucial role in managing the patient's health information, including medication schedules, vital signs, symptoms, appointments, and daily activities.

## 2. Top Questions to Resolve in Review

1. How can we create a unified platform that integrates various aspects of healthcare navigation, coordination, and support?
2. What features and functionalities should the Care Compass include to address the diverse needs of patients, caregivers, and healthcare providers?
3. How can we ensure that the Care Compass is user-friendly, accessible, and secure for all stakeholders?

## 3. Use Cases

U1. As a caregiver, I want to record and track the patient's medication schedule.
U2. As a caregiver, I want to monitor and log the patient's vital signs such as temperature, blood pressure, heart rate, and oxygen saturation.
U3: As a caregiver, I want to receive notifications for medication and vital signs monitoring, so I can maintain the prescribed care schedule.

## 4. Project Scope

### 4.1. In Scope

* Medication management and reminders
* Vital signs tracking and monitoring
* Notifications

### 4.2. Out of Scope
* Appointment scheduling and management
* Daily activity tracking and monitoring
* Symptom tracking and logging
* Integration with electronic health record (EHR) systems
* Real-time communication with healthcare providers
* Automated analysis of health data for diagnosis or treatment recommendations
* Automatic generation of reports and email delivery to doctors

## 5. Proposed Architecture Overview

The CareCompass application will be developed as a web-based platform,
accessible via web browsers on desktop. It will utilize a serverless
architecture using AWS Lambda functions and Amazon DynamoDB for data storage.
The front end will be built using modern web technologies such as HTML, CSS,
and JavaScript for dynamic user interfaces.

## 6. API

### 6.1. Public Models

```
// MedicationModel

String userId; 
String medicationName;
String medicationInfo;
Set<NotificationModel> notifications;
```

```
// VitalSignsMeasurementModel

String userId; 
LocalDateTime timestamp;
double temperature;
int heartRate;
int pulse;
int respiratoryRate;
int systolicPressure;
int diastolicPressure;
int meanArterialPressure;
double weight;
String patientPosition;
int bloodOxygenLevel;
String oxygenTherapy;
String flowDelivered;
String patientActivity;
String additionalNotes;
Set<NotificationModel> notifications;
```

```
// NotificationModel

String notificationId;
String userId;
String medicationNames;
LocalDateTime medicationsTime;
LocalDateTime vitalSignsTime; 
```

### Medication Management Reminder Endpoints

### 6.1. Create Medication Record Endpoint
* POST /medications
* Body: MedicationModel
* Description: Create a new medication entry for a patient.
* Response: Returns the newly created MedicationModel. 
![CreateMedicationRecord.png](images/CreateMedicationRecord.png)

### 6.2. Delete Medication Endpoint
+ DELETE /medications/:medicationId
* Description: Deletes a specified medication entry.
* Response: Json object return success and message.

### 6.3. List All Medications Endpoint
* GET /medications/user/:userId
* Description: Retrieves all medications.
* Response: List of medications.

### Vital Signs Tracking Endpoints
### 6.4.Log Vital Signs Endpoint
* POST /vitals
* Body: VitalSignsMeasurementModel
* Description: Logs a new set of vital signs for a patient.
* Response: Returns the newly recorded VitalSignsMeasurementModel.

### 6.5.Delete Vital Signs Record Endpoint
* DELETE /vitals/:vitalSignsId
* Description: Deletes a specified vital signs record.
* Response: Success or error message.
![DeleteVitalSignsRecord.png](images/DeleteVitalSignsRecord.png)
* 
### 6.6.List All Vital Signs Endpoint
* GET /vital-signs/user/:userId
* Retrieves all vital signs entries.
* Response: List of vital signs.

### Notification Management Endpoints
### 6.7.Add Notification for Medication
* POST /Notifications
* Body: NotificationModel
* Description: Sets up a new reminder for medication or vital signs monitoring.
* Response: Returns the newly created NotificationModel.

### 6.8 Remove Notification Endpoint
* DELETE /reminders/:reminderId
* Description: Removes a specified reminder.
* Response: JSON object return success and message.

### 6.9 List All Notifications Endpoint
* GET /notification/user/:userId
* Description: Retrieves all notifications set up for a specific user.
* Response: List of NotificationModel entries.
![ListAllNotificationsVitalSignsRecords.png](images/ListAllNotificationsVitalSignsRecords.png)

## 7. Tables

### 7.1. `medications`
```
medicationId // Primary key, string (unique identifier for each medication)
userId // Partition key, string (patient or caregiver ID)
medicationTime // Sort key, LocalDateTime (in most cases it is possible for a patient to be taking many different medications at the same time）
medicationName // string
medicationInfo //String 
```
### 7.2. `vitalSigns`
```
vitalSignId // Primary key, string (unique identifier for each vital sign record)
userId // Partition key, string
timestamp // Sort key, LocalDateTime
temperature // double
heartRate // int 
pulse // int
respiratoryRate // int 
systolicPressure // int
diastolicPressure // int
meanArterialPressure // int 
weight // double 
patientPosition // String 
bloodOxygenLevel // int 
oxygenTherapy // String 
flowDelivered // String 
patientActivity // String 
additionalNotes // String 
```
## 7.3. `notifications`
```
notificationId // Primary key, string (unique identifier for each notification)
userId // String 
medicationName // String 
medicationTime // LocalDateTime
vitalSignsTime;  // LocalDateTime
```
### 7.4. `GSI userMedicationsIndex`
```
userId // partition key, string
medicationTime // sort key, LocalDateTime
medicationName // string 
notificationId // string 
```
### 7.5. `GSI vitalSignsTrackingIndex`
```
userId // Partition Key
timestamp // Sort Key
```

### 7.6. `GSI medicationNameIndex`
```
userId // partition key, string
medicationName // sort key, String
medicationTime  // LocalDateTime
notificationId // string 
```

## 8. Pages

![(Design_CareCompass_.png)](images/OverallWorkflow.png)


## Technologies Used

* Programming Languages: Java, JavaScript.
* AWS Services: AWS Lambda, AWS API Gateway, Amazon DynamoDB, Amazon S3, AWS CloudFormation, AWS CloudWatch, AWS CloudFront.
* Web Technologies: HTML, CSS.
* Development Tools: Docker, Gradle, Git, GitHub.
* Software Development Practices: Object-Oriented Programming (OOP), Test-Driven Development (TDD).
* Additional Libraries/Frameworks: Google Guava, Amazon Cognito, Dagger.