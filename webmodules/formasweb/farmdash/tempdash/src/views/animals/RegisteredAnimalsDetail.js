import React, { Component } from 'react'

import {
	CCard,
	CCardBody,
	CCardHeader,
	CCol,
	CRow,
	CButton,
	CCardFooter,
	CFormGroup,
	CInput,
	CLabel,
	CAlert,
	CDataTable
} from '@coreui/react'

import Circle from "../mcomp/Circle";

import endpoints from "../config/Configuration";

class RegisteredAnimalsDetail extends Component {

	constructor(props) {
		super(props);

		this.state = {
			response: false,
			arData: [],
			movementRec: [],
			feedRed: [],
			healthRed: [],
			exitDate: '',
			alert: {
				title: "Here ", message: "There", display: false
			}
		};
		this.fetchAnimalRegstrationRecord();
		this.fetchAnimalMovementRecord();
		this.fetchFeedingRec();
		this.fetchHealthRec();
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

	fetchHealthRec() {
		let url = window.sessionStorage.getItem(endpoints.MB) + '/application/v1/temporarymovementgroup/animal/' + this.props.match.params.animalID
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
								this.setState({ healthRed: [] });
							} else {
								this.setState({ healthRed: data });
							}

							return this.state.healthRed;

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
				this.setAlert("Connection Error-", error.message + " (Animal movement data fetching)")
				return false
			});
	}

	fetchAnimalMovementRecord() {
		let url = window.sessionStorage.getItem(endpoints.MB) + '/application/v1/movebullforherd/move/' + this.props.match.params.animalID
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
								this.setState({ movementRec: [] });
							} else {
								this.setState({ movementRec: data });
							}

							return this.state.movementRec;

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
				this.setAlert("Connection Error-", error.message + " (Animal movement data fetching)")
				return false
			});

	}

	fetchFeedingRec() {
		let url = window.sessionStorage.getItem(endpoints.FP) + '/application/v1/assignfeed/animal/' + this.props.match.params.animalID
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
								this.setState({ feedRed: [] });
							} else {
								this.setState({ feedRed: data });
							}

							return this.state.feedRed;

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
				this.setAlert("Connection Error-", error.message + " (Animal feeding data fetching)")
				return false
			});

	}

	fetchAnimalRegstrationRecord() {
		let url = window.sessionStorage.getItem(endpoints.AR) + '/application/v1/registeranimal/' + this.props.match.params.animalID
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
								this.setState({ arData: [] });
							} else {
								this.setState({ arData: data });
								console.log(data);
								console.log(this.state.arData);


							}
							return;
						})
						.catch(error => {
							this.setAlert("Error", error.message + " (Animal Registration data fetching)")
							return false
						});
				} else {
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection Error-", error.message + " (Animal Registration data fetching)")
				return false
			});

	}


	handleBackClick(e) {
		this.props.history.goBack();
	}



	manage() {

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

	 submit = (e) => {
    
    
  };
   deregister = (e) => {
	 this.props.history.push('/movements/deregister/'+this.props.match.params.animalID)
  };
 
	getBadge = status => {
		switch (status) {
			case "In": return (<Circle key='mykey' bgColor='#A1D363' width='10' height='10'></Circle>)
			case "out": return (<Circle key='mykey' bgColor='#1C89BF' width='10' height='10'></Circle>)
			case "Feeding": return (<Circle key='mykey' bgColor='#1C89BF' width='10' height='10'></Circle>)
			case "Sell": return (<Circle key='mykey' bgColor='#FF8552' width='10' height='10'></Circle>)
			case "Slaughter": return (<Circle key='mykey' bgColor='#393E41' width='10' height='10'></Circle>)
			case "Deregister": return (<Circle key='mykey' bgColor='#E94F37' width='10' height='10'></Circle>)
			case "Active": return (<Circle key='mykey' bgColor='#A1D363' width='10' height='10'></Circle>)
			case null: return (<Circle key='mykey' bgColor='#E94F37' width='10' height='10'></Circle>)
			default: return (<Circle key='mykey' bgColor='#E94F37' width='10' height='10'></Circle>)
		}
	}

	getDetail = status => {
		switch (status) {
			case "Sell": return (<><CButton block color="danger" onClick={this.submit}>Detail about Sale</CButton></>)
			case "Slaughter": return (<><CButton block color="danger" onClick={this.submit}>Detail about Slaughter</CButton></>)
			case "Deregister": return (<><CButton block color="danger" onClick={this.deregister}>Detail about Deregister</CButton></>)
		}
	}

	goEdit = (e) =>{
		 this.props.history.push("/animals/uar/"+this.props.match.params.animalID);
	}
	render() {
		return (
			<><CRow>
				<CCol xs="12" sm="12">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
          </CCol>
				<CCol xs="12" sm="6">
					<CCard>
						<CCardHeader>
							<CRow>
								<CCol xs="8">
									{this.getBadge(this.state.arData.status)} Animal Profile: {this.props.match.params.animalID} 
								</CCol>
								<CCol xs="4">
									{this.getDetail(this.state.arData.status)}
								</CCol>
								
							</CRow>
						</CCardHeader>
						<CCardBody>
							<CRow>
								<CCol xs="6">
									<CFormGroup>
										<CLabel htmlFor="animalid">Animal ID</CLabel>
										<CInput style={{ backgroundColor: 'transparent' }} disabled='true' id="animalid" value={this.state.arData.animalID} />
									</CFormGroup>
								</CCol>
								<CCol xs="3">
									<CFormGroup>
										<CLabel htmlFor="sex">Sex</CLabel>
										<CInput style={{ backgroundColor: 'transparent' }} disabled='true' id="sex" value={this.state.arData.sex} />
									</CFormGroup>
								</CCol>
								<CCol xs="3">
									<CFormGroup>
										<CLabel htmlFor="breed">Breed</CLabel>
										<CInput style={{ backgroundColor: 'transparent' }} id="breed" disabled='true' value={this.state.arData.breed} />
									</CFormGroup>
								</CCol>
							</CRow>
							<CRow>
								<CCol xs="6">
									<CFormGroup>
										<CLabel htmlFor="placeofbirth">Place of birth</CLabel>
										<CInput style={{ backgroundColor: 'transparent' }} id="placeofbirth" disabled='true' value={this.state.arData.birthPlace} />
									</CFormGroup>
								</CCol>
								<CCol xs="3">
									<CFormGroup>
										<CLabel htmlFor="cdob">Date of Birth</CLabel>
										<CInput style={{ backgroundColor: 'transparent' }} id="cdob" disabled='true' value={this.state.arData.dateOfBirth} />
									</CFormGroup>
								</CCol>
								<CCol xs="3">
									<CFormGroup>
										<CLabel htmlFor="sex">Weight at Birth</CLabel>
										<CInput style={{ backgroundColor: 'transparent' }} disabled='true' id="sex" value={this.state.arData.weightAtBirth + " kg"} />
									</CFormGroup>
								</CCol>
							</CRow>
							<CRow>
								<CCol xs="6">
									<CFormGroup>
										<CLabel htmlFor="cmother">Mother ID</CLabel>
										<CInput style={{ backgroundColor: 'transparent' }} id="cmother" disabled='true' value={this.state.arData.animalIDMother} />
									</CFormGroup>
								</CCol>
								<CCol xs="6">
									<CFormGroup>
										<CLabel htmlFor="chandledby">Registered by</CLabel>
										<CInput style={{ backgroundColor: 'transparent' }} id="chandledby" disabled='true' value={this.state.arData.employeID} />
									</CFormGroup>
								</CCol>
							</CRow>
							<CRow>
								<CCol xs="2">
									<CFormGroup>
										<CButton block onClick={(e) => this.goEdit(e)}>[Edit]</CButton>
									</CFormGroup>
								</CCol>
								<CCol xs="10">
									
								</CCol>
							</CRow>
							 
						</CCardBody>
					</CCard>
				</CCol>
				<CCol xs="12" sm="6">
					<CCard>
						<CCardHeader>
							<CRow><CCol xs="6" sm="6">
								Animal health reports
              				<small> medical and physical examination reports</small>
							</CCol>
								<CCol xs="6" sm="6">

								</CCol></CRow>
						</CCardHeader>
						<CCardBody>
							<CDataTable
								items={this.state.healthRed}
								fields={[
									{ label:"ID", key: 'gaheid', _style: { width: '15%' } },
									{ label:"Date", key: 'gheDate', _style: { width: '15%' } },
									{ label:"Examiner", key: 'observer', _style: { width: '15%' } },
									{ label:"Wound", key: 'wound', _style: { width: '55%' } },
									{ label:"Infection", key: 'infections', _style: { width: '55%' } }
									
								]}
								hover
								striped
								itemsPerPage={5}
								pagination
								clickableRows
								onRowClick={(item) => this.props.history.push(`/insemination/mb4hl/${item.mb4hid}`)}
								scopedSlots={{
									'animalID':
										(item) => (
											<td>
												{(item.animalID === null || item.animalID === '') ? 'Host' : 'Guest'}
											</td>
										)
								}}
							/>


						</CCardBody>
					</CCard>
				</CCol>
			</CRow>
				<CRow>
					<CCol xs="12" sm="6">
						<CCard>
							<CCardHeader>
								<CRow>
									<CCol xs="12">
										Animal feeding record
              					<small> inhouse and outdoor feeding data</small>
										<strong className="text-left"> [ Total Rows: {this.state.feedRed.length} ]</strong>
									</CCol>
								</CRow>
							</CCardHeader>
							<CCardBody>
								<CDataTable
									items={this.state.feedRed}
									fields={[
										{ label:"ID", key: 'afid', _style: { width: '15%' } },
										{ label:"Assigned Date", key: 'assignedDate', _style: { width: '35%' } },
										{ label:"Finish Date", key: 'expectedFinishDate', _style: { width: '35%' } },
										{ label:"Feed Pattern (id)", key: 'fpid', _style: { width: '15%' } }
									]}
									hover
									striped
									itemsPerPage={5}
									pagination
									clickableRows
									onRowClick={(item) => this.props.history.push(`/insemination/mb4hl/${item.mb4hid}`)}
									scopedSlots={{
										'animalID':
											(item) => (
												<td>
													{(item.animalID === null || item.animalID === '') ? 'Host' : 'Guest'}
												</td>
											)
									}}
								/>


							</CCardBody>
						</CCard>
					</CCol>
					<CCol xs="12" sm="6">
						<CCard>
							<CCardHeader>
								<CRow>
									<CCol xs="12">
										Animal movements
	                 				<small className="text-muted"> movements of selected animal </small>
										<strong className="text-left"> [ Total Rows: {this.state.movementRec.length} ]</strong>
									</CCol>
								</CRow>
							</CCardHeader>
							<CCardBody>
								<CDataTable
									items={this.state.movementRec}
									fields={[
										{ key: 'animalID', _style: { width: '15%' } },
										{ key: 'entryDate', _style: { width: '15%' } },
										{ key: 'exitDate', _style: { width: '15%' } },
										{ key: 'location', _style: { width: '55%' } }
									]}
									hover
									striped
									itemsPerPage={5}
									pagination
									clickableRows
									onRowClick={(item) => this.props.history.push(`/insemination/mb4hl/${item.mb4hid}`)}
									scopedSlots={{
										'animalID':
											(item) => (
												<td>
													{(item.animalID === null || item.animalID === '') ? 'Host' : 'Guest'}
												</td>
											)
									}}
								/>
							</CCardBody>
						</CCard>
					</CCol>
				</CRow>
			</>
		);
	}
}

export default RegisteredAnimalsDetail

