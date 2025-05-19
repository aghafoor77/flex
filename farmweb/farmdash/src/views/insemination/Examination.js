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

class Examination extends Component {



	constructor() {
		super();

		this.state = {
			ims: [],
			im: [],
			animals: [],
			aeid: "",
			notes: "",
			examinationDate: "",
			refnumber: "",
			refType: "",
			sensorData: "",
			employeeID: "",
			animalID: "",
			extepctedDate: "",
			status: "Conceived",
			selectedanimal: "",
			foundsource: false,
			alert: {
				title: "Here ", message: "There", display: false
			}
		};
		this.fetchAnimals();


	}
	fetchAnimals() {
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
							let items = [];
							items.push("No Order Found ");
							if (data.length === 0) {
								this.setState({ animals: items });
								return items;
							} else {
								this.setState({ animals: data, animalID: data[0].animalID });
								this.fetchAnimal(data[0].animalID);
								return data;
							}
						})
						.catch(error => {
							this.setAlert("Server error", error.message)
							return false
						});
				} else {
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection error", error.message)
				return false
			});

	}

	fetchAnimal(val) {
		this.fetchSourceData(val)

	}

	fetchSourceData(val) {
		let url = window.sessionStorage.getItem(endpoints.SU) + '/application/v1/semenutilization/su/' + val
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
								let items = [{ suid: 'Data not found' }];
								this.setState({ ims: items, foundsource: false });
							} else {
								this.setState({ ims: data, refnumber: data[0].suid });
								this.fetchSource(data[0].suid)
								return data;
							}

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
				this.setAlert("Connection error", error.message)
				return false
			});

	}
	fetchSource(val) {
		if (val.startsWith("SU")) {
			this.setState({ refType: 'SU' });
			let url = window.sessionStorage.getItem(endpoints.SU) + '/application/v1/semenutilization/' + val
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
								items.push("No Order Found ");
								if (data.length === 0) {
									this.setState({ orderdetail: items, foundsource: false });
								} else {
									this.setState({ orderdetail: data, foundsource: true });
									console.log(this.state.orderdetail)
								}

								return data;

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
					this.setAlert("Connection error", error.message)
					return false
				});
		} else {
			if (val.startsWith("MB")) {
				this.setState({ refType: 'MB' });
				let url = window.sessionStorage.getItem(endpoints.MB) + '/application/v1/movebullforherd/' + val
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
									items.push("No Order Found ");
									if (data.length === 0) {
										this.setState({ orderdetail: items, foundsource: false });
									} else {
										this.setState({ orderdetail: data, foundsource: true });
										console.log(data);
									}
									return data;

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


	handleChange(event) {
		switch (event.target.id) {
			case 'aeid': this.setState({ aeid: event.target.value }); break;
			case 'notes': this.setState({ notes: event.target.value }); break;
			case 'examinationDate': this.setState({ examinationDate: event.target.value }); break;
			case 'refnumber': this.setState({ refnumber: event.target.value });
				this.fetchSource(event.target.value);
				break;
			case 'refType': this.setState({ refType: event.target.value }); break;
			case 'sensorData': this.setState({ sensorData: event.target.value }); break;
			case 'employeeID': this.setState({ employeeID: event.target.value }); break;
			case 'animalID': this.setState({ animalID: event.target.value }); this.fetchAnimal(event.target.value); break;
			case 'extepctedDate': this.setState({ extepctedDate: event.target.value }); break;
			case 'status': this.setState({ status: event.target.value }); break;
			default: return '';
		}
	}

	getJSON() {
		let jsondata = {
			notes: this.state.notes,
			examinationDate: this.state.examinationDate,
			refnumber: this.state.refnumber,
			refType: this.state.refType,
			sensorData: this.state.sensorData,
			employeeID: this.state.employeeID,
			animalID: this.state.animalID,
			extepctedDate: this.state.extepctedDate,
			status: this.state.status
		}
		return jsondata;

	}
	post() {
		console.log(this.getJSON());
		fetch(window.sessionStorage.getItem(endpoints.AE) + "/application/v1/animalexamination", {
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
					this.props.history.push(`/insemination/pregl`)
				} else {
					this.displayError(res);
				}
			})
			.then(json => {
				this.setState({
					isLoaded: true,
					token: json
				});
			})
			.catch(error => {
				console.error(error)
				this.setAlert("Connection error", error.message)
			});
	}

	handleBackClick = (e) => {
		this.props.history.goBack();

	}
	handleSubmitClick = (e) => {
		this.post();

	}

	getSemenUtiliation() {
		return (<CCard><CCardHeader>
			<strong>About insemination source</strong>
		</CCardHeader>
			<CCardBody>
				<CFormGroup>
					<CLabel htmlFor="val1"><strong>Insemation Date:</strong>  {this.state.orderdetail.insemationDate}</CLabel>
				</CFormGroup>
				<CFormGroup>
					<CLabel htmlFor="value2"><strong>Order No:</strong>  {this.state.orderdetail.osid}</CLabel>
				</CFormGroup>
				<CFormGroup>
					<CLabel htmlFor="value3"><strong>Notes:</strong>  {this.state.orderdetail.notes}</CLabel>
				</CFormGroup>
				<CFormGroup>
					<CLabel htmlFor="value4"><strong>Performed By:</strong> {this.state.orderdetail.employeeID}</CLabel>
				</CFormGroup>
			</CCardBody></CCard>);
	}

	getSMoveBullDisplay() {
		return (<CCard><CCardHeader>
			<strong>About insemination source</strong>
		</CCardHeader>
			<CCardBody>
				<CFormGroup>
					<CLabel htmlFor="val1"><strong>Entry Date:</strong>  {this.state.orderdetail.entryDate}</CLabel>
				</CFormGroup>
				<CFormGroup>
					<CLabel htmlFor="value4"><strong>Exit Date:</strong> {this.state.orderdetail.exitDate}</CLabel>
				</CFormGroup>
				<CFormGroup>
					<CLabel htmlFor="value3"><strong>Location:</strong>  {this.state.orderdetail.location}</CLabel>
				</CFormGroup>
				<CFormGroup>
					<CLabel htmlFor="value4"><strong>Bull ID:</strong> {this.state.orderdetail.animalID}</CLabel>
				</CFormGroup>
			</CCardBody></CCard>);
	}

	isource() {
		return this.state.refType === "SU" ? this.getSemenUtiliation() : this.getSMoveBullDisplay();


	}

	destination() {
		
		return (<CCard><CCardHeader>
			<strong> About insementated to</strong>
		</CCardHeader>
			<CCardBody>
				<CFormGroup row className="my-0">
					<CCol xs="6">
						<CFormGroup>
							<CLabel htmlFor="dateOfBirth"><strong>DoB:</strong> {this.state.animal.dateOfBirth}</CLabel>
						</CFormGroup>
					</CCol>
					<CCol xs="6">
						<CFormGroup>
							<CLabel htmlFor="breed"><strong>Breed:</strong> {this.state.animal.breed}</CLabel>
						</CFormGroup>
					</CCol>
				</CFormGroup>
				<CFormGroup>
					<CLabel htmlFor="animalIDMother"><strong>Mother:</strong> {this.state.animal.animalIDMother}</CLabel>
				</CFormGroup>
				<CFormGroup>
					<CLabel htmlFor="birthPlace"><strong>Birth Place:</strong> {this.state.animal.birthPlace}</CLabel>
				</CFormGroup>
			</CCardBody></CCard>);
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
								Pregnancy Eamination
							</CCardHeader>
							<CCardBody>
								<CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
									<CRow>
										<CCol xs="1">
										</CCol>
										<CCol xs="5">
											<CFormGroup>
												<CLabel htmlFor="animalID">Animal</CLabel>
												<CSelect custom name="animalID" id="animalID" onChange={this.handleChange.bind(this)} value={this.state.animalID}>
													{this.state.animals && this.state.animals.map(item =>
														<option value={item.animalID}>{item.animalID}</option>
													)}
												</CSelect>
											</CFormGroup>
										</CCol>
										<CCol xs="5">
											<CFormGroup>
												<CLabel htmlFor="refnumber">Insemination Source (based on stored inforamtion)<strong color='red'> Also use MB </strong></CLabel>
												<CSelect custom name="refnumber" id="refnumber" onChange={this.handleChange.bind(this)} value={this.state.refnumber}>
													{this.state.ims && this.state.ims.map(item =>
														<option value={item.suid}>{item.suid}</option>
													)}
												</CSelect>
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
												<CLabel htmlFor="status">Pregnancy Status</CLabel>
												<CSelect custom name="status" id="status" onChange={this.handleChange.bind(this)} value={this.state.status}>
													<option value="Conceived">Conceived </option>
													<option value="NotConceived">Not conceived</option>
												</CSelect>
											</CFormGroup>
										</CCol>
										<CCol xs="3">
											<CFormGroup>
												<CLabel htmlFor="examinationDate">Examination Date</CLabel>
												<CInput type="date" data-date-format="DD MMMM YYYY" name="examinationDate" id="examinationDate" onChange={this.handleChange.bind(this)} value={this.state.examinationDate} placeholder="date" />
											</CFormGroup>
										</CCol>
										<CCol xs="3">
											<CFormGroup>
												<CLabel htmlFor="extepctedDate">Extepcted Date</CLabel>
												<CInput type="date" name="extepctedDate" id="extepctedDate" onChange={this.handleChange.bind(this)} value={this.state.extepctedDate} placeholder="date" />
											</CFormGroup>
										</CCol>
										<CCol xs="1">
										</CCol>
									</CRow>
									<CRow>
										<CCol xs="1">
										</CCol>
										<CCol md="8">
											<CLabel htmlFor="notes">Comments</CLabel>
										</CCol>

										<CCol xs="1">
										</CCol>
									</CRow>

									<CRow>
										<CCol xs="1">
										</CCol>

										<CCol xs="12" md="10">
											<CTextarea

												name="notes" id="notes" onChange={this.handleChange.bind(this)} value={this.state.notes}
												rows="5"
												placeholder="Comments..."
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
											<CButton block color="info" onClick={(e) => this.handleSubmitClick(e)} >Submit</CButton>
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
					<CCol xs="12" sm="3">
						{this.state.foundsource === true ? this.isource() : null}
						{this.state.foundanimal === true ? this.destination() : null}
					</CCol>
				</CRow>

			</>
		)
	}
}

export default Examination 
