import React, { Component } from 'react';
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
  CRow,
  CSpinner
} from '@coreui/react';

import CIcon from '@coreui/icons-react';

import Logo from '../../../views/icons/riselogo.png';
import endpoints from "../../config/Configuration";

class Login extends Component {
  constructor() {
    super();
    this.fetchServices();
    this.state = {
      username: "",
      password: "",
      loading: false, // State for managing spinner
      alert: {
        title: "Here ",
        message: "There",
        display: false
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
    return this.state.alert.display ? (
      <CCardHeader>
        <CAlert color="danger">
          <CRow>
            <CCol xs="10">
              {this.state.alert.title}: {this.state.alert.message}
            </CCol>
            <CCol xs="1">
              <CButton block onClick={(e) => this.hideAlertClick(e)}>X</CButton>
            </CCol>
          </CRow>
        </CAlert>
      </CCardHeader>
    ) : null;
  }

  displayError(res) {
    switch (res.status) {
      case 200:
        return true;
      case 204:
        this.setAlert("Unauthorized", "User not registered !");
        return false;
      case 401:
        this.setAlert("Unauthorized", "Invalid username or password !");
        return false;
      case 404:
        this.setAlert("Not found", "Resource unreachable !");
        return false;
      case 500:
        res.json()
          .then(data => {
            this.setAlert("Server error", data.message);
          })
          .catch(error => { this.setAlert("General error", error.message); });
        return false;
      default:
        this.setAlert("Unknown Error", res.statusText);
        return false;
    }
  }

  register() {
    this.props.history.push('/register');
  }

  login() {
    this.setState({ loading: true }); // Show spinner when login starts
    this.post();
  }

  fetchServices() {
    let url = endpoints.CHR + '/application/v1/centralhostregistration';
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
                  window.sessionStorage.setItem(service.name, endpoints.SRV + ":" + service.port);
                }
                window.sessionStorage.setItem("VDR", endpoints.SRV + ":9030");
                window.sessionStorage.setItem("TRS", endpoints.SRV + ":9031");
                window.sessionStorage.setItem("SHD", endpoints.SRV + ":9050");
              }
              return this.state.osdata;
            })
            .catch(error => {
              this.setAlert("General error", error.message);
              return false;
            });
        } else {
          return this.displayError(res);
        }
      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Central Registery is not running !)");
        return false;
      });
  }

  post() {
    if (window.sessionStorage.getItem(endpoints.AM) == null) {
      alert("Services down. Please refresh, if error persists then contact to system administrator !");
      this.props.history.push('/dashboard');
      this.setState({ loading: false }); // Hide spinner
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
        if (res.status === 204) {
          alert("Invalid username or password !");
          this.setState({ loading: false }); // Hide spinner
          return;
        } else if (res.ok) {
          res.json()
            .then(data => {
              window.sessionStorage.setItem("role", data.roles);
              window.sessionStorage.setItem("veid", data.veid);
              window.sessionStorage.setItem("resourceid", this.state.username);
              window.sessionStorage.setItem("vblock", "Basic " + Buffer.from(this.state.username + ':' + this.state.password).toString('base64'));
              window.sessionStorage.setItem("user", this.state.username);

              this.setState({ loading: false }); // Hide spinner after success
              this.props.history.push('/dashboard');
            });
        } else {
          this.setState({ loading: false }); // Hide spinner on error
          this.displayError(res);
        }
      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Login Attempt)");
        this.setState({ loading: false }); // Hide spinner on error
        return false;
      });
  }

  getJSON() {
    return {
      email: this.state.username,
      password: this.state.password
    };
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
        {this.state.loading && (
          <div style={{
            position: 'fixed',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            zIndex: 9999
          }}>
            <CSpinner color="info" style={{ width: '4rem', height: '4rem' }} />
          </div>
        )}
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
                      <CCol>
                        <h1>FLEX</h1>
						<small>Framework for Livestock Empowerment and Decentralized Secure Data eXchange</small>
                      </CCol>
                    </CRow>
                  </CCardHeader>
                  <CCardBody>
                    <CForm>
                      <h3>Login</h3>
                      <p className="text-muted">Login to traceability platform account.</p>
                      <CRow>{this.displayAlert()}</CRow>
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
                          <CButton color="info" className="px-4" active onClick={() => { this.login(); }}>Login</CButton>
                        </CCol>
                        <CCol xs="6" className="text-right">
                          <CButton color="link" className="px-0">Forgot password?</CButton>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol xs="6"></CCol>
                        <CCol xs="6" className="text-right">
                          <CButton color="info" className="mt-3" active tabIndex={-1} onClick={() => { this.register(); }}>Register Now!</CButton>
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
    );
  }
}

export default Login;
