import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const RESULTS_KEY = 'blood-results';

class BloodGlucoseMeasurement extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'addBloodGlucoseMeasurement', 'getBloodGlucoseMeasurements', 'deleteBloodGlucoseMeasurement', 'updateToMeasurementForm', 'saveUpdatedMeasurement', 'displayBloodResults', 'getHTMLForBloodResults','confirmDelete'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayBloodResults);
        this.header = new Header(this.dataStore);
         this.client = new CareCompassClient();
        console.log("BloodGlucoseMeasurement constructor");
    }

    async mount() {
        document.getElementById('add').addEventListener('click', this.addBloodGlucoseMeasurement);
        await this.header.addHeaderToPage();
        this.client = new CareCompassClient();
        this.getBloodGlucoseMeasurements();

        // Adding event listener to the table for update and delete buttons
        document.getElementById('View-Table').addEventListener('click', (event) => {
           if (event.target.classList.contains('delete-button')) {
               console.log('Delete button clicked');
                this.confirmDelete(event);
//               this.deleteBloodGlucoseMeasurement(event);
           }
           if (event.target.classList.contains('update-button') || event.target.classList.contains('save-button')) {
               this.updateToMeasurementForm(event);
           }
        });
    }

    async submit(event) {
        event.preventDefault();
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const addBloodGlucoseButton = document.getElementById('add-bloodGlucoseMeasurement-form');
        const showAllBloodGlucoseButton = document.getElementById('search-allBloodGlucoseMeasurements-form');
        const origButtonText = addBloodGlucoseButton.innerText;
        const origSearchButtonText = showAllBloodGlucoseButton.innerText;
        addBloodGlucoseButton.innerText = 'Adding...';
        showAllBloodGlucoseButton.innerText = 'Adding...';

        const actualCheckTime = document.getElementById('actualCheckTime').value;
        const glucoseLevel = document.getElementById('glucoseLevel').value;
        const glucoseContext = document.getElementById('glucoseContext').value;
        const comments = document.getElementById('comments').value;

        try {
            const bloodGlucoseMeasurement = await this.client.addBloodGlucoseMeasurement(actualCheckTime, glucoseLevel, glucoseContext, comments);
            this.dataStore.set('bloodGlucoseMeasurement', bloodGlucoseMeasurement);
        } catch (error) {
            addBloodGlucoseButton.innerText = origButtonText;
            showAllBloodGlucoseButton.innerText = origSearchButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        } finally {
            addBloodGlucoseButton.innerText = origButtonText;
            showAllBloodGlucoseButton.innerText = origSearchButtonText;
        }
    }

    async addBloodGlucoseMeasurement(evt) {
        evt.preventDefault();

        const addButton = document.getElementById('add');
        const origButtonText = addButton.innerText;

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

//        const actualCheckTime = document.getElementById('actualCheckTime').value;
//        const glucoseLevel = document.getElementById('glucoseLevel').value;
//        const glucoseContext = document.getElementById('glucoseContext').value;
//        const comments = document.getElementById('comments').value;
          const actualCheckTimeInput = document.getElementById('actualCheckTime');
          const glucoseLevelInput = document.getElementById('glucoseLevel');
          const glucoseContextInput = document.getElementById('glucoseContext');
          const commentsInput = document.getElementById('comments');

          const actualCheckTime = actualCheckTimeInput.value;
          const glucoseLevel = glucoseLevelInput.value;
          const glucoseContext = glucoseContextInput.value;
          const comments = commentsInput.value;

        if (actualCheckTime.length === 0 || glucoseLevel.length === 0 || glucoseContext.length === 0 || comments.length === 0) {
            return;
        }

        addButton.innerText = 'Adding...';

        try {
            const bloodGlucoseMeasurement = await this.client.addBloodGlucoseMeasurement(actualCheckTime, glucoseLevel, glucoseContext, comments, (() => {}));
            // Clear input fields after successful addition
                    actualCheckTimeInput.value = '';
                    glucoseLevelInput.value = '';
                    glucoseContextInput.value = '';
                    commentsInput.value = '';
              this.getBloodGlucoseMeasurements();
            this.dataStore.set('bloodGlucoseMeasurement', bloodGlucoseMeasurement);

        } catch (error) {
            console.error('Error adding bloodGlucoseMeasurement:', error);
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        } finally {
            addButton.innerText = origButtonText;
        }
    }

    async getBloodGlucoseMeasurements() {
        console.log("Fetching bloodGlucoseMeasurements...");
        const results = await this.client.getAllBloodGlucoseMeasurements();
        console.log("Fetched bloodGlucoseMeasurement:", results);
        this.dataStore.setState({
            [RESULTS_KEY]: results,
        });
    }

    displayBloodResults() {
        const bloodResults = this.dataStore.get(RESULTS_KEY);
        const bloodResultsDisplay = document.getElementById('View-Table');
        bloodResultsDisplay.innerHTML = this.getHTMLForBloodResults(bloodResults.bloodGlucoseMeasurements);
    }

    getHTMLForBloodResults(searchResults) {
        if (searchResults.length === 0) {
            return '<h4>No results found</h4>';
        }

        let html = '<table><tr><th>Actual Check Time</th><th>Glucose Level</th><th>Glucose Context</th><th>Comments</th></tr>';
        for (const res of searchResults) {
            html += `
            <tr data-id="${res.actualCheckTime}">
                <td>${res.actualCheckTime}</td>
                <td class="editable" data-field="glucoseLevel">${res.glucoseLevel}</td>
                <td class="editable" data-field="glucoseContext">${res.glucoseContext}</td>
                <td class="editable" data-field="comments">${res.comments}</td>
                <td>
                    <button class="update-button" data-id="${res.actualCheckTime}">Update</button>
                    <button class="save-button" data-id="${res.actualCheckTime}" style="display:none;">Save</button>
                    <button class="delete-button" data-id="${res.actualCheckTime}">Delete</button>
                </td>
            </tr>`;
        }
        html += '</table>';

        return html;
    }

        updateToMeasurementForm(event) {
            if (event.target.classList.contains('update-button')) {
                const row = event.target.closest('tr');
                row.querySelectorAll('.editable').forEach(cell => {
                    const field = cell.getAttribute('data-field');
                    const value = cell.innerText; // Use textContent for reading initial value
                    cell.innerHTML = `<input type="text" name="${field}" value="${value}" />`; // Use value attribute for input fields
                });
                row.querySelector('.update-button').style.display = 'none';
                row.querySelector('.save-button').style.display = 'inline-block';
            }



        if (event.target.classList.contains('save-button')) {
            const row = event.target.closest('tr');
            const actualCheckTime = event.target.getAttribute('data-id');
            const updatedData = {};
            row.querySelectorAll('.editable').forEach(cell => {
                const field = cell.getAttribute('data-field');
                const input = cell.querySelector('input');
                updatedData[field] = input.value;
                cell.innerHTML = input.value;
            });
            row.querySelector('.update-button').style.display = 'inline-block';
            row.querySelector('.save-button').style.display = 'none';
            this.saveUpdatedMeasurement(actualCheckTime, updatedData);
        }
    }

    async saveUpdatedMeasurement(actualCheckTime, updatedData) {
        try {
            await this.client.updateBloodGlucoseMeasurementDetails(actualCheckTime, updatedData.glucoseLevel, updatedData.glucoseContext, updatedData.comments);
            console.log(`Measurement with Actual Check Time ${actualCheckTime} updated successfully.`);
        } catch (error) {
            console.error(`Error updating measurement with Actual Check Time ${actualCheckTime}:`, error);
        }
    }

    confirmDelete(event) {
     const deleteButton = event.target;
     const actualCheckTime = deleteButton.getAttribute('data-id');

     if (window.confirm(`Are you sure you want to delete actualCheckTime with actualCheckTime     ${actualCheckTime}?`)) {
                 this.deleteBloodGlucoseMeasurement(actualCheckTime);
             }else {
                    return;
                   }
             }

    async deleteBloodGlucoseMeasurement(actualCheckTime) {
        try {
            await this.client.deleteBloodGlucoseMeasurement(actualCheckTime);
            console.log(`BloodGlucoseMeasurement with actualCheckTime ${actualCheckTime} deleted successfully.`);
            this.getBloodGlucoseMeasurements();  // Refresh the list
        } catch (error) {
            console.error(`Error deleting BloodGlucoseMeasurement with actualCheckTime ${actualCheckTime}:`, error);
        }
    }
}

const main = async () => {
    const bloodGlucoseMeasurement = new BloodGlucoseMeasurement();
    bloodGlucoseMeasurement.mount();
};

window.addEventListener('DOMContentLoaded', main);