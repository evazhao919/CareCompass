import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const RESULTS_KEY = 'notification-results';

class Notification extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'addNotification', 'getNotifications', 'getNotifications', 'displayNotificationResults', 'getHTMLForNotificationResults', 'updateToNotificationForm', 'saveUpdatedNotification'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayNotificationResults);
        this.header = new Header(this.dataStore);
        console.log("Notification constructor");
    }

    async mount() {
        document.getElementById('add').addEventListener('click', this.addNotification);
        await this.header.addHeaderToPage();
        this.client = new CareCompassClient();  //kind of DAO
        this.getNotifications();

        document.getElementById('View-Table').addEventListener('click', (event) => {
            if (event.target.classList.contains('delete-button')) {
                console.log('Delete button clicked');
                this.deleteNotification(event);
            }
            if (event.target.classList.contains('update-button') || event.target.classList.contains('save-button')) {
                this.updateToNotificationForm(event);
            }
        });
    }

    async submit(event){//edit add, now we want get things
        event.preventDefault();
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const addNotificationButton = document.getElementById('add-notification-form');
        const showAllNotificationButton = document.getElementById('search-allNotifications-form');
        const origButtonText = addNotificationButton.innerText;
        const origSearchButtonText = showAllNotificationButton.innerText;
        addNotificationButton.innerText = 'Loading...';
        showAllNotificationButton.innerText = 'Loading...';

        const notificationTitle = document.getElementById('notificationTitle').value;
        const reminderContent = document.getElementById('reminderContent').value;
        const scheduledTime = document.getElementById('scheduledTime').value;
        const reminderType = document.getElementById('reminderType').value;

        try {
            const notification = await this.client.addNotification(notificationTitle, reminderContent, scheduledTime, reminderType);
            this.dataStore.set('notification', notification);
        } catch (error) {
            addNotificationButton.innerText = origButtonText;
            showAllNotificationButton.innerText = origSearchButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        } finally {
            addNotificationButton.innerText = origButtonText;
            showAllNotificationButton.innerText = origSearchButtonText;
        }
    }

async addNotification(evt) {
    evt.preventDefault();

    const addButton = document.getElementById('add');
    const origButtonText = addButton.innerText;

    const errorMessageDisplay = document.getElementById('error-message');
    errorMessageDisplay.innerText = '';
    errorMessageDisplay.classList.add('hidden');

//    const notificationTitle = document.getElementById('notificationTitle').value;
//    const reminderContent = document.getElementById('reminderContent').value;
//    const scheduledTime = document.getElementById('scheduledTime').value;
//    const reminderType = document.getElementById('reminderType').value;
      const notificationTitleInput = document.getElementById('notificationTitle');
      const reminderContentInput = document.getElementById('reminderContent');
      const scheduledTimeInput = document.getElementById('scheduledTime');
      const reminderTypeInput = document.getElementById('reminderType');

      const notificationTitle = notificationTitleInput.value;
      const reminderContent = reminderContentInput.value;
      const scheduledTime = scheduledTimeInput.value;
      const reminderType = reminderTypeInput.value;


    if (notificationTitle.length === 0 || reminderContent.length === 0 || scheduledTime.length === 0 || reminderType.length === 0) {
        return;
    }

    addButton.innerText = 'Loading...';

    try {
        const notification = await this.client.addNotification(notificationTitle, reminderContent, scheduledTime, reminderType,(()=>{}));
          // Clear input fields after successful addition
                        notificationTitleInput.value = '';
                        reminderContentInput.value = '';
                        scheduledTimeInput.value = '';
                        reminderTypeInput.value = '';
        this.getNotifications();  // Refresh the list
        this.dataStore.set('Notification', notification);

    } catch (error) {
        console.error('Error adding notification:', error);
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
    } finally {
        addButton.innerText = origButtonText;
    }
}


    async getNotifications() {  //make a call to API download some result, then write to the datastore
        console.log("Notifications results");
        const results = await this.client.getAllNotifications();
        console.log("Notifications results", results);
        this.dataStore.setState({
            [RESULTS_KEY]: results,
        });
    }

    displayNotificationResults() {
        const notificationResults = this.dataStore.get(RESULTS_KEY);
        const notificationResultsDisplay = document.getElementById('View-Table');
        notificationResultsDisplay.innerHTML = this.getHTMLForNotificationResults(notificationResults.notifications);
    }

    getHTMLForNotificationResults(searchResults) {
        if (searchResults.length === 0) {
            return '<h4>No results found</h4>';
        }

        let html = '<table><tr><th>Notification Id</th><th>Notification title</th><th>Scheduled time</th><th>Reminder type</th><th>Reminder content</th></tr>';
        for (const res of searchResults) {
            html += `
            <tr>
                <td>${res.notificationId}</td>
                <td class="editable" data-field="notificationTitle">${res.notificationTitle}</td>
                <td class="editable" data-field="scheduledTime">${res.scheduledTime}</td>
                <td class="editable" data-field="reminderType">${res.reminderType}</td>
                <td class="editable" data-field="reminderContent">${res.reminderContent}</td>
                <td>
                    <button class="update-button" data-id="${res.notificationId}">Update</button>
                    <button class="save-button" data-id="${res.notificationId}" style="display:none;">Save</button>
                    <button class="delete-button" data-id="${res.notificationId}">Delete</button>
                </td>
            </tr>`;
        }
        html += '</table>';

        return html;
    }

    updateToNotificationForm(event) {
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
            const notificationId = event.target.getAttribute('data-id');
            const updatedData = {};
            row.querySelectorAll('.editable').forEach(cell => {
                const field = cell.getAttribute('data-field');
                const input = cell.querySelector('input');
                updatedData[field] = input.value;
                cell.innerHTML = input.value;
            });
            row.querySelector('.update-button').style.display = 'inline-block';
            row.querySelector('.save-button').style.display = 'none';
            this.saveUpdatedNotification(notificationId, updatedData);
        }
    }

    async saveUpdatedNotification(notificationId, updatedData) {
        try {
            await this.client.updateNotificationDetails(notificationId, updatedData.notificationTitle, updatedData.scheduledTime, updatedData.reminderType, updatedData.reminderContent);
            console.log(`Notification with ID ${notificationId} updated successfully.`);
        } catch (error) {
            console.error(`Error updating notification with ID ${notificationId}:`, error);
        }
    }

        async deleteNotification(event) {
            console.log("Deleting notification...");
            const notificationId = event.target.getAttribute('data-id');
            try {
                await this.client.deleteNotification(notificationId);
                this.getNotifications();  // Refresh the list after deletion
            } catch (error) {
                console.error(`Error deleting notification with ID ${notificationId}:`, error);
            }
        }
}

const main = async () => {
    const notification = new Notification();
    notification.mount();
};

window.addEventListener('DOMContentLoaded', main);

