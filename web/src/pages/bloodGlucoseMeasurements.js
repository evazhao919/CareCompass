import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const VIEW_ALL_BLOOD_GLUCOSE_MEASUREMENTS_CRITERIA_KEY = 'view-allBloodGlucoseMeasurements-criteria';
const VIEW_ALL_BLOOD_GLUCOSE_MEASUREMENTS_RESULTS_KEY = 'view-allBloodGlucoseMeasurements-results';

const EMPTY_DATASTORE_STATE = {
    [VIEW_ALL_BLOOD_GLUCOSE_MEASUREMENTS_CRITERIA_KEY]: '',
    [VIEW_ALL_BLOOD_GLUCOSE_MEASUREMENTS_RESULTS_KEY]: [],
};

class BloodGlucoseMeasurements extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewAllBloodGlucoseMeasurements','setAllBloodGlucoseMeasurementsToDataStore', 'displayAllBloodGlucoseMeasurementsResults', 'getHTMLForViewAllBloodGlucoseMeasurementsResults' ], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayAllBloodGlucoseMeasurementsResults);
        this.header = new Header(this.dataStore);
    }

    async mount() {//初始化
        document.getElementById('add').addEventListener('click', this.submit);//初始化按钮功能  1
        await this.header.addHeaderToPage();
        this.client = new CareCompassClient();

        this.viewAllBloodGlucoseMeasurements();
    }

    async submit(event) {//点击按钮发生的事情 2
        event.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('add');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const actualCheckTime = document.getElementById('actualCheckTime').value;
        const glucoseLevel = document.getElementById('glucoseLevel').value;
        const glucoseContext = document.getElementById('glucoseContext').value;
        const comments = document.getElementById('comments').value;

        try {
            const bloodGlucoseMeasurement = await this.client.addBloodGlucoseMeasurement(actualCheckTime, glucoseLevel, glucoseContext, comments);
            this.dataStore.set('bloodGlucoseMeasurement', bloodGlucoseMeasurement);
        } catch (error) {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        } finally {
            createButton.innerText = origButtonText;
        }
    }

    redirectToViewAllBloodGlucoseMeasurements() { //转到'/bloodGlucoseMeasurements'端点
        const allBloodGlucoseMeasurements = this.dataStore.get('allBloodGlucoseMeasurements');
        if (allBloodGlucoseMeasurements !== null && allBloodGlucoseMeasurements !== undefined) {
            window.location.href = '/bloodGlucoseMeasurements';
        }
    }

     //AllBloodGlucoseMeasurementsToDataStore()
     setAllBloodGlucoseMeasurementsToDataStore(){   //setAll   要改成set，最终目的是setdatastore

        const viewAllBloodGlucoseMeasurementsCriteria = document.getElementById('view-allBloodGlucoseMeasurements-criteria-display').value;
        const previousViewAllBloodGlucoseMeasurementsCriteria = this.dataStore.get(VIEW_ALL_BLOOD_GLUCOSE_MEASUREMENTS_CRITERIA_KEY);

//        if (previousViewAllBloodGlucoseMeasurementsCriteria === viewAllBloodGlucoseMeasurementsCriteria) {
//            return;
//        }

        if (viewAllBloodGlucoseMeasurementsCriteria) {
            try {
                const results = await this.client.viewAllBloodGlucoseMeasurements(viewAllBloodGlucoseMeasurementsCriteria);

                this.dataStore.setState({
                    [VIEW_ALL_BLOOD_GLUCOSE_MEASUREMENTS_CRITERIA_KEY]: viewAllBloodGlucoseMeasurementsCriteria,
                    [VIEW_ALL_BLOOD_GLUCOSE_MEASUREMENTS_RESULTS_KEY]: results,
                });
            } catch (error) {
                console.error("Error fetching blood glucose measurements:", error);
                this.dataStore.setState(EMPTY_DATASTORE_STATE);
            }
        } else {
            this.dataStore.setState(EMPTY_DATASTORE_STATE);
        }
    }

        displayAllBloodGlucoseMeasurementsResults() {  //去掉view    //没人call，这个链接按钮
            const viewAllBloodGlucoseMeasurementsCriteria = this.dataStore.get(VIEW_ALL_BLOOD_GLUCOSE_MEASUREMENTS_CRITERIA_KEY);
            const viewAllBloodGlucoseMeasurementsResults = this.dataStore.get(VIEW_ALL_BLOOD_GLUCOSE_MEASUREMENTS_RESULTS_KEY);

            const viewAllBloodGlucoseMeasurementsResultsContainer = document.getElementById('view-allBloodGlucoseMeasurements-results-container');
            const viewAllBloodGlucoseMeasurementsCriteriaDisplay = document.getElementById('view-allBloodGlucoseMeasurements-criteria-display');
            const viewAllBloodGlucoseMeasurementsResultsDisplay = document.getElementById('view-allBloodGlucoseMeasurements-results-display');

            if (viewAllBloodGlucoseMeasurementsCriteria === '') {
                viewAllBloodGlucoseMeasurementsResultsContainer.classList.add('hidden');
                viewAllBloodGlucoseMeasurementsCriteriaDisplay.innerHTML = '';
                viewAllBloodGlucoseMeasurementsResultsDisplay.innerHTML = '';
            } else {
                viewAllBloodGlucoseMeasurementsResultsContainer.classList.remove('hidden');
                viewAllBloodGlucoseMeasurementsCriteriaDisplay.innerHTML = `"${viewAllBloodGlucoseMeasurementsCriteria}"`;
                viewAllBloodGlucoseMeasurementsResultsDisplay.innerHTML = this.getHTMLForViewAllBloodGlucoseMeasurementsResults(viewAllBloodGlucoseMeasurementsResults);
            }
        }

       getHTMLForViewAllBloodGlucoseMeasurementsResults(viewAllBloodGlucoseMeasurementsResults) {
           if (viewAllBloodGlucoseMeasurementsResults.length === 0) {
               return '<h4>No results found</h4>';
           }

           let html = '<table><tr><th>Actual Check Time</th><th>Glucose Level</th><th>Glucose Context</th><th>Comments</th></tr>';
           for (const res of viewAllBloodGlucoseMeasurementsResults) {
               html += `
               <tr>
                   <td>${res.actualCheckTime}</td>
                   <td>${res.glucoseLevel}</td>
                   <td>${res.glucoseContext}</td>
                   <td>${res.comments}</td>
               </tr>`;
           }
           html += '</table>';

           return html;
       }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const bloodGlucoseMeasurement = new BloodGlucoseMeasurements();
    await bloodGlucoseMeasurement.mount();
};

window.addEventListener('DOMContentLoaded', main);

