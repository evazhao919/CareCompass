import CareCompassClient from '../api/careCompassClient';
    import Header from '../components/header';
    import BindingClass from '../util/bindingClass';
    import DataStore from '../util/DataStore';

    const RESULTS_BY_TYPE_KEY = 'notification-by-type-results';
 const RESULTS_BY_STATUS_KEY = 'medication-by-status-results';
 const CURRENT_TIME_RESULTS_KEY = 'notification-current-time-results'; // 定义常量，用于存储通知结果的键

    class ChartReview extends BindingClass {
        constructor() {
            super();
            this.bindClassMethods([
                'mount',
                'getNotificationsByReminderType',
                'displayNotificationByReminderTypeResults',
                'getHTMLForNotificationByReminderTypeResults',
                'getMedicationsByMedicationStatus',
                'displayMedicationByMedicationStatusResults',
                'getHTMLForMedicationResults',
                'getNotificationByCurrentTime',
                'displayNotificationByCurrentTimeResults',
                'getHTMLForNotificationCurrentTimeResults'
            ], this);
            this.dataStore = new DataStore(); // 创建一个数据存储实例
            this.dataStore.addChangeListener(this.displayNotificationByReminderTypeResults);     // 当数据发生变化时，调用 displayNotificationByReminderTypeResults 方法
            this.dataStore.addChangeListener(this.displayMedicationByMedicationStatusResults);     // 当数据发生变化时，调用 displayMedicationByMedicationStatusResults 方法
            this.dataStore.addChangeListener(this.displayNotificationByCurrentTimeResults); // 添加数据变化监听器
            this.header = new Header(this.dataStore);     // 创建一个 Header 实例，可能用于管理页面的头部
            this.client = new CareCompassClient();   // 创建一个 CareCompassClient 实例，用于与后端 API 进行通信
        }

        async mount() {
            document.getElementById('retrieve-notifications-type-form').addEventListener('click', this.getNotificationsByReminderType); // 添加事件监听，当按钮被点击时调用 getNotificationsByReminderType 方法
             document.getElementById('retrieve-medications-status-form').addEventListener('click', this.getMedicationsByMedicationStatus); // 添加事件监听，当按钮被点击时调用 getMedicationsByMedicationStatus 方法
           document.getElementById('retrieve-notifications-current-time-form').addEventListener('click', this.getNotificationByCurrentTime);

            await this.header.addHeaderToPage();  // 等待页面头部组件加载完成
            this.client = new CareCompassClient();   // 创建 CareCompassClient 实例
              //    this.getNotificationByCurrentTime(); // 调用 getNotificationByCurrentTime 方法获取通知
        }

async getNotificationsByReminderType() {

    console.log("Fetching notifications...");
    const reminderType = document.getElementById('reminderType').value;   // 获取选择框中的药物状态值

    try {
        const results = await this.client.retrieveNotificationsByReminderType(reminderType);   // 通过 CareCompassClient 实例获取特定状态的药物数据
        this.dataStore.set(RESULTS_BY_TYPE_KEY, results);  // 将获取到的药物数据存储到 dataStore 中
    } catch (error) {
        console.error("Error fetching notifications:", error);
        if (error.response) {
            // Server responded with a type other than 200 range
            console.error("Server responded with type:", error.response.reminderType);
            console.error("Server response data:", error.response.data);
        } else if (error.request) {
            // Request was made but no response received
            console.error("No response received:", error.request);
        } else {
            // Something else happened while setting up the request
            console.error("Error setting up request:", error.message);
        }
    }
}
async getMedicationsByMedicationStatus() {

    console.log("Fetching medications...");
    const medicationStatus = document.getElementById('medicationStatus').value;   // 获取选择框中的药物状态值

    try {
        const results = await this.client.retrieveMedicationsByStatus(medicationStatus);   // 通过 CareCompassClient 实例获取特定状态的药物数据
        this.dataStore.set(RESULTS_BY_STATUS_KEY, results);  // 将获取到的药物数据存储到 dataStore 中
    } catch (error) {
        console.error("Error fetching medications:", error);
        if (error.response) {
            // Server responded with a status other than 200 range
            console.error("Server responded with status:", error.response.medicationStatus);
            console.error("Server response data:", error.response.data);
        } else if (error.request) {
            // Request was made but no response received
            console.error("No response received:", error.request);
        } else {
            // Something else happened while setting up the request
            console.error("Error setting up request:", error.message);
        }
    }
}
async getNotificationByCurrentTime() { // 异步获取通知方法

        console.log("Notifications results"); // 输出获取通知日志
        const results = await this.client.retrieveAllUpcomingNotifications(Date.now()); // 调用 client 的 getAllNotifications 方法，获取所有通知
        console.log("Notifications results", results); // 打印获取到的通知结果
        this.dataStore.setState({
            [CURRENT_TIME_RESULTS_KEY]: results.notifications// 将获取到的通知结果存储到 dataStore
        });
    }

  displayNotificationByReminderTypeResults() {
            const notificationResults = this.dataStore.get(RESULTS_BY_TYPE_KEY);    // 从 dataStore 中获取药物数据
            const notificationResultsDisplay = document.getElementById('View-Type-Cards');    // 获取页面上 ID 为 View-Cards 的元素，用于显示药物数据

  if (notificationResults) {    // 如果获取到了药物数据
            this.getHTMLForNotificationByReminderTypeResults(notificationResults).then(html => {        // 调用 getHTMLForNotificationByReminderTypeResults 方法获取药物数据的 HTML 内容，并将其显示在页面上
                notificationResultsDisplay.innerHTML = html;
            });
        } else {
          //  notificationResultsDisplay.innerHTML = '<p>No notifications found for the selected type.</p>';        // 如果没有获取到药物数据，显示一条错误信息
               notificationResultsDisplay.innerHTML = '<p></p>';        // 如果没有获取到药物数据，显示一条错误信息
        }

        }

displayMedicationByMedicationStatusResults() {
            const medicationResults = this.dataStore.get(RESULTS_BY_STATUS_KEY);    // 从 dataStore 中获取药物数据
            const medicationResultsDisplay = document.getElementById('View-status-Cards');    // 获取页面上 ID 为 View-Cards 的元素，用于显示药物数据

  if (medicationResults) {    // 如果获取到了药物数据
            this.getHTMLForMedicationResults(medicationResults).then(html => {        // 调用 getHTMLForMedicationResults 方法获取药物数据的 HTML 内容，并将其显示在页面上
                medicationResultsDisplay.innerHTML = html;
            });
        } else {
           // medicationResultsDisplay.innerHTML = '<p>No medications found for the selected status.</p>';        // 如果没有获取到药物数据，显示一条错误信息
              medicationResultsDisplay.innerHTML = '<p></p>';        // 如果没有获取到药物数据，显示一条错误信息
        }

        }

displayNotificationByCurrentTimeResults() { // 显示通知结果方法
        const notificationResults = this.dataStore.get(CURRENT_TIME_RESULTS_KEY); // 从 dataStore 获取通知结果
        const notificationResultsDisplay = document.getElementById('View-upcoming-Cards');
        if (notificationResults) { // 如果获取到了通知数据
            const html = this.getHTMLForNotificationCurrentTimeResults(notificationResults); // 调用 getHTMLForNotificationCurrentTimeResults 方法获取通知数据的 HTML 内容
            notificationResultsDisplay.innerHTML = html; // 直接设置 HTML 内容
        } else {
           // notificationResultsDisplay.innerHTML = '<p>No notifications found for the selected status.</p>'; // 如果没有获取到通知数据，显示一条错误信息
              notificationResultsDisplay.innerHTML = '<p></p>'; // 如果没有获取到通知数据，显示一条错误信息
        }
    }

    async getHTMLForNotificationByReminderTypeResults(notificationResults) {
        try {
            let reminderTypeHTML = "";     // 遍历药物数据数组，生成每个药物的 HTML 卡片内容
            notificationResults.forEach(notification => {
                const cardHtml = `
                    <div class="card mb-3">
                        <div class="card-body">
                            <h5 class="card-title">${notification.notificationTitle}</h5>
                            <p class="card-text"><strong>ReminderContent:</strong> ${notification.reminderContent}</p>
                            <p class="card-text"><strong>ScheduledTime:</strong> ${notification.scheduledTime}</p>
                            <p class="card-text"><strong>ReminderType:</strong> ${notification.reminderType}</p>
                        </div>
                    </div>
                `;
            reminderTypeHTML += cardHtml;
            }

            );
               return  reminderTypeHTML;
        } catch (error) {
            console.error("Error populating notification list:", error);
        }
    }




    async getHTMLForMedicationResults(medicationResults) {
        try {
            let medicationStatusHTML = "";     // 遍历药物数据数组，生成每个药物的 HTML 卡片内容
            medicationResults.forEach(medication => {
                const cardHtml = `
                    <div class="card mb-3">
                        <div class="card-body">
                            <h5 class="card-title">${medication.medicationName}</h5>
                            <p class="card-text"><strong>Prescription:</strong> ${medication.prescription}</p>
                            <p class="card-text"><strong>Instructions:</strong> ${medication.instructions}</p>
                            <p class="card-text"><strong>medicationStatus:</strong> ${medication.medicationStatus}</p>
                        </div>
                    </div>
                `;
            medicationStatusHTML += cardHtml;
            }

            );
               return  medicationStatusHTML;
        } catch (error) {
            console.error("Error populating medication list:", error);
        }
    }




    getHTMLForNotificationCurrentTimeResults(notificationResults) { // 获取通知结果的 HTML 方法

         try {
             let notificationUpcomingHTML = "";     // 遍历药物数据数组，生成每个药物的 HTML 卡片内容
             notificationResults.forEach(notification => {
                 const cardHtml = `
                     <div class="card mb-3">
                         <div class="card-body">
                             <h5 class="card-title">${notification.notificationTitle}</h5>
                             <p class="card-text"><strong>scheduledTime:</strong> ${notification.scheduledTime}</p>
                             <p class="card-text"><strong>reminderType:</strong> ${notification.reminderType}</p>
                             <p class="card-text"><strong>reminderContent:</strong> ${notification.reminderContent}</p>
                         </div>
                     </div>
                 `;
             notificationUpcomingHTML += cardHtml;
             }

             );
                return  notificationUpcomingHTML;
         } catch (error) {
             console.error("Error populating notification list:", error);
         }
    }
    }

    const main = async () => {
        const chartReview = new ChartReview();
        chartReview.mount();
    };

    window.addEventListener('DOMContentLoaded', main);




