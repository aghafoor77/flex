import React, { Component } from 'react'
import {
	CButton,
	CCard,
	CCardBody,
	CCardFooter,
	CCardHeader,
	CCol,
	CForm,
	CFormGroup,
	CTextarea,
	CInput,
	CLabel,
	CSelect,
	CRow,
	CAlert
} from '@coreui/react'


import endpoints from "../config/Configuration";

class Insemination extends Component {

	constructor() {
		super();

		this.state = {
			semenorders: [],
			orderdetail: [],
			animals: [],
			animal: [],
			employees: [],
			insemationDate: "",
			notes: "",
			employeeID: "",
			osid: "",
			animalID: "",
			fondsource: false,
			foundanimal: false,
			alert: {
				title: "Here ", message: "There", display: false
			}
		};

		this.fetchSemenOrders();
		this.fetchAnimals();
		this.fetchEmployees();
	}

	setAlert(title, message) {
		this.setState({ alert: { title: title, message: message, display: true } });
	}

	hideAlertClick(e) {
		this.setState({ alert: { display: false } });
	}

	displayAlert() {
		return (this.state.alert.display === true ? (<CCardHeader><CAlert color="danger">
			<CRow>
				<CCol xs="11">
					{this.state.alert.title}: {this.state.alert.message}
				</CCol>
				<CCol xs="1">
					<CButton block onClick={(e) => this.hideAlertClick(e)}>X</CButton>
				</CCol>
			</CRow></CAlert></CCardHeader>) : null);
	}

	displayError(res) {
		switch (res.status) {
			case 200:
				return true;
			case 404:
				this.setAlert("Not found", "Resource unreachable !")
				return false;
			case 500:
				res.json()
					.then(data => {
						this.setAlert("Server error 1", data.message)
					})
					.catch(error => { this.setAlert("General error", error.message) });

				return false;
			default:
				return false;
		}
	}

	fetchSemenOrders() {
		let url = window.sessionStorage.getItem(endpoints.OS) + '/application/v1/ordersemen/doneorders'
		fetch(url, {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			}
		})
			.then(res => {
				if (res.status === 200) {
					res.json()
						.then(data => {
							let items = [];
							items.push("No Order Found ");
							if (data.length === 0) {
								this.setState({ semenorders: items });

							} else {
								this.setState({ semenorders: data, osid: data[0].osid });
								this.fetchOrder(data[0].osid)
							}

							return items;

						})
						.catch(error => {
							this.setAlert("Server error 2", error.message);
							return false
						});
				} else {
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection error", error.message);
				return false
			});

	}


	fetchEmployees() {
		let url = window.sessionStorage.getItem(endpoints.AM) + '/tapi/idms/v1/account/listusers'//'/application/v1/farmer'
		fetch(url, {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
				"Authorization": window.sessionStorage.getItem("vblock")
			}
		})
			.then(res => {
				if (res.status === 200) {
					res.json()
						.then(data => {
							let items = [];
							items.push("No Order Found ");
							if (data.length === 0) {
								this.setState({ employees: items });
								return items;
							} else {
								this.setState({ employees: data });
								return data;
							}
						})
						.catch(error => {
							this.setAlert("Server error 3", error.message);
							return false
						});
				} else {
					
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection error", error.message);

				return false
			});

	}
	fetchAnimals() {
		let url = window.sessionStorage.getItem(endpoints.AR) + '/application/v1/registeranimal/cows'
		fetch(url, {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			}
		})
			.then(res => {
				if (res.status === 200) {
					res.json()
						.then(data => {
							let items = [];
							items.push("No Order Found ");
							if (data.length === 0) {
								this.setState({ animals: items });
								return items;
							} else {
								this.setState({ animals: data });
								return data;
							}
						})
						.catch(error => {
							this.setAlert("Server error 4", error.message);
							return false
						});
				} else {					
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection error", error.message);

				return false
			});

	}

