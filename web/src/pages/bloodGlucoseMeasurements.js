import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};

class BloodGlucoseMeasurements extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewAllBloodGlucoseMeasurements','search', 'displaySearchResults', 'getHTMLForSearchResults' ], this);
        //   this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displaySearchResults);
        this.header = new Header(this.dataStore);
        console.log("BloodGlucoseMeasurements constructor");
    }

    async mount() {//初始化
        document.getElementById('add-bloodGlucoseMeasurement-form').addEventListener('click', this.submit);//初始化按钮功能  1
        await this.header.addHeaderToPage();
        this.client = new CareCompassClient();

        this.viewAllBloodGlucoseMeasurements();////?????? not sure????should be here and the naming
    }

    async submit(event) {//点击按钮发生的事情 2
        event.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('add-bloodGlucoseMeasurement-form');
        const createButton = document.getElementById('search-allBloodGlucoseMeasurements-form');
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

    redirectToViewAllBloodGlucoseMeasurements() { //转到'/bloodGlucoseMeasurements'端点      //????????not sure the name right?????
        const allBloodGlucoseMeasurements = this.dataStore.get('allBloodGlucoseMeasurements');
        if (allBloodGlucoseMeasurements !== null && allBloodGlucoseMeasurements !== undefined) {
            window.location.href = '/bloodGlucoseMeasurements';
        }
    }
//
//    redirectToViewBloodGlucoseMeasurement() { //????????not sure the name right?????//转到'/bloodGlucoseMeasurements'端点
//            const allBloodGlucoseMeasurements = this.dataStore.get('bloodGlucoseMeasurement');
//            if (bloodGlucoseMeasurement !== null) {
//                window.location.href = '/bloodGlucoseMeasurements';
//            }
//        }

    async search(evt) {  //setAll   要改成set，最终目的是setdatastore
            // Prevent submitting the from from reloading the page.
            evt.preventDefault();

            const searchCriteria = document.getElementById('search-criteria').value;
            const previousSearchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);

            // If the user didn't change the search criteria, do nothing
            if (previousSearchCriteria === searchCriteria) {
                return;
            }

            if (searchCriteria) {
                const results = await this.client.search(searchCriteria);

                this.dataStore.setState({
                    [SEARCH_CRITERIA_KEY]: searchCriteria,
                    [SEARCH_RESULTS_KEY]: results,
                });
            } else {
                this.dataStore.setState(EMPTY_DATASTORE_STATE);
            }
        }

    displaySearchResults() {    //去掉view    //没人call，这个链接按钮
        const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);//1

        const searchResultsContainer = document.getElementById('search-results-container');
        const searchCriteriaDisplay = document.getElementById('search-criteria-display');
        const searchResultsDisplay = document.getElementById('search-results-display');//1

        if (searchCriteria === '') {
            searchResultsContainer.classList.add('hidden');
            searchCriteriaDisplay.innerHTML = '';
            searchResultsDisplay.innerHTML = '';
        } else {
            searchResultsContainer.classList.remove('hidden');
            searchCriteriaDisplay.innerHTML = `"${searchCriteria}"`;  // `"${viewAllBloodGlucoseMeasurementsCriteria}"`
            searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults);//2
        }
    }

       getHTMLForSearchResults(searchResults) {
           if (viewAllBloodGlucoseMeasurementsResults.length === 0) { ////viewAllBloodGlucoseMeasurementsResults????????not sure the name right?????
               return '<h4>No results found</h4>';
           }

           let html = '<table><tr><th>Actual Check Time</th><th>Glucose Level</th><th>Glucose Context</th><th>Comments</th></tr>';
           for (const res of searchResults) {
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
    const bloodGlucoseMeasurements = new BloodGlucoseMeasurements();
    bloodGlucoseMeasurements.mount();
};

window.addEventListener('DOMContentLoaded', main);