import React, { Component } from 'react'
import { CIcon } from '@coreui/icons-react';
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
	CInputCheckbox,


} from '@coreui/react'

import TableScrollbar from 'react-table-scrollbar';

import endpoints from "../config/Configuration";

import ReactTooltip from "react-tooltip";

import { cilCheckCircle } from '@coreui/icons';
import GA from '../../views/icons/access.png';

class GenericGraphView extends Component {

	constructor(props) {
		super(props);

		this.handleBackClick = this.handleBackClick.bind(this);
		this.handleGrantAccess = this.handleGrantAccess.bind(this);
		this.handleComboChange = this.handleComboChange.bind(this);
		this.handleSaveClick = this.handleSaveClick.bind(this);


		this.state = {
			isDsiabled: false,
			transdata: [],
			slaughdata: [],
			ardata: [],
			animalData: [],
			animalDataID: "",
			transid: "",
			level: "Transporter",
			slaughtid: "",
			employeeID: "",
			currentSelect: "",
			isButtonVisible: false,
			isAllowed: false,

			alert: {
				title: "Here ", message: "There", display: false
			}
		};
		this.fetchTransporters();
		this.fetchSALUGHTERHOUSEOWNERs();
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

	fetchSALUGHTERHOUSEOWNERs() {
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
								this.setState({ slaughdata: [{ slaughtid: 'No slaughterouse owner registered ' }] });
							} else {
								this.setState({ slaughdata: data, slaughtid: data[0].did });
							}
							//console.log(data);
							//console.log(this.state.slaughdata);
							return this.state.slaughdata;
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
				this.setAlert("Connection Error-", error.message + " (Slaughterhouse data fetching)")
				return false
			});
	}

	fetchTransporters() {
		let url = window.sessionStorage.getItem(endpoints.VDR) + '/application/v1/scresource/vdr/role/TRANSPORTER'
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
							//console.log(data);
							//console.log(this.state.transdata);
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

	chkTransportationAllowed(animalID, e) {
		let url = window.sessionStorage.getItem(endpoints.DT) + '/application/v1/drugstreatments/quarantine/' + animalID;

		fetch(url, {
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			}
		})
			.then(res => {
				if (res.status === 200) {
					this.selectedCows.add(e.target.value);
				} else if (res.status === 403) {
					alert("Animal is under Quarantine period !");
					e.target.checked = false;
				} else {

					alert("Animal is under Quarantine period !");
					e.target.checked = false;
					this.setState({ isAllowed: false });
					return this.displayError(res)
				}
			})
			.catch(error => {
				this.setState({ isAllowed: false });
				this.setAlert("Connection Error-", error.message + " (Animal data fetching)")
				return false
			});
	}




	handleBackClick = (e) => {
		this.props.history.goBack();
	};

	componentWillMount = () => {
		this.selectedCows = new Set();
		this.selectedData = new Set();
		this.finalSelection = new Map();
		this.resourceSelection = new Map();

	}

	componentWillUnmount() {
		this.selectedCows.delete();
		this.selectedData.delete();
		this.finalSelection.delete();
		this.resourceSelection.delete();
	}
	handleDataCheckChange = (e) => {
		if (this.selectedData.has(e.target.value)) {
			this.selectedData.delete(e.target.value);
			//this.finalSelection
		} else {
			this.selectedData.add(e.target.value);
		}

		//console.log("H=======================================");
		//console.log(this.selectedData);
	}


	handleComboChange = (e) => {
		this.resourceSelection.set(e.target.id, e.target.value);
	}


	handleCheckChange = (e) => {
		if (this.selectedCows.has(e.target.value)) {
			this.selectedCows.delete(e.target.value);
			this.finalSelection.delete(e.target.value);
		} else {

			this.chkTransportationAllowed(e.target.value, e);
		}
	}
	////

	getJSONOld() {
		let animals = [];
		this.selectedCows && this.selectedCows.map(item =>
			animals[animals.length] = item
		);

		let jsondata = {
			to: this.state.transid,
			from: window.sessionStorage.getItem("veid"),
			animals: animals
		}
		return jsondata;

	}

	getJSON() {

		let jsondata = {
			transporter: this.state.transid,
			slaughterhouse: this.state.slaughtid,
			from: window.sessionStorage.getItem("veid"),
			animals: Object.fromEntries(this.finalSelection),
		}
		return jsondata;

	}
	postOld() {
		//console.log(this.getJSON());
		fetch(window.sessionStorage.getItem(endpoints.VDR) + "/application/v1/sctransaction/submit", {
			method: "POST",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			},
			body: JSON.stringify(this.getJSONOld())
		})
			.then(res => {
				if (res.ok) {
					this.props.history.goBack();
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
	post() {
		//console.log(this.getJSON());
		fetch(window.sessionStorage.getItem(endpoints.VDR) + "/application/v1/sctransaction/submitwithal", {
			method: "POST",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			},
			body: JSON.stringify(this.getJSON())
		})
			.then(res => {
				if (res.ok) {
					this.props.history.goBack();
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
		alert(event.target.id + " " + event.target.value);
		switch (event.target.id) {
			case 'employeeID': this.setState({ employeeID: event.target.value }); break;
			case 'transid': this.setState({ transid: event.target.value }); break;
			case 'slaughtid': this.setState({ slaughtid: event.target.value }); break;
			case 'level': this.setState({ level: event.target.value }); break;
			default: return '';
		}
	}

	placeOrderOld(e) {
		//alert(this.state.transid)
		//console.log(this.selectedCows)
		console.log(this.getJSON())
		//alert(this.selectedCows)
		this.postOld()


	}
	placeOrder(e) {
		[this.finalSelection.entries()].map(([word, definition]) => {
			console.log("=========================");
			console.log(word);
			console.log(definition);
		}
		);

		//alert(this.state.transid)
		//console.log(this.selectedCows)
		console.log(this.getJSON())
		//alert(this.selectedCows)
		this.post()


	}



	fetchRegisteredAnimals() {
		let url = window.sessionStorage.getItem(endpoints.AR) + '/application/v1/registeranimal/totransferlist'
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
				this.setAlert("Connection Error-", error.message + " (Animal data fetching)")
				return false
			});
	}
	fetchAnimaData(animalID) {
		let url = window.sessionStorage.getItem(endpoints.VDR) + '/application/v1/sctransaction/fetch/records/' + animalID
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
								this.setState({ animalData: [{ animalDataID: 'not found' }] });
							} else {
								this.setState({ animalData: data });
								this.setState({ animalDataID: data[0].uniqueNo, isButtonVisible: true });

								//this.fetchAnimalHistory(data[0].animalID)
							}
							return this.state.animalData;
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
	handleGrantAccess = (e) => {

		if (this.selectedCows.has(e.target.id)) {

			this.setState({ currentSelect: e.target.id });
			this.fetchAnimaData(e.target.id)
		} else {
			alert("Please select animal !");
		}
	};
	handleSaveClick = (e) => {

		var up = new Map();

		[...this.resourceSelection.keys()].map((key, value) => {
			console.log(key + "=========================" + this.resourceSelection.get(key));
			up.set(key, this.resourceSelection.get(key));
		}

		);
		this.finalSelection.set(this.state.currentSelect, Object.fromEntries(up));
		this.resourceSelection.clear();
		this.setState({ currentSelect: "", animalData: [], isButtonVisible: false });
	}

	render() {
		const { isDsiabled } = this.state;
		return (
			<CRow>
				<CCol xs="12" sm="12">
					{this.displayAlert(this.state.alert.title, this.state.alert.message)}
				</CCol>
				<CCol xs="12" sm="11">
					<CCard>
						<CCardHeader>
							<CRow><CCol xs="11" sm="11">
								Transport Animals <small>  select animals to transport </small>
							</CCol>
							</CRow>
						</CCardHeader>
						<CCardBody>
							<CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
								<CRow>
									<CCol xs="1">
									</CCol>
									<CCol xs="7">
										<CFormGroup>
											<CLabel htmlFor="slaughtid">Select Slaughterhouse
												<CSelect custom name="slaughtid" id="slaughtid" onChange={this.handleChange.bind(this)} value={this.state.slaughtid}>
													{this.state.slaughdata && this.state.slaughdata.map(item =>
														<option value={item.did}>{item.did} ({item.company})</option>
													)}
												</CSelect></CLabel>
										</CFormGroup>
									</CCol>

									<CCol xs="1">
									</CCol>
								</CRow>
								<CRow>
									<CCol xs="1">
									</CCol>
									<CCol xs="7">
										<CFormGroup>
											<CLabel htmlFor="transid">Select Transporter
												<CSelect custom name="transid" id="transid" onChange={this.handleChange.bind(this)} value={this.state.transid}>
													{this.state.transdata && this.state.transdata.map(item =>
														<option value={item.did}>{item.did} ({item.company})</option>
													)}
												</CSelect></CLabel>
										</CFormGroup>
									</CCol>

									<CCol xs="1">
									</CCol>
								</CRow>
								<CRow>
									<CCol xs="1">
									</CCol>
									<CCol xs="4" sm="4">
										<CLabel htmlFor="drungs">Select animals for transfer </CLabel>
										<CFormGroup style={this.styles}>
											<TableScrollbar rows={14} >
												<table >
													<tbody>
														{this.state.ardata && this.state.ardata.map(item => {
															if (this.selectedCows.has(item.animalID)) {
																return (

																	<tr >
																		<div style={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
																			<CFormGroup variant="custom-checkbox" inline style={{ marginRight: "10px" }}>
																				<CInputCheckbox custom id={item.animalID} name={item.animalID} defaultChecked={true} disabled={this.state.isDsiabled} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
																				<CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
																				&nbsp;&nbsp;&nbsp;<span>
																					<div style={{ display: 'inline-flex', alignItems: 'center' }}>
																						<img
																							id={item.animalID}
																							src={GA} // Replace with your PNG file path
																							alt="Grant Access"
																							width="20" // Adjust the width
																							height="20" // Adjust the height
																							onClick={(e) => this.handleGrantAccess(e)}
																							style={{ cursor: 'pointer', marginRight: '5px' }}
																						/>
																						<span>Grant Access</span>
																					</div>
																				</span>
																			</CFormGroup>

																		</div>

																	</tr>)
															} else {
																return (
																	<tr >
																		<CFormGroup variant="custom-checkbox" inline>
																			<CInputCheckbox custom id={item.animalID} name={item.animalID} disabled={this.state.isDsiabled} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
																			<CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
																			&nbsp;&nbsp;&nbsp;<span>
																				<div style={{ display: 'inline-flex', alignItems: 'center' }}>
																					<img
																						id={item.animalID}
																						src={GA} // Replace with your PNG file path
																						alt="Grant Access"
																						width="20" // Adjust the width
																						height="20" // Adjust the height
																						onClick={(e) => this.handleGrantAccess(e)}
																						style={{ cursor: 'pointer', marginRight: '5px' }}
																					/>
																					<span>Grant Access</span>
																				</div>
																			</span>


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
									<CCol xs="6" sm="6">
										{this.state.isButtonVisible && (<CLabel htmlFor="drungs"> Define Access level of {this.state.currentSelect}</CLabel>)}

										{this.state.isButtonVisible && (<CButton id="access" block color="info" onClick={(e) => this.handleSaveClick(e)}>Grant Access</CButton>)}

										<CFormGroup style={this.styles}>
											<TableScrollbar rows={14} >
												<table >
													<tbody>
														{this.state.animalData && this.state.animalData.map(item => {
															if (item.uniqueNo != null && item.uniqueNo !== "DATA") {
																if (this.selectedData.has(item.uniqueNo)) {
																	return (

																		<tr >


																			<td>
																				<CFormGroup variant="custom-checkbox" inline style={{ marginRight: "10px" }}>
																					<CInputCheckbox custom id={"1" + item.uniqueNo} name={"1" + item.uniqueNo} defaultChecked={true} disabled={this.state.isDsiabled} value={item.uniqueNo} onClick={(e) => this.handleDataCheckChange(e)} />
																					<CLabel variant="custom-checkbox" htmlFor={"1" + item.uniqueNo}>{item.uniqueNo}[{item.evidenceType}]</CLabel>
																				</CFormGroup>
																			</td>
																			<td>
																				&nbsp;&nbsp;&nbsp;<span>


																					<small>  </small>
																					<CSelect custom name={item.uniqueNo} id={item.uniqueNo} onChange={(e) => this.handleComboChange(e)}>
																						<option value='NONE'>Do not share</option>
																						<option value='All'>All</option>
																						<option value='Transporter'>Transporter</option>
																						<option value='Slaughterhouse'>Slaughterhouse</option>
																						<option value='Transporter'>Transporter</option>
																						<option value='Both'>Both Transporter & Slaughterhouse</option>
																						<option value='Agencies'>Agencies</option>
																					</CSelect>

																				</span>
																			</td>




																		</tr>)
																} else {
																	return (
																		<tr >
																			<td>
																				<CFormGroup variant="custom-checkbox" inline>
																					<CInputCheckbox custom id={"1" + item.uniqueNo} name={"1" + item.uniqueNo} disabled={this.state.isDsiabled} value={item.uniqueNo} onClick={(e) => this.handleDataCheckChange(e)} />
																					<CLabel variant="custom-checkbox" htmlFor={"1" + item.uniqueNo}>{item.uniqueNo}[{item.evidenceType}]</CLabel>
																				</CFormGroup>
																			</td>
																			<td>
																				&nbsp;&nbsp;&nbsp;<span>

																					<CSelect customname={item.uniqueNo} id={item.uniqueNo} onChange={(e) => this.handleComboChange(e)}>
																						<option value='NONE'>Do not share</option>
																						<option value='All'>All</option>
																						<option value='Transporter'>Transporter</option>
																						<option value='Slaughterhouse'>Slaughterhouse</option>
																						<option value='Both'>Both Transporter & Slaughterhouse</option>
																						<option value='Agencies'>Agencies</option>
																					</CSelect>
																				</span>
																			</td>
																		</tr>)
																}

															}
														}
														)}

													</tbody>
													<tfoot></tfoot>
												</table>
											</TableScrollbar>
										</CFormGroup>
									</CCol>									<CCol xs="1">
									</CCol>
								</CRow>
							</CForm>
						</CCardBody>
						<CCardFooter>
							<CRow>
								<CCol xs="4" />
								<CCol xs="3" />
								<CCol xs="2">
									<CFormGroup>
										<CButton block disabled={this.state.isDsiabled} onClick={(e) => this.placeOrder(e)} color="info">Place Order</CButton>
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

export default GenericGraphView
