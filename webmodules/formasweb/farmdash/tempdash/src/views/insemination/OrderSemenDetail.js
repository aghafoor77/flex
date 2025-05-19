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
	CAlert
} from '@coreui/react'

import endpoints from "../config/Configuration";
import ReactTooltip from "react-tooltip";

class OrderSemenDetail extends Component {

	constructor(props) {
		super(props);
		this.fetchSemenOrder();
		// alert(this.props.match.params.osid);
		this.state = {
			isdone: false,
			response: false,
			su: false,
			orderdetails: [],
			tips: "",
			osid: "",
			notes: "",
			resDate: "",
			employeeID: "",
			repliedBy: "",
			billingURL: "",
			osdata: [],
			alert: {
				title: "Here ", message: "There", display: false
			}
		}
		this.fetchOrdersS();
	};

	handleChange(event) {
		switch (event.target.id) {
			case 'osid': this.setState({ osid: event.target.value }); break;
			case 'notes': this.setState({ notes: event.target.value }); break;
			case 'resDate': this.setState({ resDate: event.target.value }); break;
			case 'employeeID': this.setState({ employeeID: event.target.value }); break;
			case 'repliedBy': this.setState({ repliedBy: event.target.value }); break;
			case 'billingURL': this.setState({ billingURL: event.target.value }); break;
			default: return '';
		}
	}

	setStatusToolsTip = status => {

		switch (status) {
			case 'Pending': this.setState({ tips: "Pending" }); break;
			case 'Done': this.setState({ tips: "Response received [done]" }); break;
			case 'Cancel': this.setState({ tips: "Canceled" }); break;
			default: return ''
		}
	}

	setAlert(title, message){
		this.setState({ alert: { title: title, message: message, display: true } });
	}

	hideAlertClick(e) {
		this.setState({ alert: { display: false } });
  }
  
