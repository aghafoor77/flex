import React, { Component } from 'react'
import {
  CCardHeader,
  CAlert,
  CButton,
  CCard,
  CCardBody,
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

class Register extends Component {

  constructor() {
    super();
    this.state = {
      usrchk: true,
      cusrchk: true,
      username: "",
      cusername:"",
      password: "",
      cpassword: "",
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

  register() {
    
    if(this.state.cusername ==="" ){
      this.setAlert("Email error", "Please specify email !") 
      return; 
    }
    if(this.state.username !== this.state.cusername){
      this.setAlert("Email error", "Email and confirm email are not same !") 
      return; 
    }
    if(this.state.password === "" ){
      this.setAlert("Password error", "Please specify password !") 
      return;
    }
    if(this.state.password !== this.state.cpassword){
      this.setAlert("Password error", "Password and confirm password are not same !") 
      return;
    }
    this.post();
    
  }

  login() {
    this.props.history.push('/login');

  }
  post() {
	  console.log(window.sessionStorage.getItem(endpoints.AM));
    let url = window.sessionStorage.getItem(endpoints.AM) + "/tapi/idms/v1/account";
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
          this.props.history.push('/login')
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
        this.setAlert("Connection Error-", error.message + " (User Registration)")
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
      case 'username': this.setState({ username: event.target.value, usrchk: this.checkEmail(event.target.value)});  break;
      case 'cusername': this.setState({cusername: event.target.value, cusrchk: this.checkEmail(event.target.value)});  break;
      case 'password': this.setState({ password: event.target.value }); break;
      case 'cpassword': this.setState({ cpassword: event.target.value }); break;

      default: return '';
    }
  }
  checkEmail(email) {
    
    if (!email) {
      return false;
    }

    if (typeof email !== "undefined") {
      let lastAtPos = email.lastIndexOf('@');
      let lastDotPos = email.lastIndexOf('.');

      if (!(lastAtPos < lastDotPos && lastAtPos > 0 && email.indexOf('@@') == -1 && lastDotPos > 2 && (email.length - lastDotPos) > 2)) {
        return false;
      }
    }
    return true;
  }



  render() {
    return (
      <div className="c-app c-default-layout flex-row align-items-center">
        <CContainer>
          <CRow className="justify-content-center">
            <CCol md="9" lg="7" xl="6">
             <CCard className="p-4" color="gray" borderColor="info">

                   <CCardHeader color="info" align="center" textColor="white">

                     <CRow>
                        <CCol xs="1">
                          <img src={Logo} width="50" height="50" />
                        </CCol>
                        <CCol >
						<h1>FLEX</h1>
						<small>Framework for Livestock Empowerment and Decentralized Secure Data eXchange</small>
                        </CCol>
                      </CRow>
                      
               
                  </CCardHeader>

                <CCardBody className="p-4">
                  <CForm>
                    <h1>Register</h1>
                    <p className="text-muted">Create your account</p>
                    <CRow>{this.displayAlert(this.state.alert.title, this.state.alert.message)}</CRow>
                    <CRow>
                      <CInputGroup className="mb-3">
                        {this.state.usrchk==false?<span class="border border-danger"></span>:""}
                        <CInputGroupPrepend>
                          <CInputGroupText>@</CInputGroupText>
                        </CInputGroupPrepend>
                        <CInput name="username" id="username" type="text" autoComplete="email" value={this.state.username} placeholder="Enter email" onChange={this.handleChange.bind(this)} />
                      </CInputGroup>
                      <CInputGroup className="mb-3">
                        {this.state.cusrchk==false?<span class="border border-danger"></span>:""}
                        <CInputGroupPrepend>
                          <CInputGroupText>@</CInputGroupText>
                        </CInputGroupPrepend>
                        <CInput type="text" name="cusername" id="cusername" autoComplete="email" value={this.state.cusername} onChange={this.handleChange.bind(this)} placeholder="Confirm email" />
                      </CInputGroup>
                      <CInputGroup className="mb-3">
                        <CInputGroupPrepend>
                          <CInputGroupText>
                            <CIcon name="cil-lock-locked" />
                          </CInputGroupText>
                        </CInputGroupPrepend>
                        <CInput name="password" id="password" type="password" value={this.state.password} placeholder="new password" autoComplete="current-password" onChange={this.handleChange.bind(this)} />
                      </CInputGroup>
                      <CInputGroup className="mb-4">
                        <CInputGroupPrepend>
                          <CInputGroupText>
                            <CIcon name="cil-lock-locked" />
                          </CInputGroupText>
                        </CInputGroupPrepend>
                        <CInput name="cpassword" id="cpassword" type="password" value={this.state.cpassword} placeholder="Confirm password" autoComplete="new-password" onChange={this.handleChange.bind(this)}/>
                      </CInputGroup>

                    </CRow><CRow>
                      <CCol xs="12" sm="6">
                        <CButton color="info" block active onClick={() => { this.register() }}><span>Create Account</span></CButton>
                      </CCol>
                      <CCol xs="12" sm="6">
                        <CButton color="link" block active onClick={() => { this.login() }}><span>Go to login</span></CButton>
                      </CCol>
                    </CRow>
                  </CForm>
                </CCardBody>

              </CCard>
            </CCol>
          </CRow>
        </CContainer>
      </div>
    )
  }
}

export default Register
