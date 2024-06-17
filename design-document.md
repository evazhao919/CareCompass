# Project Design Document - CareCompass Design

## 1. Problem Statement

CareCompass is a healthcare coordination platform designed to empower patients, caregivers, and healthcare providers with seamless access to essential healthcare tools, resources, and support.
Many discharged patients, especially adults with long-term care needs, require ongoing monitoring and documentation of their health conditions after leaving the hospital.
Caregivers or family members often play a crucial role in managing the patient's health information, including medication, notifications, vital signs, and blood glucose measurements.
## 2. Top Questions to Resolve in Review

1. How can we create a unified platform that integrates various aspects of healthcare navigation, coordination, and support?
2. What features and functionalities should CareCompass include to address the diverse needs of patients, caregivers, and healthcare providers?
3. How can we ensure that CareCompass is user-friendly, accessible, and secure for all stakeholders?

## 3. Use Cases

U1. As a user, I would like to create a new medication record, so I can manage the medication schedule efficiently.

U2. As a user, I would like to update a medication record, so I can ensure the information is current.

U3. As a user, I would like to delete a medication record, so I can keep the medication list up-to-date.

U4. As a user, I would like to view all medications, so I can monitor medication intake.

U5. As a user, I would like to retrieve medications by status, so I can easily check which medications are active, completed, on hold, or discontinued.

U6. As a user, I would like to create a new set of vital signs, so I can track health metrics over time.

U7. As a user, I would like to update a specific vital signs record, so I can correct or update the information.

U8. As a user, I would like to delete a specific vital signs record, so I can correct errors or remove outdated information.

U9. As a user, I would like to retrieve all vital signs entries, so I can review historical health data.

U10. As a user, I would like to set up a new notification for medication or vital signs monitoring, so I can ensure adherence to health management protocols.

U11. As a user, I would like to remove a specified notification, so I can adjust notifications to current care requirements.

U12. As a user, I would like to update notifications for medication and vital signs monitoring, so I can keep the patient's care schedule timely and accurate.

U13. As a user, I would like to retrieve all notifications, so I can overview and manage all set notifications and alerts.

U14. As a user, I would like to retrieve all upcoming notifications, so I can stay informed about upcoming tasks and alerts.

U15. As a user, I would like to retrieve notifications by reminder type, so I can manage and prioritize them effectively.

U16. As a user, I would like to create a new blood glucose measurement to regularly monitor and manage the patient’s glucose levels.

U17. As a user, I would like to update a blood glucose measurement, so I can ensure accurate and current data.

U18. As a user, I would like to delete a blood glucose measurement to correct any errors or when it's no longer needed.

U19. As a user, I would like to view all blood glucose measurements to analyze trends and adjust treatments.

## 4. Project Scope

### 4.1. In Scope

* Medication management
* Vital signs management
* Blood glucose management
* Notification management

### 4.2. Out of Scope

* Daily activity tracking and monitoring
* Symptom tracking and logging
* Real-time communication with healthcare providers
* Automated analysis of health data for diagnosis or treatment recommendations
* Automatic generation of reports and email delivery to doctors
* Educations

Several potential features are currently designated as "out of scope" for the initial development phase of CareCompass. The decision to exclude these features was made primarily based on the following considerations:
The initial phase focuses on establishing a robust foundation with key functionalities that address the most pressing needs of our users.
More complex features are planned for future updates, where additional time and resources can be dedicated to their development and integration.


## 5. Proposed Architecture Overview

The CareCompass application will be developed as a web-based platform accessible via web browsers on desktop.
It will utilize a serverless architecture using AWS Lambda functions and Amazon DynamoDB for data storage.
The front end will be built using modern web technologies such as HTML, CSS, and JavaScript for dynamic user interfaces.

## 6. API

### 6.1. Public Models

```
// MedicationModel

String patientId;
String medicationId;
String medicationName;
String prescription;
String instructions;  
MEDICATION_STATUS // medicationStatus (enum)      
```
```
// VitalSignModel

String patientId; 
LocalDateTime actualCheckTime;       
double temperature;              
int heartRate;
int pulse;
int respiratoryRate;
int systolicPressure;
int diastolicPressure;
int meanArterialPressure;
double weight;
PATIENT_POSITION patientPosition; // enum
int bloodOxygenLevel;
OXYGEN_THERAPY oxygenTherapy; // enum
FLOW_DELIVERED flowDelivered; // enum
PATIENT_ACTIVITY patientActivity; // enum
String comments;        
```
```
// BloodGlucoseMeasurementModel
String patientId;                   
LocalDateTime actualCheckTime;
double glucoseLevel;
GLUCOSE_MEASUREMENT_CONTEXT glucoseContext; // enum
String comments;    
```
```
// NotificationModel

String patientId;
String notificationId;
String notificationTitle;
String reminderContent;
LocalDateTime scheduledTime;
REMINDER_TYPE reminderType;  // enum
```

### 6.2. Medication Management Reminder Endpoints
#### 1. Add Medication Endpoint
* POST /medications
* Body: MedicationModel
* Response: Returns the newly created MedicationModel.
  ![CreateMedicationRecord.png](resources/images/CreateMedicationRecord.png)

#### 2. Update Medication Endpoint
* PUT /medications/{medicationId}
* Body: MedicationModel
* Response: Returns the newly created MedicationModel.

