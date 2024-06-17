import CareCompassClient from '../api/careCompassClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class Index extends BindingClass {
constructor() {
super();
this.bindClassMethods(['mount'], this);
this.dataStore = new DataStore();
this.header = new Header(this.dataStore);
}


async mount() {
    await this.header.addHeaderToPage();
    this.client = new CareCompassClient();
}
}

const main = async () => {
const index = new Index();
index.mount();
};

window.addEventListener('DOMContentLoaded', main)', main);