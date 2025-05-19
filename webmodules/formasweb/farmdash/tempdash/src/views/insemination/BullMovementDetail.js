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
  CTextarea,
  CInput,
  CLabel,
  CAlert
} from '@coreui/react'

import endpoints from "../config/Configuration";

class BullMovementDetail extends Component {

  constructor(props) {
    super(props);

    this.state = {
      response: false,
      bullmovement: [],
      groupFemales:'',
      exitDate: '',
			alert: {
				title: "Here ", message: "There", display: false
			}
    };
    this.fetchBullMovementRecord();
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

  fetchBullMovementRecord() {
    let url = window.sessionStorage.getItem(endpoints.MB) + '/application/v1/movebullforherd/' + this.props.match.params.mb4hid
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
                this.setState({ bullmovement: [] });
              } else {
                this.setState({ bullmovement: data });
                console.log(data);
                console.log(this.state.bullmovement);
                this.setState({groupFemales:this.state.bullmovement.groupFemale})
                
              }
              return;
            })
            .catch(error => {
              this.setAlert("Error", error.message + " (Bull Movement data fetching)")
              return false
            });
        } else {
          return this.displayError(res)
        }

      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Bull Movement data fetching)")
        return false
      });

  }

  getJSON() {
    let jsondata = {
      exitDate: this.state.exitDate
    }
    return jsondata;

  }

  saveExit(e) {

    let url = window.sessionStorage.getItem(endpoints.MB) + '/application/v1/movebullforherd/exit/' + this.props.match.params.mb4hid;
    fetch(url, {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify(this.getJSON())
    })
      .then(res => {
        if (res.ok) {
          this.props.history.push(`/insemination/mb4hl`)
        } else {
          throw Error(res.statusText);
        }
      })
      .then(json => {
        this.setState({
          isLoaded: true,
          token: json
        });
      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Bull movement exit )")
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
    let st = this.state.bullmovement.exitDate
    if (st === "" || st === null) {
      return <CCardFooter>
        <CRow>
          <CCol col="8" sm="4" md="2" xl className="mb-3 mb-xl-0">
            <CFormGroup>
              <CFormGroup>
                <CLabel htmlFor="exitDate">Exit Date</CLabel>
                <CInput type="date" name="exitDate" id="exitDate" onChange={this.handleChange.bind(this)} value={this.state.exitDate} placeholder="date" />
              </CFormGroup>
            </CFormGroup>
          </CCol>
        </CRow>
        <CRow>
          <CCol col="8" sm="4" md="2" xl className="mb-3 mb-xl-0">
            <CButton block color="info" onClick={(e) => this.saveExit(e)}>Save</CButton>
          </CCol>
          <CCol col="8" sm="4" md="2" xl className="mb-3 mb-xl-0">
            <CButton block color="info" onClick={(e) => this.handleBackClick(e)}>Back</CButton>
          </CCol>
        </CRow>
      </CCardFooter>
    }
    else {
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
              
                <CRow>
									<CCol xs="12">
										Bull Movement Record: {this.props.match.params.mb4hid}
									</CCol>
									
								</CRow>


            </CCardHeader>
            <CCardBody>

              <table className="table table-striped table-hover">
                <tbody>
                  <tr >
                    <td>Animal No.:</td>
                    <td><strong>{this.state.bullmovement.animalID}</strong></td>
                  </tr>
                  <tr >
                    <td>Total Prtners:</td>
                    <td><strong>{this.state.bullmovement.groupFemale}</strong></td>
                  </tr>
                  <tr >
                    <td>Entered for Herd:</td>
                    <td><strong>{this.state.bullmovement.entryDate}</strong></td>
                  </tr>
                  <tr >
                    <td>Exited from Herd:</td>
                    <td><strong>{this.state.bullmovement.exitDate}</strong></td>
                  </tr>
                  <tr >
                    <td>Location:</td>
                    <td><strong>{this.state.bullmovement.location}</strong></td>
                  </tr>
                  <tr >
                    <td>Entered by:</td>
                    <td><strong>{this.state.bullmovement.employeeID}</strong></td>
                  </tr>

                </tbody>
              </table>
            </CCardBody>
            {this.manage()}
          </CCard>
        </CCol>
        <CCol xs="10" sm="4">
          <CCard>
            <CCardHeader>
              List of partners in herd area
          </CCardHeader>
            <CCardBody>
              <CCol>
                <CTextarea
                  name="notes-input"
                  id="notes-input"
                  rows="4"
                  value= {this.state.groupFemales.replace(/;/g , "\n")}
                  placeholder="Content..."
                />

              </CCol>
            </CCardBody>
          </CCard>
          <CCard>
            <CCardHeader>
              Comments
        </CCardHeader>
            <CCardBody>
              <CCol>
                <CTextarea
                  name="notes-input"
                  id="notes-input"
                  value={this.state.bullmovement.notes}
                  rows="4"
                  placeholder="Content..."
                />

              </CCol>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    )
  }
}

export default BullMovementDetail
