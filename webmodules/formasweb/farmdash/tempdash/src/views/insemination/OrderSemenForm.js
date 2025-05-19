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

class OrderSemenForm extends Component {

	constructor() {
		super();
		this.fetchBreeds();
		this.state = {
			breeds: [],
			osid: "",
			notes: "",
			contact: "",
			orderedTo: "",
			employeeID: "",
			emailto: "",
			farmID: "F-123-456",
			orderDate: "",
			breed: "",
			alert: {
				title: "Here ", message: "There", display: false
			}

		};
	}


	setAlert(title, message) {
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

	

	handleChange(event) {
		switch (event.target.id) {
			case 'osid': this.setState({ osid: event.target.value }); break;
			case 'notes': this.setState({ notes: event.target.value }); break;
			case 'contact': this.setState({ contact: event.target.value }); break;
			case 'orderedTo': this.setState({ orderedTo: event.target.value }); break;
			case 'employeeID': this.setState({ employeeID: event.target.value }); break;
			case 'emailto': this.setState({ emailto: event.target.value }); break;
			case 'farmID': this.setState({ farmID: event.target.value }); break;
			case 'orderDate': this.setState({ orderDate: event.target.value }); break;
			case 'breed': this.setState({ breed: event.target.value }); break;
			default: return '';
		}
	}

	getJSON() {
		let jsondata = {
			osid: this.state.osid,
			notes: this.state.notes,
			contact: this.state.contact,
			orderedTo: this.state.orderedTo,
			employeeID: this.state.employeeID,
			emailto: this.state.emailto,
			farmID: this.state.farmID,
			orderDate: this.state.orderDate,
			breed: this.state.breed
		}
		return jsondata;
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

	post() {
		let url = window.sessionStorage.getItem(endpoints.OS) + "/application/v1/ordersemen";
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
				this.setState({
					isLoaded: true,
					token: json
				});
			})
			.catch(error => {
				this.setAlert("Connection Error", error.message + " (Posting OS data)")
				return false
			});
	}

	fetchBreeds() {
		console.log("Breed data fetching ")
		
		let url = window.sessionStorage.getItem(endpoints.AR) + '/application/v1/breed'		
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
							items.push("No Record Found ");
							if (data.length === 0) {
								this.setState({ breeds: items });
							} else {
								this.setState({ breeds: data });
							}
							this.state.breed = "Select Option";
							return items;

						})
						.catch(error => {
							this.setAlert("Error", error.message + " (Breed data fetching)")
							return false
						});
				} else {
					this.setAlert("Error", " Problems in breed data fetching !")
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection Error-", error.message + " (Breed data fetching)")
				return false
			});

	}

	handleBackClick = (e) => {
		this.props.history.push("/insemination/orderlist");

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
								<CRow>
									<CCol xs="12">

										Oder Semen
	             						 <small>  Order submision for semen</small>
									</CCol>
									
								</CRow>
							</CCardHeader>
							<CCardBody>
								<CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
									<CRow>
										<CCol xs="1">

										</CCol>
										<CCol xs="5">
											<CFormGroup>
												<CLabel htmlFor="breed">Breed</CLabel>
												<CSelect custom name="breed" id="breed" onChange={this.handleChange.bind(this)} value={this.state.breed}>
													{this.state.breeds && this.state.breeds.map(item =>
														<option value={item.breedname}>{item.breedname}</option>
													)}
												</CSelect>
											</CFormGroup>
										</CCol>
										<CCol xs="5">
											<CFormGroup>
												<CLabel htmlFor="orderedTo">Order To</CLabel>
												<CInput name="orderedTo" id="orderedTo" onChange={this.handleChange.bind(this)} value={this.state.orderedTo} placeholder="Enter name of 'order to' company" />
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
												<CLabel htmlFor="emailto">Email</CLabel>
												<CInput type="email" name="emailto" id="emailto" onChange={this.handleChange.bind(this)} value={this.state.emailto} placeholder="Enter email address of company" autoComplete="email" />
											</CFormGroup>
										</CCol>
										<CCol xs="5">
											<CFormGroup>
												<CLabel htmlFor="contact">Contact #</CLabel>
												<CInput name="contact" id="contact" onChange={this.handleChange.bind(this)} value={this.state.contact} placeholder="Enter contact number with country code" />
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
												name="notes"
												id="notes"
												onChange={this.handleChange.bind(this)}
												value={this.state.notes}
												rows="5"
												placeholder="Content..."
											/>
										</CCol>
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
											<CButton block color="info" onClick={(e) => this.post(e)}>Submit</CButton>
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
				</CRow>
			</>
		)
	}
}

export default OrderSemenForm
