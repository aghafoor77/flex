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
	CButton
} from '@coreui/react'

import endpoints from "../config/Configuration";

//import orderedSemenData from './OrderedSemenData'

class OrderSemenList extends Component {

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

	getBadge = status => {
		switch (status) {
			case 'Pending': return 'warning'
			case 'Done': return 'success'
			case 'Cancel': return 'danger'
			default: return 'primary'

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

	fetchOrdersS() {
		let url = window.sessionStorage.getItem(endpoints.OS)+'/application/v1/ordersemen'
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



	render() {
		return (
			<CRow>
				  <CCol xs="12" sm="12">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
          </CCol>
				<CCol xl={12}>
					<CCard>
						<CCardHeader>
							Order No
	                 <small className="text-muted"> List of all orders</small>
							<strong className="text-left"> [ Total Rows: {this.state.osdata.length} ]</strong>
						</CCardHeader>
						<CCardBody>
							<CDataTable
								items={this.state.osdata}
								fields={[
									{ label: 'No.', key: 'osid', _style: { width: '15%' } },
									{ label: 'Order to', key: 'orderedTo', _style: { width: '25%' } },
									{ label: 'Email to', key: 'emailto', _style: { width: '20%' } },
									{ label: 'Contact No.', key: 'contact', _style: { width: '15%' } },
									{ label: 'Date', key: 'orderDate', _style: { width: '10%' } },
									{ label: 'Breed', key: 'breed', _style: { width: '10%' } },
									{ label: 'Status', key: 'status', _style: { width: '5%' } }
								]}
								hover
								striped
								columnFilter
								itemsPerPage={7}
								pagination
								clickableRows
								onRowClick={(item) => this.props.history.push(`/insemination/orderlist/${item.osid}`)}
								scopedSlots={{
									'status':
										(item) => (
											<td>
												<CBadge color={this.getBadge(item.status)}>
													{item.status}
												</CBadge>
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

export default OrderSemenList
