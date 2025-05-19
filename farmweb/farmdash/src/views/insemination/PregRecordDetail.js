import React, { Component } from 'react'
import { CCard, CCardBody, CCardHeader, CCol, CRow, CLabel } from '@coreui/react'

import {
	CButton,
	CCardFooter,
	CBadge,
	CTextarea,
	CAlert
} from '@coreui/react'

import Circle from "../mcomp/Circle";
import endpoints from "../config/Configuration";

class PregRecordDetail extends Component {

	constructor(props) {
		super(props);

		this.state = {
			response: false,
			aerecord: [],
			exitDate: '',
			alert: {
				title: "Here ", message: "There", display: false
			}

		};
		this.fetchAERecord();
	}

	getBadge = status => {
	  switch (status) {
	    case "Conceived": return (<><Circle key='mykey' bgColor='#A1D363' width='15' height='15'></Circle>  <CLabel>  Conceived</CLabel></>)
	    default: return (<><Circle key='mykey' bgColor='#E94F37' width='15' height='15'>Not Conceived</Circle>  <CLabel>  Not Conceived</CLabel></>)
	  }
	}
	

	getMethod = code => {
		switch (code) {
			case "SU": return (<strong>Artifical Insemination</strong>)
			case "MB": return (<strong>Natural Herding</strong>)
			default: return (<strong>Not Mentioned</strong>)
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

	fetchAERecord() {
		let url = window.sessionStorage.getItem(endpoints.AE)+'/application/v1/animalexamination/' + this.props.match.params.peno
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
								this.setState({ aerecord: [] });
							} else {
								this.setState({ aerecord: data });
								console.log(data);
								console.log(this.state.aerecord);
							}
							return;
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


	handleBackClick(e) {
		this.props.history.goBack();
	}

	handleChange(event) {
		switch (event.target.id) {
			case 'exitDate': this.setState({ exitDate: event.target.value }); break;
			default: return '';
		}
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

	render() {
		return (
			<CRow>
				  <CCol xs="12" sm="12">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
          </CCol>
				<CCol xs="12" sm="6">
					<CCard>
						<CCardHeader>
							Animal Examination Record: {this.props.match.params.mb4hid}
						</CCardHeader>
						<CCardBody>

							<table className="table table-striped table-hover">
								<tbody>
									<tr >
										<td>Animal No.:</td>
										<td><strong>{this.state.aerecord.animalID}</strong></td>
									</tr>
									<tr >
										<td>Examination date:</td>
										<td><strong>{this.state.aerecord.examinationDate}</strong></td>
									</tr>
									<tr >
										<td>Expected date:</td>
										<td><strong>{this.state.aerecord.extepctedDate}</strong></td>
									</tr>
									<tr >
										<td>Insemination process:</td>
										<td><strong>{this.getMethod(this.state.aerecord.refType)}</strong></td>
									</tr>
									<tr >
										<td>Insemination record:</td>
										<td><strong>{this.state.aerecord.refnumber}</strong></td>
									</tr>
									<tr >
										<td>Examined by:</td>
										<td><strong>{this.state.aerecord.employeeID}</strong></td>
									</tr>
									<tr >
										<td>Comments:</td>
										<td>
											<CTextarea
												name="notes-input"
												id="notes-input"
												value={this.state.aerecord.notes}
												rows="4"
												placeholder="Content..."
											/>
										</td>
									</tr>
									<tr >
										<td>Pregnancy status:</td>
										<td><strong>{this.getBadge(this.state.aerecord.status)}</strong></td>
									</tr>
								</tbody>
							</table>
						</CCardBody>
						{this.manage()}
					</CCard>
				</CCol>
			</CRow>
		)
	}
}

export default PregRecordDetail;
