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

class BullMovementList extends Component {

	constructor() {
		super();
		this.state = {
			osdata: [],
			alert: {
				title: "Here ", message: "There", display: false
			}
		};
		this.fetchOrdersS();

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

	fetchOrdersS() {
		let url = window.sessionStorage.getItem(endpoints.MB) + '/application/v1/movebullforherd'
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
								this.setState({ osdata: [] });
							} else {
								this.setState({ osdata: data });
							}

							return this.state.osdata;

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
				this.setAlert("Connection Error-", error.message + " (Move bull data fetching)")
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
	                 				<small className="text-muted"> Bull movements for herd</small>
									<strong className="text-left"> [ Total Rows: {this.state.osdata.length} ]</strong>
								</CCol>
								
							</CRow>
						</CCardHeader>
						<CCardBody>
							<CDataTable
								items={this.state.osdata}
								fields={[
									{ label:'No.', key: 'mb4hid', _style: { width: '10%' } },
									{ label:'Animal', key: 'animalID', _style: { width: '15%' } },
									{ label:'Employee', key: 'employeeID', _style: { width: '15%' } },
									{ label:'Entry Date', key: 'entryDate', _style: { width: '15%' } },
									{ label:'Exit Date', key: 'exitDate', _style: { width: '10%' } },
									{ label:'Group', key: 'groupFemale', _style: { width: '10%' } },
									{ label:'Location', key: 'location', _style: { width: '10%' } },
									{ label:'Status', key: 'status', _style: { width: '5%' } }
								]}
								hover
								striped
								columnFilter
								itemsPerPage={7}
								pagination
								clickableRows
								onRowClick={(item) => this.props.history.push(`/insemination/mb4hl/${item.mb4hid}`)}
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

export default BullMovementList