	 displayAlert() {
    return ( this.state.alert.display === true ? (<CCardHeader><CAlert color="danger">
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

	fetchSemenOrder() {
		let url = window.sessionStorage.getItem(endpoints.OS) + '/application/v1/ordersemen/' + this.props.match.params.osid
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
								this.setStatusToolsTip(this.state.orderdetails.status);
							}

							return this.state.orderdetails;

						})
						.catch(error => {
							this.setAlert("Server error", error.message);
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

	post(e) {

		let url = window.sessionStorage.getItem(endpoints.OS) + "/application/v1/responseordersemen";
		fetch(url, {
			method: "POST",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			},
			body: JSON.stringify(this.getJSON())
		})
			.then(res => {
				
				this.doneOrder(e);
				if (res.ok) {

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
			.catch(error => console.error(error));
	}

	getJSON() {
		let jsondata = {
			osid: this.props.match.params.osid,
			notes: this.state.notes,
			resDate: this.state.resDate,
			repliedBy: this.state.repliedBy,
			billingURL: this.state.billingURL
		}
		return jsondata;

	}
	doneOrder(e) {

		let url = window.sessionStorage.getItem(endpoints.OS) + '/application/v1/ordersemen/done/' + this.props.match.params.osid;
		fetch(url, {
			method: "PUT",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			}
		})
			.then(res => {
				
				if (res.status === 200) {
					this.setState({ response: false })
					this.fetchSemenOrder();

				} else {
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection error", error.message);
				return false
			});


	}



	handleCancelClick(e) {

		let url = window.sessionStorage.getItem(endpoints.OS) + '/application/v1/ordersemen/cancel/' + this.props.match.params.osid;
		fetch(url, {
			method: "PUT",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			}
		})
			.then(res => {
				if (res.status === 200) {
					this.fetchSemenOrder();

				} else {
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection error", error.message);

				return false
			});


	}

	handleBackClick(e) {
		this.props.history.goBack();
	}

	handleResponseRecForm() {

		if (this.state.response === false) {
			this.setState({ response: true });
		}
	}


	getBadge = status => {
		switch (status) {
			case 'Pending': return 'warning'
			case 'Done': return 'success'
			case 'Cancel': return 'danger'
			default: return 'primary'

		}
	}

	fetchOrdersS() {
		let url = window.sessionStorage.getItem(endpoints.SU) + '/application/v1/semenutilization/os/' + this.props.match.params.osid
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
								this.setState({ osdata: [], su: false });
							} else {
								this.setState({ osdata: data, su: true });
							}

							return this.state.osdata;

						})
						.catch(error => {
							this.setAlert("Server error", error.message);
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


	displaySemenUtilization() {
		return <CCol xs="12" sm="6">
			<CCard>
				<CCardHeader>
					Insemation Record
      </CCardHeader>
				<CCardBody>
					<CDataTable
						items={this.state.osdata}
						fields={[
							{ key: 'suid', label: 'No.', _style: { width: '15%' } },
							{ key: 'animalID', label: 'Animal', _style: { width: '20%' } },
							{ key: 'insemationDate', label: 'Date', _style: { width: '25%' } },
							{ key: 'notes', _style: { width: '50%' } }
						]}
						hover
						striped
						itemsPerPage={3}
						pagination
						clickableRows
						onRowClick={(item) => this.props.history.push(`/insemination/orderlist/${item.osid}`)}
					/>
				</CCardBody>

			</CCard>
		</CCol>
	}

	displayRForm() {
		return (<CRow>
		  <CCol xs="12" sm="12">
			<CCard>
				<CCardHeader>
					Response
            <small>  Record response received against an order</small>
				</CCardHeader>
				<CCardBody>
					<CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
						<CRow>
							<CCol xs="1">
							</CCol>
							<CCol xs="5">
								<CFormGroup>
									<CLabel htmlFor="repliedBy">Replied by</CLabel>
									<CInput name="repliedBy" id="repliedBy" onChange={this.handleChange.bind(this)} value={this.state.repliedBy} placeholder="Enter name of sender" />
								</CFormGroup>
							</CCol>
							<CCol xs="5">
								<CFormGroup>
									<CLabel htmlFor="resDate">Responsae received date</CLabel>
									<CInput type="date" name="resDate" id="resDate" onChange={this.handleChange.bind(this)} value={this.state.resDate} placeholder="date" />
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
									rows="3"
									placeholder="Content..."
								/>
							</CCol>
							<CCol xs="1">
							</CCol>
						</CRow>
						<CRow>
							<CCol xs="1">
							</CCol>
							<CCol xs="12" md="10">
								<CFormGroup>
									<CLabel htmlFor="billingURL">Billing information by</CLabel>
									<CInput name="billingURL" id="billingURL" onChange={this.handleChange.bind(this)} value={this.state.billingURL} placeholder="Enter billing data" />
								</CFormGroup>
							</CCol>
							<CCol xs="1">
							</CCol>
						</CRow>

					</CForm>
				</CCardBody>
				<CCardFooter>
					<CRow>
						<CCol col="3" sm="4" md="2" xl className="mb-3 mb-xl-0">

						</CCol>
						<CCol col="3" sm="4" md="2" xl className="mb-3 mb-xl-0">
							<CButton block color="info" onClick={(e) => this.post(e)}>Submit</CButton>
						</CCol>
						<CCol col="2" sm="4" md="2" xl className="mb-3 mb-xl-0">
						</CCol>
					</CRow>
				</CCardFooter>
			</CCard>
		</CCol></CRow>)
	}
	// Manage status and buttons
	manage(st) {

		if (st === 'Pending') {
			return <CCardFooter>
				<CRow>
					<CCol col="3" sm="4" md="2" xl className="mb-3 mb-xl-0">
						<CButton block color="danger" onClick={(e) => this.handleCancelClick(e)} >Cancel Order</CButton>
					</CCol>
					<CCol col="3" sm="4" md="2" xl className="mb-3 mb-xl-0">
						<CButton block color="info" onClick={(e) => this.handleResponseRecForm(e)}>Received Response</CButton>
					</CCol>
					<CCol col="2" sm="4" md="2" xl className="mb-3 mb-xl-0">
						<CButton block color="info" onClick={(e) => this.handleBackClick(e)}>Back</CButton>
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
				<CCol xs="6" sm="6">
					<CCard>
						<CCardHeader>
							<CBadge data-tip data-for="registerTip" color={this.getBadge(this.state.orderdetails.status)} shape="pill" style={{ position: 'static' }}>.
          <ReactTooltip id="registerTip" place="top" effect="solid">{this.state.tips}  </ReactTooltip></CBadge>  Order No: {this.props.match.params.osid}
						</CCardHeader>
						<CCardBody>
							<table className="table table-striped table-hover">
								<tbody>
									<tr key="breed">
										<td>Breed</td>
										<td><strong>{this.state.orderdetails.breed}</strong></td>
									</tr>
									<tr key="orderedTo">
										<td>Ordered To</td>
										<td><strong>{this.state.orderdetails.orderedTo}</strong></td>
									</tr>
									<tr key="emailto">
										<td>Email</td>
										<td><strong>{this.state.orderdetails.emailto}</strong></td>
									</tr>
									<tr key="contact">
										<td>Contact</td>
										<td><strong>{this.state.orderdetails.contact}</strong></td>
									</tr>
									<tr key="orderDate">
										<td>Ordered  Date</td>
										<td><strong>{this.state.orderdetails.orderDate}</strong></td>
									</tr>
									<tr key="notes">
										<td>Notes</td>
										<td><strong>{this.state.orderdetails.notes}</strong></td>
									</tr>

								</tbody>
							</table>
						</CCardBody>
						{this.manage(this.state.orderdetails.status)}
					</CCard>
				</CCol>

				{
					(this.state.response === true ? this.displayRForm() : this.state.su ? this.displaySemenUtilization() : null)}
			</CRow>
		)
	}
}

export default OrderSemenDetail