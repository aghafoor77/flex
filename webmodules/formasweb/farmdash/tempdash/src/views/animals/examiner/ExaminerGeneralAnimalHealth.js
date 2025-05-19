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

import endpoints from "../../config/Configuration";

class GeneralAnimalHealth extends Component {

  constructor(props) {
    super(props);
  //  alert(this.props.match.params.aasid);
        
    this.handleBackClick = this.handleBackClick.bind(this);
    this.state = {
     
      ardata:[],
      observer : window.sessionStorage.getItem("user"), 
      wound : "", 
      notes : "", 
      notation : "", 
      temperature : "", 
      infections : "", 
      lameness : "", 
      swelling : "", 
      gheDate : "", 
      animalID : "", 
      weak : "",
      reportType: "",
      AASID: this.props.match.params.aasid,
      aasid: "",
      alert: {
				title: "Here ", message: "There", display: false
			}
    };
    this.fetchAnimalID();   
   
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
        case 'reportType': this.setState({ reportType: event.target.value }); break;        
        default: return '';
    }
  }

  getJSON(){
    let jsondata =  {
      gaheid : this.state.gaheid, 
      observer : this.state.observer, 
      wound : this.state.wound, 
      notes : this.state.notes, 
      notation : this.state.notation, 
      temperature : this.state.temperature, 
      infections : this.state.infections, 
      lameness : this.state.lameness, 
      swelling : this.state.swelling, 
      gheDate : this.state.gheDate, 
      animalID : this.state.animalID, 
      weak : this.state.weak,
      reportType : this.state.reportType,
      assignedStatusId : this.props.match.params.aasid
    }
    return jsondata; 
  }

  handleBackClick = (e) => {
	//alert(this.state.reportType );
    this.props.history.goBack();
  };

   submit = (e) => {
    //console.log(this.getJSON())
    this.post();
	//alert(this.state.aasid);
  };



  post() {
		console.log(this.getJSON());
		fetch(window.sessionStorage.getItem(endpoints.GHE)+"/application/v1/generalhealthexamination", {
			method: "POST",
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
					this.displayError(res);
				}
			})
			.then(json => {
				
			})
			.catch(error => {
				console.error(error)
				this.setAlert("Connection Error-", error.message + " (Animal post data)")
			});
  }
  
  fetchAnimalID() {
		let url =/* window.sessionStorage.getItem(endpoints.AR) */ 'http://localhost:9002' + '/application/v1/assignanimalstatus/'+this.props.match.params.aasid
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
								this.setState({ ardata: [{animalID:'not found'}] });
							} else {               
								this.setState({ animalID: data.animals, aasid: data.aasid });
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
                Animal Health Examination <small>  Animal heath status. [ {this.props.match.params.aasid} ] </small>
                <CLabel htmlFor="animalID"> [Animal to be examined : {this.state.animalID}] </CLabel>                  
              </CCol>
                <CCol xs="1" sm="1">
                  
                </CCol></CRow>
            </CCardHeader>

            <CCardBody>
              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
                <CRow>
                  <CCol xs="1"></CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="observer">Examined by </CLabel>
                      <CInput name="observer" id="observer" disabled='true' onChange={this.handleChange.bind(this)} value={this.state.observer}  placeholder="Enter examined by employe/advisor" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                  <CFormGroup>
                    <CLabel htmlFor="reportType">Select Report Type</CLabel>
                    <CSelect custom name="reportType" id="reportType" onChange={(e) => this.handleChange(e)} value={this.state.reportType}>
                    <option value="NOA">Select Report Type</option>
	                  <option value="Farm Internal">Farm Internal</option>
	                  <option value="Predeparture">Predeparture</option>
	                  <option value="Onarrival">Onarrival</option>
	                  <option value="Preslaughter">Preslaughter</option>
                    </CSelect>
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
                      <CInput id="wound" onChange={this.handleChange.bind(this)} value={this.state.wound}   id="wound" placeholder="Wond description" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">

                    <CFormGroup>
                      <CLabel htmlFor="notation">Notation of deviation from normal</CLabel>
                      <CInput name="notation" id="notation" onChange={this.handleChange.bind(this)} value={this.state.notation} placeholder="Notation of deviation from normal" />
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
                      <CInput   id="swelling" onChange={this.handleChange.bind(this)} value={this.state.swelling} placeholder="Swelling description" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="lameness">Lameness</CLabel>
                      <CInput   id="lameness" onChange={this.handleChange.bind(this)} value={this.state.lameness} placeholder="Lameness description" />
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
                      <CInput   id="weak" onChange={this.handleChange.bind(this)} value={this.state.weak} placeholder="Weakness description" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="temperature">Body temperature</CLabel>
                      <CInput  name="temperature" id="temperature" onChange={this.handleChange.bind(this)} value={this.state.temperature}placeholder="Body temperature in C" />
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
                      <CInput   id="infections" onChange={this.handleChange.bind(this)} value={this.state.infections} placeholder="Infections" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="gheDate">Date of Control</CLabel>
                      <CInput type="date" name="gheDate" id="gheDate" onChange={this.handleChange.bind(this)} value={this.state.gheDate} placeholder="date" />
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
                    <CButton block   color="info" onClick={this.submit}>Submit</CButton>
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

export default GeneralAnimalHealth