	fetchAnimal(e) {
		let url = window.sessionStorage.getItem(endpoints.AR) + '/application/v1/registeranimal/' + e.target.value
		fetch(url, {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			}
		})
			.then(res => {
				if (res.status === 200) {
					res.json()
						.then(data => {
							let items = [];
							items.push("No Order Found ");
							if (data.length === 0) {
								this.setState({ animal: items, foundanimal: false });
								return items;
							} else {
								this.setState({ animal: data, foundanimal: true });
								return data;
							}
						})
						.catch(error => {
							this.setAlert("Server error 5", error.message);
							return false
						});
				} else {
					
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection error", error.message);

				return false
			});

	}



	fetchOrder(val) {
		let url = window.sessionStorage.getItem(endpoints.OS) + '/application/v1/ordersemen/' + val
		fetch(url, {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			}
		})
			.then(res => {
				if (res.status === 200) {
					res.json()
						.then(data => {
							let items = [];
							items.push("No Order Found ");
							if (data.length === 0) {
								this.setState({ orderdetail: items, fondsource: false });
							} else {
								this.setState({ orderdetail: data, fondsource: true });

							}

							return data;

						})
						.catch(error => {
							this.setAlert("Server error 6", error.message);
							return false
						});
				} else {
					
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection error", error.message);

				return false
			});

	}


	handleChange(event) {
		switch (event.target.id) {

			case 'insemationDate': this.setState({ insemationDate: event.target.value }); break;
			case 'notes': this.setState({ notes: event.target.value }); break;
			case 'employeeID': this.setState({ employeeID: event.target.value }); break;
			case 'osid': this.setState({ osid: event.target.value }); this.fetchOrder(event.target.value); break;
			case 'animalID': this.setState({ animalID: event.target.value }); this.fetchAnimal(event); break;
			default: return '';
		}
	}
	getJSON() {
		let jsondata = {
			insemationDate: this.state.insemationDate,
			notes: this.state.notes,
			employeeID: this.state.employeeID,
			osid: this.state.osid,
			animalID: this.state.animalID
		}
		return jsondata;

	}

	handleBackClick = (e) => {
		this.props.history.goBack();

	}
	handleSubmitClick = (e) => {
		this.post();

	}

