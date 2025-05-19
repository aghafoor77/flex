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


import endpoints from "../config/Configuration";

class Facilities extends Component {

	constructor() {
		super();
		this.state = {
			facilityData: [],
			alert: {
				title: "Here ", message: "There", display: false
			}
		};
		this.fetchFacilities();

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

	fetchFacilities() {
		
		let url = window.sessionStorage.getItem(endpoints.HR) +"/application/v1/facility/byfarm/farmId";//window.sessionStorage.getItem(endpoints.MB) + '/application/v1/movebullforherd'
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
								this.setState({ facilityData: [] });
							} else {
								this.setState({ facilityData: data });
							}

							return this.state.facilityData;

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
				this.setAlert("Connection Error-", error.message + " (Facility data fetching)")
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
									Bull Movements
	                 				<small className="text-muted">Facilities</small>
									<strong className="text-left"> [ Total Rows: {this.state.facilityData.length} ]</strong>
								</CCol>
							</CRow>
						</CCardHeader>
						<CCardBody>
							<CDataTable
								items={this.state.facilityData}
								fields={[
									{ label:'No.', key: 'facilityNr', _style: { width: '5%' } },
									{ label:'Size', key: 'areasize', _style: { width: '10%' } },
									{ label:'Date', key: 'fromDate', _style: { width: '7%' } },
									{ label:'Movementacross EU', key: 'movementacrossEU', _style: { width: '10%' } },
									{ label:'Capacity', key: 'maxcapacity', _style: { width: '5%' } },
									{ label:'Activity', key: 'temporaryactivity', _style: { width: '10%' } },
									{ label:'Anlaggningsnumber', key: 'anlaggningsnumber', _style: { width: '10%' } },
									{ label:'Animal Type', key: 'specieskept', _style: { width: '5%' } },
									{ label:'Address', key: 'address', _style: { width: '18%' } },
									{ label:'Type', key: 'type', _style: { width: '5%' } },
									{ label:'Breeding Materials', key: 'breedingmaterials', _style: { width: '5%' } }
								]}
								hover
								striped
								columnFilter
								itemsPerPage={7}
								pagination
								clickableRows
								onRowClick={(item) => this.props.history.push(`/farm/faciities/${item.facilityId}`)}
								scopedSlots={{
									'status':
										(item) => (
											<td>
												{this.getBadge(item.exitDate)}
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

export default Facilities
