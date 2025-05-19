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

class AssignFeed2Animal extends Component {

	constructor() {
		super();
		this.state = {
			fpdata: [],
			alert: {
				title: "Here ", message: "There", display: false
			}
		};
		this.fetchRegisteredAnimals();

	}
 
  handleDetailClick(e, item) {
    //history.push(`/animals/registeredanimals/${item.animalID}`);
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

	fetchRegisteredAnimals() {
		let url = window.sessionStorage.getItem(endpoints.FP) + '/application/v1/feedpattern'
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
								this.setState({ fpdata: [] });
							} else {
								this.setState({ fpdata: data });
							}

							return this.state.fpdata;

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
									<strong className="text-left"> [ Total Rows: {this.state.fpdata.length} ]</strong>
								</CCol>
								
							</CRow>
						</CCardHeader>
						<CCardBody>
							<CDataTable
                				items={this.state.fpdata}
              					fields={[
									{ label:'No.', key: 'fpid', _style: { width: '10%' } },
									{ label:'Name', key: 'feedName', _style: { width: '15%' } },
									{ label:'Type', key: 'feedType', _style: { width: '15%' } },
									{ label:'Percentage', key: 'percentage', _style: { width: '15%' } },
									{ label:'Provider', key: 'foodSource', _style: { width: '10%' } },
									{ label:'Certificate', key: 'certiOfIngredients', _style: { width: '10%' } },
									{ label:'Date', key: 'creationDate', _style: { width: '5%' } },									
									{ label:'Food for',  key: 'operation', _style: { width: '5%' } }
								]}
								hover
								striped
								columnFilter
								itemsPerPage={7}
								pagination
								clickableRows
                
								onRowClick={(item) => this.props.history.push(`/animals/animalfeeding/${item.fpid}`)}
								 scopedSlots={{
                'operation':
                  (item) => (
                    <td>
                      <CButton block color="info" id={item.animalID} onClick={(e) => this.handleDetailClick(e, item)}>Select</CButton>
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

export default AssignFeed2Animal
