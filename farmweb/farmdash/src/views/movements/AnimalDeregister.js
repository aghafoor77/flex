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
  CAlert,
  CInputCheckbox

} from '@coreui/react'

import TableScrollbar from 'react-table-scrollbar';

import endpoints from "../config/Configuration";

class AnimalDeregister extends Component {

  constructor(props) {
    super(props);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.styles = {
      border: '2px solid rgba(0, 0, 0, 0.05)',
    };
    this.state = {
      ardata: [],
      animalID : "", 
      deregisterDate : "", 
      notes : "", 
      reportTo : "", 
      location : "", 
      emailTo : "", 
      dcode : "",
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
    this.fetchRegisteredAnimals();
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
                this.setState({ ardata: [{ animalID: 'Not Found' }] });
              } else {
                this.setState({ ardata: data });
                this.setState({ animalID: data[0].animalID });
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

  handleChange(event) {
	switch (event.target.id) {
		  case 'animalID': this.setState({ animalID: event.target.value }); break;
		  case 'deregisterDate': this.setState({ deregisterDate: event.target.value }); break;
		  case 'notes': this.setState({ notes: event.target.value }); break;
		  case 'reportTo': this.setState({ reportTo: event.target.value }); break;
		  case 'location': this.setState({ location: event.target.value }); break;
		  case 'emailTo': this.setState({ emailTo: event.target.value }); break;
		  case 'dcode': this.setState({ dcode: event.target.value }); break;
		  default: return '';
	}
}


getJSON(){
	let jsondata =  {
		animalID : this.state.animalID, 
		deregisterDate : this.state.deregisterDate, 
		notes : this.state.notes, 
		reportTo : this.state.reportTo, 
		location : this.state.location, 
		emailTo : this.state.emailTo, 
		dcode : this.state.dcode
	}
	return jsondata; 

}

  handleBackClick = (e) => {
    this.props.history.goBack();
  };

  submit = (e) => {
    console.log(this.getJSON())
    //alert("Here are ")
    this.post();
  };


   put() {
    console.log(this.getJSON());
    fetch(window.sessionStorage.getItem(endpoints.AR) + "/application/v1/registeranimal/status/"+this.state.animalID, {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      body: "Deregister"
    })
      .then(res => {
        if (res.ok) {
          this.props.history.push('/animal/reglist/'+this.state.animalID)
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
        alert(error.message)
      });
  }

  post() {
    console.log(this.getJSON());
    fetch(window.sessionStorage.getItem(endpoints.ADR) + "/application/v1/animalderegister", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify(this.getJSON())
    })
      .then(res => {
        if (res.ok) {
          this.put();
        } else {
          this.displayError(res);
        }
      })
      .then(json => {
        
      })
      .catch(error => {
        console.error(error)
        alert(error.message)
      });
  }

  render() {
    const { isDsiabled } = this.state;
    return (
      <CRow>
          <CCol xs="12" sm="12">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
          </CCol>
        <CCol xs="12" sm="9">
          <CCard>
            <CCardHeader>
              <CRow><CCol xs="11" sm="11">
                Deregister Animal <small>  If animal died then it must be deregistered. </small>
              </CCol>
                <CCol xs="1" sm="1">

                </CCol></CRow>
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
                        {this.state.ardata && this.state.ardata.map(item =>
                          <option value={item.animalID}>{item.animalID}</option>
                        )}
                      </CSelect>
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="dcode">Death Code </CLabel>
                      <CInput name="dcode" id="dcode" onChange={this.handleChange.bind(this)} value={this.state.dcode} placeholder="Enter death code" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CFormGroup>
                      <CLabel htmlFor="location">Location</CLabel>
                      <CInput name="location" id="location" onChange={this.handleChange.bind(this)} value={this.state.location} placeholder="Enter location where animal died!" />
                    </CFormGroup>
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="deregisterDate">Date</CLabel>
                      <CInput type="date" name="deregisterDate" id="deregisterDate" onChange={this.handleChange.bind(this)} value={this.state.deregisterDate} placeholder="date" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="2">
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="10">
                    <CFormGroup>
                      <CLabel htmlFor="reportTo">Reported to </CLabel>
                      <CInput name="reportTo" id="reportTo" onChange={this.handleChange.bind(this)} value={this.state.reportTo} placeholder="Enter contact of person to send report" />
                    </CFormGroup>
                                 
                      <CFormGroup>
                      <CLabel htmlFor="emailTo">Email for report </CLabel>
                      <CInput name="emailTo" id="emailTo" onChange={this.handleChange.bind(this)} value={this.state.emailTo} placeholder="Enter contact of person to send report" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>

                  <CCol xs="10">
                    <CFormGroup>
                      <CLabel htmlFor="notes">Special notes about deregistration or cause of death</CLabel>
                      <CTextarea
                        htmlFor="notes"
                        name="notes"
                        id="notes"
                        onChange={this.handleChange.bind(this)}
                        value={this.state.notes}
                        rows="5"
                        placeholder="Content..."
                      />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
              </CForm>
            </CCardBody>
            <CCardFooter>
              <CRow>
                <CCol xs="4" />
                <CCol xs="3" />
                <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" onClick={this.submit}>Deregister</CButton>
                  </CFormGroup>
                </CCol>
                <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" onClick={this.handleBackClick}>Back</CButton>
                  </CFormGroup>
                </CCol>
                <CCol xs="1" />
              </CRow>
            </CCardFooter>
          </CCard>
        </CCol>
      </CRow>
    );
  }
}

export default AnimalDeregister
