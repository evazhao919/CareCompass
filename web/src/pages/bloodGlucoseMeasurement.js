import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const RESULTS_KEY = 'blood-results';

class BloodGlucoseMeasurement extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewAllBloodGlucoseMeasurement', 'getBloodGlucoseMeasurements', 'displayBloodResults', 'deleteMeasurement','showUpdateForm', 'updateMeasurement', 'getHTMLForBloodResults'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayBloodResults);
        this.header = new Header(this.dataStore);
        console.log("BloodGlucoseMeasurement constructor");
    }

    async mount() {
        document.getElementById('add-bloodGlucoseMeasurement-form').addEventListener('click', this.submit);

//        document.getElementById('delete').addEventListener('click', this.deleteMeasurement);//TODO
        document.getElementById('update-measurement-form').addEventListener('click', this.updateMeasurement);
        await this.header.addHeaderToPage();
        this.client = new CareCompassClient();
        this.getBloodGlucoseMeasurements();  //kind of DAO
    }

    async submit(event){//edit add, now we want get things
        event.preventDefault();
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const addBloodGlucoseButton = document.getElementById('add-bloodGlucoseMeasurement-form');
        const showAllBloodGlucoseButton = document.getElementById('search-allBloodGlucoseMeasurements-form');
        const origButtonText = addBloodGlucoseButton.innerText;
        const origSearchButtonText = showAllBloodGlucoseButton.innerText;
        addBloodGlucoseButton.innerText = 'Loading...';
        showAllBloodGlucoseButton.innerText = 'Loading...';

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

    async deleteMeasurement(event) {
        const actualCheckTime = event.target.getAttribute('data-id');
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        try {
            const updatedMeasurementList = await this.client.deleteBloodGlucoseMeasurement(actualCheckTime);
            this.dataStore.set('bloodGlucoseMeasurement', updatedMeasurementList);
        } catch (error) {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        }
    }

    showUpdateForm(event) {
        const actualCheckTime = event.target.getAttribute('data-id');
        const measurement = this.dataStore.get('bloodGlucoseMeasurement').find(m => m.actualCheckTime === actualCheckTime);
        if (!measurement) {
            return;
        }

        document.getElementById('update-actualCheckTime').value = measurement.actualCheckTime;
        document.getElementById('update-glucoseLevel').value = measurement.glucoseLevel;
        document.getElementById('update-glucoseContext').value = measurement.glucoseContext;
        document.getElementById('update-comments').value = measurement.comments;

        document.getElementById('update-measurement-form').classList.remove('hidden');
    }

    async updateMeasurement(event) {//event: response click event
        event.preventDefault();

        const actualCheckTime = document.getElementById('update-actualCheckTime').value;
        const glucoseLevel = document.getElementById('update-glucoseLevel').value;
        const glucoseContext = document.getElementById('update-glucoseContext').value;
        const comments = document.getElementById('update-comments').value;

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        try {
            const updatedMeasurementList = await this.client.updateBloodGlucoseMeasurement(actualCheckTime, glucoseLevel, glucoseContext, comments);
            this.dataStore.set('measurements', updatedMeasurementList);
        } catch (error) {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        }

        document.getElementById('update-measurement-form').classList.add('hidden');
    }

    async getBloodGlucoseMeasurements() {
              console.log("BloodGlucoseMeasurements results");
            const results = await this.client.getAllBloodGlucoseMeasurements(()=>{})
              console.log("BloodGlucoseMeasurements results",results);
//             createButton.innerText = origButtonText; // 恢复按钮原始文本
//                        errorMessageDisplay.innerText = `Error: ${error.message}`; // 设置错误消息文本
//                        errorMessageDisplay.classList.remove('hidden'); // 显示错误消息区域});  //empty
            this.dataStore.setState({
                [RESULTS_KEY]: results,
            });
    }

    displayBloodResults() {

        const bloodResults = this.dataStore.get(RESULTS_KEY);
        const bloodResultsDisplay = document.getElementById('View-Table');//table
            bloodResultsDisplay.innerHTML = this.getHTMLForBloodResults(bloodResults.bloodGlucoseMeasurements);//

   }

    getHTMLForBloodResults(searchResults) {
        if (searchResults.length === 0) {
            return '<h4>No results found</h4>';
        }

        let html = '<table><tr><th>Actual Check Time</th><th>Glucose Level</th><th>Glucose Context</th><th>Comments</th></th></tr>';
        for (const res of searchResults) {
            html += `
            <tr>
                <td>${res.actualCheckTime}</td>
                <td>${res.glucoseLevel}</td>
                <td>${res.glucoseContext}</td>
                <td>${res.comments}</td>
                <td>
                    <button class="update-button" data-id="${res.actualCheckTime}">Update</button>
                    <button class="delete-button" data-id="${res.actualCheckTime}">Delete</button>
                </td>
            </tr>`;
        }
        html += '</table>';

        return html;
    }

    redirectToViewAllBloodGlucoseMeasurement() {
        const allBloodGlucoseMeasurements = this.dataStore.get('bloodGlucoseMeasurement');
        if (allBloodGlucoseMeasurements !== null) {
            window.location.href = '/bloodGlucoseMeasurements';
        }
    }
}

const main = async () => {
    const bloodGlucoseMeasurement = new BloodGlucoseMeasurement();
    bloodGlucoseMeasurement.mount();
};

window.addEventListener('DOMContentLoaded', main);
