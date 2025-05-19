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

class AnimalTreatmentEdit extends Component {

  constructor(props) {
    super(props);

    this.handleBackClick = this.handleBackClick.bind(this);
    this.editableComponent = this.editableComponent.bind(this);
    this.state = {
      isDsiabled: true,
      ardata: [],
      animalHistory:[],
     dtid : "", 
      assignedDate : "", 
      reason : "", 
      drungs : "", 
      refnumber : "", 
      examinedBy : "", 
      animalID : "", 
      reftype : "",
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
    let url = window.sessionStorage.getItem(endpoints.DT) + '/application/v1/drugstreatments/' + this.props.match.params.dtid
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
                  dtid : data.dtid, 
                  assignedDate : data.assignedDate, 
                  reason : data.reason, 
                  drungs : data.drungs, 
                  refnumber : data.refnumber, 
                  examinedBy : data.examinedBy, 
                  animalID : data.animalID, 
                  reftype : data.reftype
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

fetchAnimalHistory(val) {
		let url = window.sessionStorage.getItem(endpoints.GHE) + '/application/v1/generalhealthexamination/animal/'+val
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

              console.log(data);              
							if (data.length === 0) {
								this.setState({ animalHistory: [{gaheid:'Not found'}],refnumber:'Not found' });
							} else {
                this.setState({ animalHistory: data, refnumber: data[0].gaheid });
							}
							
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
  
  put = (e) => {
    let url = window.sessionStorage.getItem(endpoints.DT) + '/application/v1/drugstreatments/' + this.props.match.params.dtid
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
          this.props.history.push('/animals/animaldtlist')
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

  getJSON(){
    let jsondata =  {
      dtid : this.state.dtid, 
      assignedDate : this.state.assignedDate, 
      reason : this.state.reason, 
      drungs : this.state.drungs, 
      refnumber : this.state.refnumber, 
      examinedBy : this.state.examinedBy, 
      animalID : this.state.animalID, 
      reftype : this.state.reftype
    }
    return jsondata; 

  }
  handleChange(event) {
    switch (event.target.id) {
        case 'dtid': this.setState({ dtid: event.target.value }); break;
        case 'assignedDate': this.setState({ assignedDate: event.target.value }); break;
        case 'reason': this.setState({ reason: event.target.value }); break;
        case 'drungs': this.setState({ drungs: event.target.value }); break;
        case 'refnumber': this.setState({ refnumber: event.target.value }); break;
        case 'examinedBy': this.setState({ examinedBy: event.target.value }); break;
        case 'animalID': this.setState({ animalID: event.target.value }); this.fetchAnimalHistory(event.target.value); break;
        case 'reftype': this.setState({ reftype: event.target.value }); break;
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
                      <CLabel htmlFor="examinedBy">Prescribed by </CLabel>
                      <CInput name="examinedBy" id="examinedBy" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.examinedBy}  placeholder="Enter examined by employe/advisor" />
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
                      <CLabel htmlFor="refnumber">History</CLabel>
                      <small>  Select examination or observation detail (if medicine is already prescribed) </small>
                      <CSelect custom name="refnumber" id="refnumber" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.refnumber}>
                        {this.state.animalHistory && this.state.animalHistory.map(item =>
														<option value={item.gaheid}>{item.gaheid}</option>
													)}
                      </CSelect>
                    </CFormGroup>
                  </CCol>
                <CCol xs="5">
                     <CFormGroup>
                      <CLabel htmlFor="assignedDate">Date of Control</CLabel>
                      <CInput type="date" name="assignedDate" id="assignedDate" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.assignedDate} placeholder="date" />
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
                      <CLabel htmlFor="drungs">Drugs and Quantity</CLabel>
                    <small>  Drugs and recommended quantity and time</small>
                       <CTextarea                    
                      name="drungs" 
                      id="drungs" 
                      disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} 
                      value={this.state.drungs}
                      rows="7"
                      placeholder="Content..."
                    />
                    </CFormGroup>
                  </CCol>
                <CCol xs="5">
                     <CFormGroup>
                      <CLabel htmlFor="reason">Reasons</CLabel>
                    <small>  What are the reasons for prescribing above medicine</small>
                      <CTextarea                    
                      name="reason" 
                      id="reason" 
                      disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} 
                      value={this.state.reason}
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

export default AnimalTreatmentEdit
