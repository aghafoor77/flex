
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

class RegisteredAnimalsList extends Component {

	constructor() {
		super();
		this.state = {
			ardata: [],
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
/* ["#393E41", "#E94F37", "#1C89BF", "#A1D363",
                 "#85FFC7", "#297373", "#FF8552", "#A40E4C"]; */

	getBadge = status => {
		switch (status) {
			case "In": return (<Circle key='mykey' bgColor='#A1D363' width='10' height='10'></Circle>)
			case "out": return (<Circle key='mykey' bgColor='#1C89BF' width='10' height='10'></Circle>)
			case "Feeding": return (<Circle key='mykey' bgColor='#1C89BF' width='10' height='10'></Circle>)
			case "Sell": return (<Circle key='mykey' bgColor='#FF8552' width='10' height='10'></Circle>)
			case "Slaughter": return (<Circle key='mykey' bgColor='#393E41' width='10' height='10'></Circle>)
			case "Deregister": return (<Circle key='mykey' bgColor='#E94F37' width='10' height='10'></Circle>)
			case "Active": return (<Circle key='mykey' bgColor='#A1D363' width='10' height='10'></Circle>)
			case "TRANSFERED": return (<Circle key='mykey' bgColor='#c510d0' width='10' height='10'></Circle>)
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
		let url = window.sessionStorage.getItem(endpoints.AR) + '/application/v1/registeranimal'
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
									//
									{ label: '', key: 'status', _style: { width: '3%' } },
									{ label: 'No.', key: 'animalID', _style: { width: '10%' } },
									{ label: 'Parent', key: 'animalIDMother', _style: { width: '15%' } },
									{ label: 'Sex', key: 'sex', _style: { width: '15%' } },
									{ label: 'Date of Birth', key: 'dateOfBirth', _style: { width: '15%' } },
									{ label: 'Place of Birth', key: 'birthPlace', _style: { width: '10%' } },
									{ label: 'Registrar', key: 'employerID', _style: { width: '10%' } },
									{ label: 'Breed', key: 'breed', _style: { width: '10%' } },
									{ label: 'Examination Report', key: 'pregnancyExamination', _style: { width: '5%' } }
								]}
								hover
								striped
								columnFilter
								itemsPerPage={7}
								pagination
								clickableRows
								onRowClick={(item) => this.props.history.push(`/animal/reglist/${item.animalID}`)}
								scopedSlots={{
									'pregnancyExamination':
										(item) => (
											<td>
												<CButton block color="info" id={item.animalID} onClick={(e) => this.handleDetailClick(e, item)}>Detail</CButton>
											</td>
										),
									'status':
										(item) => (
											<td>
												{this.getBadge(item.status)}
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

export default RegisteredAnimalsList
