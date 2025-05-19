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

class ObservingAnimal extends Component {

  constructor(props) {
    super(props);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.state = {
      ardata:[],
      ghoid : "", 
      limping : "", 
      observer : "", 
      wound : "", 
      bcs : "", 
      notes : "", 
      swelling : "", 
      gheDate : "", 
      animalID : "", 
      wight : "",
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
        case 'ghoid': this.setState({ ghoid: event.target.value }); break;
        case 'limping': this.setState({ limping: event.target.value }); break;
        case 'observer': this.setState({ observer: event.target.value }); break;
        case 'wound': this.setState({ wound: event.target.value }); break;
        case 'bcs': this.setState({ bcs: event.target.value }); break;
        case 'notes': this.setState({ notes: event.target.value }); break;
        case 'swelling': this.setState({ swelling: event.target.value }); break;
        case 'gheDate': this.setState({ gheDate: event.target.value }); break;
        case 'animalID': this.setState({ animalID: event.target.value }); break;
        case 'wight': this.setState({ wight: event.target.value }); break;
        default: return '';
    }
  }


  getJSON(){
    let jsondata =  {
      ghoid : this.state.ghoid, 
      limping : this.state.limping, 
      observer : this.state.observer, 
      wound : this.state.wound, 
      bcs : this.state.bcs, 
      notes : this.state.notes, 
      swelling : this.state.swelling, 
      gheDate : this.state.gheDate, 
      animalID : this.state.animalID, 
      wight : this.state.wight
    }
    return jsondata; 

  }

  handleBackClick = (e) => {
    this.props.history.goBack();
  };

   submit = (e) => {
    console.log(this.getJSON())
    alert("Here are ")
    this.post();
  };



  post() {
		fetch(window.sessionStorage.getItem(endpoints.GHO)+"/application/v1/generalhealthobservation", {
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
								this.setState({ ardata: [{animalID:'not found'}] });
							} else {
                this.setState({ ardata: data,animalID: data[0].animalID });
                console.log(data[0].animalID);
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
                      <CSelect custom name="animalID" id="animalID"   onChange={this.handleChange.bind(this)} value={this.state.animalID}>
                        {this.state.ardata && this.state.ardata.map(item =>
														<option value={item.animalID}>{item.animalID}</option>
													)}
                      </CSelect>
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="observer">Observer </CLabel>
                      <CInput name="observer" id="observer" onChange={this.handleChange.bind(this)} value={this.state.observer}  placeholder="Enter examined by employe/advisor" />
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
                      <CLabel htmlFor="weight">Weight</CLabel>
                      <CInput name="weight" id="weight" onChange={this.handleChange.bind(this)} value={this.state.weight} placeholder="Animal weight in KGs" />
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
                      <CLabel htmlFor="limping">Sign of limping</CLabel>
                      
                      <CInput   id="limping" onChange={this.handleChange.bind(this)} value={this.state.limping} placeholder="sign of limping" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="bcs">BCS</CLabel>
                      <CInput   id="bcs" onChange={this.handleChange.bind(this)} value={this.state.bcs} placeholder="BCS attributed " />
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
                      
                      <CInput   id="swelling" onChange={this.handleChange.bind(this)} value={this.state.swelling} placeholder="Swelling if any" />
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
                    <CLabel htmlFor="decription-input">Visual Appearance</CLabel>
                    <small>  Extra information about the animal 's visual apperance</small>
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

export default ObservingAnimal
