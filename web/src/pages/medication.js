//import CareCompassClient from '../api/careCompassClient';
//import Header from '../components/header';
//import BindingClass from '../util/bindingClass';
//import DataStore from '../util/DataStore';
//
//const RESULTS_KEY = 'medication-results';
//
//class Medication extends BindingClass {
//    constructor() {
//        super();
//        this.bindClassMethods(['mount', 'submit', 'getMedications', "displayMedicationResults", "getHTMLForMedicationResults"], this);
//        this.dataStore = new DataStore();
//        this.dataStore.addChangeListener(this.displayMedicationResults);
//        this.header = new Header(this.dataStore);
//        console.log("Medication constructor");
//    }
//
//    async mount() {
//        document.getElementById('add-medication-form').addEventListener('click', this.submit);
//
////        document.getElementById('delete').addEventListener('click', this.deleteMedication);//TODO
//        document.getElementById('update-medication-form').addEventListener('click', this.updateMedication);
//        await this.header.addHeaderToPage();
//        this.client = new CareCompassClient();  //kind of DAO
//        this.getMedications();
//    }
//
//async submit(event){//edit add, now we want get things
//        event.preventDefault();
//        const errorMessageDisplay = document.getElementById('error-message');
//        errorMessageDisplay.innerText = ``;
//        errorMessageDisplay.classList.add('hidden');
//
//        const addMedicationButton = document.getElementById('add-medication-form');
//        const showAllMedicationButton = document.getElementById('search-allMedications-form');
//        const origButtonText = addMedicationButton.innerText;
//        const origSearchButtonText = showAllMedicationButton.innerText;
//        addMedicationButton.innerText = 'Loading...';
//        showAllMedicationButton.innerText = 'Loading...';
//
//        const medicationId = document.getElementById('medicationId').value;
//        const medicationName = document.getElementById('medicationName').value;
//        const prescription = document.getElementById('prescription').value;
//        const instructions = document.getElementById('instructions').value;
//        const medicationStatus = document.getElementById('medicationStatus').value;
//
//        try {
//            const medication = await this.client.addMedication(medicationName, prescription, instructions, medicationStatus);
//            this.dataStore.set('medication', medication);
//        } catch (error) {
//            addMedicationButton.innerText = origButtonText;
//            showAllMedicationButton.innerText = origSearchButtonText;
//            errorMessageDisplay.innerText = `Error: ${error.message}`;
//            errorMessageDisplay.classList.remove('hidden');
//        } finally {
//            addMedicationButton.innerText = origButtonText;
//            showAllMedicationButton.innerText = origSearchButtonText;
//        }
//    }
//
//async getMedications() {  //make a call to API download some result, the write to the datastore
//    //      const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY).value; //only you want typing things to search box
//        console.log("Medications results");
//        const results = await this.client.getAllMedications(()=>{}) //TODO
//        console.log("Medications results",results);
//    //             createButton.innerText = origButtonText; // 恢复按钮原始文本
//    //             errorMessageDisplay.innerText = `Error: ${error.message}`; // 设置错误消息文本
//    //             errorMessageDisplay.classList.remove('hidden'); // 显示错误消息区域});  //empty
//           this.dataStore.setState({
//                    [RESULTS_KEY]: results,
//           });
//    }
//
//displayMedicationResults() {
//
//        const medicationResults = this.dataStore.get(RESULTS_KEY);
//        const medicationResultsDisplay = document.getElementById('View-Table');//table
//        medicationResultsDisplay.innerHTML = this.getHTMLForMedicationResults(medicationResults.medications);//
//        const medicationResultsTable = document.getElementById('medication-results-table');
//        console.log("medicationResultsTable" + JSON.stringify(medicationResultsTable));//jody ++
//        medicationResultsTable.addEventListener('click', this.updateToMedicationForm);
//    }
//
//getHTMLForMedicationResults(searchResults) {
//       if (searchResults.length === 0) {
//            return '<h4>No results found</h4>';
//       }
//
//       let html = '<table id="medication-results-table"><tr><th>Medication Id</th><th>Medication Name</th><th>Status</th><th>Prescription</th><th>Instructions</th></th></tr>';
//       for (const res of searchResults) {
//       html += `
//       <tr data-id="${res.medicationId}">
//          <td>${res.medicationId}</td>
//          <td>${res.medicationName}</td>
//          <td>${res.prescription}</td>
//          <td>${res.instructions}</td>
//          <td>${res.medicationStatus}</td>
//          <td>
//          <button class="update-button" data-id="${res.medicationId}">Update</button>
//          <button class="delete-button" data-id="${res.medicationId}">Delete</button>
//          </td>
//       </tr>`;
//   }
//   html += '</table>';
//
//   return html;
//}
//
//updateToMedicationForm(event){
//event.preventDefault();
//console.log("Event!!!!!" + JSON.stringify(event));
//
//
//}
//
//
//
//}
//
//
//
//
//
//
//const main = async () => {
//    const medication = new Medication();
//    medication.mount();
//};
//
//window.addEventListener('DOMContentLoaded', main);
//
//




