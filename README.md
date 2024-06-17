<h1>
    <a href="https://d32uixdjt5mu84.cloudfront.net/">
        CareCompass
    </a>
</h1>


[View Design Document](design-document.md)

---------
Table of Contents
---------

- [Overview](#overview)
- [Features](#features)
- [Administrative Functions](#administrative-functions)
- [Examples](#examples)
- [Acknowledgements](#acknowledgements)

---------
Overview
---------

CareCompass serves as a centralized platform for healthcare navigation, coordination, and support. It provides patients, caregivers, and healthcare providers with convenient access to essential healthcare tools.

The platform addresses the challenges faced by discharged patients, particularly adults with long-term care needs, by facilitating ongoing monitoring and documentation of their health conditions post-hospitalization. Caregivers and family members can efficiently manage medications, track vital signs, and measure blood glucose levels. Additionally, the notification feature can manage appointments, medication refills, glucose measurements, immunizations, medical supplies, and custom reminders. These features are designed to assist patients in the day-to-day management of their medical schedules with clear documentation and planning.

By integrating various healthcare services and data sources, CareCompass streamlines the process of accessing healthcare information, enhancing the overall experience for patients and caregivers.

---------
Features
---------

CareCompass offers the following key features:

* **Medication Management**
    - Medication name
    - Prescription
    - Instructions
    - Medication status (active, discontinued, on hold, temporary stop)

* **Notification Management**
    - Notification title
    - Reminder content
    - Scheduled time
    - Reminder type (appointment, medication refill, medication, vital signs, blood glucose measurement, immunization, medical supply, custom reminder)

* **Vital Signs Management**
    - Actual check time
    - Temperature
    - Heart rate
    - Pulse
    - Respiratory rate
    - Systolic pressure
    - Diastolic pressure
    - Mean arterial pressure
    - Weight
    - Patient position (sitting, standing, laying)
    - Blood oxygen level
    - Oxygen therapy (none, nasal cannula, simple face mask, non-rebreather mask, venturi mask, high flow nasal cannula)
    - Flow delivered (none, low flow, medium flow, high flow)
    - Patient activity (sitting, standing, laying down, post exercise)
    - Comments

* **Blood Glucose Management**
    - Actual check time
    - Glucose level
    - Glucose context (fasting, before meal, after meal, bedtime)
    - Comments

---------
Administrative Functions
---------

* **Data Population**: Relevant healthcare data is sourced from various providers and patient inputs, processed, and uploaded to AWS DynamoDB tables.
* **Metrics Monitoring**: Metrics are tracked to monitor user interactions, search queries, and service utilization. This data guides decision-making for product iterations and improvements.

---------
Examples
---------

* **Patient Scenario**:
  John, a discharged patient, needs to manage multiple medications and track his vital signs post-surgery. Using CareCompass, John creates a medication schedule and logs his vital signs regularly. Any changes in his health condition are documented for review during follow-up appointments with his healthcare provider.

* **Caregiver Scenario**:
  Sarah, a caregiver for her elderly mother, uses CareCompass to schedule and manage appointments with healthcare providers and order medical supplies. She also tracks her mother's daily activities and glucose measurements to ensure she maintains an active lifestyle.

---------
Acknowledgements
---------

A million thanks to Jody Alford, Gabe Cziprusz, Jean Soderkvist, Charlie Penner, and the developers of the Amazon Technical Academy curriculum.