#### 3. Delete Medication Endpoint
* DELETE /medications/{medicationId}
* Body: MedicationModel
* Response: return success and message.

#### 4. Get All Medications Endpoint
* GET /medications
* Body: MedicationModelList
* Response: List of medications.

#### 5. Retrieve Medications By Status Endpoint
* GET /medicationsByStatus/{medicationStatus}
* Body: MedicationModelList
* Response: List of medications.

### 6.3.Vital Signs Tracking EndpointS
#### 1. Add Vital Signs Endpoint
* POST /vitalSigns
* Body: VitalSignModel
* Response: Returns the newly recorded VitalSignsMeasurementModel.

#### 2. Update Vital Signs Endpoint
* PUT /vitalSigns/{actualCheckTime}
* Body: VitalSignModel
* Response: Returns the newly recorded VitalSignsMeasurementModel.

#### 3. Delete Vital Signs Endpoint
* DELETE /vitalSigns/{actualCheckTime}
* Body: VitalSignModel
* Response: return success and message.
  ![DeleteVitalSignsRecord.png](resources/images/DeleteVitalSignsRecord.png)
*
#### 4. Get All Vital Signs Endpoint
* GET /vitalSigns
* Body: VitalSignModelList
* Response: List of vital signs.

### 6.4. Notification Management Endpoints
#### 1. Add Notification Endpoint
* POST /notifications
* Body: NotificationModel
* Response: Returns the newly created NotificationModel.

#### 2. Update Notification Endpoint
* PUT /notifications/{notificationId}
* Body: NotificationModel
* Response: Returns the newly created NotificationModel.

#### 3. Delete Notification Endpoint
* DELETE /notifications/{notificationId}
* Body: NotificationModel
* Response: return success and message.

#### 4. Get All Notifications Endpoint
* GET /notifications
* Body: NotificationModelList
* Response: List of NotificationModel.
  ![ListAllNotificationsVitalSignsRecords.png](resources/images/ListAllNotificationsVitalSignsRecords.png)

#### 5. Retrieve All Upcoming Notifications Endpoint
* GET /upcomingNotifications/
* Body: NotificationModelList
* Response: List of NotificationModel.

#### 6. Retrieve Notifications By Reminder Type Endpoint
* GET /notificationsByReminderType/{reminderType}
* Body: NotificationModelList
* Response: List of NotificationModel.

### 6.5. Blood Glucose Measurement Management Endpoints
#### 1. Add Blood Glucose Measurement Endpoint
* POST /bloodGlucoseMeasurements
* Body: BloodGlucoseMeasurementModel
* Response: Returns the newly created BloodGlucoseMeasurementModel.

#### 2. Update Blood Glucose Measurement Endpoint
* PUT /bloodGlucoseMeasurements/{actualCheckTime}
* Body: BloodGlucoseMeasurementModel
* Response: Returns the newly created BloodGlucoseMeasurementModel.

#### 3. Delete Blood Glucose Measurement Endpoint
* DELETE /bloodGlucoseMeasurements/{actualCheckTime}
* Body: BloodGlucoseMeasurementModel
* Response: return success and message.

#### 4. Get All Blood Glucose Measurement Endpoint
* GET /bloodGlucoseMeasurements/
* Body: BloodGlucoseMeasurementModelList
* Response: List of BloodGlucoseMeasurementModel.

## 7. Tables

### 7.1. `medications`
```
patientId // String (Partition Key)
medicationId // String (Sort Key)
medicationName // String 
prescription // String 
instructions // String 
medicationStatus // MEDICATION_STATUS (enum)
```

### 7.2. `vitalSigns`
```
patientId // String (Partition Key)
actualCheckTime // LocalDateTime (Sort Key)
temperature // double 
heartRate // int
pulse // int 
respiratoryRate // int 
systolicPressure // int
diastolicPressure // int 
meanArterialPressure // int 
weight // double
patientPosition // PATIENT_POSITION (enum)
bloodOxygenLevel // int 
oxygenTherapy // OXYGEN_THERAPY (enum)
flowDelivered // FLOW_DELIVERED (enum)
patientActivity // PATIENT_ACTIVITY (enum)
comments // String
```
## 7.3. `notifications`
```
patientId // String (Partition Key)
notificationId // String (Sort Key)
notificationTitle // String
reminderContent // String 
scheduledTime // LocalDateTime 
reminderType // REMINDER_TYPE (enum)
```
## 7.4. `bloodGlucoseMeasurements`
```
patientId // String (Partition Key)
actualCheckTime // LocalDateTime (Sort Key)
glucoseLevel // double 
glucoseContext // GLUCOSE_MEASUREMENT_CONTEXT (enum)
comments // String
```
### 7.5. `GSI notificationsIndex`
```
patientId // Partition Key, String
actualCheckTime // LocalDateTime (Sort Key)
```

## 8. Pages

![(Design_CareCompass_.png)](resources/images/OverallWorkflow.png)


## Technologies Used

* Programming Languages: Java, JavaScript.
* AWS Services: AWS Lambda, AWS API Gateway, Amazon DynamoDB, Amazon S3, AWS CloudFormation, AWS CloudWatch, AWS CloudFront.
* Web Technologies: HTML, CSS.
* Development Tools: Docker, Gradle, Git, GitHub.
* Software Development Practices: Object-Oriented Programming (OOP), Test-Driven Development (TDD).
* Additional Libraries/Frameworks: Amazon Cognito, Dagger.