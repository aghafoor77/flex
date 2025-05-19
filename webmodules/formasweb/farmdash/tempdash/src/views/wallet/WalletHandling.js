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
  CInput,
  CLabel,
  CRow,
  CAlert,
  CTextarea
} from '@coreui/react'

import endpoints from "../config/Configuration";

import Circle from "../mcomp/Circle";

class WalletHandling extends Component {

  constructor(props) {
    super(props);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.state = {
      acctData: [],
      isdap: false,
      istrace: false,
      isRSAReg: "0",
      dapaddr: "",
      traceaddr: "",
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
    this.fetchAcctData();
    this.fetchDAP();
    this.fetchTRACE();
    this.isRSARegister();

  }
	getBadge = status => {
    
		switch (status) {
			 case "1": return (<Circle key='mykey' bgColor='#A1D363' width='15' height='15'></Circle>)
			case "0": return (<Circle key='mykey' bgColor='#E94F37' width='15' height='15'></Circle>)
			

    }

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

  fetchAcctData() {
    let url = window.sessionStorage.getItem(endpoints.AM) + '/tapi/idms/v1/account';
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

              if (data.length === 0) {
                this.setState({ acctData: [] });
              } else {
                this.setState({ acctData: data });
              }

            })
            .catch(error => {
              this.setAlert("General error", error.message + "( Account data fetching)");
              return false
            });
        } else {
          return this.displayError(res)
        }
      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Account data fetching)")
        return false
      });

  }
  fetchDAP() {
    let url = window.sessionStorage.getItem(endpoints.AM) + '/tapi/sc/v1/DAP';
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
              
              this.setState({ isdap: true, dapaddr: data.message });

            })
            .catch(error => {

              this.setState({ isdap: false });
              return false
            });
        } else {
          if (res.status === 404) {

            this.setState({ isdap: false });

          } else {
            res.json()
              .then(data => {


              })
              .catch(error => {

                return false
              });
          }
        }
      })
      .catch(error => {

        this.setState({ isdap: false });
        this.setAlert("Connection Error-", error.message + " (Smart contract (dap) data fetching)")
        return false
      });

  }
  //@Path("/rsa/status")
  fetchTRACE() {
    let url = window.sessionStorage.getItem(endpoints.AM) + '/tapi/sc/v1/TRACE';
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

              this.setState({ istrace: true, traceaddr: data.message });
            })
            .catch(error => {

              this.setState({ istrace: false });
              return false
            });
        } else {
          if (res.status === 404) {

            this.setState({ istrace: false });

          } else {
            res.json()
              .then(data => {


              })
              .catch(error => {

                return false
              });
          }
        }
      })
      .catch(error => {

        this.setState({ istrace: false });
        this.setAlert("Connection Error-", error.message + " (Smart contract (trace) data fetching)")
        return false
      });

  }


  depositeEther() {
    let url = window.sessionStorage.getItem(endpoints.AM) + '/tapi/idms/v1/deposit';
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

              if (data.length === 0) {
                this.setState({ acctData: [] });
              } else {
                this.setState({ acctData: data });
              }

            })
            .catch(error => {
              this.setAlert("General error", error.message + "( Deposting ether)");
              return false
            });
        } else {
          return this.displayError(res)
        }
      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Deposting ether)")
        return false
      });

  }

  isRSARegister = () => {
    fetch(window.sessionStorage.getItem(endpoints.AM) + '/tapi/idms/v1/rsa/status', {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Authorization": window.sessionStorage.getItem("vblock")
      }
    })
      .then(res => {
        if (res.ok) {
          
         this.setState({ isRSAReg: "1" });
        } else {
          this.setState({ isRSAReg: "0" });
        }
      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message)
        return false
      });
  }

  regRSA = () => {
    fetch(window.sessionStorage.getItem(endpoints.AM) + '/tapi/idms/v1/rsa', {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Authorization": window.sessionStorage.getItem("vblock")
      }
    })
      .then(res => {
        if (res.ok) {
         this.isRSARegister();
        } else {
          res.json()
            .then(data => {
              alert("[Registring RSA Key] " + data.message);

            })
        }
      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message)
        return false
      });
  }

  deployDAP = () => {
    fetch(window.sessionStorage.getItem(endpoints.AM) + '/tapi/sc/v1/deploy/DAP', {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Authorization": window.sessionStorage.getItem("vblock")
      }
    })
      .then(res => {
        if (res.ok) {
          this.fetchAcctData()
          this.fetchDAP();
          this.fetchTRACE();
        } else {
          res.json()
            .then(data => {
              alert("[Deploying DAP] " + data.message);

            })
        }
      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message)
        return false
      });
  }

  deployTrace = () => {
    fetch(window.sessionStorage.getItem(endpoints.AM) + '/tapi/sc/v1/deploy/TRACE', {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Authorization": window.sessionStorage.getItem("vblock")
      }
    })
      .then(res => {
        if (res.ok) {
          this.fetchAcctData()
          this.fetchDAP();
          this.fetchTRACE();
        } else {
          res.json()
            .then(data => {
              alert("[Deploying DAP] " + data.message);

            })
        }
      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message)
        return false
      });
  }

  getSCRow() {
    return (<CRow>
      <CCol xs="1" />
      <CCol xs="5"><CFormGroup>
          <CLabel htmlFor="rsaPublicKeyHex">Trace Smart Contract Address </CLabel>
          <CInput name="ether" id="ether" value={this.state.istrace ? this.state.traceaddr : "not deployed yet"} placeholder="" />
        </CFormGroup>
      </CCol>
      <CCol xs="5">
        
        <CFormGroup>
          <CLabel htmlFor="rsaPublicKeyHex">DAP Smart Contract Address </CLabel>
          <CInput name="ether" id="ether" value={this.state.isdap ? this.state.dapaddr : "not deployed yet"} placeholder="" />
        </CFormGroup>
      </CCol>

      <CCol xs="1" />
    </CRow>)
  }



  handleBackClick = (e) => {
    this.props.history.goBack();
  };

  submit = (e) => {
    this.depositeEther();
  };

  render() {

    return (
      <CRow>
        <CCol xs="8">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
        </CCol>
        <CCol xs="8">
          <CCard>
            <CCardHeader>
              <CRow>
                <CCol xs="8">
                  Veidblock Wallet  <small>  . </small>
                </CCol>
              </CRow>
            </CCardHeader>

            <CCardBody>
              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="6">
                    <CFormGroup>
                      <CLabel htmlFor="username">Email/Username </CLabel>
                      <CInput name="username" id="username" value={this.state.acctData.username} placeholder="" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="4">
                    <CFormGroup>
                      <CLabel htmlFor="ether">Total Ethers in Account </CLabel>
                      <CInput name="ether" id="ether" value={(this.state.acctData.ether == -1 ? "Ether fetching problem" : this.state.acctData.ether)} placeholder="" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="10">
                    <CFormGroup>
                      <CLabel htmlFor="baddress">Blockchain Address </CLabel>
                      <CInput name="baddress" id="baddress" value={this.state.acctData.address} placeholder="" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>

                <CRow>
                  <CCol xs="1" />

                  <CCol xs="10">
                    <CFormGroup>
                      <CLabel htmlFor="rsaPublicKeyPem">RSA Public Key (PEM) [Register RSA Key] </CLabel>
                      <CTextarea
                        name="rsaPublicKeyPem"
                        id="rsaPublicKeyPem"
                        value={this.state.acctData.rsaPublicKeyPem}
                        rows="3"
                        placeholder=""
                      />
                    </CFormGroup>
                  </CCol>

                  <CCol xs="1" />
                </CRow>
                <CRow>
                  <CCol xs="1" />

                  <CCol xs="10">
                    <CFormGroup>
                      {this.getBadge(this.state.isRSAReg)}  <CLabel htmlFor="rsaPublicKeyHex">RSA Public Key (HEX) </CLabel>
                      <CTextarea
                        name="rsaPublicKeyHex"
                        id="rsaPublicKeyHex"
                        value={this.state.acctData.rsaPublicKeyHex}
                        rows="3"
                        placeholder=""
                      />
                    </CFormGroup>
                  </CCol>

                  <CCol xs="1" />
                </CRow>
                {this.getSCRow()}
              </CForm>
            </CCardBody>
            <CCardFooter>
              <CRow>

                <CCol xs="3">
                  <CFormGroup>
                    <CButton block color="info" disabled={this.state.istrace} onClick={this.deployTrace}>Deploy Traceability</CButton>
                  </CFormGroup>
                </CCol>
                <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" disabled={this.state.isdap} onClick={this.deployDAP}>Deploy DAP</CButton>
                  </CFormGroup>
                </CCol>
                
                <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" onClick={this.submit}>Deposit Either</CButton>
                  </CFormGroup>
                </CCol>
                <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" onClick={this.handleBackClick}>Back</CButton>
                  </CFormGroup>
                </CCol>
                 <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" onClick={this.regRSA}>Register Key</CButton>
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

export default WalletHandling
