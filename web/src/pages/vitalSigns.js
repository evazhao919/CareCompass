import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const RESULTS_KEY = 'vitalSigns-results';

class VitalSigns extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'addVitalSigns', 'getVitalSigns', 'displayVitalSignsResults', 'getHTMLForVitalSignsResults', 'updateToVitalSignsForm', 'saveUpdatedVitalSigns','deleteVitalSigns'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayVitalSignsResults);
        this.header = new Header(this.dataStore);
        this.client = new CareCompassClient();
        console.log("VitalSigns constructor");
    }

    async mount() {
          document.getElementById('add').addEventListener('click', this.addVitalSigns);
        await this.header.addHeaderToPage();
        this.client = new CareCompassClient();
        this.getVitalSigns();

document.getElementById('add').addEventListener('submit', this.addVitalSigns);
document.getElementById('View-Table').addEventListener('click', (event) => {
    if (event.target.classList.contains('delete-button')) {
        this.deleteVitalSigns(event);
    }
    if (event.target.classList.contains('update-button') || event.target.classList.contains('save-button')) {
        this.updateToVitalSignsForm(event);
    }
});

    }

    async submit(event) {
        event.preventDefault();
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const addVitalSignsButton = document.getElementById('add-vitalSigns-form');
        const showAllVitalSignsButton = document.getElementById('search-allVitalSigns-form');
        const origButtonText = addVitalSignsButton.innerText;
        const origSearchButtonText = showAllVitalSignsButton.innerText;
        addVitalSignsButton.innerText = 'Loading...';
        showAllVitalSignsButton.innerText = 'Loading...';

        const vitalSignsDetails = {
            actualCheckTime: document.getElementById('actualCheckTime').value,
            temperature: document.getElementById('temperature').value,
            heartRate: document.getElementById('heartRate').value,
            pulse: document.getElementById('pulse').value,
            respiratoryRate: document.getElementById('respiratoryRate').value,
            systolicPressure: document.getElementById('systolicPressure').value,
            diastolicPressure: document.getElementById('diastolicPressure').value,
            meanArterialPressure: document.getElementById('meanArterialPressure').value,
            weight: document.getElementById('weight').value,
            patientPosition: document.getElementById('patientPosition').value,
            bloodOxygenLevel: document.getElementById('bloodOxygenLevel').value,
            oxygenTherapy: document.getElementById('oxygenTherapy').value,
            flowDelivered: document.getElementById('flowDelivered').value,
            patientActivity: document.getElementById('patientActivity').value,
            comments: document.getElementById('comments').value
        };

        try {
            const vitalSigns = await this.client.addVitalSigns(vitalSignsDetails);

            this.dataStore.set('vitalSigns', vitalSigns);
        } catch (error) {
            addVitalSigns.innerText = origButtonText;
            showAllVitalSignsButton.innerText = origSearchButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        } finally {
            addVitalSignsButton.innerText = origButtonText;
            showAllVitalSignsButton.innerText = origSearchButtonText;
        }
    }

    async addVitalSigns(evt) {
        evt.preventDefault();
debugger;
        const addButton = document.getElementById('add');
        const origButtonText = addButton.innerText;

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

//        const vitalSignsDetails = {
//            actualCheckTime: document.getElementById('actualCheckTime').value,
//            temperature: document.getElementById('temperature').value,
//            heartRate: document.getElementById('heartRate').value,
//            pulse: document.getElementById('pulse').value,
//            respiratoryRate: document.getElementById('respiratoryRate').value,
//            systolicPressure: document.getElementById('systolicPressure').value,
//            diastolicPressure: document.getElementById('diastolicPressure').value,
//            meanArterialPressure: document.getElementById('meanArterialPressure').value,
//            weight: document.getElementById('weight').value,
//            patientPosition: document.getElementById('patientPosition').value,
//            bloodOxygenLevel: document.getElementById('bloodOxygenLevel').value,
//            oxygenTherapy: document.getElementById('oxygenTherapy').value,
//            flowDelivered: document.getElementById('flowDelivered').value,
//            patientActivity: document.getElementById('patientActivity').value,
//            comments: document.getElementById('comments').value
//        };
         const actualCheckTimeInput = document.getElementById('actualCheckTime'); //Inputs prepare for clean up
             const temperatureInput = document.getElementById('temperature');
             const heartRateInput = document.getElementById('heartRate');
             const pulseInput = document.getElementById('pulse');
             const respiratoryRateInput = document.getElementById('respiratoryRate');
             const systolicPressureInput = document.getElementById('systolicPressure');
             const diastolicPressureInput = document.getElementById('diastolicPressure');
             const meanArterialPressureInput = document.getElementById('meanArterialPressure');
             const weightInput = document.getElementById('weight');
             const patientPositionInput = document.getElementById('patientPosition');
             const bloodOxygenLevelInput = document.getElementById('bloodOxygenLevel');
             const oxygenTherapyInput = document.getElementById('oxygenTherapy');
             const flowDeliveredInput = document.getElementById('flowDelivered');
             const patientActivityInput = document.getElementById('patientActivity');
             const commentsInput = document.getElementById('comments');

             const vitalSignsDetails = {
                 actualCheckTime: actualCheckTimeInput.value,
                 temperature: temperatureInput.value,
                 heartRate: heartRateInput.value,
                 pulse: pulseInput.value,
                 respiratoryRate: respiratoryRateInput.value,
                 systolicPressure: systolicPressureInput.value,
                 diastolicPressure: diastolicPressureInput.value,
                 meanArterialPressure: meanArterialPressureInput.value,
                 weight: weightInput.value,
                 patientPosition: patientPositionInput.value,
                 bloodOxygenLevel: bloodOxygenLevelInput.value,
                 oxygenTherapy: oxygenTherapyInput.value,
                 flowDelivered: flowDeliveredInput.value,
                 patientActivity: patientActivityInput.value,
                 comments: commentsInput.value
             }
        if (actualCheckTime.length === 0 ||temperature.length === 0 ||heartRate.length === 0 ||pulse.length === 0||respiratoryRate.length === 0 ||systolicPressure.length === 0 ||diastolicPressure.length === 0 ||meanArterialPressure.length === 0 ||weight.length === 0 ||patientPosition.length === 0 ||bloodOxygenLevel.length === 0 ||oxygenTherapy.length === 0 ||flowDelivered.length === 0 ||patientActivity.length === 0 ||comments.length === 0) {
            return;
        }

        addButton.innerText = 'Loading...';

        try {

            const vitalSigns = await this.client.addVitalSigns(vitalSignsDetails,(()=>{}));
             actualCheckTimeInput.value = '';   // Clear input fields after successful addition
                    temperatureInput.value = '';
                    heartRateInput.value = '';
                    pulseInput.value = '';
                    respiratoryRateInput.value = '';
                    systolicPressureInput.value = '';
                    diastolicPressureInput.value = '';
                    meanArterialPressureInput.value = '';
                    weightInput.value = '';
                    patientPositionInput.value = '';
                    bloodOxygenLevelInput.value = '';
                    oxygenTherapyInput.value = '';
                    flowDeliveredInput.value = '';
                    patientActivityInput.value = '';
                    commentsInput.value = '';

                  this.getVitalSigns();  // Refresh the list
            this.dataStore.set('vitalSigns', vitalSigns);
        } catch (error) {
            console.error('Error adding vitalSigns:', error);
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        } finally {
            addButton.innerText = origButtonText;
        }
    }

    async getVitalSigns() {
        console.log("Fetching vitalSigns...");
        const results = await this.client.getAllVitalSigns();
        console.log("Fetched vitalSigns:", results);
        this.dataStore.setState({
            [RESULTS_KEY]: results,
        });
    }

    displayVitalSignsResults() {
        const vitalSignsResults = this.dataStore.get(RESULTS_KEY);
        const vitalSignsResultsDisplay = document.getElementById('View-Table');
        vitalSignsResultsDisplay.innerHTML = this.getHTMLForVitalSignsResults(vitalSignsResults.vitalSigns);
    }

   getHTMLForVitalSignsResults(searchResults) {
       if (searchResults.length === 0) {
           return '<h4>No results found</h4>';
       }

       let html = '<table id="vitalSigns-results-table"><tr><th>CheckTime</th><th>Temp</th><th>HeartR</th><th>Pulse</th><th>RespR</th><th>SysBP</th><th>DiaBP</th><th>MAP</th><th>Weight</th><th>PatientPosition</th><th>SpO2 (%)</th><th>OxygenTherapy</th><th>FlowDelivered</th><th>PatientActivity</th><th>Comments</th></tr>';
       for (const res of searchResults) {
           html += `
           <tr data-id="${res.actualCheckTime}">
               <td>${res.actualCheckTime}</td>
               <td class="editable" data-field="temperature">${res.temperature}</td>
               <td class="editable" data-field="heartRate">${res.heartRate}</td>
               <td class="editable" data-field="pulse">${res.pulse}</td>
               <td class="editable" data-field="respiratoryRate">${res.respiratoryRate}</td>
               <td class="editable" data-field="systolicPressure">${res.systolicPressure}</td>
               <td class="editable" data-field="diastolicPressure">${res.diastolicPressure}</td>
               <td class="editable" data-field="meanArterialPressure">${res.meanArterialPressure}</td>
               <td class="editable" data-field="weight">${res.weight}</td>
               <td class="editable" data-field="patientPosition">${res.patientPosition}</td>
               <td class="editable" data-field="bloodOxygenLevel">${res.bloodOxygenLevel}</td>
               <td class="editable" data-field="oxygenTherapy">${res.oxygenTherapy}</td>
               <td class="editable" data-field="flowDelivered">${res.flowDelivered}</td>
               <td class="editable" data-field="patientActivity">${res.patientActivity}</td>
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

    updateToVitalSignsForm(event) {
//        if (event.target.classList.contains('update-button')) {
//            const row = event.target.closest('tr');
//            row.querySelectorAll('.editable').forEach(cell => {
//                const field = cell.getAttribute('data-field');
//                const value = cell.innerText;
//                cell.innerHTML = `<input type="text" name="${field}" value="${value}" />`;
//            });
//            row.querySelector('.update-button').style.display = 'none';
//            row.querySelector('.save-button').style.display = 'inline-block';
//        }
if (event.target.classList.contains('update-button')) {
        const row = event.target.closest('tr');
        row.querySelectorAll('.editable').forEach(cell => {
            const field = cell.getAttribute('data-field');
            const value = cell.innerText.trim(); // Trim any leading/trailing spaces


            cell.innerHTML = `<input type="text" class="form-control" name="${field}" value="${value}" />`;     // Replace cell content with input field
        });

        // Toggle button display
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
            this.saveUpdatedVitalSigns(actualCheckTime, updatedData);
        }
    }

    async saveUpdatedVitalSigns(actualCheckTime, updatedData) {
        try {
            await this.client.updateVitalSignsDetails(
                actualCheckTime,
                updatedData.temperature,
                updatedData.heartRate,
                updatedData.pulse,
                updatedData.respiratoryRate,
                updatedData.systolicPressure,
                updatedData.diastolicPressure,
                updatedData.meanArterialPressure,
                updatedData.weight,
                updatedData.patientPosition,
                updatedData.bloodOxygenLevel,
                updatedData.oxygenTherapy,
                updatedData.flowDelivered,
                updatedData.patientActivity,
                updatedData.comments
            );
            console.log(`VitalSigns with ID ${actualCheckTime} updated successfully.`);
        } catch (error) {
            console.error(`Error updating vitalSigns with ID ${actualCheckTime}:`, error);
        }
    }

        async deleteVitalSigns(event) {
            console.log("Deleting vitalSigns...");
            const actualCheckTime = event.target.getAttribute('data-id');
            console.log(`actualCheckTime to delete: ${actualCheckTime}`);
            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');

            try {
                await this.client.deleteVitalSigns(actualCheckTime);
                console.log(`VitalSigns with actualCheckTime ${actualCheckTime} deleted successfully.`);
                this.getVitalSigns();  // Refresh the list
            } catch (error) {
                console.error(`Error deleting vitalSigns with ID ${actualCheckTime}:`, error);
            }
        }

}

const main = async () => {
    const vitalSigns = new VitalSigns();
    vitalSigns.mount();
};

window.addEventListener('DOMContentLoaded', main);

