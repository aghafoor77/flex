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

class AssignUserRole extends Component {

  constructor(props) {
    super(props);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.editableComponent = this.editableComponent.bind(this);
    this.state = {
      isDsiabled: true,
      rdata :[],
      veid: "",
      email: "",
      roles: "",
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
    this.fetchRoles();
    this.fetchRegisteredUsers();

  }
	getJSON() {
	    let jsondata = {
	    		veid: this.state.veid,
	    		email: this.state.receivedFarmID,
	    		roles: this.state.roles
	    }
	    return jsondata;

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

  
  handleBackClick = (e) => {
    this.props.history.goBack();
  };


  put = (e) => {
    let url = window.sessionStorage.getItem(endpoints.AM) + '/tapi/idms/v1/account/assignrole'
    fetch(url, {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Authorization": window.sessionStorage.getItem("vblock")
      },
      body: JSON.stringify(this.getJSON())
    })
      .then(res => {
        if (res.ok) {
          this.props.history.push(`/humanresource/userlist`)
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
        this.setAlert("Connection Error-", error.message + " (Assign Role)")
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
      veid: this.state.veid,
      email: this.state.email,
      roles: this.state.newroles
    }
    return jsondata;

  }

  handleChange(event) {
    switch (event.target.id) {
      case 'veid': this.setState({ veid: event.target.value }); break;
      case 'email': this.setState({ email: event.target.value }); break;
      case 'roles': this.setState({ roles: event.target.value }); break;
      case 'newroles': this.setState({ newroles: event.target.value }); break;
      
      default: return '';
    }
  }
  fetchRegisteredUsers() {
    let url = window.sessionStorage.getItem(endpoints.AM) + '/tapi/idms/v1/account/users/' + this.props.match.params.veid
    fetch(url, {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Authorization": window.sessionStorage.getItem("vblock")
      }
    })
      .then(res => {
        if (res.status === 200) {
          res.json()
            .then(data => {
            	 this.setState({ veid: data.veid}); 
            	 this.setState({ email: data.email});
            	 this.setState({ roles: data.roles});
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
        this.setAlert("Connection Error-", error.message + " (Fetch user)")
        return false
      });
  }
  
  fetchRoles() {
	    let url = window.sessionStorage.getItem(endpoints.AM) + '/tapi/idms/v1/account/listroles'
	    fetch(url, {
	      method: "GET",
	      headers: {
	        Accept: "application/json",
	        "Content-Type": "application/json",
	        "Authorization": window.sessionStorage.getItem("vblock")
	      }
	    })
	      .then(res => {
	        if (res.status === 200) {
	          res.json()
	            .then(data => {
	            	
	            	this.setState({ newroles: data[0]});
	            	this.setState({ rdata: data});
	                return this.state.rdata;
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
	        this.setAlert("Connection Error-", error.message + " (Fetch user)")
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
                User Role Assignment <small> User Role Assignment. </small>
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
                      <CLabel htmlFor="veid">VEID</CLabel>
                      <CInput name="veid" id="veid" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.veid} placeholder="Enter veid" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                  <CFormGroup>
                    <CLabel htmlFor="email">Email</CLabel>
                    <small>  Email address of user </small>
                    <CInput id="email" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.email} id="email" placeholder="email" />
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
                    <CLabel htmlFor="roles">Current Role</CLabel>
                    <CInput name="roles" id="roles" disabled={this.state.isDsiabled} onChange={this.handleChange.bind(this)} value={this.state.roles} placeholder="Roles" />
                  </CFormGroup>
                </CCol>
                  
                  <CCol xs="5">
                  <CFormGroup>
                    <CLabel htmlFor="newroles">User Role Assignment</CLabel>
                    <small>  Select role to assign new one. </small>
                    <CSelect custom name="newroles" id="newroles" onChange={this.handleChange.bind(this)} value={this.state.newroles}>
                      {this.state.rdata && this.state.rdata.map(item =>
                        <option value={item}>{item}</option>
                      )}
                    </CSelect>
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
                    <CButton block onClick={(e) => this.put(e)} color="info">Save</CButton>
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

export default AssignUserRole
