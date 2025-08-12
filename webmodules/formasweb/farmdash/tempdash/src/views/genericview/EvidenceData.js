
import React, { Component } from 'react'
import {
	CBadge,
	CCard,
	CCardBody,
	CCardHeader,
	CCol,
	CDataTable,
	CRow,
	CButton,
	CAlert
} from '@coreui/react'

import Circle from "../mcomp/Circle";

import endpoints from "../config/Configuration";

class TrasactionalData extends Component {

	constructor(props) {
		super(props);
		this.handleDetailClick = this.handleDetailClick.bind(this);
		this.state = {
			ardata: [],
			alert: {
				title: "Here ", message: "There", display: false
			}
		};
		this.fetchRegisteredAnimals();


	}

	handleDetailClick(e, item) {
		this.props.history.push('/transporter/assigncarrier/' + item.cid);
	}
	handleDownloadClick(item) {
		// Construct the download URL
		  const baseUrl = window.sessionStorage.getItem(endpoints.VDR);
		  if (!baseUrl) {
		      console.error("Base URL not found in sessionStorage");
		      return;
		  }
		  const fileUrl = `${baseUrl}/application/v1/scevidence/download/evidence/opendata/${item.link}`;
		  // Create an invisible anchor element
		  const a = document.createElement("a");
		  a.href = fileUrl;
		  a.download = item.link; // Suggested filename (can be changed)
		  document.body.appendChild(a); // Append to the document

		  // Trigger the download
		  a.click();

		  // Remove the element after the download
		  document.body.removeChild(a);
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
	/* ["#393E41", "#E94F37", "#1C89BF", "#A1D363",
					 "#85FFC7", "#297373", "#FF8552", "#A40E4C"]; */

	getBadge = status => {
		switch (status) {
			case true: return (<Circle key='mykey' bgColor='#30a60e' width='10' height='10'></Circle>)
			case false: return (<Circle key='mykey' bgColor='#df0405' width='10' height='10'></Circle>)
			case null: return (<Circle key='mykey' bgColor='#E94F37' width='10' height='10'></Circle>)
			default: return (<Circle key='mykey' bgColor='#E94F37' width='10' height='10'></Circle>)
		}
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
	fetchRegisteredAnimals() {
		let url = window.sessionStorage.getItem(endpoints.VDR) + '/application/v1/scevidence/fetchby/transaction/' + this.props.match.params.trasactionID

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
								this.setState({ ardata: [] });
							} else {
								this.setState({ ardata: data });
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
								<CCol xs="12">
									Evidence
									<small className="text-muted">   </small>
									<strong className="text-left"> [ Total Rows: {this.state.ardata.length} ]</strong>
								</CCol>
							</CRow>
						</CCardHeader>
						<CCardBody>
							<CDataTable
								items={this.state.ardata}
								fields={[
									{ label: 'Transaction ID', key: 'transactionID', _style: { width: '10%' } },
									{ label: 'Evidence ID', key: 'eid', _style: { width: '10%' } },
									{ label: 'Download Link', key: 'link', _style: { width: '40%' } },
									{ label: 'Evidence Type', key: 'resType', _style: { width: '40%' } },
									{ label: 'Access Level', key: 'accessLevel', _style: { width: '40%' } },
									{ label: 'Download', key: 'Download', _style: { width: '40%' } },

								]}
								hover
								striped
								columnFilter
								itemsPerPage={7}
								pagination
								clickableRows
								
								scopedSlots={{
									'Download':
										(item) => (
											
											
											<td>
												<CButton block color="info" id={item.animalID} onClick={(e) => this.handleDownloadClick(item)}>Detail</CButton>
											</td>
											
										)

								}}
							/>
						</CCardBody>
					</CCard>
				</CCol>
			</CRow>
		)
	}
}

export default TrasactionalData
