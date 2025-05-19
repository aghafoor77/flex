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

import ReactTooltip from "react-tooltip";

class GeneralAnimalHealthEdit extends Component {

  constructor(props) {
    super(props);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.editableComponent = this.editableComponent.bind(this);
    this.state = {
      isDsiabled: true,
      ardata:[],
      gaheid: "",
      observer: "",
      wound: "",
      notes: "",
      notation: "",
      temperature: "",
      infections: "",
      lameness: "",
      swelling: "",
      gheDate: "",
      animalID: "",
      weak: "",
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
    this.fetchGAHRecord();
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

  fetchGAHRecord() {
    let url = window.sessionStorage.getItem(endpoints.GHE) + '/application/v1/generalhealthexamination/' + this.props.match.params.gaheid
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
                this.setState({ gahdr: [] });
              } else {
                this.setState({
                  ardata: [],
                  gaheid: data.gaheid,
                  observer: data.observer,
                  wound: data.wound,
                  notes: data.notes,
                  notation: data.notation,
                  temperature: data.temperature,
                  infections: data.infections,
                  lameness: data.lameness,
                  swelling: data.swelling,
                  gheDate: data.gheDate,
                  animalID: data.animalID,
                  weak: data.weak
                });
                console.log(data);
                console.log(this.state.gahdr);
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

  handleBackClick = (e) => {
    this.props.history.goBack();
  };


  put = (e) => {
    let url = window.sessionStorage.getItem(endpoints.GHE) + '/application/v1/generalhealthexamination/' + this.props.match.params.gaheid
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
          this.props.history.push(`/animals/healthrecords`)
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
        this.setAlert("Connection Error-", error.message + " (Animal Registration)")
        return false
      });
  }

  purchased() {
    return 0;
  }


  editableComponent() {
    this.setState({ isDsiabled: !this.state.isDsiabled });
  }


  getJSON() {
    let jsondata = {
      gaheid: this.state.gaheid,
      observer: this.state.observer,
      wound: this.state.wound,
      notes: this.state.notes,
      notation: this.state.notation,
      temperature: this.state.temperature,
      infections: this.state.infections,
      lameness: this.state.lameness,
      swelling: this.state.swelling,
      gheDate: this.state.gheDate,
      animalID: this.state.animalID,
      weak: this.state.weak
    }
    return jsondata;

  }

  handleChange(event) {
    switch (event.target.id) {
      case 'gaheid': this.setState({ gaheid: event.target.value }); break;
      case 'observer': this.setState({ observer: event.target.value }); break;
      case 'wound': this.setState({ wound: event.target.value }); break;
      case 'notes': this.setState({ notes: event.target.value }); break;
      case 'notation': this.setState({ notation: event.target.value }); break;
      case 'temperature': this.setState({ temperature: event.target.value }); break;
      case 'infections': this.setState({ infections: event.target.value }); break;
      case 'lameness': this.setState({ lameness: event.target.value }); break;
      case 'swelling': this.setState({ swelling: event.target.value }); break;
      case 'gheDate': this.setState({ gheDate: event.target.value }); break;
      case 'animalID': this.setState({ animalID: event.target.value }); break;
      case 'weak': this.setState({ weak: event.target.value }); break;
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
                      <CLabel htmlFor="animalID">Animal to be examined</CLabel>
                      <small>  Select animal from folloiwng list. </small>
                      <CSelect custom name="animalID" id="animalID" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.animalID}>
                        {this.state.ardata && this.state.ardata.map(item =>
                          <option value={item.animalID}>{item.animalID}</option>
                        )}
                      </CSelect>
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="observer">Examined by </CLabel>
                      <CInput name="observer" id="observer" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.observer} placeholder="Enter examined by employe/advisor" />
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
                      <CLabel htmlFor="wound">Wound</CLabel>
                      <small>  Any woud on the animal skin. </small>
                      <CInput id="wound" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.wound} id="wound" placeholder="Wond description" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">

                    <CFormGroup>
                      <CLabel htmlFor="notation">Notation of deviation from normal</CLabel>
                      <CInput name="notation" id="notation" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.notation} placeholder="Notation of deviation from normal" />
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
                      <CLabel htmlFor="swelling">Swelling</CLabel>
                      <small>  Any woud on the body of animal. </small>
                      <CInput id="swelling" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.swelling} placeholder="Swelling description" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="lameness">Lameness</CLabel>
                      <CInput id="lameness" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.lameness} placeholder="Lameness description" />
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
                      <CLabel htmlFor="weak">Weak</CLabel>
                      <small>  Any weakness . </small>
                      <CInput id="weak" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.weak} placeholder="Weakness description" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="temperature">Body temperature</CLabel>
                      <CInput name="temperature" id="temperature" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.temperature} placeholder="Body temperature in C" />
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
                      <CLabel htmlFor="infections">Infections</CLabel>
                      <small>  If any Infections </small>
                      <CInput id="infections" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.infections} placeholder="Infections" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="gheDate">Date of Control</CLabel>
                      <CInput type="date" name="gheDate" id="gheDate" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.gheDate} placeholder="date" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol md="8">
                    <CLabel htmlFor="decription-input">Notes</CLabel>
                    <small>  Extra information about the animal control</small>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="12" md="10">
                    <CTextarea
                      name="notes-input"
                      id="notes"
                      disabled={this.state.isDsiabled}
                      onChange={this.handleChange.bind(this)}
                      value={this.state.notes}
                      rows="2"
                      placeholder="Content..."
                    />
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

export default GeneralAnimalHealthEdit
