import React, { Component } from 'react'
import { CCard, CCardBody, CCardHeader, CCol, CRow } from '@coreui/react'

import {
	CButton,
	CCardFooter,
	CForm,
	CFormGroup,
	CTextarea,
	CInput,
	CLabel,
	CBadge,
	CDataTable,
	CAlert,
	CInputCheckbox
} from '@coreui/react'


import endpoints from "../config/Configuration";
import ReactTooltip from "react-tooltip";
import TableScrollbar from 'react-table-scrollbar';

class Test extends Component {

	constructor(props) {
		super(props);
		this.styles = {
			border: '2px solid rgba(0, 0, 0, 0.05)',
		};
		this.state = {
			isdone: false,
			animallist: false,
			orderdetails: [],
			tips: "",
			osid: "",
			notes: "",
			resDate: "",
			employeeID: "",
			repliedBy: "",
			billingURL: "",
			radata: []
		}
		this.fetchRegisteredAnimal();
	};

	handleChange(event) {
		switch (event.target.id) {
			case 'rid': this.setState({ rid: event.target.value }); break;
			case 'notes': this.setState({ notes: event.target.value }); break;
			case 'response': this.setState({ response: event.target.value }); break;
			case 'sex': this.setState({ sex: event.target.value }); break;
			case 'numberofanimals': this.setState({ numberofanimals: event.target.value }); break;
			case 'reportingDate': this.setState({ reportingDate: event.target.value }); break;
			case 'employeeID': this.setState({ employeeID: event.target.value }); break;
			case 'age': this.setState({ age: event.target.value }); break;
			case 'breed': this.setState({ breed: event.target.value }); break;
			default: return '';
		}
	}

	setAlert(title, message) {
		this.setState({ alert: { title: title, message: message, display: true } });
	}

	hideAlertClick(e) {
		this.setState({ alert: { display: false } });
	}
	displayAlert() {
		return (this.state.alert.display === true ? (<CAlert color="danger">

			<CRow>
				<CCol xs="11">

					{this.state.alert.title}: {this.state.alert.message}
				</CCol>
				<CCol xs="1">
					<CButton block onClick={(e) => this.hideAlertClick(e)}>X</CButton>
				</CCol>
			</CRow></CAlert>) : null);

	}

	handleResponseRecForm() {
		alert("-------");
	}

	fetchRegisteredAnimal() {
		let url = endpoints.AR + '/application/v1/registeranimal/age'
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
								this.setState({ radata: [] });
							} else {
								this.setState({ radata: data });
							}

							return this.state.radata;

						})
						.catch(error => {
							alert("4" + error.message);
							return false
						});
				} else {
					return this.displayError(res)
				}

			})
			.catch(error => {
				alert("5" + error.message);
				return false
			});

	}

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

	manageChecks(item) {
		return (

			<td>
				<CFormGroup variant="custom-checkbox" inline>
					<CInputCheckbox custom id={item.animalID} name={item.animalID} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
					<CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
				</CFormGroup>
			</td>)

	}


	// Main render return functions
	render() {
		return (
			<CRow>
				<CCol xs="12" sm="6">
					<CCard>
						<CCardHeader>
						</CCardHeader>
						<CCardBody>
							<TableScrollbar rows={14} >
								<table white-space='nowrap' width="100%">
									<tbody>
										{this.state.radata && this.state.radata.map((item,index) => {
											let bkgclr = index % 2 === 0 ? '#f2f2f2' : 'white'
											if (this.selectedCows.has(item.animalID)) {
												return (
													<tr height="40px" bgcolor={bkgclr}>
														<td width="40%" style={this.styles}>
															<CFormGroup variant="custom-checkbox" inline>
																<CInputCheckbox custom id={item.animalID} name={item.animalID} defaultChecked={true} disabled={this.state.isDsiabled} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
																<CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
															</CFormGroup>
														</td>
														<td width="30%" style={this.styles}>{item.breed}</td>
														<td width="30%" style={this.styles}>{item.dateOfBirth}</td>

													</tr>)
											} else {
												return (
													<tr height="40px" bgcolor={bkgclr}>
														<td width="40%" style={this.styles}>
															<CFormGroup variant="custom-checkbox" inline>
																<CInputCheckbox custom id={item.animalID} name={item.animalID} disabled={this.state.isDsiabled} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
																<CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
															</CFormGroup>
														</td>
														<td width="30%" style={this.styles}>{item.breed}</td>
														<td width="30%" style={this.styles}>{item.dateOfBirth}</td>
													</tr>)
											}
										}
										)}
									</tbody>
								</table>
							</TableScrollbar>

						</CCardBody>
						<CCardFooter>
							<CRow>
								<CCol col="2" sm="4" md="2" xl className="mb-3 mb-xl-0">
									<CButton block color="info" onClick={(e) => this.handleResponseRecForm(e)}>Update</CButton>
								</CCol>
							</CRow>
						</CCardFooter>
					</CCard>
				</CCol>
			</CRow>
		)
	}
}

export default Test