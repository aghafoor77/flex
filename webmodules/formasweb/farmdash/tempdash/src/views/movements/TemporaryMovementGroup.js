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
  CLabel,
  CRow,
  CAlert,
  CDataTable

} from '@coreui/react'

import Circle from "../mcomp/Circle";
import endpoints from "../config/Configuration";

class TemporaryMovementGroup extends Component {

  constructor(props) {
    super(props);
    this.editableComponent = this.editableComponent.bind(this);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.state = {
      tmData: [],
      tmgData: [],
      updateEnable: true,
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
    this.fetchTemprorayMovement();
    this.fetchTemprorayMovementGroup();
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

  fetchTemprorayMovement() {
    let url = window.sessionStorage.getItem(endpoints.TM) + '/application/v1/temporarymovement/' + this.props.match.params.tmid
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
                this.setState({ tmData: data });
                if (data.inDate === null) {
                  this.setState({ updateEnable: false });
                }
                console.log(data);
              }
              return;
            })
            .catch(error => {
              this.setAlert("Error", error.message + " (Temp Movement data fetching)")
              return false
            });
        } else {
          return this.displayError(res)
        }

      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Temp Movement data fetching)")
        return false
      });
  }

  fetchTemprorayMovementGroup() {
    let url = window.sessionStorage.getItem(endpoints.TM) + '/application/v1/temporarymovementgroup/group/' + this.props.match.params.tmid
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
                this.setState({ tmgData: data });
                console.log(data);
              }
              return;
            })
            .catch(error => {
              this.setAlert("Error", error.message + " (Temp Movement Group data fetching)")
              return false
            });
        } else {
          return this.displayError(res)
        }

      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Temp Movement Group data fetching)")
        return false
      });
  }
  put = (e) => {
    let url = window.sessionStorage.getItem(endpoints.TM) + '/application/v1/temporarymovement/all/' + this.props.match.params.tmid
    fetch(url, {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      }
    })
      .then(res => {
        if (res.ok) {
          this.props.history.push('/movements/list')
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
        this.setAlert("Connection Error-", error.message + "(Temporary Movement)")
        return false
      });
  }



  editableComponent = (e) => {
    this.props.history.push('/movements/edit/' + this.props.match.params.tmid)
  };

  handleBackClick = (e) => {
    this.props.history.goBack();
  };

  submit = (e) => {
    this.put();
  };




  displayID(val) {
    if (val === null) {
      return (<Circle key='mykey' bgColor='#E94F37' width='10' height='10'></Circle>)
    } else {
      return (<Circle key='mykey' bgColor='#A1D363' width='10' height='10'></Circle>)
    }


  }
  render() {

    return (
      <CRow>
          <CCol xs="12" sm="12">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
          </CCol>
        <CCol xs="6">
          <CCard>
            <CCardHeader>

              <CRow><CCol sm="12">
                Temporary Movement <small>  Animals moved out from farm. </small> {this.displayAlert(this.state.alert.title, this.state.alert.message)}
              </CCol>
              </CRow>


            </CCardHeader>
            <CCardBody>
              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
                <table className="table table-striped table-hover">
                  <tbody>
                    <tr key="Type">
                      <td>Type</td>
                      <td><strong>{this.state.tmData.tmType}</strong></td>
                    </tr>
                    <tr key="Purpose">
                      <td>Purpose</td>
                      <td><strong>{this.state.tmData.purpose}</strong></td>
                    </tr>
                    <tr key="Employee">
                      <td>Employee</td>
                      <td><strong>{this.state.tmData.employeeID}</strong></td>
                    </tr>
                    <tr key="Outgoingdate">
                      <td>Outgoing date</td>
                      <td><strong>{this.state.tmData.outDate}</strong></td>
                    </tr>
                    <tr key="Incomingdate">
                      <td>Incoming date</td>
                      <td><strong>{this.state.tmData.inDate}</strong></td>
                    </tr>
                  </tbody>
                </table>
              </CForm>
              <CRow>
                <CCol xs="12">
                  <CFormGroup>
                    <CLabel htmlFor="notes">Special notes about movement</CLabel>
                    <CTextarea
                      name="notes"
                      id="notes"
                      disabled={false}
                      value={this.state.tmData.notes}
                      rows="5"
                      placeholder="Content..."
                    />
                  </CFormGroup>
                </CCol>
              </CRow>
            </CCardBody>
            <CCardFooter>
            </CCardFooter>
          </CCard>
        </CCol>
        <CCol xs="6">
          <CCard>
            <CCardHeader>
              <CRow>
                <CCol xs="7">
                  Temporary Movement Group
	                		<small className="text-muted"> All animals in the group. </small>
                </CCol>
                <CCol xs="5">
                  {this.displayAlert(this.state.alert.title, this.state.alert.message)}
                </CCol>

              </CRow>
            </CCardHeader>
            <CCardBody>
              <CDataTable
                items={this.state.tmgData}
                fields={[
                  { label: '', key: 'sign', _style: { width: '2%' } },
                  { label: 'No.', key: 'tmgid', _style: { width: '20%' } },
                  { label: 'Out', key: 'outDate', _style: { width: '15%' } },
                  { label: 'In', key: 'inDate', _style: { width: '15%' } },
                  { label: 'Employee', key: 'employeeID', _style: { width: '20%' } },
                  { label: 'Animal', key: 'animalID', _style: { width: '25%' } }

                ]}
                hover
                striped
                columnFilter
                itemsPerPage={5}
                pagination
                clickableRows
                onRowClick={(item) => {
                  this.props.history.push('/movements/in/' + item.tmid)
                }
                }

                scopedSlots={{

                  'sign':
                    (item) => (
                      <td>
                        {this.displayID(item.inDate)}
                      </td>
                    ),
                  'outDate':
                    (item) => (
                      <td>
                        {(item.outDate.indexOf(':') === -1 ? item.outDate : item.outDate.substring(0, 11))}
                      </td>
                    ),
                  'inDate':
                    (item) => (
                      <td>
                        {(item.inDate === null ? '------' : (item.inDate.indexOf(':') === -1 ? item.inDate : item.inDate.substring(0, 11)))
                        }
                      </td>
                    ),
                }}
              />


            </CCardBody>
            <CCardFooter>
              <CRow>
                <CCol xs="2" />
                <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" disabled={this.state.updateEnable} onClick={this.editableComponent}>Update</CButton>
                  </CFormGroup>
                </CCol>
                <CCol xs="4">
                  <CFormGroup>
                    <CButton block color="info" onClick={this.submit}>Group Came Back</CButton>
                  </CFormGroup>
                </CCol>
                <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" onClick={this.handleBackClick}>Back</CButton>
                  </CFormGroup>
                </CCol>
                <CCol xs="2" />
              </CRow>
            </CCardFooter>
          </CCard>
        </CCol>
      </CRow>

    );
  }
}

export default TemporaryMovementGroup
