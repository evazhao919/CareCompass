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



import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const SEARCH_CRITERIA_KEY = 'search-criteria';  // 定义搜索条件的键名
const SEARCH_RESULTS_KEY = 'search-results';   // 定义搜索结果的键名
const EMPTY_DATASTORE_STATE = {                // 定义空的数据存储状态
    [SEARCH_CRITERIA_KEY]: '',                  // 初始搜索条件为空字符串
    [SEARCH_RESULTS_KEY]: [],                   // 初始搜索结果为空数组
};

class BloodGlucoseMeasurements extends BindingClass {
    constructor() {
        super();
        // 绑定类方法
        this.bindClassMethods(['mount', 'submit', 'redirectToViewAllBloodGlucoseMeasurements', 'search', 'displaySearchResults', 'getHTMLForSearchResults'], this);
        // 创建 DataStore 实例
        this.dataStore = new DataStore();
        // 添加数据变化监听器
        this.dataStore.addChangeListener(this.displaySearchResults);
        // 创建 Header 实例并传入 DataStore 实例
        this.header = new Header(this.dataStore);
        console.log("BloodGlucoseMeasurements 构造函数");
    }

    async mount() {//初始化
        // 添加点击事件监听器，用于处理添加血糖测量的表单提交
        document.getElementById('add-bloodGlucoseMeasurement-form').addEventListener('click', this.submit);
        // 添加页眉到页面
        await this.header.addHeaderToPage();
        // 创建 CareCompassClient 实例
        this.client = new CareCompassClient();
    }
}

async submit(event) {//点击按钮发生的事情 2
    // 阻止表单默认提交行为
    event.preventDefault();

    // 获取错误信息显示元素并清空显示内容
    const errorMessageDisplay = document.getElementById('error-message');
    errorMessageDisplay.innerText = ``;
    // 隐藏错误信息显示元素
    errorMessageDisplay.classList.add('hidden');

    // 获取添加血糖测量按钮元素，并获取其原始文本内容
    const createButton = document.getElementById('add-bloodGlucoseMeasurement-form');
    // 获取搜索所有血糖测量按钮元素，并获取其原始文本内容
    const createButton = document.getElementById('search-allBloodGlucoseMeasurements-form');
    const origButtonText = createButton.innerText;
    // 将按钮文本设置为 'Loading...'
    createButton.innerText = 'Loading...';

    // 获取实际检查时间、血糖水平、血糖情境和备注输入框中的值
    const actualCheckTime = document.getElementById('actualCheckTime').value;
    const glucoseLevel = document.getElementById('glucoseLevel').value;
    const glucoseContext = document.getElementById('glucoseContext').value;
    const comments = document.getElementById('comments').value;

    try {
        // 调用 CareCompassClient 实例的 addBloodGlucoseMeasurement 方法添加血糖测量
        const bloodGlucoseMeasurement = await this.client.addBloodGlucoseMeasurement(actualCheckTime, glucoseLevel, glucoseContext, comments);
        // 将添加的血糖测量信息存储到数据存储中
        this.dataStore.set('bloodGlucoseMeasurement', bloodGlucoseMeasurement);
    } catch (error) {
        // 如果出现错误，恢复按钮原始文本内容，并显示错误信息
        createButton.innerText = origButtonText;
        errorMessageDisplay.innerText = `错误: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
    } finally {
        // 最终将按钮文本恢复为原始文本内容
        createButton.innerText = origButtonText;
    }
}

redirectToViewAllBloodGlucoseMeasurements() { //转到'/bloodGlucoseMeasurements'端点      //????????not sure the name right?????
    // 获取所有血糖测量数据
    const allBloodGlucoseMeasurements = this.dataStore.get('allBloodGlucoseMeasurements');
    // 如果数据不为空或未定义
    if (allBloodGlucoseMeasurements !== null && allBloodGlucoseMeasurements !== undefined) {
        // 跳转到 '/bloodGlucoseMeasurements' 端点
        window.location.href = '/bloodGlucoseMeasurements';
    }
}

async search(evt) {  // 设置搜索条件并更新数据存储
    // 阻止表单默认提交行为
    evt.preventDefault();

    // 获取搜索条件
    const searchCriteria = document.getElementById('search-criteria').value;
    // 获取先前的搜索条件
    const previousSearchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);

    // 如果用户未更改搜索条件，则不执行任何操作
    if (previousSearchCriteria === searchCriteria) {
        return;
    }

    if (searchCriteria) {
        // 如果存在搜索条件，则执行搜索操作，并将结果设置到数据存储中
        const results = await this.client.search(searchCriteria);

        this.dataStore.setState({
            [SEARCH_CRITERIA_KEY]: searchCriteria,
            [SEARCH_RESULTS_KEY]: results,
        });
    } else {
        // 如果搜索条件为空，则将数据存储状态设置为空
        this.dataStore.setState(EMPTY_DATASTORE_STATE);
    }
}

displaySearchResults() {    // 显示搜索结果
    // 获取搜索条件和搜索结果
    const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
    const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

    // 获取搜索结果的显示容器和搜索条件的显示元素
    const searchResultsContainer = document.getElementById('search-results-container');
    const searchCriteriaDisplay = document.getElementById('search-criteria-display');
    const searchResultsDisplay = document.getElementById('search-results-display');

    if (searchCriteria === '') {
        // 如果搜索条件为空，则隐藏搜索结果容器并清空搜索条件和搜索结果的显示内容
        searchResultsContainer.classList.add('hidden');
        searchCriteriaDisplay.innerHTML = '';
        searchResultsDisplay.innerHTML = '';
    } else {
        // 如果搜索条件不为空，则显示搜索结果
        searchResultsContainer.classList.remove('hidden');
        // 在页面上显示搜索条件，并显示搜索结果的 HTML 内容
        searchCriteriaDisplay.innerHTML = `"${searchCriteria}"`;
        searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults);
    }
}

getHTMLForSearchResults(searchResults) {  // 获取用于显示搜索结果的 HTML
    if (searchResults.length === 0) {
        // 如果搜索结果为空，则显示未找到结果的提示
        return '<h4>No results found</h4>';
    }

    // 构建包含搜索结果的 HTML 表格
    let html = '<table><tr><th>实际检查时间</th><th>血糖水平</th><th>血糖情境</th><th>备注</th></tr>';
    for (const res of searchResults) {
        // 遍历搜索结果，构建表格行
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

/**
 * 页面内容加载完成后要运行的主要方法。
 */
const main = async () => {
    // 创建 BloodGlucoseMeasurements 实例
    const bloodGlucoseMeasurements = new BloodGlucoseMeasurements();
    // 初始化页面
    bloodGlucoseMeasurements.mount();
};

// 在 DOMContentLoaded 事件触发时运行主方法
window.addEventListener('DOMContentLoaded', main);

