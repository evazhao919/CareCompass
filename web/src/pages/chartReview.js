import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const RESULTS_BY_STATUS_KEY = 'medication-by-status-results';
const RESULTS_BY_SCHEDULED_TIME_KEY = 'notification-by-scheduled-time-results';

class ChartReview extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods([
            'mount',
            'getMedicationsByStatus',
            'displayMedicationResults',
            'getNotificationsByScheduledTime',
            'displayNotificationResults'
        ], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displayMedicationResults);
        this.dataStore.addChangeListener(this.displayNotificationResults);

        // Instantiate the client and assign it to this.client
        this.client = new CareCompassClient();
    }

    async mount() {
        await this.header.addHeaderToPage();
        document.getElementById('retrieve-medication-by-status-form').addEventListener('click', this.getMedicationsByStatus);
        document.getElementById('retrieve-notification-by-scheduled-time-form').addEventListener('click', this.getNotificationsByScheduledTime);

        this.getMedicationsByStatus();
        this.getNotificationsByScheduledTime();
    }

    async getMedicationsByStatus() {
        const medicationStatus = document.getElementById('medicationStatus').value;

        try {
            const results = await this.client.retrieveMedicationsByStatus(medicationStatus);
            this.dataStore.set(RESULTS_BY_STATUS_KEY, results);
        } catch (error) {
            console.error("Error fetching medications:", error);
        }
    }

    displayMedicationResults() {
        const medicationResults = this.dataStore.get(RESULTS_BY_STATUS_KEY);
        const medicationResultsDisplay = document.getElementById('Status-Table');
        if (medicationResults && medicationResults.medications) {
            medicationResultsDisplay.innerHTML = this.getHTMLForMedicationResults(medicationResults.medications);
        } else {
            medicationResultsDisplay.innerHTML = '<tr><td colspan="4">No medications found.</td></tr>';
        }
    }

    getHTMLForMedicationResults(medications) {
        return medications.map(med => `
            <tr>
                <td>${med.medicationName}</td>
                <td>${med.prescription}</td>
                <td>${med.instructions}</td>
                <td>${med.medicationStatus}</td>
            </tr>
        `).join('');
    }

    async getNotificationsByScheduledTime() {
        const scheduledTime = document.getElementById('scheduledTime').value;

        try {
            const results = await this.client.retrieveAllUpcomingNotifications(scheduledTime);
            this.dataStore.set(RESULTS_BY_SCHEDULED_TIME_KEY, results);
        } catch (error) {
            console.error("Error fetching notifications:", error);
        }
    }

    displayNotificationResults() {
        const notificationResults = this.dataStore.get(RESULTS_BY_SCHEDULED_TIME_KEY);
        const notificationResultsDisplay = document.getElementById('scheduledTime-Table');
        if (notificationResults && notificationResults.notifications) {
            notificationResultsDisplay.innerHTML = this.getHTMLForNotificationResults(notificationResults.notifications);
        } else {
            notificationResultsDisplay.innerHTML = '<tr><td colspan="4">No notifications found.</td></tr>';
        }
    }

    getHTMLForNotificationResults(notifications) {
        return notifications.map(notif => `
            <tr>
                <td>${notif.notificationTitle}</td>
                <td>${notif.reminderContent}</td>
                <td>${notif.scheduledTime}</td>
                <td>${notif.reminderType}</td>
            </tr>
        `).join('');
    }
}

const main = async () => {
    const chartReview = new ChartReview();
    chartReview.mount();
};

window.addEventListener('DOMContentLoaded', main);
