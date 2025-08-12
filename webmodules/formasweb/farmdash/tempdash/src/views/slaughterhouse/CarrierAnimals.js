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
	CAlert,
	CInputCheckbox

} from '@coreui/react'

import TableScrollbar from 'react-table-scrollbar';

import endpoints from "../config/Configuration";

import ReactTooltip from "react-tooltip";



class CarrierAnimals extends Component {

	constructor(props) {
		super(props);
		this.handleBackClick = this.handleBackClick.bind(this);
		this.fetchSlaughterhouses();
		this.state = {
			isDsiabled: false,
			transdata: [],
			ardata: [],
			slaughterhoseName: "",
			location: "",
			startDate: "",
			endDate: "",
			transid: "",
			employeeID: "",
			transdata: [],
			alert: {
				title: "Here ", message: "There", display: false
			}
		};
		this.fetchAnimlasToTransfer();
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


	handleBackClick = (e) => {
		this.props.history.goBack();
	};

	componentWillMount = () => {
		this.selectedCows = new Set();
	}

	componentWillUnmount() {
		this.selectedCows.delete();
	}

	handleCheckChange = (e) => {
		if (this.selectedCows.has(e.target.value)) {
			this.selectedCows.delete(e.target.value);
		} else {
			this.selectedCows.add(e.target.value);
		}
	}

	getJSON() {
		let animals = [];
		this.selectedCows && this.selectedCows.map(item =>
			animals[animals.length] = item
		);

		let jsondata = {
			to: this.state.transid,
			from: window.sessionStorage.getItem("veid"),
			cid:this.props.match.params.cid,
			salughterhousename:this.state.slaughterhoseName,
			location:this.state.location,
			startDate:this.state.startDate,
			endDate:this.state.endDate,
			animals:animals
		}
		return jsondata;

	}

	//To save, Assign Carrier to transporter 
	post() {
		console.log(this.getJSON());
		fetch(window.sessionStorage.getItem(endpoints.TRS) + "/application/v1/carrierslaughterhouse", {
			method: "POST",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			},
			body: JSON.stringify(this.getJSON())
		})
			.then(res => {
				if (res.ok) {

				} else {
					this.displayError(res);
				}
			})
			.then(json => {

			})
			.catch(error => {
				console.error(error)
				alert(error.message)
			});
	}

	handleChange(event) {
		switch (event.target.id) {
			case 'slaughterhoseName': this.setState({ slaughterhoseName: event.target.value }); break;
			case 'location': this.setState({ location: event.target.value }); break;
			case 'startDate': this.setState({ startDate: event.target.value }); break;
			case 'endDate': this.setState({ endDate: event.target.value }); break;
			case 'location': this.setState({ location: event.target.value }); break;

			//

			default: return '';
		}
	}
	placeOrder(e) {
		console.log(this.selectedCows)
		console.log(this.getJSON())
		this.post()
	}

	fetchSlaughterhouses() {
		let url = window.sessionStorage.getItem(endpoints.VDR) + '/application/v1/scresource/vdr/role/SALUGHTERHOUSEOWNER'
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
								this.setState({ transdata: [{ transid: 'No transporter exist' }] });
							} else {
								this.setState({ transdata: data, transid: data[0].did });
							}
							console.log(data);
							console.log(this.state.transdata);
							return this.state.transdata;
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

