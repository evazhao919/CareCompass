import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const RESULTS_KEY = '-results';

class BloodGlucoseMeasurement extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewAllBloodGlucoseMeasurement', 'getBloodGlucoseMeasurements', 'displayBloodResults', 'deleteMeasurement','showUpdateForm', 'updateMeasurement', 'getHTMLForBloodResults'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayBloodResults);
        this.header = new Header(this.dataStore);
        console.log("BloodGlucoseMeasurements constructor");
    }
















const main = async () => {
    const VitalSigns = new VitalSigns();
    notification.mount();
};

window.addEventListener('DOMContentLoaded', main);