import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const RESULTS_KEY = 'notification-results';

class Notification extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'getNotifications', "displayNotificationResults", "getHTMLForNotificationResults"], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayNotificationResults);
        this.header = new Header(this.dataStore);
        console.log("Notification constructor");
    }

    async mount() {
        document.getElementById('add-notification-form').addEventListener('click', this.submit);

//        document.getElementById('delete').addEventListener('click', this.deleteNotification);//TODO
        document.getElementById('update-notification-form').addEventListener('click', this.updateNotification);
        await this.header.addHeaderToPage();
        this.client = new CareCompassClient();  //kind of DAO
        this.getNotifications();
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

        const notificationId = document.getElementById('notificationId').value;
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

async getNotifications() {  //make a call to API download some result, the write to the datastore
    //      const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY).value; //only you want typing things to search box
        console.log("Notifications results");
        const results = await this.client.getAllNotifications(()=>{}) //TODO
        console.log("Notifications results",results);
    //             createButton.innerText = origButtonText; // 恢复按钮原始文本
    //             errorMessageDisplay.innerText = `Error: ${error.message}`; // 设置错误消息文本
    //             errorMessageDisplay.classList.remove('hidden'); // 显示错误消息区域});  //empty
           this.dataStore.setState({
                    [RESULTS_KEY]: results,
           });
    }

displayNotificationResults() {

        const notificationResults = this.dataStore.get(RESULTS_KEY);
        const notificationResultsDisplay = document.getElementById('View-Table');//table
        notificationResultsDisplay.innerHTML = this.getHTMLForNotificationResults(notificationResults.notifications);//

    }

getHTMLForNotificationResults(searchResults) {
       if (searchResults.length === 0) {
            return '<h4>No results found</h4>';
       }

       let html = '<table><tr><th>Notification Id</th><th>Notification title</th><th>Scheduled time</th><th>Reminder type</th><th>Reminder content</th></th></tr>';
       for (const res of searchResults) {
       html += `
       <tr>
          <td>${res.notificationId}</td>
          <td>${res.notificationTitle}</td>
          <td>${res.scheduledTime}</td>
          <td>${res.reminderType}</td>
          <td>${res.reminderContent}</td>
          <td>
          <button class="update-button" data-id="${res.notificationId}">Update</button>
          <button class="delete-button" data-id="${res.notificationId}">Delete</button>
          </td>
       </tr>`;
   }
   html += '</table>';

   return html;
}
}






const main = async () => {
    const notification = new Notification();
    notification.mount();
};

window.addEventListener('DOMContentLoaded', main);

