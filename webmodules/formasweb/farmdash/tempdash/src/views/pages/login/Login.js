import React, { Component, Spinner } from 'react'
import {
  CCardHeader,
  CAlert,
  CButton,
  CCard,
  CCardBody,
  CCardGroup,
  CCol,
  CContainer,
  CForm,
  CInput,
  CInputGroup,
  CInputGroupPrepend,
  CInputGroupText,
  CRow
} from '@coreui/react'

import CIcon from '@coreui/icons-react'



import Logo from '../../../views/icons/riselogo.png';


import endpoints from "../../config/Configuration";

class Login extends Component {

  constructor() {
	//alert(window.location.href)
    super();
    this.fetchServices(); 
    <Spinner animation="border" role="status">
      <span className="sr-only">Loading...</span>
    </Spinner>
    this.state = {
      username: "",
      password: "",
      alert: {
        title: "Here ", message: "There", display: false
      }
    };    
  }

  setAlert(title, message) {
	    this.setState({ alert: { title: title, message: message, display: true } });
	  }
  

  hideAlertClick(e) {
    this.setState({ alert: { display: false } });
  }

  displayAlert() {
    return (this.state.alert.display === true ? (<CCardHeader><CAlert color="danger">
      <CRow>
        <CCol xs="10">
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
      case 204:    	  
          this.setAlert("Unauthorized", "User not registered !")
          return false;
      case 401:
        this.setAlert("Unauthorized", "Invalid username or password !")
        return false;
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
        this.setAlert("Unknown Error", res.statusText)
        return false;
    }
  }

  register() {
    this.props.history.push('/register');
  }

  login() {
    this.post();
  }
  
  fetchServices() {	  
		let url = endpoints.CHR + '/application/v1/centralhostregistration'
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
                this.setState({ osdata: [] });
              } else {

                for (let service of data) {
                  window.sessionStorage.setItem(service.name,endpoints.SRV+":"+service.port);
                  //window.sessionStorage.setItem(service.name,"http://192.168.10.127:"+service.port);
				  
                }
              }
              return this.state.osdata;
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
        this.setAlert("Connection Error-", error.message + " (Central Registery is not running !)")
        return false
      });

  }

  post() {

	if(window.sessionStorage.getItem(endpoints.AM)  == null){
		alert("Services down. Please refresh, if error persists then contact to system administrator !")
		 this.props.history.push('/dashboard');		
		return;
	}  
    let url = window.sessionStorage.getItem(endpoints.AM) + "/tapi/idms/v1/auth";
    
    fetch(url, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Authorization": "Basic " + Buffer.from(this.state.username + ':' + this.state.password).toString('base64')
      },
      body: JSON.stringify(this.getJSON())
    })
      .then(res => {
    	  if(res.status == 204){
    		  alert("Invalid username or password !")
    		  return;
    	  }
    	  else if (res.ok) {
        	res.json()
            .then(data => {
            	window.sessionStorage.setItem("role", data.roles)
          	    window.sessionStorage.setItem("resourceid", this.state.username);	
                window.sessionStorage.setItem("vblock", "Basic " + Buffer.from(this.state.username + ':' + this.state.password).toString('base64'));
                window.sessionStorage.setItem("user", this.state.username);
                
                this.props.history.push('/dashboard');
            })
        
        } else {
         
          this.displayError(res);
        }
      })
      .then(json => {

      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Login Attempt)")
        return false
      });
  }
  getJSON() {

    let jsondata = {
      email: this.state.username,
      password: this.state.password

    }
    return jsondata;
  }

  handleChange(event) {

    switch (event.target.id) {
      case 'username': this.setState({ username: event.target.value }); break;
      case 'password': this.setState({ password: event.target.value }); break;

      default: return '';
    }
  }

  render() {
    return (
      <div className="c-app c-default-layout flex-row align-items-center">
        <CContainer>
          <CRow className="justify-content-center">
            <CCol md="5">
              <CCardGroup>
                <CCard className="p-4" color="gray" borderColor="info">
                  <CCardHeader color="info" align="center" textColor="white">
                    <CRow>
                      <CCol xs="1">
                        <img src={Logo} width="50" alt="logo" height="50" />
                      </CCol>
                      <CCol >
                        <h1>Verifiable Traceability</h1>
                      </CCol>
                    </CRow>

                  </CCardHeader>
                  <CCardBody>
                    <CForm>
                      <h3>Login</h3>
                      <p className="text-muted">Login to traeability platform account.</p>
                      <CRow>{this.displayAlert(this.state.alert.title, this.state.alert.message)}</CRow>
                      <CInputGroup className="mb-3">
                        <CInputGroupPrepend>
                          <CInputGroupText>
                            <CIcon name="cil-user" />
                          </CInputGroupText>
                        </CInputGroupPrepend>
                        <CInput name="username" id="username" type="text" autoComplete="username" value={this.state.username} placeholder="Enter email" onChange={this.handleChange.bind(this)} />
                      </CInputGroup>
                      <CInputGroup className="mb-4">
                        <CInputGroupPrepend>
                          <CInputGroupText>
                            <CIcon name="cil-lock-locked" />
                          </CInputGroupText>
                        </CInputGroupPrepend>
                        <CInput name="password" id="password" type="password" value={this.state.password} placeholder="Enter password" autoComplete="current-password" onChange={this.handleChange.bind(this)} />
                      </CInputGroup>
                      <CRow>
                        <CCol xs="6">
                          <CButton color="info" className="px-4" active onClick={() => { this.login() }}>Login</CButton>
                        </CCol>
                        <CCol xs="6" className="text-right">
                          <CButton color="link" className="px-0">Forgot password?</CButton>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol xs="6">
                        </CCol>
                        <CCol xs="6" className="text-right">
                          <CButton color="info" className="mt-3" active tabIndex={-1} onClick={() => { this.register() }}>Register Now!</CButton>
                        </CCol>
                      </CRow>
                    </CForm>
                  </CCardBody>
                </CCard>
              </CCardGroup>
            </CCol>
          </CRow>
        </CContainer>
      </div>
    )
  }
}

export default Login