import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const RESULTS_KEY = 'medication-results';

class Medication extends BindingClass {//TODO do not touch
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'getMedications', "displayMedicationResults", "getHTMLForMedicationResults", "updateToMedicationForm", "saveUpdatedMedication"], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayMedicationResults);
        this.header = new Header(this.dataStore);
        console.log("Medication constructor");
    }

    async mount() {//TODO do not touch
        document.getElementById('add-medication-form').addEventListener('click', this.submit);
      //document.getElementById('delete').addEventListener('click', this.deleteMedication);//TODO
        document.getElementById('update-medication-form').addEventListener('click', this.updateMedication);
        await this.header.addHeaderToPage();
        this.client = new CareCompassClient();  // kind of DAO
        this.getMedications();
    }

    async submit(event) { //edit add, now we want get things//TODO do not touch
        event.preventDefault();
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');


        const addMedicationButton = document.getElementById('add-medication-form');
        const showAllMedicationButton = document.getElementById('search-allMedications-form');
        const origButtonText = addMedicationButton.innerText;
        const origSearchButtonText = showAllMedicationButton.innerText;
        addMedicationButton.innerText = 'Loading...';
        showAllMedicationButton.innerText = 'Loading...';

        const medicationId = document.getElementById('medicationId').value;
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

    async getMedications() {//TODO do not touch
        console.log("Medications results");
        const results = await this.client.getAllMedications(() => {});  //TODO
        console.log("Medications results", results);
         //             createButton.innerText = origButtonText; // 恢复按钮原始文本
        //    //             errorMessageDisplay.innerText = `Error: ${error.message}`; // 设置错误消息文本
        //    //             errorMessageDisplay.classList.remove('hidden'); // 显示错误消息区域});  //empty
        this.dataStore.setState({
            [RESULTS_KEY]: results,
        });
    }

    displayMedicationResults() {//TODO do not touch
        const medicationResults = this.dataStore.get(RESULTS_KEY);
        const medicationResultsDisplay = document.getElementById('View-Table');
        medicationResultsDisplay.innerHTML = this.getHTMLForMedicationResults(medicationResults.medications);
        // Delegate the event listener for dynamically created elements
        medicationResultsDisplay.addEventListener('click', this.updateToMedicationForm);
    }

    getHTMLForMedicationResults(searchResults) {//TODO do not touch
        if (searchResults.length === 0) {
            return '<h4>No results found</h4>';
        }

        let html = '<table id="medication-results-table"><tr><th>Medication Id</th><th>Medication Name</th><th>Prescription</th><th>Instructions</th><th>Status</th><th>Actions</th></tr>';
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
                </td>
            </tr>`;
        }
        html += '</table>';

        return html;
    }

    updateToMedicationForm(event) {//TODO do not touch
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

    async saveUpdatedMedication(medicationId, updatedData) {//TODO do not touch
        try {
            await this.client.updateMedicationDetails(medicationId, updatedData.medicationName, updatedData.prescription, updatedData.instructions, updatedData.medicationStatus);
            console.log(`Medication with ID ${medicationId} updated successfully.`);
        } catch (error) {
            console.error(`Error updating medication with ID ${medicationId}:`, error);
        }
    }
}











const main = async () => {//TODO do not touch
    const medication = new Medication();
    medication.mount();
};

window.addEventListener('DOMContentLoaded', main);

