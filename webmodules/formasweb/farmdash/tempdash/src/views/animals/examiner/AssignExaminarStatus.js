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

import Circle from "../../mcomp/Circle";
import endpoints from "../../config/Configuration";

class ExaminarAssignedStatus extends Component {

  constructor(props) {
    super(props);
    this.editableComponent = this.editableComponent.bind(this);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.state = {
      aaData: [],
      aasData: [],
      updateEnable: true,
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
    this.fetchAnimalAssignment();
    this.fetchAnimalAssignmentStatus();
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

  fetchAnimalAssignment() {
    let url = 'http://localhost:9002' + '/application/v1/assignanimal/' + this.props.match.params.aaid;//window.sessionStorage.getItem(endpoints.TM) + '/v1/assignanimal/' + this.props.match.params.aaid
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
                this.setState({ aaData: data });
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

  fetchAnimalAssignmentStatus() {
    //let url = /aaid///window.sessionStorage.getItem(endpoints.TM) + '/application/v1/temporarymovementgroup/group/' + this.props.match.params.aaid
	  let url = 'http://localhost:9002' + '/application/v1/assignanimalstatus/aaid/' + this.props.match.params.aaid;//window.sessionStorage.getItem(endpoints.TM) + '/v1/assignanimal/' + this.props.match.params.aaid
	   
    
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
                this.setState({ aasData: data });
                console.log(data);
              }
              return;
            })
            .catch(error => {
              this.setAlert("Error", error.message + " (assign animal status aaid)")
              return false
            });
        } else {
          return this.displayError(res)
        }

      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Assign animal status data fetching)")
        return false
      });
  }
 /* put = (e) => {
    let url = window.sessionStorage.getItem(endpoints.TM) + '/application/v1/temporarymovement/all/' + this.props.match.params.aaid
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
  }*/

  editableComponent = (e) => {
    this.props.history.push('/movements/edit/' + this.props.match.params.tmid)
  };

  handleBackClick = (e) => {
    this.props.history.goBack();
  };

  submit = (e) => {
	  this.props.history.push('/animals/health/'+e.target.id);
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
        <CCol xs="4">
          <CCard>
            <CCardHeader>

              <CRow><CCol sm="12">
                Examiner Assigned Status <small>  Assign examiner request status. </small> {this.displayAlert(this.state.alert.title, this.state.alert.message)}
              </CCol>
              </CRow>

                            
            </CCardHeader>
            <CCardBody>
              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
                <table className="table table-striped table-hover">
                  <tbody>
                    <tr key="aaid">
                      <td>No.</td>
                      <td><strong>{this.state.aaData.aaid}</strong></td>
                    </tr>
                    <tr key="examiner">
                      <td>Examiner</td>
                      <td><strong>{this.state.aaData.examiner}</strong></td>
                    </tr>
                    <tr key="assignedDate">
                      <td>Assigned Date</td>
                      <td><strong>{this.state.aaData.assignedDate}</strong></td>
                    </tr>
                    <tr key="action">
                      <td>Action</td>
                      <td><strong>{this.state.aaData.action}</strong></td>
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
                      value={this.state.aaData.notes}
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
        <CCol xs="8">
          <CCard>
            <CCardHeader>
              <CRow>
                <CCol xs="7">
                  Healthcare Report Status
	                		<small className="text-muted"> All assigned animals in the group. </small>
                </CCol>
                <CCol xs="5">
                  {this.displayAlert(this.state.alert.title, this.state.alert.message)}
                </CCol>
              </CRow>
            </CCardHeader>
            <CCardBody>
              <CDataTable
                items={this.state.aasData}
                fields={[
                  { label: '', key: 'sign', _style: { width: '2%' } },
                  { label: 'No.', key: 'aasid', _style: { width: '20%' } },
                  { label: 'submissionDate', key: 'submissionDate', _style: { width: '15%' } },
                  { label: 'Animal ID', key: 'animals', _style: { width: '15%' } },
                  { label: 'reportReference', key: 'reportReference', _style: { width: '10%' } },
                  { label: 'Report', key: 'hh', _style: { width: '10%' } }
                 
                ]}
                hover
                striped
                columnFilter
                itemsPerPage={5}
                pagination
                scopedSlots={{
                  'sign':
                    (item) => (
                      <td>
                        {this.displayID(item.reportReference)}
                      </td>
                    ),
                  'submissionDate':
                    (item) => (
                      <td>
                        {(item.submissionDate === null ? "-" : item.submissionDate)}
                      </td>
                    ),
                  'reportReference':
                    (item) => (
                      <td>
                      {(item.reportReference === null ? "-" : item.reportReference)}
                      </td>
                    ),
                    'hh':
                        (item) => (
                          <td>
                          {(item.reportReference === null ? <CButton block color="info" onClick={this.submit} id={item.aasid}>Submit</CButton> : "")}
                          </td>
                        ),
                }}              
              />
            </CCardBody>
            <CCardFooter>
              <CRow>
                <CCol xs="9" />
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

export default ExaminarAssignedStatus
