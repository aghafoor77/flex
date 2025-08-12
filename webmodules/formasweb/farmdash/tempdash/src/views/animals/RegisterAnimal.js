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

class RegisterAnimal extends Component {



  constructor() {
    super();

    //this.motherDataList = ["Mother ID 1", "Mother ID 2", "Mother ID 3"];
    //this.pregReports = ["pregExamRep 1", "pregExamRep 2", "pregExamRep 3"];

    this.state = {
      name: "React",
      isnewlyborn: false,
      ispurchased: false,
      mothers:[],
      pregExamRep : [],

      animalID : "", 
      receivedFarmID : "", 
      animalIDMother : "", 
      notes : "", 
      pregnancyExamination : "", 
      sex : "Cow", 
      weight : 0, 
      dateOfBirth : "", 
      breed : "", 
      birthPlace : "", 
      employerID : "", 
      unit : "kg", 
      receivedFarmName : "", 
      previousAnimalID : "", 
      aboutAnimal : "newlyborn",
      status: 'Active', 
      others : "",

      alert: {
				title: "Here ", message: "There", display: false
			}

    };


    this.hideComponent = this.hideComponent.bind(this);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.createConceivedList();
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
  
  getJSON() {
        if(this.state.ispurchased === false) {
      this.setState({
        receivedFarmID: null,
        breed: null,
        receivedFarmName: null,
        previousAnimalID: null
      })
    }

    let jsondata = {
      animalID: this.state.animalID,
      receivedFarmID: this.state.receivedFarmID,
      animalIDMother: this.state.animalIDMother,
      notes: this.state.notes,
      pregnancyExamination: this.state.pregnancyExamination,
      sex: this.state.sex,
      weight: this.state.weight,
      dateOfBirth: this.state.dateOfBirth,
      breed: this.state.breed,
      birthPlace: this.state.birthPlace,
      employerID: this.state.employerID,
      unit: this.state.unit,
      receivedFarmName: this.state.receivedFarmName,
      previousAnimalID: this.state.previousAnimalID,
      aboutAnimal: this.state.aboutAnimal,
      others: this.state.others,
      status: this.state.status,
    }
    return jsondata;

  }

  // /v1/animalexamination/animal/{animalID}/status/{status}
  createConceivedListByAnimal(animalID) {
    
    let url = window.sessionStorage.getItem(endpoints.AE)+'/application/v1/animalexamination/animal/'+animalID+'/status/Conceived'
    
    fetch(url)
      .then(res => {
        if (res.status === 200) {
          res.json()
            .then(data => {
              let items = [];
              items.push("[{animalID:No Record Found}]");
              if(data.length === 0){
              this.setState({pregExamRep:items});
            } else{
              this.setState({pregExamRep:data, pregnancyExamination:data[0].aeid});
            }
              
              return items;

            })
            .catch(error => {
               this.setAlert("Error", error.message + " (Bull data fetching)")
              return false
            });
        } else {
          return this.displayError(res)
        }

      })
      .catch(error => {
              this.setAlert("General Error", error.message + " (Bull data fetching)")
              return false
            });

  }

  createConceivedList() {
    let url = window.sessionStorage.getItem(endpoints.AE)+'/application/v1/animalexamination/status/Conceived'
    fetch(url)
      .then(res => {
        if (res.status === 200) {
          res.json()
            .then(data => {
              let items = [];
              items.push("[{animalID:No Record Found}]");
              if(data.length === 0){
              this.setState({mothers:items});
            } else{
              this.setState({mothers:data});
              this.selectMotherDataExp (data[0].animalID)
            }
              
              return items;

            })
            .catch(error => {
              this.setAlert("Error", error.message + " (Animal data fetching)")
              return false
            });
        } else {
          return this.displayError(res)
        }

      })
      .catch(error => {
               this.setAlert("General Error", error.message + " (Animal data fetching)")
              return false
            });

  }

  post() {
	  console.log(this.getJSON())
      let url = window.sessionStorage.getItem(endpoints.AR) + "/application/v1/registeranimal";
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
          this.props.history.push('/animal/reglist')
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

  handleBackClick = (e) => {
   this.props.history.goBack();

  }

  selectMotherDataExp (val) {
    this.createConceivedListByAnimal(val)
    this.setState({ animalIDMother: val });

  }

 
  selectMotherData = (e) => {
    if(e.target.value === 'NOA'){
      this.setState({pregExamRep:[{aeid:'Select Mother ID'}], pregnancyExamination:'', animalIDMother:'NOA'});
      return;
    }
    this.createConceivedListByAnimal(e.target.value)
    this.setState({ animalIDMother: e.target.value });

  }

  handleMCombo() {
    return <CSelect custom name="animalIDMother" id="animalIDMother" onChange={(e) => this.selectMotherData(e)} value={this.state.animalIDMother}>
      <option value="NOA">Select Parent ID</option>
      {this.state.mothers && this.state.mothers.map(item =>
                    <option value={item.animalID}>{item.animalID}</option>
                )}
      this.state.animalIDMother= "Select Parent ID";
    </CSelect>
  }

  handleMotherRelatedInfo() {
    return <CInput name="animalIDMother" id="animalIDMother" onChange={this.handleChange.bind(this)} value={this.state.animalIDMother} placeholder="Enter parent animal ID" />
  }

  hideComponent(e) {
    switch (e.target.value) {
      case "purchased":
        this.setState({aboutAnimal:'purchased', isnewlyborn: true, ispurchased: false, pregExamRep:[{aeid:'Select Mother ID'}], pregnancyExamination:'', animalIDMother:'NOA'});
        break;
      case "newlyborn":
        this.setState({aboutAnimal:'newlyborn', isnewlyborn: false, ispurchased: true });
        break;
      default:
        console.log('default');
    }
  }

  handleChange(event) {
    switch (event.target.id) {
      case 'animalID': this.setState({ animalID: event.target.value }); break;
      case 'receivedFarmID': this.setState({ receivedFarmID: event.target.value }); break;
      case 'animalIDMother': this.setState({ animalIDMother: event.target.value }); break;
      case 'notes': this.setState({ notes: event.target.value }); break;
      case 'pregnancyExamination': this.setState({ pregnancyExamination: event.target.value }); break;
      case 'sex': this.setState({ sex: event.target.value }); break;
      case 'weight': this.setState({ weight: event.target.value }); break;
      case 'dateOfBirth': this.setState({ dateOfBirth: event.target.value }); break;
      case 'breed': this.setState({ breed: event.target.value }); break;
      case 'birthPlace': this.setState({ birthPlace: event.target.value }); break;
      case 'employerID': this.setState({ employerID: event.target.value }); break;
      case 'unit': this.setState({ unit: event.target.value }); break;
      case 'previousAnimalID': this.setState({ previousAnimalID: event.target.value }); break;
      //case 'aboutAnimal': this.setState({ aboutAnimal: event.target.value }); break;
      case 'others': this.setState({ others: event.target.value }); break;
      default: return '';
    }
  }


  render() {
    const { isnewlyborn, ispurchased } = this.state;
    return (
		
      <CRow>
        <CCol xs="12" sm="12">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
          </CCol>
        <CCol xs="12" sm="9">
          <CCard>
            <CCardHeader>
              Register Animal
              <small>  Register a animal in the local farm. </small>
            </CCardHeader>
            <CCardBody>
              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
			    <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="3">
                    <CFormGroup>
                      <CLabel htmlFor="aboutAnimal">About Animal</CLabel>
                      <CSelect custom name="aboutAnimal" id="aboutAnimal" value={this.state.aboutAnimal} onChange={(e) => this.hideComponent(e)}>
                        <option value="newlyborn">Newly Born</option>
                        <option value="purchased">Purchased/Gifted</option>
                      </CSelect>
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">

                    <CFormGroup>
                      <CLabel htmlFor="animalID">Animal ID</CLabel>
                      <small>  Automatically set by the system </small>
                      <CInput name="animalID" id="animalID" disabled="true" onChange={this.handleChange.bind(this)} value={this.state.animalID} placeholder="System generated animal ID" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="2">
                    <CFormGroup>
                      <CLabel htmlFor="sex">Sex</CLabel>
                      <CSelect custom name="sex" id="sex" onChange={this.handleChange.bind(this)} value={this.state.sex}>
                        <option value="Cow">Cow</option>
                        <option value="Bull">Bull</option>
                        <option value="Other">Other</option>
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
                      <CLabel htmlFor="animalIDMother">Animal ID of Mother</CLabel>
                      <small>  Mother of the animal </small>
                      {this.state.isnewlyborn ? this.handleMotherRelatedInfo() : this.handleMCombo()}
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="pregnancyExamination">Pregnancy Examination Report Number</CLabel>
                      <small>  It will help to identiy the parents </small>
                      <CSelect custom name="pregnancyExamination" id="pregnancyExamination" onChange={this.handleChange.bind(this)} value={this.state.pregnancyExamination} disabled={this.state.isnewlyborn}>
                        {/* {this.createPregExaminationList()} */}
                        {this.state.pregExamRep && this.state.pregExamRep.map(item =>
                    <option value={item.aeid}>{item.aeid}</option>
                )}
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
                      <CLabel htmlFor="dateOfBirth">Date of Birth</CLabel>
                      <CInput type="date" name="dateOfBirth" id="dateOfBirth" onChange={this.handleChange.bind(this)} value={this.state.dateOfBirth} placeholder="date" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="birthPlace">Birth Place</CLabel>
                      <small>  name of farm or location </small>
                      <CInput name="birthPlace" id="birthPlace" onChange={this.handleChange.bind(this)} value={this.state.birthPlace} placeholder="Enter birth place" />
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
                      <CLabel htmlFor="employerID">Registrar ID (Employee at farm)</CLabel>
                      <small> if you are the owener keep it bank  </small>
                      <CInput name="employerID" id="employerID" onChange={this.handleChange.bind(this)} value={this.state.employerID} placeholder="Enter employee ID" />
                    </CFormGroup>
                  </CCol>

                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="weight">Weight at the time of birth (KGs) </CLabel>
                      <small> if not available then current weight of animal </small>
                      <CInput name="weight" id="weight" onChange={this.handleChange.bind(this)} value={this.state.weight} placeholder="Enter weight (KGs)" />
                    </CFormGroup>
                  </CCol>

                  <CCol xs="1">
                  </CCol>
                </CRow>
                {isnewlyborn ? (this.purchased()) : null}
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol md="8">
                    <CLabel htmlFor="notes">Notes</CLabel>
                    <small>  Extra information about the animal </small>
                  </CCol>

                  <CCol xs="1">
                  </CCol>
                </CRow>

                <CRow>
                  <CCol xs="1">
                  </CCol>

                  <CCol xs="12" md="10">
                    <CTextarea
                      name="notes" id="notes" onChange={this.handleChange.bind(this)} value={this.state.notes}
                      rows="5"
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
                    <CButton block color="info" onClick={(e) => this.post()}>Register</CButton>
                  </CFormGroup>
                </CCol>
                <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" onClick={(e) => this.handleBackClick(e)}>Back</CButton>
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
  purchased() {
    return <><CRow>
      <CCol xs="1">
      </CCol>
      <CCol xs="5">
          <CFormGroup>
            <CLabel htmlFor="previousAnimalID">Previous Animal ID </CLabel>
            <small> Specify the ID of anmial if it has </small>
            <CInput name="previousAnimalID" id="previousAnimalID" onChange={this.handleChange.bind(this)} value={this.state.previousAnimalID} placeholder="Enter previous animal ID " />
          </CFormGroup>
        </CCol>
      <CCol xs="5">
        <CFormGroup>
          <CLabel htmlFor="breed">Breed </CLabel>
          <small>  Breed of animal </small>
          <CInput name="breed" id="breed" onChange={this.handleChange.bind(this)} value={this.state.breed} placeholder="Enter breed" />
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
            <CLabel htmlFor="receivedFarmName">Received Farm Name </CLabel>
            <small> Specify the name of farm or organization </small>
            <CInput name="receivedFarmName" id="receivedFarmName" onChange={this.handleChange.bind(this)} value={this.state.receivedFarmName} placeholder="Enter name of organization" />
          </CFormGroup>
        </CCol>
        <CCol xs="5">
          <CFormGroup>
            <CLabel htmlFor="receivedFarmID">Received Farm ID </CLabel>
            <small> Specify the ID of farm or organization </small>
            <CInput name="receivedFarmID" id="receivedFarmID" onChange={this.handleChange.bind(this)} value={this.state.receivedFarmID} placeholder="Enter farm or organization ID" />
          </CFormGroup>
        </CCol>
        <CCol xs="1">
        </CCol>
      </CRow>
    </>
  }
}

export default RegisterAnimal
