import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class RemoveBloodGlucoseMeasurement extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'populateBloodGlucoseMeasurementDropdown', 'showSuccessMessageAndRedirect', 'showFailMessageRedirect'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }

    async mount() {
        await this.header.addHeaderToPage();
        this.client = new CareCompassClient();
        await this.populateBloodGlucoseMeasurementDropdown();
        document.getElementById('remove-bloodGlucoseMeasurement-form').addEventListener('submit', (event) => this.submit(event));
    }

    async populateBloodGlucoseMeasurementDropdown() {
        const actualCheckTimeDropdown = document.getElementById('actualCheckTime');
        try {
            const bloodGlucoseMeasurementsResponse = await this.client.getBloodGlucoseMeasurement();
            console.log('BloodGlucoseMeasurements:', bloodGlucoseMeasurementsResponse);

            const bloodGlucoseMeasurementsArray = Array.isArray(bloodGlucoseMeasurementsResponse.bloodGlucoseMeasurement)
                ? bloodGlucoseMeasurementsResponse.bloodGlucoseMeasurement
                : [];
            console.log('BloodGlucoseMeasurements:', bloodGlucoseMeasurementsArray);

            bloodGlucoseMeasurementsArray.forEach((bloodGlucoseMeasurement) => {
                const option = document.createElement('option');
                option.value = bloodGlucoseMeasurement.actualCheckTime;
                option.text = bloodGlucoseMeasurement.actualCheckTime;
                actualCheckTimeDropdown.add(option);
            });
        } catch (error) {
            console.error('Error fetching blood glucose measurements:', error);
        }
    }

    async submit(event) {
        event.preventDefault();

        const userConfirmed = window.confirm('Are you sure you want to remove this blood glucose measurement?');

        if (!userConfirmed) {
            return;
        }

        const loadingMessage = document.getElementById('loading-message');
        loadingMessage.style.display = 'block';

        const actualCheckTime = document.getElementById('actualCheckTime').value;

        try {
            await this.client.removeBloodGlucoseMeasurement(actualCheckTime);
            this.showSuccessMessageAndRedirect();
        } catch (error) {
            console.error("Error removing blood glucose measurement:", error);
            this.showFailMessageRedirect();
        } finally {
            loadingMessage.style.display = 'none';
        }
    }

    showSuccessMessageAndRedirect() {
        alert('Blood glucose measurement removed successfully.');
        window.location.href = '/bloodGlucoseMeasurements';
    }

    showFailMessageRedirect() {
        alert('Failed to remove blood glucose measurement. Please try again.');
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const removeBloodGlucoseMeasurement = new RemoveBloodGlucoseMeasurement();
    await removeBloodGlucoseMeasurement.mount();
};

window.addEventListener('DOMContentLoaded', main);
