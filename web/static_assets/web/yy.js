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

async mount() {
await this.header.addHeaderToPage();
this.client = new CareCompassClient();
document.getElementById('add-bloodGlucoseMeasurement-form').addEventListener('submit', this.submit);
}

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

showSuccessMessageAndRedirect() {
// Hide everything except the header and body background
const allChildren = document.body.children;
for (let i = 0; i < allChildren.length; i++) {
const element = allChildren[i];
if (element.id !== 'header') {
element.style.display = 'none';
}
}

// Create success message with card class
const messageElement = document.createElement('div');
messageElement.className = 'card';
const messageText = document.createElement('p');
messageText.innerText = "Blood glucose measurement has been added to your chart review successfully!";
messageText.style.textAlign = "center";
messageText.style.color = "#FFFFFF";
messageText.style.fontSize = "40px";
messageText.style.margin = "20px 0";
messageElement.appendChild(messageText);
document.body.appendChild(messageElement);
setTimeout(() => {
window.location.href = `/chartReview.html`;
}, 3000);  // redirect after 3 seconds
}

showFailMessageRedirect() {
// Hide everything except the header and body background
const allChildren = document.body.children;
for (let i = 0; i < allChildren.length; i++) {
const element = allChildren[i];
if (element.id !== 'header') {
element.style.display = 'none';
}
}

// Create error message with card class
const messageElement = document.createElement('div');
messageElement.className = 'card';
const messageText = document.createElement('p');
messageText.innerText = "Error occurred trying to add blood glucose measurement! Try Again";
messageText.style.textAlign = "center";
messageText.style.color = "#FFFFFF";
messageText.style.fontSize = "40px";
messageText.style.margin = "20px 0";
messageElement.appendChild(messageText);
document.body.appendChild(messageElement);

setTimeout(() => {
window.location.href = `/addBloodGlucoseMeasurement.html`;
}, 4000);  // redirect after 4 seconds
}
}

/**
* Main method to run when the page contents have loaded.
*/
const main = async () => {
const addBloodGlucoseMeasurement = new AddBloodGlucoseMeasurement();
addBloodGlucoseMeasurement.mount();
};
window.addEventListener('DOMContentLoaded', main);
