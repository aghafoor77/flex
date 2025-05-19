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

class TemporaryMovement extends Component {

  constructor(props) {
    super(props);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.styles = {
      border: '2px solid rgba(0, 0, 0, 0.05)',
    };
    this.state = {
       ardata: [],
      tmid: "",
      outDate: "",
      notes: "",
      purpose: "",
      inDate: "",
      tmType: "",
      employeeID: "",
      animals: [],
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
                this.setState({ ardata: [{ animalID: 'not found' }] });
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
      case 'outDate': this.setState({ outDate: event.target.value }); break;
      case 'notes': this.setState({ notes: event.target.value }); break;
      case 'purpose': this.setState({ purpose: event.target.value }); break;
      case 'inDate': this.setState({ inDate: event.target.value }); break;
      case 'tmType': this.setState({ tmType: event.target.value }); break;
      case 'employeeID': this.setState({ employeeID: event.target.value }); break;
      default: return '';
    }
  }

  getJSON() {
    let animals = [];
    this.selectedCows && this.selectedCows.map(item =>
      animals[animals.length] = item
    );
    let jsondata = {
      outDate: this.state.outDate,
      notes: this.state.notes,
      purpose: this.state.purpose,
      inDate: this.state.inDate,
      tmType: this.state.tmType,
      employeeID: this.state.employeeID,
      animals : {data:animals}
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

  componentWillMount = () => {
    this.selectedCows = new Set();
  }

  componentWillUnmount() {
    this.selectedCows.delete();
  }

  handleCheckChange = (e) => {
    if (this.selectedCows.has(e.target.value)) {
      this.selectedCows.delete(e.target.value);
    } else {
      this.selectedCows.add(e.target.value);
    }
  }

  post() {
    console.log(this.getJSON());
    fetch(window.sessionStorage.getItem(endpoints.TM) + "/application/v1/temporarymovement", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify(this.getJSON())
    })
      .then(res => {
        if (res.ok) {
          this.props.history.push('/movements/list')
        } else {
          this.displayError(res);
        }
      })
      .then(json => {
    	  //this.put();
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
          <CCol xs="9" sm="9">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
          </CCol>
        <CCol xs="9" sm="9">
          <CCard>
            <CCardHeader>
              <CRow><CCol xs="11" sm="11">
                Move out record <small>  Temporary movement of animal out from farm. </small>
              </CCol>
                <CCol xs="1" sm="1">

                </CCol></CRow>
            </CCardHeader>

            <CCardBody>
              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="3">
                    <CFormGroup>
                      <CLabel htmlFor="purpose">Purpose </CLabel>
                      <CInput name="purpose" id="purpose" onChange={this.handleChange.bind(this)} value={this.state.purpose} placeholder="Why animals are moved? " />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="3">
                  <CFormGroup>
                    <CLabel htmlFor="employeeID">Employee ID </CLabel>
                    <CInput name="employeeID" id="employeeID" onChange={this.handleChange.bind(this)} value={this.state.employeeID} placeholder="Employee email? " />
                  </CFormGroup>
                </CCol>
                  <CCol xs="2">
                    <CFormGroup>
                      <CLabel htmlFor="tmType">Type of Movement</CLabel>
                      <CSelect custom name="tmType" id="tmType" onChange={this.handleChange.bind(this)} value={this.state.tmType}>
                          <option value='Donnotknow'>Don't Know</option>
                          <option value='Feeding'>Feeding</option>
                          <option value='MovedasGuest'>Moved as Guest</option>
                          <option value='TemporaryArrangement'>Temporary Arrangement</option>
                      </CSelect>
                    </CFormGroup>
                  </CCol>
                  <CCol xs="2">
                    <CFormGroup>
                      <CLabel htmlFor="outDate">Date</CLabel>
                      <CInput type="date" name="outDate" id="outDate" onChange={this.handleChange.bind(this)} value={this.state.outDate} placeholder="date" />
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
                      <CLabel htmlFor="notes">Special notes about movement</CLabel>
                      <CTextarea
                        name="notes"
                        id="notes"
                        onChange={this.handleChange.bind(this)}
                        value={this.state.notes}
                        rows="17"
                        placeholder="Content..."
                      />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CLabel htmlFor="drungs">Select animals for movements </CLabel>
                    <CFormGroup  style={this.styles}>
                      <TableScrollbar rows={16} >
                        <table >
                          <tbody>
                            {this.state.ardata && this.state.ardata.map(item => {
                              return (
                                <tr >
                                  <CFormGroup variant="custom-checkbox" inline>
                                    <CInputCheckbox custom id={item.animalID} name={item.animalID} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
                                    <CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID} [ breed: {item.breed} ]</CLabel>
                                  </CFormGroup>
                                </tr>)
                            }
                            )}
                          </tbody>
                        </table>
                      </TableScrollbar>
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

export default TemporaryMovement