	post() {
		let url = window.sessionStorage.getItem(endpoints.SU) + "/application/v1/semenutilization";
		fetch(url, {
			method: "POST",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			},
			body: JSON.stringify(this.getJSON())
		})
			.then(res => {
				if (res.ok) {
					//return res.json();
					this.props.history.push(`/insemination/orderlist`)
				} else {
					throw Error(res.statusText);
				}
			})
			.then(json => {

			})
			.catch(error => {
				this.setAlert("Server error 7", error.message);


			});
	}


	isource() {
		return (<CCard><CCardHeader>
			<strong>About insemination source</strong>
		</CCardHeader>
			<CCardBody>
				<CFormGroup>
					<CLabel htmlFor="val1"><strong>Breed:</strong>  {this.state.orderdetail.breed}</CLabel>
				</CFormGroup>
				<CFormGroup>
					<CLabel htmlFor="value2"><strong>Ordered To:</strong>  {this.state.orderdetail.orderedTo}</CLabel>
				</CFormGroup>
				<CFormGroup>
					<CLabel htmlFor="value3"><strong>Email:</strong>  {this.state.orderdetail.emailto}</CLabel>
				</CFormGroup>

				<CFormGroup>
					<CLabel htmlFor="value4"><strong>Order Date:</strong> {this.state.orderdetail.orderDate}</CLabel>
				</CFormGroup>
			</CCardBody></CCard>);

	}
	destination() {
		return (<CCard><CCardHeader>
			<strong> About insementated to</strong>
		</CCardHeader>
			<CCardBody>
				<CFormGroup row className="my-0">
					<CCol xs="6">
						<CFormGroup>
							<CLabel htmlFor="dateOfBirth"><strong>DoB:</strong> {this.state.animal.dateOfBirth}</CLabel>
						</CFormGroup>
					</CCol>
					<CCol xs="6">
						<CFormGroup>
							<CLabel htmlFor="breed"><strong>Breed:</strong> {this.state.animal.breed}</CLabel>
						</CFormGroup>
					</CCol>
				</CFormGroup>
				<CFormGroup>
					<CLabel htmlFor="animalIDMother"><strong>Mother:</strong> {this.state.animal.animalIDMother}</CLabel>
				</CFormGroup>
				<CFormGroup>
					<CLabel htmlFor="birthPlace"><strong>Birth Place:</strong> {this.state.animal.birthPlace}</CLabel>
				</CFormGroup>
			</CCardBody></CCard>);
	}


	render() {
		return (
			<>
				<CRow>
					<CCol xs="9" sm="9">
						{this.displayAlert(this.state.alert.title, this.state.alert.message)}
					</CCol>
					<CCol xs="9" sm="9">
						<CCard>
							<CCardHeader>
								Insemination
              <small>  </small>
							</CCardHeader>
							<CCardBody>
								<CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
									<CRow>
										<CCol xs="1">
										</CCol>
										<CCol xs="5">
											<CFormGroup>
												<CLabel htmlFor="osid">Insemination Source</CLabel>
												<CSelect custom name="osid" id="osid" onChange={(e) => this.handleChange(e)} value={this.state.osid}>
													{this.state.semenorders && this.state.semenorders.map(item =>
														<option value={item.osid}>{item.osid}</option>
													)}

												</CSelect>
											</CFormGroup>
										</CCol>
										<CCol xs="5">
											<CFormGroup>
												<CLabel htmlFor="animalID">Inseminated to</CLabel>
												<CSelect custom name="animalID" id="animalID" onChange={this.handleChange.bind(this)} value={this.state.animalID}>
													{this.state.animals && this.state.animals.map(item =>
														<option value={item.animalID}>{item.animalID}</option>
													)}
												</CSelect>
											</CFormGroup>
										</CCol>
										<CCol xs="1">
										</CCol>
									</CRow>
									<CRow>
										<CCol xs="1">
										</CCol>
										<CCol xs="5">
											<CFormGroup>
												<CLabel htmlFor="insemationDate">Insemination Date</CLabel>
												<CInput type="date" name="insemationDate" id="insemationDate" onChange={this.handleChange.bind(this)} value={this.state.insemationDate} placeholder="date" />
											</CFormGroup>
										</CCol>
										<CCol xs="5">
											<CFormGroup>
												<CLabel htmlFor="employeeID">Perfomed by (Employee) </CLabel>
												<CSelect custom name="employeeID" id="employeeID" onChange={this.handleChange.bind(this)} value={this.state.employeeID}>
													{this.state.employees && this.state.employees.map(item =>
														<option value={item.email}>{item.email}</option>
													)}
												</CSelect>
											</CFormGroup>
										</CCol>

										<CCol xs="1">
										</CCol>
									</CRow>
									<CRow>
										<CCol xs="1">
										</CCol>
										<CCol md="8">
											<CLabel htmlFor="notes">Notes</CLabel>
										</CCol>

										<CCol xs="1">
										</CCol>
									</CRow>
									<CRow>
										<CCol xs="1">
										</CCol>

										<CCol xs="12" md="10">
											<CTextarea
												name="notes" id="notes" onChange={this.handleChange.bind(this)} value={this.state.notes}
												rows="5"
												placeholder="Content..."
											/>
										</CCol>
										<CCol xs="1">
										</CCol>
									</CRow>
									<CRow></CRow>
									<CRow>
										<CCol xs="2">
										</CCol>
										<CFormGroup variant="custom-checkbox" inline>

										</CFormGroup>
										<CCol xs="1">
										</CCol>
									</CRow>
								</CForm>
							</CCardBody>
							<CCardFooter>

								<CRow>
									<CCol xs="12" sm="3" />
									<CCol xs="12" sm="4" />
									<CCol xs="12" sm="2">
										<CFormGroup>
											<CButton block color="info" onClick={(e) => this.handleSubmitClick(e)} >Submit</CButton>
										</CFormGroup>
									</CCol>
									<CCol xs="12" sm="2">
										<CFormGroup>
											<CButton block color="info" onClick={(e) => this.handleBackClick(e)}>Back</CButton>
										</CFormGroup>
									</CCol>
									<CCol xs="12" sm="1" />
								</CRow>


							</CCardFooter>
						</CCard>
					</CCol>
					<CCol xs="12" sm="3">
						{this.state.fondsource === true ? this.isource() : null}
						{this.state.foundanimal === true ? this.destination() : null}
					</CCol>
				</CRow>

			</>
		)
	}
}

export default Insemination