	fetchAnimlasToTransfer() {
		var veid = window.sessionStorage.getItem("veid");
		let url = window.sessionStorage.getItem(endpoints.TRS) + '/application/v1/transferedanimal/list/totransfer/' + veid;
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
							console.log(data)
							if (data.length === 0) {
								this.setState({ ardata: [{ animalID: 'not found' }] });
							} else {
								this.setState({ ardata: data });
								this.setState({ animalID: data[0].animalID });
								//this.fetchAnimalHistory(data[0].animalID)
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
				this.setAlert("Connection Error-", error.message + " (Animals to trasnfer data fetching)")
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
								Assign Transport (Carrier) [ {this.props.match.params.cid} ]o Animals <small>   </small>
							</CCol>

							</CRow>
						</CCardHeader>
						<CCardBody>
							<CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
								<CRow>
									<CCol xs="4" sm="4">
										<CFormGroup>
											<CLabel htmlFor="slaughterhoseName">Slaughterhousename</CLabel>
											<small>  name of Slaughterhouse </small>
											<CInput name="slaughterhoseName" id="slaughterhoseName" onChange={this.handleChange.bind(this)} value={this.state.slaughterhoseName} placeholder="Name of slaughterhose" />
										</CFormGroup>
									</CCol>
									<CCol xs="4" sm="4">
										<CFormGroup>
											<CLabel htmlFor="location">Location</CLabel>
											<small>  lucation of Slaughterhouse </small>
											<CInput name="location" id="location" onChange={this.handleChange.bind(this)} value={this.state.location} placeholder="Location of slaughterhose" />
										</CFormGroup>
									</CCol>

								</CRow>
								<CRow>
									<CCol xs="4" sm="4">
										<CFormGroup>
											<CLabel htmlFor="startDate">Start Date</CLabel>
											<small>  Start date </small>
											<CInput name="startDate" id="startDate" onChange={this.handleChange.bind(this)} value={this.state.startDate} placeholder="start Date" />
										</CFormGroup>
									</CCol>
									<CCol xs="4" sm="4">
										<CFormGroup>
											<CLabel htmlFor="endDate">Expected arrival</CLabel>
											<small>  lucation of Slaughterhouse </small>
											<CInput name="endDate" id="endDate" onChange={this.handleChange.bind(this)} value={this.state.endDate} placeholder="Expected ariival " />
										</CFormGroup>
									</CCol>
								</CRow>
								<CRow>
									<CCol xs="4" sm="1">
									</CCol>

									<CCol xs="7">
										<CFormGroup>
											<CLabel htmlFor="transid">Select Slaughterhouse
												<CSelect custom name="transid" id="transid" onChange={this.handleChange.bind(this)} value={this.state.transid}>
													{this.state.transdata && this.state.transdata.map(item =>
														<option value={item.did}>{item.did}</option>
													)}
												</CSelect></CLabel>
										</CFormGroup>
									</CCol>

									<CCol xs="1">
									</CCol>
								</CRow>
								<CRow>
									<CCol xs="4" sm="1">
									</CCol>
									<CCol xs="7">
										<CLabel htmlFor="drungs">Select animal(s) for transporting to slaughterhouse </CLabel>
										<CFormGroup style={this.styles}>
											<TableScrollbar rows={14} >
												<table >
													<tbody>
														{this.state.ardata && this.state.ardata.map(item => {
															if (this.selectedCows.has(item.animalID)) {
																return (
																	<tr >
																		<CFormGroup variant="custom-checkbox" inline>
																			<CInputCheckbox custom id={item.animalID} name={item.animalID} defaultChecked={true} disabled={this.state.isDsiabled} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
																			<CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
																		</CFormGroup>


																	</tr>)
															} else {
																return (
																	<tr >
																		<CFormGroup variant="custom-checkbox" inline>
																			<CInputCheckbox custom id={item.animalID} name={item.animalID} disabled={this.state.isDsiabled} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
																			<CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
																		</CFormGroup>


																	</tr>)
															}
														}
														)}
													</tbody>
												</table>
											</TableScrollbar>
										</CFormGroup>
									</CCol>
									<CCol xs="1">
									</CCol>
								</CRow>
							</CForm>
						</CCardBody>
						<CCardFooter>
							<CRow>
								<CCol xs="4" />
								<CCol xs="2" />
								<CCol xs="3">
									<CFormGroup>
										<CButton block disabled={this.state.isDsiabled} onClick={(e) => this.placeOrder(e)} color="info">Move to Salughterhouse</CButton>
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

export default CarrierAnimals
