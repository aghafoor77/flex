import React, { Component } from 'react'
import TableScrollbar from 'react-table-scrollbar';

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
  CInputCheckbox,
  CAlert

} from '@coreui/react'

import endpoints from "../config/Configuration";


class MoveBullForHeardForm extends Component {

  constructor() {
    super();
    this.styles = {
      border: '2px solid rgba(0, 0, 0, 0.05)',
    };
    this.state = {
      bulls: [],
      mb4hid: "",
      groupFemale: "",
      notes: "",
      entryDate: "",
      employeeID: "",
      location: "",
      animalID: "",
      exitDate: "",
      selectedBull:[],
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
    this.fetchBulls();

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
      case 'mb4hid': this.setState({ mb4hid: event.target.value }); break;
      case 'groupFemale': this.setState({ groupFemale: event.target.value }); break;
      case 'notes': this.setState({ notes: event.target.value }); break;
      case 'entryDate': this.setState({ entryDate: event.target.value }); break;
      case 'employeeID': this.setState({ employeeID: event.target.value }); break;
      case 'location': this.setState({ location: event.target.value }); break;
      case 'animalID': 
            this.setState({ animalID: event.target.value });
            const selBull = this.state.bulls.find(radO => radO.animalID.toString() === event.target.value)
            this.setState({selectedBull:selBull})
            break;
      case 'exitDate': this.setState({ exitDate: event.target.value }); break;
      default: return '';
    }
  }

  componentWillMount = () => {
    this.selectedCows = new Set();
  }

  handleCheckChange(event) {

    if (this.selectedCows.has(event.target.value)) {
      this.selectedCows.delete(event.target.value);
    } else {
      this.selectedCows.add(event.target.value);
    }
  }

  fetchBulls() {
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
              items.push("No Record Found ");
              if (data.length === 0) {
                this.setState({ bulls: items });
              } else {
                this.setState({ bulls: data });
                const selBull = data[0]
                this.setState({selectedBull:selBull, animalID:selBull.animalID})
              }

              return items;

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
        this.setAlert("Connection Error-", error.message + " (Fetch bulls data)")
        return false
      });

  }

  getJSON() {
    let females = "";
    this.selectedCows && this.selectedCows.map(item =>
      females = females + item + ";"
    );
    let jsondata = {
      groupFemale: females,
      notes: this.state.notes,
      entryDate: this.state.entryDate,
      location: this.state.location,
      animalID: this.state.animalID,
      exitDate: this.state.exitDate
    }
    console.log(jsondata)
    return jsondata;

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


  post() {
      let url = window.sessionStorage.getItem(endpoints.MB) + "/application/v1/movebullforherd";
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
        this.setAlert("Connection Error-", error.message + " (Move bull post data)")
        return false
      });
  }

  handleBackClick = (e) => {
    this.props.history.goBack();
    
  }

  render() {
    return (
      <>
        <CRow>
          <CCol xs="12" sm="12">
            {this.displayAlert(this.state.alert.title, this.state.alert.message)}
          </CCol>
          <CCol xs="12" sm="9">
            <CCard>
              <CCardHeader>
                <CRow>
                  <CCol xs="12">
                    Move Bull for Herd
								</CCol>
                 
                </CRow>
              </CCardHeader>
              <CCardBody>
                <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
                  <CRow>
                    <CCol xs="1">
                    </CCol>
                    <CCol xs="5">
                      <CFormGroup>
                        <CLabel htmlFor="animalID">Bull</CLabel>
                        <CSelect custom name="animalID" id="animalID" onChange={this.handleChange.bind(this)} value={this.state.animalID}>
                          {this.state.bulls && this.state.bulls.map(item =>
                            <option value={item.animalID}>{item.animalID}</option>
                          )}
                        </CSelect>
                      </CFormGroup>
                    </CCol>
                    <CCol xs="5">
                      <CFormGroup>
                        <CLabel htmlFor="entryDate">Entry Date</CLabel>
                        <CInput type="date" name="entryDate" id="entryDate" onChange={this.handleChange.bind(this)} value={this.state.entryDate} placeholder="date" />
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
                        <CLabel htmlFor="location">Address / Location</CLabel>
                        <CInput name="location" id="location" onChange={this.handleChange.bind(this)} value={this.state.location} placeholder="Enter address and location of bull for herd " />
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
                        <CLabel htmlFor="notes">Comments/Notes </CLabel>
                      </CFormGroup>
                    </CCol>
                    <CCol xs="5">
                      <CFormGroup>
                        <CLabel htmlFor="groupFemale">Select Female Animals for Heard</CLabel>
                      </CFormGroup>
                    </CCol>
                    <CCol xs="1">
                    </CCol>
                  </CRow>
                  <CRow>
                    <CCol xs="1">
                    </CCol>
                    <CCol xs="12" md="5">
                      <CTextarea
                        name="notes" id="notes" onChange={this.handleChange.bind(this)} value={this.state.notes}
                        rows="5"
                        placeholder="Content..."
                      />
                    </CCol>
                    <CCol xs="12" md="5" style={this.styles}>
                      <TableScrollbar rows={5} >
                        <table >
                          <tbody>
                            {this.state.bulls && this.state.bulls.map(item => {
                              return (
                                <tr >
                                  <CFormGroup variant="custom-checkbox" inline>
                                    <CInputCheckbox custom id={item.animalID} name={item.animalID} value={item.animalID} onChange={this.handleCheckChange.bind(this)} />
                                    <CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID} [ breed: {item.breed} ]</CLabel>
                                  </CFormGroup>
                                </tr>)
                            }
                            )}
                          </tbody>
                        </table>
                      </TableScrollbar>

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
                      <CButton block color="info" onClick={(e) => this.post(e)}>Submit</CButton>
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
            <CCard>
              <CCardHeader>
                Bull Detail
              <small> Detailed information about the selected bull</small>
              </CCardHeader>
              <CCardBody>
                <CFormGroup>
                  <CLabel htmlFor="company"><strong>Breed:</strong> {this.state.selectedBull.breed}</CLabel>
                </CFormGroup>
                <CFormGroup>
                  <CLabel htmlFor="company"><strong>Sex:</strong> {this.state.selectedBull.sex}</CLabel>
                </CFormGroup>
                <CFormGroup>
                  <CLabel htmlFor="company"><strong>Parent:</strong> {this.state.selectedBull.animalIDMother}</CLabel>
                </CFormGroup>
                <CFormGroup>
                  <CLabel htmlFor="company"><strong>Date of Birth:</strong> {this.state.selectedBull.dateOfBirth}</CLabel>
                </CFormGroup>
                <CFormGroup>
                  <CLabel htmlFor="company"><strong>Place of Birth:</strong> {this.state.selectedBull.birthPlace}</CLabel>
                </CFormGroup>
                <CFormGroup>
                  <CLabel htmlFor="company"><strong>Notes:</strong>  </CLabel>
                  <CTextarea
                        name="dnotes" id="dnotes" onChange={this.handleChange.bind(this)} value={this.state.selectedBull.notes}
                        rows="5"
                        placeholder="Content..."
                      />
                </CFormGroup>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </>
    )
  }
}

export default MoveBullForHeardForm 
