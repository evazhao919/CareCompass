import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class AddBloodGlucoseMeasurement extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'showSuccessMessageAndRedirect', 'showFailMessageRedirect'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToChartReview);
        this.header = new Header(this.dataStore);
    }

    /**
     * Method to mount the AddBloodGlucoseMeasurement component.
     * Adds header to the page and sets up event listeners.
     */
    async mount() {
        await this.header.addHeaderToPage();
        this.client = new CareCompassClient();
        document.getElementById('add-bloodGlucoseMeasurement-form').addEventListener('submit', this.submit);
    }

    /**
    * Method to handle form submission for adding blood glucose measurement.
    * Prevents default form submission, prompts user confirmation,
    * sends data to the server, and handles success/failure responses.
    * @param {Event} event - The form submission event
    */
    async submit(event) {
        event.preventDefault();

        const userConfirmed = window.confirm('Are you sure you want to add this blood glucose measurement?');

        if (!userConfirmed) {
            return;
        }

        // Show the loading message while processing
        const loadingMessage = document.getElementById('loading-message');
        loadingMessage.style.display = 'block';

        const actualCheckTime = document.getElementById('actualCheckTime').value;
        const glucoseLevel = document.getElementById('glucoseLevel').value;
        const glucoseContext = document.getElementById('glucoseContext').value;
        const comments = document.getElementById('comments').value;

        const bloodGlucoseMeasurementDetails = {
            actualCheckTime: actualCheckTime,
            glucoseLevel: glucoseLevel,
            glucoseContext: glucoseContext,
            comments: comments
        };

        try {
            const addBloodGlucoseMeasurement = await this.client.addBloodGlucoseMeasurement(bloodGlucoseMeasurementDetails);
            this.showSuccessMessageAndRedirect();
        } catch (error) {
            console.error("Error adding blood glucose measurement: ", error);
            this.showFailMessageRedirect();
        } finally {
            // Hide the loading message after processing
            loadingMessage.style.display = 'none';
        }
    }

    redirectToGetAllBloodGlucoseMeasurements() {
        const allBloodGlucoseMeasurements = this.dataStore.get('AllBloodGlucoseMeasurements');
        if (allBloodGlucoseMeasurements !== null && allBloodGlucoseMeasurements !== undefined) {
            window.location.href = '/bloodGlucoseMeasurements';
        }
    }

    showSuccessMessageAndRedirect() {
        alert('Blood glucose measurement added successfully.');
        window.location.href = '/bloodGlucoseMeasurements';
    }

    showFailMessageRedirect() {
        alert('Failed to add blood glucose measurement. Please try again.');
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const addBloodGlucoseMeasurement = new AddBloodGlucoseMeasurement();
    await addBloodGlucoseMeasurement.mount();
};

window.addEventListener('DOMContentLoaded', main);


