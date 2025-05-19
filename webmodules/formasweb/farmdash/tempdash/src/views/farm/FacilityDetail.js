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

class FacilityDetail extends Component {

	constructor(props) {
	    super(props);
		this.state = {
			facilitydata: [],
			isedit: true,
			alert: {
				title: "Here ", message: "There", display: false
			}
		};
		this.fetchFacilityData();
		

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

	fetchFacilityData() {
		let url =  window.sessionStorage.getItem(endpoints.HR) +"/application/v1/facility/"+this.props.match.params.facilityId
		
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
								this.setState({ facilitydata: [] });
							} else {
								this.setState({ facilitydata: data });
							}

							return this.state.facilitydata;

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
			            Facility detail
			              <small> </small>
			            </CCardHeader>
			            <CCardBody>
			              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
			              <CRow>
		                  <CCol xs="1">
		                  </CCol>
		                  <CCol xs="4">
		                  <CFormGroup>
			                <CLabel htmlFor="farmid">Farm Id</CLabel>
			                <CInput id="farmId" name="farmid" placeholder="Enter your company name" value={this.state.facilitydata.farmId} />
			              </CFormGroup>
		                  </CCol>
		                  <CCol xs="2">
		                  <CFormGroup>
			                <CLabel htmlFor="facilityId">Facility ID</CLabel>
			                <CInput id="facilityId" name="facilityId" placeholder="DE1234567890" value={this.state.facilitydata.facilityId} />
			              </CFormGroup>
		                  </CCol>
		                  <CCol xs="4">
		                  <CFormGroup>
			                <CLabel htmlFor="facilityNr">Facility Number</CLabel>
			                <CInput id="facilityNr" name="facilityNr" placeholder="Enter date name" value={this.state.facilitydata.facilityNr}/>
			              </CFormGroup>
		                  </CCol>
		                  <CCol xs="1">
		                  </CCol>
		                </CRow>
			                <CRow>
			                  <CCol xs="1">
			                  </CCol>
			                  <CCol xs="2">
			                    <CFormGroup>
			                      <CLabel htmlFor="maxcapacity">Max Capacity</CLabel>
			                      <CInput id="maxcapacity" name="maxcapacity" placeholder="Enter your company name" value={this.state.facilitydata.maxcapacity} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="3">
			                    <CFormGroup>
			                      <CLabel htmlFor="areasize">Total Area</CLabel>
			                      <CInput id="areasize" name="areasize" placeholder="Enter contact number with country code" value={this.state.facilitydata.areasize} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="5">
			                    <CFormGroup>
			                      <CLabel htmlFor="temporaryactivity">Temporary Activity</CLabel>
	
			                      <CInput id="temporaryactivity" name="temporaryactivity" value={this.state.facilitydata.temporaryactivity} placeholder=""/>
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="1">
			                  </CCol>
			                </CRow>
			                <CRow>
			                  <CCol xs="1">
			                  </CCol>
			                  <CCol xs="6">
			                    <CFormGroup>
			                      <CLabel htmlFor="anlaggningsnumber">Anlaggnings Number</CLabel>
			                      <CInput id="anlaggningsnumber" name="anlaggningsnumber" placeholder="Enter display form name" value={this.state.facilitydata.anlaggningsnumber} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="2">
			                    <CFormGroup>
			                      <CLabel htmlFor="fromDate">Apprival Date</CLabel>
			                      <CInput name="fromDate" id="fromDate" placeholder="Enter your postal code" value={this.state.facilitydata.fromDate} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="2">
			                    <CFormGroup>
			                      <CLabel htmlFor="movementacrossEU">Movement Across EU</CLabel>
			                      <CInput name="movementacrossEU" id="movementacrossEU" placeholder="Country name" value={ (this.state.facilitydata.movementacrossEU == true?"Yes":"No")} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="1">
			                  </CCol>
			                </CRow>
			                <CRow>
			                  <CCol xs="1">
			                  </CCol>
			                  <CCol xs="10">
			                    <CFormGroup>
			                      <CLabel htmlFor="address">Address</CLabel>
			                      <CInput type="input" name="address" id="address" name="address" placeholder="Enter Street" autoComplete="email" value={this.state.facilitydata.address} />
			                    </CFormGroup>
			                  </CCol>

			                  <CCol xs="1">
			                  </CCol>
			                </CRow>
			                <CRow>
			                  <CCol xs="1">
			                  </CCol>
			                  <CCol xs="3">
			                    <CFormGroup>
			                      <CLabel htmlFor="type">Type</CLabel>
			                      <CInput id="type" name="type" placeholder="Enter your city" value={this.state.facilitydata.type} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="7">
			                    <CFormGroup>
			                      <CLabel htmlFor="breedingmaterials">Breeding Materials</CLabel>
			                      <CInput id="breedingmaterials" name="breedingmaterials" placeholder="Enter your city" value={this.state.facilitydata.breedingmaterials} />
			                    </CFormGroup>
			                  </CCol>
			                  <CCol xs="1">
			                  </CCol>
			                </CRow>
			                <CRow>
			                  <CCol xs="1">
			                  </CCol>
			                  <CCol xs="10">
			                    <CFormGroup>
			                      <CLabel htmlFor="specieskept">Species Kept</CLabel>
			                      <CInput name="specieskept" id="specieskept" placeholder="Enter your postal code" value={this.state.facilitydata.specieskept} />
			                    </CFormGroup>
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

export default FacilityDetail
