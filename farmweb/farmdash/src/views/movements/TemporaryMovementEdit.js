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

import ReactTooltip from "react-tooltip";

class TemporaryMovementEdit extends Component {

  constructor(props) {
    super(props);

    this.handleBackClick = this.handleBackClick.bind(this);
    this.editableComponent = this.editableComponent.bind(this);
    this.state = {
      isDsiabled: true,
      ardata: [],
      tmid: "",
      outDate: "",
      notes: "",
      purpose: "",
      inDate: "",
      tmType: "",
      employeeID: "",
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
    this.fetchTemprorayMovement();
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

  fetchTemprorayMovement() {
    let url = window.sessionStorage.getItem(endpoints.TM) + '/application/v1/temporarymovement/cur/' + this.props.match.params.tmid
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

              } else {
                data.animals.data.map(item => {
                  this.selectedCows.add(item)
                });
                this.setState({
                  tmid: data.tmid,
                  outDate: data.outDate,
                  notes: data.notes,
                  purpose: data.purpose,
                  inDate: data.inDate,
                  tmType: data.tmType,
                  employeeID: data.employeeID,
                });
                console.log(data);
              }
              return;
            })
            .catch(error => {
              this.setAlert("Error", error.message + " (Temp Movement data fetching)")
              return false
            });
        } else {
          return this.displayError(res)
        }

      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Temp Movement data fetching)")
        return false
      });
  }



  handleBackClick = (e) => {
    this.props.history.goBack();
  };

  put = (e) => {
    let url = window.sessionStorage.getItem(endpoints.TM) + '/application/v1/temporarymovement/' + this.props.match.params.tmid
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
          this.props.history.push('/movements/list')
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
        this.setAlert("Connection Error-", error.message + "(Temporary Movement)")
        return false
      });
  }

  purchased() {
    return 0;
  }
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


  editableComponent() {
    this.setState({ isDsiabled: !this.state.isDsiabled });
  }

  
  getJSON() {
    let animals = [];
    this.selectedCows && this.selectedCows.map(item =>
      animals[animals.length] = item
    );

    let jsondata = {
      tmid: this.state.tmid,
      outDate: this.state.outDate,
      notes: this.state.notes,
      purpose: this.state.purpose,
      inDate: this.state.inDate,
      tmType: this.state.tmType,
      employeeID: this.state.employeeID,
      animals: { data: animals }
    }
    return jsondata;

  }


    handleChange(event) {
    switch (event.target.id) {
      case 'tmid': this.setState({ tmid: event.target.value }); break;
      case 'outDate': this.setState({ outDate: event.target.value }); break;
      case 'notes': this.setState({ notes: event.target.value }); break;
      case 'purpose': this.setState({ purpose: event.target.value }); break;
      case 'inDate': this.setState({ inDate: event.target.value }); break;
      case 'tmType': this.setState({ tmType: event.target.value }); break;
      case 'employeeID': this.setState({ employeeID: event.target.value }); break;
      default: return '';
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
                this.setState({ ardata: [{ animalID: 'not found' }] });
              } else {
                this.setState({ ardata: data });
                this.setState({ animalID: data[0].animalID });
                this.fetchAnimalHistory(data[0].animalID)
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
                Animal Health Examination <small>  Animal heath status. </small>
              </CCol>
                <CCol xs="1" sm="1">
                  <CFormGroup>
                    <CButton data-tip data-for="registerTip" block color="info" onClick={(e) => this.editableComponent()}>Edit</CButton>
                    <ReactTooltip id="registerTip" place="top" effect="solid">{this.state.isDsiabled ? 'Press to edit form' : 'Press to disable form'}  </ReactTooltip>
                  </CFormGroup>
                </CCol></CRow>
            </CCardHeader>
            <CCardBody>
              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="purpose">Purpose </CLabel>
                      <CInput name="purpose" id="purpose" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.purpose} placeholder="Why animals are moved? " />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="3">
                    <CFormGroup>
                      <CLabel htmlFor="tmType">Type of Movement</CLabel>
                      <CSelect custom name="tmType" id="tmType" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.tmType}>
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
                      <CInput type="date" name="outDate" id="outDate" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.outDate} placeholder="date" />
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
                        disabled={this.state.isDsiabled}
                        onChange={this.handleChange.bind(this)}
                        value={this.state.notes}
                        rows="15"
                        placeholder="Content..."
                      />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CLabel htmlFor="drungs">Select animals for movements </CLabel>
                    <CFormGroup style={this.styles}>
                      <TableScrollbar rows={14} >
                        <table >
                          <tbody>
                            {this.state.ardata && this.state.ardata.map(item => {
                              if (this.selectedCows.has(item.animalID)) {
                                return (
                                  <tr >
                                    <CFormGroup variant="custom-checkbox" inline>
                                      <CInputCheckbox custom id={item.animalID} name={item.animalID} defaultChecked={true} disabled={this.state.isDsiabled} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
                                      <CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
                                    </CFormGroup>


                                  </tr>)
                              } else {
                                return (
                                  <tr >
                                    <CFormGroup variant="custom-checkbox" inline>
                                      <CInputCheckbox custom id={item.animalID} name={item.animalID} disabled={this.state.isDsiabled} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
                                      <CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
                                    </CFormGroup>


                                  </tr>)
                              }
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
                    <CButton block disabled={this.state.isDsiabled} onClick={(e) => this.put(e)} color="info">Save</CButton>
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

export default TemporaryMovementEdit
