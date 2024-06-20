import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const RESULTS_KEY = 'medication-results';

class Medication extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'getMedications', 'displayMedicationResults', 'getHTMLForMedicationResults', 'updateToMedicationForm', 'saveUpdatedMedication', 'addMedication', 'deleteMedication','confirmDelete'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayMedicationResults);
        this.header = new Header(this.dataStore);
        this.client = new CareCompassClient();
        console.log("Medication constructor");

    }

    async mount() {
        document.getElementById('add').addEventListener('click', this.addMedication);
        await this.header.addHeaderToPage();
        this.client = new CareCompassClient();
        this.getMedications();

        // Adding event listener to the table for update and delete buttons
        document.getElementById('View-Table').addEventListener('click', (event) => {
            if (event.target.classList.contains('delete-button')) {
                console.log('Delete button clicked');
                //this.deleteMedication(event);
                this.confirmDelete(event);
            }
            if (event.target.classList.contains('update-button') || event.target.classList.contains('save-button')) {
                this.updateToMedicationForm(event);
            }
        });
    }

   async submit(event) {
        event.preventDefault();
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const addMedicationButton = document.getElementById('add-medication-form');
        const showAllMedicationButton = document.getElementById('search-allMedications-form');
        const origButtonText = addMedicationButton.innerText;
        const origSearchButtonText = showAllMedicationButton.innerText;
        addMedicationButton.innerText = 'Adding...';
        showAllMedicationButton.innerText = 'Adding...';

        const medicationName = document.getElementById('medicationName').value;
        const prescription = document.getElementById('prescription').value;
        const instructions = document.getElementById('instructions').value;
        const medicationStatus = document.getElementById('medicationStatus').value;

        try {
            const medication = await this.client.addMedication(medicationName, prescription, instructions, medicationStatus);
            this.dataStore.set('medication', medication);
        } catch (error) {
            addMedicationButton.innerText = origButtonText;
            showAllMedicationButton.innerText = origSearchButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        } finally {
            addMedicationButton.innerText = origButtonText;
            showAllMedicationButton.innerText = origSearchButtonText;
        }
    }

async addMedication(evt) {
    evt.preventDefault();

    const addButton = document.getElementById('add');
    const origButtonText = addButton.innerText;

    const errorMessageDisplay = document.getElementById('error-message');
    errorMessageDisplay.innerText = '';
    errorMessageDisplay.classList.add('hidden');
//
//    const medicationName = document.getElementById('medicationName').value;
//    const prescription = document.getElementById('prescription').value;
//    const instructions = document.getElementById('instructions').value;
//    const medicationStatus = document.getElementById('medicationStatus').value;
       const medicationNameInput = document.getElementById('medicationName');
           const prescriptionInput = document.getElementById('prescription');
           const instructionsInput = document.getElementById('instructions');
           const medicationStatusInput = document.getElementById('medicationStatus');

           const medicationName = medicationNameInput.value;
           const prescription = prescriptionInput.value;
           const instructions = instructionsInput.value;
           const medicationStatus = medicationStatusInput.value;


    if (medicationName.length === 0 || prescription.length === 0 || instructions.length === 0 || medicationStatus.length === 0) {
        return;
    }

    addButton.innerText = 'Adding...';

    try {
        const medication = await this.client.addMedication(medicationName, prescription, instructions, medicationStatus,(()=>{}));
         // Clear input fields after successful addition
                medicationNameInput.value = '';
                prescriptionInput.value = '';
                instructionsInput.value = '';
                medicationStatusInput.value = '';

        this.getMedications();
        this.dataStore.set('medication', medication);

    } catch (error) {
        console.error('Error adding medication:', error);
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
    } finally {
        addButton.innerText = origButtonText;
    }
}



    async getMedications() {
        console.log("Fetching medications...");
        const results = await this.client.getAllMedications();
        console.log("Fetched medications:", results);
        this.dataStore.setState({
            [RESULTS_KEY]: results,
        });
    }

    displayMedicationResults() {
        const medicationResults = this.dataStore.get(RESULTS_KEY);
        const medicationResultsDisplay = document.getElementById('View-Table');
        medicationResultsDisplay.innerHTML = this.getHTMLForMedicationResults(medicationResults.medications);
    }

    getHTMLForMedicationResults(searchResults) {
        if (searchResults.length === 0) {
            return '<h4>No results found</h4>';
        }

        let html = '<table id="medication-results-table"><tr><th>Medication Id</th><th>Medication Name</th><th>Prescription</th><th>Instructions</th><th>Status</th></tr>';
        for (const res of searchResults) {
            html += `
            <tr data-id="${res.medicationId}">
                <td>${res.medicationId}</td>
                <td class="editable" data-field="medicationName">${res.medicationName}</td>
                <td class="editable" data-field="prescription">${res.prescription}</td>
                <td class="editable" data-field="instructions">${res.instructions}</td>
                <td class="editable" data-field="medicationStatus">${res.medicationStatus}</td>
                <td>
                    <button class="update-button" data-id="${res.medicationId}">Update</button>
                    <button class="save-button" data-id="${res.medicationId}" style="display:none;">Save</button>
                    <button class="delete-button" data-id="${res.medicationId}">Delete</button>
                </td>
            </tr>`;
        }
        html += '</table>';

        return html;
    }

    updateToMedicationForm(event) {
        if (event.target.classList.contains('update-button')) {
            const row = event.target.closest('tr');
            row.querySelectorAll('.editable').forEach(cell => {
                const field = cell.getAttribute('data-field');
                const value = cell.innerText;
                cell.innerHTML = `<input type="text" name="${field}" value="${value}" />`;
            });
            row.querySelector('.update-button').style.display = 'none';
            row.querySelector('.save-button').style.display = 'inline-block';
        }

        if (event.target.classList.contains('save-button')) {
            const row = event.target.closest('tr');
            const medicationId = event.target.getAttribute('data-id');
            const updatedData = {};
            row.querySelectorAll('.editable').forEach(cell => {
                const field = cell.getAttribute('data-field');
                const input = cell.querySelector('input');
                updatedData[field] = input.value;
                cell.innerHTML = input.value;
            });
            row.querySelector('.update-button').style.display = 'inline-block';
            row.querySelector('.save-button').style.display = 'none';
            this.saveUpdatedMedication(medicationId, updatedData);
        }
    }

    async saveUpdatedMedication(medicationId, updatedData) {
        try {
            await this.client.updateMedicationDetails(medicationId, updatedData.medicationName, updatedData.prescription, updatedData.instructions, updatedData.medicationStatus);
            console.log(`Medication with ID ${medicationId} updated successfully.`);
        } catch (error) {
            console.error(`Error updating medication with ID ${medicationId}:`, error);
        }
    }

     confirmDelete(event) {
        const deleteButton = event.target;
        const medicationId = deleteButton.getAttribute('data-id');

        if (window.confirm(`Are you sure you want to delete medication with ID ${medicationId}?`)) {
                     this.deleteMedication(medicationId);
                 }else {
                        return;
                       }
                 }
     async deleteMedication(medicationId) {
        try {
            await this.client.deleteMedication(medicationId);
                  this.getMedications();  // Refresh the list
            } catch (error) {
                  console.error(`Error deleting medication with ID ${medicationId}:`, error);
          }
     }
}

const main = async () => {
    const medication = new Medication();
    medication.mount();
};

window.addEventListener('DOMContentLoaded', main);

