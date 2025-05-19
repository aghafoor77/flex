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

class HumanResourceEdit extends Component {

  constructor(props) {
    super(props);

    this.handleBackClick = this.handleBackClick.bind(this);
    this.editableComponent = this.editableComponent.bind(this);
    this.state = {
      isDsiabled: true,
      roles: [],
      resourceId : "", 
  	country : "", 
  	role : "", 
  	street : "", 
  	contact : "", 
  	name : "", 
  	postcode : "", 
  	county : "", 
  	municipality : "", 
  	farmId : "", 
  	email : "",
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
    this.fetchRoles();
    this.fetchFarmer();
    
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
  fetchRoles() {
	    let url = "http://localhost:9020/application/v1/farmer/roles";//window.sessionStorage.getItem(endpoints.AR) + '/application/v1/registeranimal'
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
	                this.setState({ roles: items });
	              } else {
	                this.setState({ roles: data });	               
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
	        this.setAlert("Connection Error-", error.message + " (Fetch roles data)")
	        return false
	      });

	  }
  fetchFarmer(){
    let url = "http://localhost:9020/application/v1/farmer/zfTXhNiOb_0S";//+window.sessionStorage.getItem("resourceid");
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
                this.setState({
                	resourceId : data.resourceId, 
        			country : data.country, 
        			role : data.role, 
        			street : data.street, 
        			contact : data.contact, 
        			name : data.name, 
        			postcode : data.postcode, 
        			county : data.county, 
        			municipality : data.municipality, 
        			farmId : data.farmId, 
        			email : data.email,
                });
                console.log(data);
              }
              return;
            })
            .catch(error => {
              this.setAlert("Error", error.message + " (Human resource data fetching)")
              return false
            });
        } else {
          return this.displayError(res)
        }

      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Human resource data fetching)")
        return false
      });
  }



  handleBackClick = (e) => {
    this.props.history.goBack();
  };

  put = (e) => {
    let url = "http://localhost:9020/application/v1/farmer/zfTXhNiOb_0S";//+window.sessionStorage.getItem("resourceid");
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
          alert("Profile updated !");
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
        this.setAlert("Connection Error-", error.message + "(Human resource update)")
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
			resourceId : this.state.resourceId, 
			country : this.state.country, 
			role : this.state.role, 
			street : this.state.street, 
			contact : this.state.contact, 
			name : this.state.name, 
			postcode : this.state.postcode, 
			county : this.state.county, 
			municipality : this.state.municipality, 
			farmId : this.state.farmId, 
			email : this.state.email
		}
		return jsondata; 

	}


  handleChange(event) {
		switch (event.target.id) {
			  case 'resourceId': this.setState({ resourceId: event.target.value }); break;
			  case 'country': this.setState({ country: event.target.value }); break;
			  case 'role': this.setState({ role: event.target.value }); break;
			  case 'street': this.setState({ street: event.target.value }); break;
			  case 'contact': this.setState({ contact: event.target.value }); break;
			  case 'name': this.setState({ name: event.target.value }); break;
			  case 'postcode': this.setState({ postcode: event.target.value }); break;
			  case 'county': this.setState({ county: event.target.value }); break;
			  case 'municipality': this.setState({ municipality: event.target.value }); break;
			  case 'farmId': this.setState({ farmId: event.target.value }); break;
			  case 'email': this.setState({ email: event.target.value }); break;
			  default: return '';
		}

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
              Human Resource<small>  Update human resource data. </small>
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
                <CLabel htmlFor="resourceId">Resource Id</CLabel>
                <CInput name="resourceId" id="resourceId" disabled={true} onChange={this.handleChange.bind(this)} value={this.state.resourceId} placeholder="Enter unique resource identification" />
                </CFormGroup>
              </CCol>
              <CCol xs="5">
                <CFormGroup>
                  <CLabel htmlFor="role">Role</CLabel>
                  <CSelect custom name="role" id="role" disabled={true} onChange={this.handleChange.bind(this)} value={this.state.role}>
                      <option value='Donnotknow'>Not Assigned</option>
                        {this.state.roles&& this.state.roles.map(item =>
                        <option value={item}>{item}</option>
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
            <CCol xs="4">
              <CFormGroup>
                <CLabel htmlFor="email">Email</CLabel>
                <CInput type="email" id="email" name="email" placeholder="Enter email" disabled={true} onChange={this.handleChange.bind(this)} autoComplete="email" value={this.state.email} />
              </CFormGroup>
            </CCol>
            
            <CCol xs="3">
            <CFormGroup>  
              <CLabel htmlFor="name">Name</CLabel>
              <CInput id="name" name ="name" placeholder="Enter full name" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.name} />
            </CFormGroup>
          </CCol>
          
            <CCol xs="3">
              <CFormGroup>  
                <CLabel htmlFor="contact">Contact #</CLabel>
                <CInput id="contact" name ="contact" placeholder="Enter contact number with country code" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.contact} />
              </CFormGroup>
            </CCol>
            <CCol xs="1">
            </CCol>
          </CRow>
          <CRow>
          <CCol xs="1">
          </CCol>
          <CCol xs="7">
            <CFormGroup>
            <CLabel htmlFor="street" >Street</CLabel>
            <CInput name="street" id="street" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.street} placeholder="Enter street" />
            </CFormGroup>
          </CCol>
          <CCol xs="3">
          <CFormGroup>
          <CLabel htmlFor="postcode" >Postal code</CLabel>
          <CInput  name="postcode" id="postcode" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.postcode} placeholder="Enter postal code" />
          
          </CFormGroup>
        </CCol>
          
          
          
          <CCol xs="1">
          </CCol>
        </CRow>
        <CRow>
        <CCol xs="1">
        </CCol>
        <CCol xs="4">
          <CFormGroup>
          <CLabel htmlFor="county" >County/City</CLabel>
          <CInput name="county" id="county" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.county} placeholder="Enter county/city" />
          </CFormGroup>
        </CCol>
        <CCol xs="3">
          <CFormGroup>
          <CLabel htmlFor="municipality" >Municipality</CLabel>
          <CInput name="municipality" id="municipality" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.municipality} placeholder="Enter Municipality" />
          </CFormGroup>
        </CCol>
        <CCol xs="3">
        <CFormGroup>
        <CLabel htmlFor="country" >Country</CLabel>
        <CInput name="country" id="country" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.country} placeholder="Enter country name" />
        
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

export default HumanResourceEdit
