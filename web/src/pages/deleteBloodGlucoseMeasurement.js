import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

// 从 '../api/careCompassClient' 模块导入 CareCompassClient 类。
// 从 '../components/header' 模块导入 Header 组件。
// 从 '../util/bindingClass' 模块导入 BindingClass 类。
// 从 '../util/DataStore' 模块导入 DataStore 类。

class DeleteBloodGlucoseMeasurement extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'populateBloodGlucoseMeasurementDropdown', 'showSuccessMessageAndRedirect', 'showFailMessageRedirect'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }
// 定义 DeleteBloodGlucoseMeasurement 类，继承自 BindingClass。
// 构造函数：
// 调用父类的构造函数。
// 绑定 'mount'、'submit'、'populateBloodGlucoseMeasurementDropdown'、'showSuccessMessageAndRedirect' 和 'showFailMessageRedirect' 方法到当前实例。
// 创建一个新的 DataStore 实例并赋值给 this.dataStore。
// 创建一个新的 Header 实例，传入 this.dataStore。

    async mount() {
        await this.header.addHeaderToPage();
        this.client = new CareCompassClient();
        await this.populateBloodGlucoseMeasurementDropdown();
        document.getElementById('delete-bloodGlucoseMeasurement-form').addEventListener('submit', (event) => this.submit(event));
    }
// 定义异步方法 mount：
// 调用 this.header.addHeaderToPage 方法，并等待其完成。
// 创建一个新的 CareCompassClient 实例，并赋值给 this.client。
// 调用 this.populateBloodGlucoseMeasurementDropdown 方法，并等待其完成。
// 为 id 为 'delete-bloodGlucoseMeasurement-form' 的表单添加提交事件监听器，绑定到 this.submit 方法。

    async populateBloodGlucoseMeasurementDropdown() {
        const actualCheckTimeDropdown = document.getElementById('actualCheckTime');
        try {
            const bloodGlucoseMeasurementsResponse = await this.client.getBloodGlucoseMeasurement();
            console.log('BloodGlucoseMeasurements:', bloodGlucoseMeasurementsResponse);

            const bloodGlucoseMeasurementsArray = Array.isArray(bloodGlucoseMeasurementsResponse.bloodGlucoseMeasurement)
                ? bloodGlucoseMeasurementsResponse.bloodGlucoseMeasurement
                : [];
            console.log('BloodGlucoseMeasurements:', bloodGlucoseMeasurementsArray);

            bloodGlucoseMeasurementsArray.forEach((bloodGlucoseMeasurement) => {
                const option = document.createElement('option');
                option.value = bloodGlucoseMeasurement.actualCheckTime;
                option.text = bloodGlucoseMeasurement.actualCheckTime;
                actualCheckTimeDropdown.add(option);
            });
        } catch (error) {
            console.error('Error fetching blood glucose measurements:', error);
        }
    }
// 定义异步方法 populateBloodGlucoseMeasurementDropdown：
// 获取 id 为 'actualCheckTime' 的下拉菜单元素。
// 尝试获取血糖测量数据，并在控制台打印返回的响应。
// 如果返回的响应包含血糖测量数据，将其存储在数组中。
// 遍历数组，为每个血糖测量创建一个下拉菜单选项，并添加到下拉菜单中。
// 如果发生错误，打印错误信息到控制台。
    async submit(event) {
        event.preventDefault();

        const userConfirmed = window.confirm('Are you sure you want to delete this blood glucose measurement?');

        if (!userConfirmed) {
            return;
        }

        const loadingMessage = document.getElementById('loading-message');
        loadingMessage.style.display = 'block';

        const actualCheckTime = document.getElementById('actualCheckTime').value;

        try {
            await this.client.deleteBloodGlucoseMeasurement(actualCheckTime);
            this.showSuccessMessageAndRedirect();
        } catch (error) {
            console.error("Error removing blood glucose measurement:", error);
            this.showFailMessageRedirect();
        } finally {
            loadingMessage.style.display = 'none';
        }
    }
// 定义异步方法 submit：
// 阻止默认的表单提交行为。
// 弹出确认框，询问用户是否确认删除该血糖测量。
// 如果用户未确认，直接返回。
// 显示加载消息。
// 获取用户选择的实际检查时间。
// 使用 try-catch-finally 结构：
// 尝试调用 this.client.deleteBloodGlucoseMeasurement 方法删除血糖测量。
// 如果成功，调用 this.showSuccessMessageAndRedirect 方法。
// 如果发生错误，打印错误信息并调用 this.showFailMessageRedirect 方法。
// 最后，无论是否发生错误，隐藏加载消息。

    showSuccessMessageAndRedirect() {
        alert('Blood glucose measurement deleted successfully.');
        window.location.href = '/bloodGlucoseMeasurements';
    }
// 定义方法 showSuccessMessageAndRedirect：
// 弹出成功删除血糖测量的提示信息。
// 重定向浏览器到 '/bloodGlucoseMeasurements' 页面。

    showFailMessageRedirect() {
        alert('Failed to delete blood glucose measurement. Please try again.');
    }
// 定义方法 showFailMessageRedirect：
// 弹出删除血糖测量失败的提示信息。
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const deleteBloodGlucoseMeasurement = new DeleteBloodGlucoseMeasurement();
    await deleteBloodGlucoseMeasurement.mount();
};

window.addEventListener('DOMContentLoaded', main);
// 定义主函数 main，页面内容加载完成后运行。
// 创建 DeleteBloodGlucoseMeasurement 类的新实例，并调用其 mount 方法。
