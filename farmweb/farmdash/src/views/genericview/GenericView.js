import React, { Component } from 'react'

import {
  CButton,
  CCard,
  CCardBody,
  CCardFooter,
  CCardHeader,
  CCol,
  CLabel,
  CSelect,
  CRow,
  CAlert

} from '@coreui/react'

import endpoints from "../config/Configuration";

class GenericGraphView extends Component {

  constructor(props) {
    super(props);
    this.state = {
      ardata: [],
      animalID:'',
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
    this.fetchRegisteredAnimals();
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
  

  handleChange(event) {
    switch (event.target.id) {
      case 'animalID':
         this.setState({animalID:event.target.value });
        break;
      default: return '';
    }
  }

  handleBackClick(event) {

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
                this.setAlert("Animal not found", "No animal identity found in the system !");
              } else {
                this.setState({ ardata: data, animalID:data[0].animalID });
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

  animalRegistration(e){
    alert(this.state.animalID);

  }
  preAndPostPregnancyPhase(e){
    alert("registratin ");

  }
  animalPregnancyExamination(e){
    alert("registratin ");

  }
  animalFeeding(e){
    alert("registratin ");

  }
  animalHealthExamination(e){
    alert("registratin ");

  }
                
  animalHealthObservation(e){
    alert("registratin ");

  }

  animalTreatments(e){
    alert("registratin ");

  }
  reportingSlaughterhouse(e){
    alert("registratin ");

  }
  temporaryMovement(e){
    alert("registratin ");

  }

  visNtk(){
                     


  }

  render() {
    return (
      <CRow>
        <CCol xs="12" sm="12">
          <CCard>
            <CCardHeader>
              <CRow>
                <CCol xs="6">
                  Generic View for Animal Transportation
	                		<small className="text-muted"> animal control </small>
                </CCol>
                <CCol xs="6">
                  <CLabel htmlFor="animalID">Select Animal<CSelect custom name="animalID" id="animalID" onChange={this.handleChange.bind(this)} value={this.state.animalID}>
                    {this.state.ardata && this.state.ardata.map(item =>
                      <option value={item.animalID}>{item.animalID}</option>
                    )}
                  </CSelect></CLabel>
                </CCol>
              </CRow>
            </CCardHeader>
            <CCardBody>
              <CRow>
                <CCol xs="4">
                  <CButton block color="info" onClick= {(e) => this.animalRegistration(e)}>Animal Registration</CButton>
                </CCol>
                <CCol xs="4">
                  <CButton block color="info" onClick={(e) => this.preAndPostPregnancyPhase()}>Animal Movement for Herd or Semen Order (Semen Utiization as well)</CButton>
                </CCol>
                <CCol xs="4">
                  <CButton block color="info" onClick={(e) => this.animalPregnancyExamination()}>Animal Pregnancy Examination</CButton>
                </CCol>
              </CRow>
              <CRow>
                <CCol xs="12">
                  <CButton block color="white" onClick={this.handleBackClick}></CButton>
                </CCol>
              </CRow>
              <CRow>
                <CCol xs="4">
                  <CButton block color="info" onClick={(e) => this.animalFeeding()}>Animal Feeding</CButton>
                </CCol>
                <CCol xs="4">
                  <CButton block color="info" onClick={(e) => this.animalHealthExamination()}>Animal Health Examination</CButton>
                </CCol>
                <CCol xs="4">
                  <CButton block color="info" onClick={(e) => this.animalHealthObservation()}>Animal Health Observation</CButton>
                </CCol>
              </CRow>
              <CRow>
                <CCol xs="12">
                  <CButton block color="white" onClick={this.handleBackClick}></CButton>
                </CCol>
              </CRow>
              <CRow>
                <CCol xs="4">
                  <CButton block color="info" onClick={(e) => this.animalTreatments()}>Animal Treatments</CButton>
                </CCol>
                <CCol xs="4">
                  <CButton block color="info" onClick={(e) => this.reportingSlaughterhouse()}>Reporting Slaughterhouse</CButton>
                </CCol>
                <CCol xs="4">
                  <CButton block color="info" onClick={(e) => this.temporaryMovement()}>Temporary Movement</CButton>
                </CCol>
              </CRow>
            </CCardBody>
            <CCardFooter>
              <CRow>
                <CCol xs="12">
                 
                </CCol>
              </CRow>
            </CCardFooter>
          </CCard>
        </CCol>
      </CRow>

    );
  }
}

export default GenericGraphView
