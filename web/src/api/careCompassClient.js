import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the CareCompassService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class CareCompassClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'addBloodGlucoseMeasurement',
        'addMedication', 'addNotification', 'addVitalSigns', 'deleteBloodGlucoseMeasurement', 'deleteMedication',
        'deleteNotification', 'deleteVitalSigns', 'getAllBloodGlucoseMeasurements','getAllMedications',
        'getAllNotifications','getAllVitalSigns','retrieveAllUpcomingNotifications','retrieveMedicationsByStatus',
        'retrieveNotificationsByReminderType', 'updateMedicationDetails', 'updateNotificationDetails'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Asynchronously initiates the login process using the authenticator.
     * @returns {Promise<void>} A Promise that resolves when the login process is initiated.
     */
    async login() {
        this.authenticator.login();
    }

    /**
     * Asynchronously initiates the logout process using the authenticator.
     * @returns {Promise<void>} A Promise that resolves when the logout process is initiated.
     */
    async logout() {
        this.authenticator.logout();
    }

    /**
     * Asynchronously retrieves the user token or throws an error if the user is not authenticated.
     * @param {string} unauthenticatedErrorMessage - The error message to throw if the user is not authenticated.
     * @returns {Promise<string>} A Promise that resolves with the user token if the user is authenticated.
     * @throws {Error} If the user is not authenticated, an error with the specified message is thrown.
     */
    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

     /**
      * Add blood glucose measurement.
      * @param {Object} bloodGlucoseMeasurementDetails - Details of the blood glucose measurement to add.
      * @param {Function} errorCallback - Callback function to handle errors.
      * @returns {Object} A blood glucose measurement that is added.
      */
     async addBloodGlucoseMeasurement(actualCheckTime, glucoseLevel, glucoseContext, comments, errorCallback) {
         try {
             const token = await this.getTokenOrThrow("Only authenticated users can add a blood glucose measurement.");
             const response = await this.axiosClient.post(`bloodGlucoseMeasurements`, {
                  actualCheckTime: actualCheckTime,
                  glucoseLevel: glucoseLevel,
                  glucoseContext: glucoseContext,
                  comments: comments
             }, {
                 headers: {
                     Authorization: `Bearer ${token}`
                 }
             });
             return response.data.bloodGlucoseMeasurement;
         } catch (error) {
             this.handleError(error, errorCallback)
             throw error;
         }
     }

    /**
    * Add a new medication.
     * @param {Object} medicationDetails - Details of the medication to add.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The added medication.
     */
    async addMedication(medicationName, prescription, instructions, medicationStatus, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a medication.");
            const response = await this.axiosClient.post(`medications`, {
                   medicationName: medicationName,
                   prescription: prescription,
                   instructions: instructions,
                   medicationStatus: medicationStatus
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.medication;
        } catch (error) {
            this.handleError(error, errorCallback)
            throw error;
        }
    }

    /**
     * Add a new notification.
     * @param {Object} notificationDetails - Details of the notification to add.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The added notification.
     */
    async addNotification(notificationTitle, reminderContent, scheduledTime, reminderType, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a notification.");
            const response = await this.axiosClient.post(`notifications`, {
                    notificationTitle: notificationTitle,
                    reminderContent: reminderContent,
                    scheduledTime: scheduledTime,
                    reminderType: reminderType
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.notification;
        } catch (error) {
            this.handleError(error, errorCallback)
            throw error;
        }
    }

    /**
      * Add vital signs data.
      * @param {Object} vitalSignsDetails - Details of the vital signs data to add.
      * @param {Function} errorCallback - Callback function to handle errors.
      * @returns {Object} The added vital signs data.
     */
    async addVitalSigns(vitalSignsDetails, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a VitalSigns.");
            const response = await this.axiosClient.post(`vitalSigns`, {
                    actualCheckTime: vitalSignsDetails.actualCheckTime,
                    temperature: vitalSignsDetails.temperature,
                    heartRate: vitalSignsDetails.heartRate,
                    pulse: vitalSignsDetails.pulse,
                    respiratoryRate: vitalSignsDetails.respiratoryRate,
                    systolicPressure: vitalSignsDetails.systolicPressure,
                    diastolicPressure: vitalSignsDetails.diastolicPressure,
                    meanArterialPressure: vitalSignsDetails.meanArterialPressure,
                    weight: vitalSignsDetails.weight,
                    patientPosition: vitalSignsDetails.patientPosition,
                    bloodOxygenLevel: vitalSignsDetails.bloodOxygenLevel,
                    oxygenTherapy: vitalSignsDetails.oxygenTherapy,
                    flowDelivered: vitalSignsDetails.flowDelivered,
                    patientActivity: vitalSignsDetails.patientActivity,
                    comments: vitalSignsDetails.comments
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.VitalSigns;
        } catch (error) {
            this.handleError(error, errorCallback)
            throw error;
        }
    }

    /**
     * Delete a blood glucose measurement.
     * @param {string} actualCheckTime - The actual check time of the blood glucose measurement to delete.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The deleted blood glucose measurement.
     */
    async deleteBloodGlucoseMeasurement(actualCheckTime, errorCallback) {
       try {
           const token = await this.getTokenOrThrow("Only authenticated users can delete a blood glucose measurement.");

           const response = await this.axiosClient.delete(`bloodGlucoseMeasurements/${actualCheckTime}`, {
               headers: {
                   Authorization: `Bearer ${token}`
               }
               });
           return response.data.bloodGlucoseMeasurement;
       } catch (error) {
           this.handleError(error, errorCallback)
           throw error;
       }
    }

    /**
     * Delete a medication by its ID.
     * @param {string} medicationId - The ID of the medication to delete.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The deleted medication.
     */
    async deleteMedication(medicationId, errorCallback) {
            try {
                const token = await this.getTokenOrThrow("Only authenticated users can delete a medication.");

                const response = await this.axiosClient.delete(`medications/${medicationId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                    });
                return response.data.medication;
            } catch (error) {
                this.handleError(error, errorCallback)
                throw error;
            }
        }

    /**
     * Delete a notification by its ID.
     * @param {string} notificationId - The ID of the notification to delete.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The deleted notification.
     */
    async deleteNotification(notificationId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can delete a notification.");

            const response = await this.axiosClient.delete(`notifications/${notificationId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.notification;
        } catch (error) {
            this.handleError(error, errorCallback);
            throw error;
        }
    }

    /**
     * Delete vital signs data by its actual check time.
     * @param {string} actualCheckTime - The actual check time of the vital signs data to delete.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The deleted vital signs data.
     */
    async deleteVitalSigns(actualCheckTime, errorCallback) {
            try {
                const token = await this.getTokenOrThrow("Only authenticated users can delete a VitalSigns.");

                const response = await this.axiosClient.delete(`vitalSigns/${actualCheckTime}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                    });
                return response.data.vitalSigns;
            } catch (error) {
                this.handleError(error, errorCallback)
                throw error;
            }
        }

    /**
     * Retrieves all blood glucose measurements associated with authenticated users.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The list of blood glucose measurements.
     */
    async getAllBloodGlucoseMeasurements(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can see all blood glucose measurements.");
            const response = await this.axiosClient.get(`bloodGlucoseMeasurements`,
            {
                headers: {
                    Authorization: `Bearer ${token}`
                    }
            });
            const result = {
                      bloodGlucoseMeasurements: response.data.bloodGlucoseMeasurementModelList
                    };
                    return result;
                } catch (error) {
                    this.handleError(error, errorCallback);
                    throw error;
                }
                }


    /**
     * Retrieves all medications associated with authenticated users.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The list of medications.
     */
    async getAllMedications(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can see all medications.");
            const response = await this.axiosClient.get(`medications`,
            {
                headers: {
                    Authorization: `Bearer ${token}`
                    }
            });

            const result = {
              medications: response.data.medicationModelList
            };
            return result;
        } catch (error) {
            this.handleError(error, errorCallback);
            throw error;
        }
    }

    /**
     * Retrieves all notifications associated with authenticated users.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The list of notifications.
     */
    async getAllNotifications(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can see all  notifications.");
            const response = await this.axiosClient.get(`notifications`,
            {
                headers: {
                     Authorization: `Bearer ${token}`
                     }
            });

            const result = {
              notifications: response.data.notificationModelList
            };
            return result;
        } catch (error) {
            this.handleError(error, errorCallback);
            throw error;
        }
    }

    /**
     * Retrieves all vital signs associated with authenticated users.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The list of vital signs.
     */
    async getAllVitalSigns(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can see all blood glucose measurements.");
            const response = await this.axiosClient.get(`vitalSigns`,
            {
                 headers: {
                     Authorization: `Bearer ${token}`
                     }
            });

            const result = {
              vitalSigns: response.data.vitalSignModelList
            };
            return result;
        } catch (error) {
            this.handleError(error, errorCallback);
            throw error;
        }
    }

    /**
     * Retrieves all upcoming notifications based on the provided scheduled time.
     * @param {string} scheduledTime - The scheduled time to filter upcoming notifications.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The list of upcoming notifications.
     */
    async retrieveAllUpcomingNotifications(scheduledTime, errorCallback) {
        try {
        const token = await this.getTokenOrThrow("Only authenticated users can get notifications.");

        const response = await this.axiosClient.get(`upcomingNotifications`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: {
          scheduledTime: scheduledTime,
            },
          });

          const result = {
              notifications: response.data.notificationModelList
          };

          return result;
    }
    catch (error) {
       this.handleError(error, errorCallback);
       throw error;
       }
    }

    /**
     * Retrieves medications based on their status.
     * @param {string} medicationStatus - The status of the medications to retrieve.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The list of medications with the specified status.
     */
    async retrieveMedicationsByStatus(medicationStatus, errorCallback) {
        try {

            const token = await this.getTokenOrThrow("Only authenticated users can retrieve all medications by status.");
            const response = await this.axiosClient.get(`medicationsByStatus/${medicationStatus}`);

            return response.data.medicationModelList;
         } catch (error) {
            this.handleError(error, errorCallback)
            throw error;
         }
    }

    /**
     * Retrieves notifications based on the provided reminder type.
     * @param {string} reminderType - The reminder type to filter notifications.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Array} The list of notifications filtered by the reminder type.
     */
    async retrieveNotificationsByReminderType(reminderType, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can retrieve all notifications by reminder type.");
            const response = await this.axiosClient.get(`notificationsByReminderType/${reminderType}`);
            return response.data.notificationModelList;
         } catch (error) {
            this.handleError(error, errorCallback)
            throw error;
         }
    }

    /**
     * Updates the details of a medication.
     * @param {string} medicationId - The ID of the medication to update.
     * @param {string} medicationName - The updated name of the medication.
     * @param {string} prescription - The updated prescription of the medication.
     * @param {string} medicationStatus - The updated status of the medication.
     * @param {string} instructions - The updated instructions for the medication.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The updated medication data.
     */
    async updateMedicationDetails(medicationId, medicationName, prescription, instructions, medicationStatus, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update a medication.");

            const response = await this.axiosClient.put(`medications/${medicationId}`, {
                medicationId: medicationId,
                medicationName: medicationName,
                prescription: prescription,
                instructions: instructions,
                medicationStatus: medicationStatus
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.medication;
        } catch (error) {
            this.handleError(error, errorCallback);
            throw error;
        }
    }

    /**
     * Updates the details of a notification.
     * @param {string} notificationId - The ID of the notification to update.
     * @param {string} notificationTitle - The updated title of the notification.
     * @param {string} reminderType - The updated reminder type of the notification.
     * @param {string} reminderContent - The updated reminder content of the notification.
     * @param {string} scheduledTime - The updated scheduled time of the notification.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The updated notification data.
     */
    async updateNotificationDetails(notificationId, notificationTitle, scheduledTime, reminderType, reminderContent) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update a notification.");

            const response = await this.axiosClient.put(`notifications/${notificationId}`, {
                notificationId: notificationId,
                notificationTitle: notificationTitle,
                reminderContent: reminderContent,
                scheduledTime: scheduledTime,
                reminderType: reminderType
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.notification;
        } catch (error) {
            this.handleError(error, errorCallback);
            throw error;
        }
    }

    async updateBloodGlucoseMeasurementDetails(actualCheckTime, glucoseLevel, glucoseContext, comments, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update a blood glucose measurement.");

            const response = await this.axiosClient.put(`bloodGlucoseMeasurements/${actualCheckTime}`, {
                actualCheckTime: actualCheckTime,
                glucoseLevel: glucoseLevel,
                glucoseContext: glucoseContext,
                instructions: instructions,
                comments: comments
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.bloodGlucoseMeasurement;
        } catch (error) {
            this.handleError(error, errorCallback);
            throw error;
        }
    }

    async updateVitalSignsDetails(actualCheckTime, temperature, heartRate, pulse, respiratoryRate, systolicPressure, diastolicPressure, meanArterialPressure, weight, patientPosition, bloodOxygenLevel, oxygenTherapy, flowDelivered, patientActivity, comments, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update a blood glucose measurement.");
            const response = await this.axiosClient.put(`vitalSigns/${actualCheckTime}`, {
                actualCheckTime: actualCheckTime,
                temperature: temperature,
                heartRate: heartRate,
                pulse: pulse,
                respiratoryRate: respiratoryRate,
                systolicPressure: systolicPressure,
                diastolicPressure: diastolicPressure,
                meanArterialPressure: meanArterialPressure,
                weight: weight,
                patientPosition: patientPosition,
                bloodOxygenLevel: bloodOxygenLevel,
                oxygenTherapy: oxygenTherapy,
                flowDelivered: flowDelivered,
                patientActivity: patientActivity,
                comments: comments
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.vitalSigns;
        } catch (error) {
            this.handleError(error, errorCallback);
            throw error;
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
