# Project Design Document - CareCompass Design

## 1. Problem Statement

Care Compass is a healthcare coordination platform designed to empower patients, caregivers, and healthcare providers with seamless access to essential healthcare tools, resources, and support.
Many discharged patients, especially older adults with long-term care needs, require ongoing monitoring and documentation of their health condition after leaving the hospital. Caregivers or family members often play a crucial role in managing the patient's health information, including medication schedules, vital signs, symptoms, appointments, and daily activities.

## 2. Top Questions to Resolve in Review

1. How can we create a unified platform that integrates various aspects of healthcare navigation, coordination, and support?
2. What features and functionalities should the Care Compass include to address the diverse needs of patients, caregivers, and healthcare providers?
3. How can we ensure that the Care Compass is user-friendly, accessible, and secure for all stakeholders?

## 3. Use Cases

U1. As a caregiver, I would like to create a new medication record, so I can manage the medication schedule efficiently.

U2. As a caregiver, I would like to delete a medication record, so I can keep the medication list up-to-date.

U3. As a caregiver, I would like to view all medications for a specific user, so I can monitor and manage their medication intake.

U4. As a caregiver, I would like to log a new set of vital signs, so I can track the patient’s health metrics over time.

U5. As a caregiver, I would like to delete a specific vital signs record, so I can correct errors or remove outdated information.

U6. As a caregiver, I would like to retrieve all vital signs entries for a specific user, so I can review their historical health data.

U7. As a caregiver, I would like to set up new reminders for medication or vital signs monitoring, so I can ensure adherence to health management protocols.

U8. As a caregiver, I would like to remove a specified reminder, so I can adjust notifications to current care requirements.

U9. As a caregiver, I would like to retrieve all notifications set up for a specific user, so I can overview and manage all set reminders and alerts.

U10. As a caregiver, I would like to receive notifications for medication and vital signs monitoring, so I can maintain the prescribed care schedule and respond promptly to any changes in the patient’s condition.

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

Several potential features are currently designated as "out of scope" for the initial development phase of CareCompass. The decision to exclude these features was made primarily based on the following considerations:
The initial phase focuses on establishing a robust foundation with key functionalities that address the most pressing needs of our users. More complex features such as appointment scheduling, symptom tracking, and automated analysis of health data are planned for future updates, where additional time and resources can be dedicated to their development and integration.

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