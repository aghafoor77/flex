import React, { Component } from 'react'
import { CCard, CCardBody, CCardHeader, CCol, CRow } from '@coreui/react'

import {
	CButton,
	CCardFooter,
	CFormGroup,
	CTextarea,
	CLabel,
	CBadge,
	CAlert,
	CInputCheckbox
} from '@coreui/react'

import TableScrollbar from 'react-table-scrollbar';

import endpoints from "../config/Configuration";
import ReactTooltip from "react-tooltip";

class SaleReport2SlaughterhouseResponse extends Component {

	constructor(props) {
		super(props);
		this.fetchReport();
		this.state = {
			isdone: false,
			animallist: false,
			orderdetails: [],
			tips: "",
			radata: [],
			alert: {
				title: "Here ", message: "There", display: false
			}
		}
		this.fetchReportslaughterhouseanimalsData();

	};

	update() {
		let animals = [];
		this.selectedCows && this.selectedCows.map(item =>
			animals[animals.length] = item
		);

		let url = window.sessionStorage.getItem(endpoints.RS) + '/application/v1/reportslaughterhouseanimals/animals/' + this.props.match.params.rid;
		fetch(url, {
			method: "POST",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			},
			body: JSON.stringify({ data: animals })
		})
			.then(res => {
				if (res.ok) {
					this.props.history.push('/movements/r2shuslist')
				} else {
					throw Error(res.statusText);
				}
			})
			.then(json => {
				this.setState({
					isLoaded: true,
					token: json
				});
			})
			.catch(error => {
				this.setAlert("Connection Error-", error.message + " (Assign feed  post data)")
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

	setStatusToolsTip = status => {
		switch (status) {
			case 'Pending': this.setState({ animallist: true, tips: "Pending" }); break;
			case 'Done': this.setState({ animallist: true, tips: "Closed" }); break;
			case 'Cancel': this.setState({ animallist: false, tips: "Canceled" }); break;
			default: return this.setState({ animallist: true, tips: "Pending" }); break;
		}
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

	fetchReport() {
		let url = window.sessionStorage.getItem(endpoints.RS) + '/application/v1/reportslaughterhouse/' + this.props.match.params.rid
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
								this.setState({ orderdetails: [] });
							} else {
								this.setState({ orderdetails: data });
								this.setStatusToolsTip(this.state.orderdetails.response);
							}

							return this.state.orderdetails;

						})
						.catch(error => {
							this.setAlert("RS-Server error", error.message)
							return false
						});
				} else {
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("RS-Connection error", error.message)
				return false
			});

	}

	doneOrder(e) {

		let url = window.sessionStorage.getItem(endpoints.RS) + '/application/v1/reportslaughterhouse/done/' + this.props.match.params.rid;
		fetch(url, {
			method: "PUT",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			}
		})
			.then(res => {
				alert(res.status)
				if (res.status === 200) {
					this.setState({ animallist: false })
					this.fetchReport();

				} else {
					return this.displayError(res)
				}

			})
			.catch(error => {
				alert(error.message);
				return false
			});
	}



	handleCancelClick(e) {

		let url = window.sessionStorage.getItem(endpoints.RS) + '/application/v1/reportslaughterhouse/cancel/' + this.props.match.params.rid;
		fetch(url, {
			method: "PUT",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			}
		})
			.then(res => {
				if (res.status === 200) {
					this.fetchReport();

				} else {
					return this.displayError(res)
				}

			})
			.catch(error => {
				alert(error.message);
				return false
			});


	}

	handleBackClick(e) {
		this.props.history.goBack();
	}

	handleCloseClick(e) {
		this.doneOrder(e);
		this.fetchReport(e);
	}

	handleResponseRecForm(e) {
		this.update();
		this.fetchReport(e);
	}

	getBadge = status => {
		switch (status) {
			case 'Pending': return 'warning'
			case 'Done': return 'success'
			case 'Cancel': return 'danger'
			default: return 'warning'

		}
	}

	fetchReportslaughterhouseanimalsData() {
		let url = window.sessionStorage.getItem(endpoints.RS) + '/application/v1/reportslaughterhouseanimals/animals/' + this.props.match.params.rid
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
							data.data.map(item => {
								this.selectedCows.add(item)
							});
							this.fetchReisteredAnimal();
							return;
						})
						.catch(error => {
							this.setAlert("Error", error.message + " (Selected animal data for SH fetching)")
							return false
						});
				} else {
					return this.displayError(res)
				}
			})
			.catch(error => {
				this.setAlert("Connection Error-", error.message + " (Feed Pattern animal data fetching)")
				return false
			});

	}


	fetchReisteredAnimal() {
		let url = window.sessionStorage.getItem(endpoints.AR) + '/application/v1/registeranimal/age'
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

	displayReisteredAnimals() {
		return <CCol xs="12" sm="6">
			<CCard>
				<CCardHeader>
					Select Animal for Slaughterhouse <small>  where animal Id, breed and age is shown</small>
				</CCardHeader>
				<CCardBody>
					<TableScrollbar rows={23} >
						<table white-space='nowrap' width="100%">
							<tbody>
								{this.state.radata && this.state.radata.map((item, index) => {
									let bkgclr = index % 2 === 0 ? '#f2f2f2' : 'white'
									let res = this.state.orderdetails.response;
									let r = false;
									if (res === 'Done' || res === 'Cancel') {
										r = true
									}
									if (this.selectedCows.has(item.animalID)) {
										return (
											<tr height="40px" bgcolor={bkgclr}>
												<td width="40%" style={this.styles}>
													<CFormGroup variant="custom-checkbox" inline>
														<CInputCheckbox custom id={item.animalID} name={item.animalID} defaultChecked={true} disabled={r} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
														<CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
													</CFormGroup>
												</td>
												<td width="30%" style={this.styles}>{item.breed}</td>
												<td width="30%" style={this.styles}>{item.dateOfBirth}</td>

											</tr>)
									} else {
										if (!(r)) {
											return (
												<tr height="40px" bgcolor={bkgclr}>
													<td width="40%" style={this.styles}>
														<CFormGroup variant="custom-checkbox" inline>
															<CInputCheckbox custom id={item.animalID} name={item.animalID} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
															<CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
														</CFormGroup>
													</td>
													<td width="30%" style={this.styles}>{item.breed}</td>
													<td width="30%" style={this.styles}>{item.dateOfBirth}</td>
												</tr>)
										} else { return null; }
									}
								}
								)}
							</tbody>
						</table>
					</TableScrollbar>
				</CCardBody>

			</CCard>
		</CCol>
	}

	// Manage status and buttons
	manage(st) {

		if (st === 'Pending' || st === '') {
			return <CCardFooter>
				<CRow>
					<CCol col="3" sm="4" md="2" xl className="mb-3 mb-xl-0">
						<CButton block color="danger" onClick={(e) => this.handleCancelClick(e)} >Cancel Order</CButton>
					</CCol>
					<CCol col="3" sm="4" md="2" xl className="mb-3 mb-xl-0">
						<CButton block color="info" onClick={(e) => this.handleCloseClick(e)}>Close Report</CButton>
					</CCol>
					<CCol col="2" sm="4" md="2" xl className="mb-3 mb-xl-0">
						<CButton block color="info" onClick={(e) => this.handleResponseRecForm(e)}>Update</CButton>
					</CCol>
				</CRow>
			</CCardFooter>
		}
		if (st === 'Cancel' || st === 'Done') {
			return <CCardFooter>
				<CRow>
					<CCol xs="10">
					</CCol>
					<CCol xs="2">
						<CButton block color="info" onClick={(e) => this.handleBackClick(e)}>Back</CButton>
					</CCol>
				</CRow>
			</CCardFooter>
		}
	}

	// Main render return functions
	render() {
		return (
			<CRow>
				<CCol xs="12" sm="12">
					{this.displayAlert(this.state.alert.title, this.state.alert.message)}
				</CCol>
				<CCol xs="12" sm="6">
					<CCard>
						<CCardHeader>
							<CBadge data-tip data-for="registerTip" color={this.getBadge(this.state.orderdetails.response)} shape="pill" style={{ position: 'static' }}>.
          					<ReactTooltip id="registerTip" place="top" effect="solid">{this.state.tips}  </ReactTooltip></CBadge>  Report No: {this.props.match.params.rid}
						</CCardHeader>
						<CCardBody>
							<table className="table table-striped table-hover">
								<tbody>
									<tr key="numberofanimals">
										<td>No. of Animals</td>
										<td><strong>{this.state.orderdetails.numberofanimals}</strong></td>
									</tr>
									<tr key="age">
										<td>Age</td>
										<td><strong>{this.state.orderdetails.age}</strong></td>
									</tr>
									<tr key="sex">
										<td>Sex</td>
										<td><strong>{this.state.orderdetails.sex}</strong></td>
									</tr>
									<tr key="breed">
										<td>Breed</td>
										<td><strong>{this.state.orderdetails.breed}</strong></td>
									</tr>
									<tr key="reportingDate">
										<td>Reporting Date</td>
										<td><strong>{this.state.orderdetails.reportingDate}</strong></td>
									</tr>

									<tr key="employeeID">
										<td>Reported by</td>
										<td><strong>{this.state.orderdetails.employeeID}</strong></td>
									</tr>
									<tr key="notes">
										<td colspan="2">
											<CLabel htmlFor="notes">Special notes for slaughterhouses</CLabel>
										</td>
									</tr>
									<tr key="notes">
										<td colspan="2">
											<CTextarea
												name="notes"
												id="notes"
												value={this.state.orderdetails.notes}
												rows="3"
												placeholder="Content..."
											/>
										</td>
									</tr>

								</tbody>
							</table>
						</CCardBody>
						{this.manage(this.state.orderdetails.response)}
					</CCard>
				</CCol>	{
					(this.state.animallist === true && this.state.orderdetails.response !== 'Cancel' ? this.displayReisteredAnimals() : null)}
			</CRow>
		)
	}
}

export default SaleReport2SlaughterhouseResponse