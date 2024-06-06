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
        'getAllNotifications','getAllVitalSigns','retrieveAllUpcomingNotifications','RetrieveMedicationsByStatus',
        'retrieveNotificationsByReminderType', 'updateMedicationDetails', 'updateNotificationDetails'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
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

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

     /**
      * Add blood glucose measurement.
      * @param {Object} bloodGlucoseMeasurementAttributes - Attributes of the blood glucose measurement to add.
      * @param {Function} errorCallback - Callback function to handle errors.
      * @returns {Object} A blood glucose measurement that is added.
      */
     async addBloodGlucoseMeasurement(bloodGlucoseMeasurementAttributes, errorCallback) {
         try {
             const token = await this.getTokenOrThrow("Only authenticated users can add a blood glucose measurement.");
             const response = await this.axiosClient.post(`bloodGlucoseMeasurements`, {
                  actualCheckTime：bloodGlucoseMeasurementAttributes.actualCheckTime,
                  glucoseLevel: bloodGlucoseMeasurementAttributes.glucoseLevel,
                  glucoseContext: bloodGlucoseMeasurementAttributes.glucoseContext,
                  comments: bloodGlucoseMeasurementAttributes.comments
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
     * @param {Object} medicationAttributes - Attributes of the medication to add.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The added medication.
     */
    async addMedication(medicationAttributes, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a medication.");
            const response = await this.axiosClient.post(`medications`, {
                   //  TODO ?  private final String medicationId;
                   medicationName: medicationAttributes.medicationName,
                   prescription: medicationAttributes.prescription,
                   instructions: medicationAttributes.instructions,
                   medicationStatus: medicationAttributes.medicationStatus
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.medications;
        } catch (error) {
            this.handleError(error, errorCallback)
            throw error;
        }
    }

    /**
     * Add a new notification.
     * @param {Object} notificationAttributes - Attributes of the notification to add.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The added notification.
     */
    async addNotification(notificationAttributes, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a notification.");
            const response = await this.axiosClient.post(`notifications`, {
                //TODO    private final String notificationId;
                    notificationTitle: notificationAttributes.notificationTitle,
                    reminderContent: notificationAttributes.reminderContent,
                    scheduledTime: notificationAttributes.scheduledTime,
                    reminderType: notificationAttributes.reminderType
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
      * @param {Object} vitalSignsAttributes - Attributes of the vital signs data to add.
      * @param {Function} errorCallback - Callback function to handle errors.
      * @returns {Object} The added vital signs data.
     */
    async addVitalSigns(vitalSignsAttributes, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a VitalSigns.");
            const response = await this.axiosClient.post(`vitalSigns`, {
                    actualCheckTime: vitalSignsAttributes.actualCheckTime,
                    temperature: vitalSignsAttributes.temperature,
                    heartRate: vitalSignsAttributes.heartRate,
                    pulse: vitalSignsAttributes.pulse,
                    respiratoryRate: vitalSignsAttributes.respiratoryRate,
                    systolicPressure: vitalSignsAttributes.systolicPressure,
                    diastolicPressure: vitalSignsAttributes.diastolicPressure,
                    meanArterialPressure: vitalSignsAttributes.meanArterialPressure,
                    weight: vitalSignsAttributes.weight,
                    patientPosition: vitalSignsAttributes.patientPosition,
                    bloodOxygenLevel: vitalSignsAttributes.bloodOxygenLevel,
                    oxygenTherapy: vitalSignsAttributes.oxygenTherapy,
                    flowDelivered: vitalSignsAttributes.flowDelivered,
                    patientActivity: vitalSignsAttributes.patientActivity,
                    comments: vitalSignsAttributes.comments
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
              medications: response.data.vitalSignModelList
            };
            return result;
        } catch (error) {
            this.handleError(error, errorCallback);
            throw error;
        }
    }

//TODO  ???not sure......this is right,cause may do not enter the time may enter the time
    /**
     * Retrieves all upcoming notifications based on the provided scheduled time.
     * @param {string} scheduledTime - The scheduled time to filter upcoming notifications.
     * @param {Function} errorCallback - Callback function to handle errors.
     * @returns {Object} The list of upcoming notifications.
     */
    async RetrieveAllUpcomingNotifications(scheduledTime, errorCallback) {
        try {
        const token = await this.getTokenOrThrow("Only authenticated users can get notifications.");

        const response = await this.axiosClient.get(`upcomingNotifications`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: {
          scheduledTime: scheduledTime,   //TODO not sure  i should name it in request class like filterByTime
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

    async RetrieveMedicationsByStatus(criteria, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can retrieve all medications by status.");
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            const response = await this.axiosClient.get(`playlists/search?${queryString}`);

            return response.data.playlists;
        } catch (error) {
            this.handleError(error, errorCallback);
            throw error;
        }

    }

    async RetrieveNotificationsByReminderType(criteria, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can retrieve all notifications by reminder type.");
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            const response = await this.axiosClient.get(`playlists/search?${queryString}`);

            return response.data.playlists;
        } catch (error) {
            this.handleError(error, errorCallback);
            throw error;
        }

    }

    async UpdateMedicationDetails(medName, medInfo, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update a medication.");

            const response = await this.axiosClient.put(`medications/${medName}`, {
                medName: medName,
                medInfo: medInfo
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

    async UpdateNotificationDetails(medName, medInfo, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update a notification.");

            const response = await this.axiosClient.put(`medications/${medName}`, {
                medName: medName,
                medInfo: medInfo
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