//
//
//import CareCompassClient from '../api/careCompassClient';
//import Header from '../components/header';
//import BindingClass from '../util/bindingClass';
//import DataStore from '../util/DataStore';
//
//const RESULTS_KEY = 'notification-results';
//
//class ChartReview extends BindingClass {
//    constructor() { // 构造函数
//        super(); // 调用父类的构造函数
//        this.bindClassMethods(['mount', 'getNotifications', 'displayNotificationResults', 'getHTMLForNotificationResults'], this);
//        this.dataStore = new DataStore();
//        this.dataStore.addChangeListener(this.displayNotificationResults);
//        this.header = new Header(this.dataStore);
//    }
//
//    async mount() { // 异步挂载方法
//
//        await this.header.addHeaderToPage();
//        this.client = new CareCompassClient();
//        this.getNotifications();
//    }
//
//    async getNotifications() {
//
//        console.log("Notifications results");
//        const results = await this.client.retrieveAllUpcomingNotifications(Date.now());
//        console.log("Notifications results", results);
//        this.dataStore.setState({
//            [RESULTS_KEY]: results.notifications
//        });
//    }
//
//  displayNotificationResults() {
//        const notificationResults = this.dataStore.get(RESULTS_KEY);
//        const notificationResultsDisplay = document.getElementById('View-upcoming-Cards');
//        if (notificationResults) { // 如果获取到了通知数据
//            const html = this.getHTMLForNotificationResults(notificationResults);
//            notificationResultsDisplay.innerHTML = html;
//        } else {
//            notificationResultsDisplay.innerHTML = '<p>No notifications found for the selected status.</p>';
//        }
//    }
//
//    getHTMLForNotificationResults(notificationResults) {
//
//         try {
//             let notificationUpcomingHTML = "";
//             notificationResults.forEach(notification => {
//                 const cardHtml = `
//                     <div class="card mb-3">
//                         <div class="card-body">
//                             <h5 class="card-title">${notification.notificationTitle}</h5>
//                             <p class="card-text"><strong>scheduledTime:</strong> ${notification.scheduledTime}</p>
//                             <p class="card-text"><strong>reminderType:</strong> ${notification.reminderType}</p>
//                             <p class="card-text"><strong>reminderContent:</strong> ${notification.reminderContent}</p>
//                         </div>
//                     </div>
//                 `;
//             notificationUpcomingHTML += cardHtml;
//             }
//
//             );
//                return  notificationUpcomingHTML;
//         } catch (error) {
//             console.error("Error populating notification list:", error);
//         }
//    }
//}
//
//const main = async () => {
//    const chartReview = new ChartReview();
//    chartReview.mount();
//};
//
//window.addEventListener('DOMContentLoaded', main);



