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
  CRow,
  CAlert
} from '@coreui/react'

import endpoints from "../config/Configuration";

class SaleReport2Slaughterhouse extends Component {

  constructor(props) {
    super(props);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.state = {
      slaughterhousename: "",
      slaughterhousecontact: "",
      rid: "",
      notes: "",
      response: "",
      sex: "",
      numberofanimals: 0,
      reportingDate: "",
      employeeID: "",
      age: "",
      breed: "",
      reftype: "",
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
  }


  setAlert(title, message) {
    this.setState({ alert: { title: title, message: message, display: true } });
  }

  hideAlertClick(e) {
    this.setState({ alert: { display: false } });
  }

  displayAlert() {
    return (this.state.alert.display === true ? (<CCardHeader><CAlert color="danger">
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
      case 'slaughterhousename': this.setState({ slaughterhousename: event.target.value }); break;
      case 'slaughterhousecontact': this.setState({ slaughterhousecontact: event.target.value }); break;
      case 'rid': this.setState({ rid: event.target.value }); break;
      case 'notes': this.setState({ notes: event.target.value }); break;
      case 'response': this.setState({ response: event.target.value }); break;
      case 'sex': this.setState({ sex: event.target.value }); break;
      case 'numberofanimals': this.setState({ numberofanimals: event.target.value }); break;
      case 'reportingDate': this.setState({ reportingDate: event.target.value }); break;
      case 'employeeID': this.setState({ employeeID: event.target.value }); break;
      case 'age': this.setState({ age: event.target.value }); break;
      case 'breed': this.setState({ breed: event.target.value }); break;
      default: return '';
    }
  }



  getJSON() {
    let jsondata = {

      slaughterhousename: this.state.slaughterhousename,
      slaughterhousecontact: this.state.slaughterhousecontact,
      rid: this.state.rid,
      notes: this.state.notes,
      response: this.state.response,
      sex: this.state.sex,
      numberofanimals: this.state.numberofanimals,
      reportingDate: this.state.reportingDate,
      employeeID: this.state.employeeID,
      age: this.state.age,
      breed: this.state.breed
    }
    console.log(jsondata);
    return jsondata;

  }

  handleBackClick = (e) => {
    this.props.history.goBack();
  };

  submit = (e) => {
    this.post();
  };



  post() {
    let url = window.sessionStorage.getItem(endpoints.RS) + "/application/v1/reportslaughterhouse";
    fetch(url, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify(this.getJSON())
    })
      .then(res => {
        if (res.ok) {
          this.props.history.push('/movements/r2shuslist')
        } else {
          throw Error(res.statusText);
        }
      })
      .then(json => {

      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Report Slaughterhouse)")
        return false
      });
  }

  render() {

    return (
      <CRow>
        <CCol xs="12" sm="12">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
        </CCol>
        <CCol xs="12" sm="9">
          <CCard>
            <CCardHeader>
              <CRow><CCol xs="11" sm="11">
                Report Slaughterhouse <small>  . </small>
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
                      <CLabel htmlFor="slaughterhousename">Slaughterhouse Name </CLabel>
                      <CInput name="slaughterhousename" id="slaughterhousename" onChange={this.handleChange.bind(this)} value={this.state.slaughterhousename} placeholder="Enter name of slaughterhouse" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="slaughterhousecontact">Slaughterhouse Contact </CLabel>
                      <CInput name="slaughterhousecontact" id="slaughterhousecontact" onChange={this.handleChange.bind(this)} value={this.state.slaughterhousecontact} placeholder="Enter contact number of slaughterhouse" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="3">
                    <CFormGroup>
                      <CLabel htmlFor="numberofanimals">Number of Animal </CLabel>
                      <CInput name="numberofanimals" id="numberofanimals" onChange={this.handleChange.bind(this)} value={this.state.numberofanimals} placeholder="Enter total number of animal for slaughterhouse" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="4">
                    <CFormGroup>
                      <CLabel htmlFor="sex">Sex </CLabel>
                      <CInput name="sex" id="sex" onChange={this.handleChange.bind(this)} value={this.state.sex} placeholder="Enter sex of animals intended to ship" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="3">
                    <CFormGroup>
                      <CLabel htmlFor="age">Age </CLabel>
                      <CInput name="age" id="age" onChange={this.handleChange.bind(this)} value={this.state.age} placeholder="Enter estiamted age of animals intended to ship" />
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
                      <CLabel htmlFor="breed">Breed of Animals </CLabel>
                      <CInput name="breed" id="breed" onChange={this.handleChange.bind(this)} value={this.state.breed} placeholder="Enter breed of animals intended to ship" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="reportingDate">Reproting Date </CLabel>
                      <CInput type="date" name="reportingDate" id="reportingDate" onChange={this.handleChange.bind(this)} value={this.state.reportingDate} placeholder="date" />
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
                      <CLabel htmlFor="notes">Special notes for slaughterhouses</CLabel>
                      <small>  </small>
                      <CTextarea
                        name="notes"
                        id="notes"
                        onChange={this.handleChange.bind(this)}
                        value={this.state.notes}
                        rows="7"
                        placeholder="Content..."
                      />
                    </CFormGroup>
                  </CCol>

                  <CCol xs="1">
                  </CCol>
                </CRow>              </CForm>
            </CCardBody>
            <CCardFooter>
              <CRow>
                <CCol xs="4" />
                <CCol xs="3" />
                <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" onClick={this.submit}>Submit</CButton>
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

export default SaleReport2Slaughterhouse
