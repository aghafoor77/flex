import React, { Component } from 'react'
class RESTClient extends Component {
	constructor() {

	}
	displayError(res) {
		switch (res.status) {
			case 200:
				return true;
			case 404:
				alert("Resource unreachable !")
				return false;
			case 500:
				alert(res.json())
				return false;
			default:
				return false;
		}
	}
	handleGet(url) {
		fetch(url)
			.then(res => {
				if (res.status === 200) {
					res.json()
						.then(data => {
							return data
						})
						.catch(error => {
							alert(error);
							return false
						});
				} else {
					return this.displayError(res)
				}

			})
			.catch(console.log)

	}
}
export default RESTClient; 