import React, { Component } from 'react'
import {
	CBadge,
	CCard,
	CCardBody,
	CCardHeader,
	CCol,
	CDataTable,
	CRow,
	CAlert,
	CButton,
	CLabel,
	CSelect
} from '@coreui/react'

import endpoints from "../config/Configuration";

class SaleReport2SlaughterhouseList extends Component {

	constructor() {
		super();
		this.state = {
			shdata: [],
			ardata: [],
			criteria: "",
			animalID: "",
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


	getBadge = status => {
		switch (status) {
			case "Conceived": return (<CBadge color='success'> ........... </CBadge>)
			default: return (<CBadge color='danger'> ....x.... </CBadge>)
		}
	}


	handleChange(event) {
		switch (event.target.id) {
			case 'animalID':
				this.setState({ animalID: event.target.value })
				this.fetchReportedData(event.target.value);
				break;
			default: return '';
		}
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
								this.setState({ ardata: [{ animalID: 'Select All' }] });
							} else {
								data.push({ animalID: "Select All" });
								this.setState({ ardata: data, animalID: "Select All" });
								this.fetchReportedData("Select All");
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

	fetchReportedData(val) {
		let cri = (val === "Select All" ? "" : "/housename/" + val);
		let url = window.sessionStorage.getItem(endpoints.RS) + '/application/v1/reportslaughterhouse' + cri;
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
								this.setState({ shdata: [] });
							} else {
								this.setState({ shdata: data });
							}

							return this.state.shdata;

						})
						.catch(error => {
							this.setAlert("General-Error", error.message + " (Reporting data fetching)")
							return false
						});
				} else {
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection Error-", error.message + " (Reporting data fetching)")
				return false
			});

	}

	render() {
		return (
			<CRow>
				<CCol xs="12" sm="12">
					{this.displayAlert(this.state.alert.title, this.state.alert.message)}
				</CCol>
				<CCol xl={12}>
					<CCard>
						<CCardHeader>
							<CRow>
								<CCol xs="4">
									reportslaughterhouse
	                		<small className="text-muted"> animal control </small>
								</CCol>

								<CCol xs="3">

									<CLabel htmlFor="animalID">Select Animal<CSelect custom name="animalID" id="animalID" onChange={this.handleChange.bind(this)} value={this.state.animalID}>
										{this.state.ardata && this.state.ardata.map(item =>
											<option value={item.animalID}>{item.animalID}</option>
										)}
									</CSelect></CLabel>


								</CCol>
							</CRow>
						</CCardHeader>
						<CCardBody>
							<CDataTable
								items={this.state.shdata}
								fields={[
									{ label: 'No.', key: 'rid', _style: { width: '10%' } },
									{ label: 'Total Animal', key: 'numberofanimals', _style: { width: '5%' } },
									{ label: 'Sex.', key: 'sex', _style: { width: '10%' } },
									{ label: 'Breed', key: 'breed', _style: { width: '10%' } },
									{ label: 'Age', key: 'age', _style: { width: '10%' } },
									{ label: 'Report Date', key: 'reportingDate', _style: { width: '10%' } },
									{ label: 'Status', key: 'response', _style: { width: '10%' } }
								]}
								hover
								striped
								columnFilter
								itemsPerPage={7}
								pagination
								clickableRows
								onRowClick={(item) => {
									this.props.history.push('/movements/r2shuslist/' + item.rid)
								}}
								scopedSlots={{

								}}
							/>


						</CCardBody>
					</CCard>
				</CCol>
			</CRow>
		)
	}
}

export default SaleReport2SlaughterhouseList
