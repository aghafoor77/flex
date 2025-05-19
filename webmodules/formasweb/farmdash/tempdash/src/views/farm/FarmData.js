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
  CRow,
  CBadge,
  CAlert
  
} from '@coreui/react'

import CIcon from '@coreui/icons-react'
import { DocsLink } from 'src/reusable'

import endpoints from "../config/Configuration";

class FarmData extends Component {

	constructor() {
		super();
		this.state = {
			farmdata: [],
			isedit: true,
			alert: {
				title: "Here ", message: "There", display: false
			}
		};
		this.fetchFarmData();
		

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

	getBadge = status => {
		switch (status) {
			case "": return (<CBadge color='success'> In herd area </CBadge>)
			case null: return (<CBadge color='success'> In herd area </CBadge>)
			default: return (<CBadge color='danger'> Exited </CBadge>)
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

	fetchFarmData() {
		let url =  window.sessionStorage.getItem(endpoints.HR) +"/application/v1/farm/farmdatabyowner";
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
								this.setState({ farmdata: [] });
							} else {
								this.setState({ farmdata: data });
							}

							return this.state.farmdata;

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
				this.setAlert("Connection Error-", error.message + " (Farm/Facility data fetching)")
				return false
			});

	}
	
	render() {
		return (
			    <>
			      <CRow>
			        <CCol xs="12" sm="12">
			          <CCard>
			            <CCardHeader>
			              Farm Registration
			              <small> Farm registration request submission</small>
			            </CCardHeader>
			            <CCardBody>
			              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
			              <CRow>
		                  <CCol xs="1">
		                  </CCol>
		                  <CCol xs="4">
		                  <CFormGroup>
			                <CLabel htmlFor="farmid">Farm ID</CLabel>
			                <CInput id="farmid" name="farmid" placeholder="Enter your company name" value={this.state.farmdata.farmId} />
			              </CFormGroup>
		                  </CCol>
		                  <CCol xs="2">
		                  <CFormGroup>
			                <CLabel htmlFor="owner">Owner</CLabel>
			                <CInput id="owner" name="owner" placeholder="DE1234567890" value={this.state.farmdata.owner} />
			              </CFormGroup>
		                  </CCol>
		                  <CCol xs="4">
		                  <CFormGroup>
			                <CLabel htmlFor="ad">Approval date</CLabel>
			                <CInput id="ad" name="ad" placeholder="Enter date name" value={this.state.farmdata.dateTime}/>
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
			                      <CLabel htmlFor="company">Company</CLabel>
			                      <CInput id="company" name="company" placeholder="Enter your company name" value={this.state.farmdata.farmCompany} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="5">
			                    <CFormGroup>
			                      <CLabel htmlFor="displayname">Display Name</CLabel>
			                      <CInput id="displayname" name="displayname" placeholder="Enter display form name" value={this.state.farmdata.farmName} />
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
			                      <CLabel htmlFor="email">Email</CLabel>
			                      <CInput type="email" name="email" id="email-input"  placeholder="Enter Email" autoComplete="email" value={this.state.farmdata.email} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="5">
			                    <CFormGroup>
			                      <CLabel htmlFor="contact">Contact #</CLabel>
			                      <CInput id="contact" name="contact" placeholder="Enter contact number with country code" value={this.state.farmdata.farmContactNo} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="1">
			                  </CCol>
			                </CRow>
			                <CRow>
			                  <CCol xs="1">
			                  </CCol>
			                  <CCol md="3">
			                    <CLabel>Farm Address</CLabel>
			                  </CCol>
			                  <CCol xs="5">
			                  </CCol>
			                  <CCol xs="1">
			                  </CCol>
			                </CRow>
			                <CRow>
			                  <CCol xs="1">
			                  </CCol>
			                  <CCol xs="10">
			                    <CFormGroup>
			                      <CLabel htmlFor="street">Street</CLabel>
			                      <CInput type="input" name="street" id="street-input" name="street-input" placeholder="Enter Street" autoComplete="email" value={this.state.farmdata.street} />
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
			                      <CLabel htmlFor="city">City</CLabel>
			                      <CInput id="city" name="city" placeholder="Enter your city" value={this.state.farmdata.city} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="2">
			                    <CFormGroup>
			                      <CLabel htmlFor="postalcode">Postal Code</CLabel>
			                      <CInput name="postalcode" id="postalcode" placeholder="Enter your postal code" value={this.state.farmdata.postalcode} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="4">
			                    <CFormGroup>
			                      <CLabel htmlFor="country">Country</CLabel>
			                      <CInput name="country" id="country" placeholder="Country name" value={this.state.farmdata.country} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="1">
			                  </CCol>
			                </CRow>

			                <CRow>
			                  <CCol xs="1">
			                  </CCol>
			                  <CCol md="8">
			                    <CLabel htmlFor="decription-input">Description</CLabel>
			                  </CCol>

			                  <CCol xs="1">
			                  </CCol>
			                </CRow>

			                <CRow>
			                  <CCol xs="1">
			                  </CCol>

			                  <CCol xs="12" md="10">
			                    <CTextarea
			                      name="decription-input"
			                      id="decription-input"
			                      rows="4"			                      
			                      placeholder="Content..."
			                    	  value={this.state.farmdata.notes}   
			                    />
			                  </CCol>
			                  <CCol xs="1">
			                  </CCol>
			                </CRow>
			              </CForm>
			            </CCardBody>
			            
			          </CCard>
			        </CCol>
			        
			      </CRow>

			    </>
			  )
	}
}

export default FarmData
