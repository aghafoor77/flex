
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

class CarrierList extends Component {

	constructor() {
		super();
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
		this.props.history.push('/transporter/assigncarrier/'+item.cid);
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
		let url = window.sessionStorage.getItem(endpoints.TRS) + '/application/v1/carrier'
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
									Registered Animals
	                 				<small className="text-muted"> all registered animal in the form </small>
									<strong className="text-left"> [ Total Rows: {this.state.ardata.length} ]</strong>
								</CCol>
								
							</CRow>
						</CCardHeader>
						<CCardBody>
							<CDataTable
								items={this.state.ardata}
								fields={[
									{ label: 'CID', key: 'cid', _style: { width: '10%' } },
									{ label: 'Carrier #', key: 'carrierNumber', _style: { width: '10%' } },
									{ label: 'Animals', key: 'species', _style: { width: '15%' } },
									{ label: 'Transport Type', key: 'transportType', _style: { width: '10%' } },
									{ label: 'TID', key: 'tid', _style: { width: '10%' } },
									{ label: 'Long Distance', key: 'longDistance', _style: { width: '5%' } },									
									{ label: 'notes', key: 'notes', _style: { width: '20%' } },
									{ label: 'book', key: 'book', _style: { width: '5%' } }
								]}
								hover
								striped
								columnFilter
								itemsPerPage={7}
								pagination
								clickableRows
								onRowClick={(item) => this.props.history.push(`/animal/reglist/${item.cid}`)}
								scopedSlots={{
									'book':
										(item) => (
											<td>
											<CButton
											  block
											  color="info"
											  id={item.cid}
											  onClick={(e) => {
											    e.stopPropagation(); // Prevent row click event
											    this.handleDetailClick(e, item);
											  }}
											>
											  Book
											</CButton>
											</td>
										),
									'longDistance':
										(item) => (
											<td>
												{this.getBadge(item.longDistance)}
											</td>
										),
										
								}}
							/>
						</CCardBody>
					</CCard>
				</CCol>
			</CRow>
		)
	}
}

export default CarrierList
