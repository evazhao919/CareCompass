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
        'updateMedicationDetails', 'updateNotificationDetails'];
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
      * Add a song to a playlist.
      * @param id The id of the playlist to add a song to.
      * @param asin The asin that uniquely identifies the album.
      * @param trackNumber The track number of the song on the album.
      * @returns The list of songs on a playlist.
      */
     async addBloodGlucoseMeasurement(BloodGlucoseMeasurementAttributes, errorCallback) {
         try {
             const token = await this.getTokenOrThrow("Only authenticated users can add a song to a blood glucose measurement.");
             const response = await this.axiosClient.post(`playlists/${id}/songs`, {
                 //id: id,
                 //asin: asin,
                 trackNumber: trackNumber
             }, {
                 headers: {
                     Authorization: `Bearer ${token}`
                 }
             });
             return response.data.bloodGlucoseMeasurement;
         } catch (error) {
             this.handleError(error, errorCallback)
         }
     }

    /**
     * Add a song to a playlist.
     * @param id The id of the playlist to add a song to.
     * @param asin The asin that uniquely identifies the album.
     * @param trackNumber The track number of the song on the album.
     * @returns The list of songs on a playlist.
     */
    async addMedication(medicationAttributes, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a medication.");
            const response = await this.axiosClient.post(`playlists/${id}/songs`, {
                //id: id,
                //asin: asin,
                trackNumber: trackNumber
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.songList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Add a song to a playlist.
     * @param id The id of the playlist to add a song to.
     * @param asin The asin that uniquely identifies the album.
     * @param trackNumber The track number of the song on the album.
     * @returns The list of songs on a playlist.
     */
    async addNotification(notificationAttributes, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a notification.");
            const response = await this.axiosClient.post(`playlists/${id}/songs`, {
                //id: id,
                //asin: asin,
                trackNumber: trackNumber
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.notification;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Add a song to a playlist.
     * @param id The id of the playlist to add a song to.
     * @param asin The asin that uniquely identifies the album.
     * @param trackNumber The track number of the song on the album.
     * @returns The list of songs on a playlist.
     */
    async addVitalSigns(vitalSignsAttributes, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a VitalSigns.");
            const response = await this.axiosClient.post(`playlists/${id}/songs`, {
                //id: id,
                //asin: asin,
                trackNumber: trackNumber
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.VitalSigns;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


    async deleteBloodGlucoseMeasurement(actualCheckTime, errorCallback) {
            try {
                const token = await this.getTokenOrThrow("Only authenticated users can delete a blood glucose measurement.");

                const response = await this.axiosClient.delete(`medications/${actualCheckTime}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                    });
                return response.data.bloodGlucoseMeasurements;
            } catch (error) {
                this.handleError(error, errorCallback)
                throw error;
            }
        }
    async deleteMedication(medicationName, errorCallback) {
            try {
                const token = await this.getTokenOrThrow("Only authenticated users can delete a medication.");

                const response = await this.axiosClient.delete(`medications/${actualCheckTime}`, {
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
    async deleteNotification(actualCheckTime, errorCallback) {
            try {
                const token = await this.getTokenOrThrow("Only authenticated users can delete a notification.");

                const response = await this.axiosClient.delete(`notifications/${actualCheckTime}`, {
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
    async deleteVitalSigns(actualCheckTime, errorCallback) {
            try {
                const token = await this.getTokenOrThrow("Only authenticated users can delete a VitalSigns.");

                const response = await this.axiosClient.delete(`VitalSigns/${actualCheckTime}`, {
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
     * Gets the playlist for the given ID.
     * @param id Unique identifier for a playlist
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The playlist's metadata.
     */
    async getAllBloodGlucoseMeasurements(BloodGlucoseMeasurementAttributes or patientId,errorCallback) {
        try {
            const response = await this.axiosClient.get(`playlists/${id}`);
            return response.data.playlist;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async getAllMedications(errorCallback) {
        try {
            const response = await this.axiosClient.get(`playlists/${id}`);
            return response.data.playlist;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async getAllNotifications(errorCallback) {
        try {
            const response = await this.axiosClient.get(`playlists/${id}`);
            return response.data.playlist;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async getAllVitalSigns(errorCallback) {
        try {
            const response = await this.axiosClient.get(`playlists/${id}`);
            return response.data.playlist;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Search for a soong.
     * @param criteria A string containing search criteria to pass to the API.
     * @returns The playlists that match the search criteria.
     */
    async RetrieveAllUpcomingNotifications(criteria, errorCallback) {
        try {
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            const response = await this.axiosClient.get(`playlists/search?${queryString}`);

            return response.data.playlists;
        } catch (error) {
            this.handleError(error, errorCallback)
        }

    }
/**
     * Search for a soong.
     * @param criteria A string containing search criteria to pass to the API.
     * @returns The playlists that match the search criteria.
     */
    async RetrieveMedicationsByStatus(criteria, errorCallback) {
        try {
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            const response = await this.axiosClient.get(`playlists/search?${queryString}`);

            return response.data.playlists;
        } catch (error) {
            this.handleError(error, errorCallback)
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
            this.handleError(error, errorCallback)
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
            this.handleError(error, errorCallback)
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
