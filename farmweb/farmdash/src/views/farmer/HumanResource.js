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

class HumanResource extends Component {

  constructor(props) {
    super(props);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.styles = {
      border: '2px solid rgba(0, 0, 0, 0.05)',
    };
    this.state = {
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
    		farmId : "111222333", 
    		email : "",
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
    this.fetchRoles();
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

  handleBackClick = (e) => {
    this.props.history.goBack();
  };

  submit = (e) => {
    console.log(this.getJSON())
    // alert("Here are ")
    this.post();
  };

  post() {
	var url = 'http://localhost:9020/application/v1/farmer';//window.sessionStorage.getItem(endpoints.TM) + "/application/v1/temporarymovement"
    console.log(this.getJSON());
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
          this.props.history.push('/farmer/detail')
        } else {
          this.displayError(res);
        }
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
              Add Resource [{this.state.farmId}]<small> Provide information about human resource.</small>
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
                    <CLabel htmlFor="resourceId">Resource Id</CLabel>
                    <CInput name="resourceId" id="resourceId" onChange={this.handleChange.bind(this)} value={this.state.resourceId} placeholder="Enter unique resource identification" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="role">Role</CLabel>
                      <CSelect custom name="role" id="role" onChange={this.handleChange.bind(this)} value={this.state.role}>
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
                    <CInput type="email" id="email" name="email" placeholder="Enter email" onChange={this.handleChange.bind(this)} autoComplete="email" value={this.state.email} />
                  </CFormGroup>
                </CCol>
                
                <CCol xs="3">
                <CFormGroup>  
                  <CLabel htmlFor="name">Name</CLabel>
                  <CInput id="name" name ="name" placeholder="Enter full name" onChange={this.handleChange.bind(this)} value={this.state.name} />
                </CFormGroup>
              </CCol>
              
                <CCol xs="3">
                  <CFormGroup>  
                    <CLabel htmlFor="contact">Contact #</CLabel>
                    <CInput id="contact" name ="contact" placeholder="Enter contact number with country code" onChange={this.handleChange.bind(this)} value={this.state.contact} />
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
                <CInput name="street" id="street" onChange={this.handleChange.bind(this)} value={this.state.street} placeholder="Enter street" />
                </CFormGroup>
              </CCol>
              <CCol xs="3">
              <CFormGroup>
              <CLabel htmlFor="postcode" >Postal code</CLabel>
              <CInput  name="postcode" id="postcode" onChange={this.handleChange.bind(this)} value={this.state.postcode} placeholder="Enter postal code" />
              
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
              <CInput name="county" id="county" onChange={this.handleChange.bind(this)} value={this.state.county} placeholder="Enter county/city" />
              </CFormGroup>
            </CCol>
            <CCol xs="3">
              <CFormGroup>
              <CLabel htmlFor="municipality" >Municipality</CLabel>
              <CInput name="municipality" id="municipality" onChange={this.handleChange.bind(this)} value={this.state.municipality} placeholder="Enter Municipality" />
              </CFormGroup>
            </CCol>
            <CCol xs="3">
            <CFormGroup>
            <CLabel htmlFor="country" >Country</CLabel>
            <CInput name="country" id="country" onChange={this.handleChange.bind(this)} value={this.state.country} placeholder="Enter country name" />
            
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

export default HumanResource
