package com.devyanan.CareCompass.dependency;

import com.devyanan.CareCompass.activity.*;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Care Compass Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return AddBloodGlucoseMeasurementActivity
     */
    AddBloodGlucoseMeasurementActivity provideAddBloodGlucoseMeasurementActivity();

    /**
     * Provides the relevant activity.
     * @return AddMedicationActivity
     */
    AddMedicationActivity provideAddMedicationActivity();

    /**
     * Provides the relevant activity.
     * @return AddNotificationActivity
     */
    AddNotificationActivity provideAddNotificationActivity();

    /**
     * Provides the relevant activity.
     * @return AddVitalSignsRequestActivity
     */
    AddVitalSignsRequestActivity provideAddVitalSignsRequestActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteBloodGlucoseMeasurementActivity
     */
    DeleteBloodGlucoseMeasurementActivity provideDeleteBloodGlucoseMeasurementActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteMedicationActivity
     */
    DeleteMedicationActivity provideDeleteMedicationActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteNotificationActivity
     */
    DeleteNotificationActivity provideDeleteNotificationActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteVitalSignsActivity
     */
    DeleteVitalSignsActivity provideDeleteVitalSignsActivity();

    /**
     * Provides the relevant activity.
     * @return GetAllBloodGlucoseMeasurementsActivity
     */
    GetAllBloodGlucoseMeasurementsActivity provideGetAllBloodGlucoseMeasurementsActivity();

    /**
     * Provides the relevant activity.
     * @return GetAllMedicationsActivity
     */
    GetAllMedicationsActivity provideGetAllMedicationsActivity();

    /**
     * Provides the relevant activity.
     * @return GetAllNotificationsActivity
     */
    GetAllNotificationsActivity provideGetAllNotificationsActivity();

    /**
     * Provides the relevant activity.
     * @return GetAllVitalSignsActivity
     */
    GetAllVitalSignsActivity provideGetAllVitalSignsActivity();

    /**
     * Provides the relevant activity.
     * @return UpdateNotificationDetailsActivity
     */
    UpdateNotificationDetailsActivity provideUpdateNotificationDetailsActivity();

    /**
     * Provides the relevant activity.
     * @return UpdateMedicationDetailsActivity
     */
    UpdateMedicationDetailsActivity provideUpdateMedicationDetailsActivity();

    /**
     * Provides the relevant activity.
     * @return RetrieveCurrentMedicationsActivity
     */
    RetrieveCurrentMedicationsActivity provideRetrieveCurrentMedicationsActivity();
}
