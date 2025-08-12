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

class AnimalTreatment extends Component {

	constructor(props) {
		super(props);
		this.handleBackClick = this.handleBackClick.bind(this);
		this.state = {
			ardata: [],
			animalHistory: [],
			assignedDate: "",
			reason: "",
			drungs: "",
			dtid: "",
			refnumber: "",
			examinedBy: "",
			animalID: "",
			reftype: "",
			endDate: "",
			isAntibiotic: "No",
			quarantinePerod: 0,
			alert: {
				title: "Here ", message: "There", display: false
			}
		};
		this.fetchRegisteredAnimals();
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
						this.setAlert("Server error", data.message)
					})
					.catch(error => { this.setAlert("General error", error.message) });
				return false;
			default:
				return false;
		}
	}

	handleChange(event) {
		switch (event.target.id) {
			case 'assignedDate': this.setState({ assignedDate: event.target.value }); break;
			case 'reason': this.setState({ reason: event.target.value }); break;
			case 'drungs': this.setState({ drungs: event.target.value }); break;
			case 'dtid': this.setState({ dtid: event.target.value }); break;
			case 'refnumber': this.setState({ refnumber: event.target.value }); break;
			case 'examinedBy': this.setState({ examinedBy: event.target.value }); break;
			case 'animalID': this.setState({ animalID: event.target.value }); this.fetchAnimalHistory(event.target.value); break;
			case 'reftype': this.setState({ reftype: event.target.value }); break;
			case 'endDate': this.setState({ endDate: event.target.value }); break;
			case 'quarantinePerod': this.setState({ quarantinePerod: event.target.value }); break;

			case 'isAntibiotic': this.setState({ isAntibiotic: event.target.value }); break;

			default: return '';
		}
	}

	getJSON() {
		let jsondata = {
			assignedDate: this.state.assignedDate,
			reason: this.state.reason,
			drungs: this.state.drungs,
			dtid: this.state.dtid,
			refnumber: this.state.refnumber,
			examinedBy: this.state.examinedBy,
			animalID: this.state.animalID,
			reftype: this.state.reftype,
			endDate: this.state.endDate,
			isAntibiotic: this.state.isAntibiotic,
			quarantinePeriod: this.state.quarantinePerod

		}
		return jsondata;

	}

	handleBackClick = (e) => {
		this.props.history.goBack();
	};

	submit = (e) => {
		console.log(this.getJSON())
		alert("Here are ")
		this.post();
	};



	post() {
		console.log(this.getJSON());
		fetch(window.sessionStorage.getItem(endpoints.DT) + "/application/v1/drugstreatments", {
			method: "POST",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			},
			body: JSON.stringify(this.getJSON())
		})
			.then(res => {
				if (res.ok) {
					this.props.history.push(`/animals/healthrecords`)
				} else {
					this.displayError(res);
				}
			})
			.then(json => {
				this.setState({
					isLoaded: true,
					token: json
				});
			})
			.catch(error => {
				console.error(error)
				alert(error.message)
			});
	}

	fetchRegisteredAnimals() {
		let url = window.sessionStorage.getItem(endpoints.AR) + '/application/v1/registeranimal'
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

							if (data.length === 0) {
								this.setState({ ardata: [{ animalID: 'not found' }] });
							} else {
								this.setState({ ardata: data });
								this.setState({ animalID: data[0].animalID });
								this.fetchAnimalHistory(data[0].animalID)
							}
							return this.state.ardata;
						})
						.catch(error => {
							this.setAlert("General error", error.message);
							return false
						});
				} else {
					return this.displayError(res)
				}
			})
			.catch(error => {
				this.setAlert("Connection Error-", error.message + " (Animal data fetching)")
				return false
			});
	}

	fetchAnimalHistory(val) {
		let url = window.sessionStorage.getItem(endpoints.GHE) + '/application/v1/generalhealthexamination/animal/' + val
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

							console.log(data);
							if (data.length === 0) {
								this.setState({ animalHistory: [{ gaheid: 'Not found' }], refnumber: 'Not found' });
							} else {
								this.setState({ animalHistory: data, refnumber: data[0].gaheid });
							}

						})
						.catch(error => {
							this.setAlert("General error", error.message);
							return false
						});
				} else {
					return this.displayError(res)
				}
			})
			.catch(error => {
				this.setAlert("Connection Error-", error.message + " (Animal data fetching)")
				return false
			});
	}


	render() {
		const { isDsiabled } = this.state;
		return (
			<CRow>
				<CCol xs="12" sm="12">
					{this.displayAlert(this.state.alert.title, this.state.alert.message)}
				</CCol>
				<CCol xs="12" sm="9">
					<CCard>
						<CCardHeader>
							<CRow><CCol xs="11" sm="11">
								Prescription <small>  Prescribe drugs and medicine for animal. </small>
							</CCol>
								<CCol xs="1" sm="1">

								</CCol></CRow>
						</CCardHeader>

						<CCardBody>
							<CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
								<CRow>
									<CCol xs="1">
									</CCol>
									<CCol xs="5">
										<CFormGroup>
											<CLabel htmlFor="animalID">Animal to be examined</CLabel>
											<small>  Select animal from folloiwng list. </small>
											<CSelect custom name="animalID" id="animalID" onChange={this.handleChange.bind(this)} value={this.state.animalID}>
												{this.state.ardata && this.state.ardata.map(item =>
													<option value={item.animalID}>{item.animalID}</option>
												)}
											</CSelect>
										</CFormGroup>
									</CCol>
									<CCol xs="5">
										<CFormGroup>
											<CLabel htmlFor="examinedBy">Prescribed by </CLabel>
											<CInput name="examinedBy" id="examinedBy" onChange={this.handleChange.bind(this)} value={this.state.examinedBy} placeholder="Enter examined by employe/advisor" />
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
											<CLabel htmlFor="refnumber">History</CLabel>
											<small>  Select examination of observation if prescription is based on. </small>
											<CSelect custom name="refnumber" id="refnumber" onChange={this.handleChange.bind(this)} value={this.state.refnumber}>
												{this.state.animalHistory && this.state.animalHistory.map(item =>
													<option value={item.gaheid}>{item.gaheid}</option>
												)}
											</CSelect>
										</CFormGroup>
									</CCol>
									<CCol xs="5">
										<CFormGroup>
											<CLabel htmlFor="isAntibiotic">Antibiotic</CLabel>
											<small>  </small>
											<CSelect custom name="isAntibiotic" id="isAntibiotic" onChange={this.handleChange.bind(this)} value={this.state.isAntibiotic}>
												<option value='Yes'>Yes</option>
												<option value='No'>No</option>
											</CSelect>
										</CFormGroup>
									</CCol>

									<CCol xs="1">
									</CCol>
								</CRow>

								<CRow>
									<CCol xs="1">
									</CCol>
									<CCol xs="4">
										<CFormGroup>
											<CLabel htmlFor="assignedDate">Date of Control</CLabel>
											<CInput type="date" name="assignedDate" id="assignedDate" onChange={this.handleChange.bind(this)} value={this.state.assignedDate} placeholder="date" />
										</CFormGroup>
									</CCol>
									<CCol xs="4">
										<CFormGroup>
											<CLabel htmlFor="endDate">End date of medication</CLabel>
											<CInput type="date" name="endDate" id="endDate" onChange={this.handleChange.bind(this)} value={this.state.endDate} placeholder="end Date" />
										</CFormGroup>
									</CCol>
									<CCol xs="2">
										<CFormGroup>
											<CLabel htmlFor="quarantinePerod">End date of medication</CLabel>
											<CInput type="text" name="quarantinePerod" id="quarantinePerod" onChange={this.handleChange.bind(this)} value={this.state.quarantinePerod} placeholder="quarantine period" />
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
											<CLabel htmlFor="drungs">Drugs and Quantity</CLabel>
											<small>  Drugs and recommended quantity and time</small>
											<CTextarea
												name="drungs"
												id="drungs"
												onChange={this.handleChange.bind(this)}
												value={this.state.drungs}
												rows="7"
												placeholder="Content..."
											/>
										</CFormGroup>
									</CCol>
									<CCol xs="5">
										<CFormGroup>
											<CLabel htmlFor="reason">Reasons</CLabel>
											<small>  What are the reasons for prescribing above medicine</small>
											<CTextarea
												name="reason"
												id="reason"
												onChange={this.handleChange.bind(this)}
												value={this.state.reason}
												rows="7"
												placeholder="Content..."
											/>
										</CFormGroup>
									</CCol>

									<CCol xs="1">
									</CCol>
								</CRow>              </CForm>
						</CCardBody>
						<CCardFooter>
							<CRow>
								<CCol xs="4" />
								<CCol xs="3" />
								<CCol xs="2">
									<CFormGroup>
										<CButton block color="info" onClick={this.submit}>Submit</CButton>
									</CFormGroup>
								</CCol>
								<CCol xs="2">
									<CFormGroup>
										<CButton block color="info" onClick={this.handleBackClick}>Back</CButton>
									</CFormGroup>
								</CCol>
								<CCol xs="1" />
							</CRow>
						</CCardFooter>
					</CCard>
				</CCol>
			</CRow>
		);
	}
}

export default AnimalTreatment
