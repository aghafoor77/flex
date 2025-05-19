import React, { Component } from 'react'

import {
  CButton,
  CCard,
  CCardBody,
  CCardFooter,
  CCardHeader,
  CCol,
  CInputCheckbox,
  CFormGroup,
  CLabel,
  CRow,
  CAlert,
  CTextarea

} from '@coreui/react'

import TableScrollbar from 'react-table-scrollbar';

import endpoints from "../config/Configuration";

class AssignFeedDetail extends Component {

  constructor(props) {
    super(props);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.styles = {
      border: '2px solid rgba(0, 0, 0, 0.05)',
    };
    this.state = {
      ardata: [],
      fpdata: [],
      cacheck1:true,
      alert: {
        title: "Here ", message: "There", display: false
      }
    };

    this.fetchRegisteredAnimals();
    this.fetchAssignedFeedData();
    this.fetchFPData();
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
  ///
  post() {
    let animals = [];
    this.selectedCows && this.selectedCows.map(item =>
      animals[animals.length] = item
    );
    let url = window.sessionStorage.getItem(endpoints.FP) + "/application/v1/assignfeed/fp/" + this.props.match.params.fpid;
    fetch(url, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ data: animals })
    })
      .then(res => {
        if (res.ok) {
          this.props.history.push('/animals/animalfeeding')
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
        this.setAlert("Connection Error-", error.message + " (Assign feed  post data)")
        return false
      });
  }

  fetchAssignedFeedData() {
    let url = window.sessionStorage.getItem(endpoints.FP) + '/application/v1/assignfeed/fp1/' + this.props.match.params.fpid
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
              data.data.map(item => {
                this.selectedCows.add(item)
              });
              return;
            })
            .catch(error => {
              this.setAlert("Error", error.message + " (Feed Pattern animal data fetching)")
              return false
            });
        } else {
          return this.displayError(res)
        }
      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Feed Pattern animal data fetching)")
        return false
      });

  }


  fetchFPData() {
    let url = window.sessionStorage.getItem(endpoints.FP) + '/application/v1/feedpattern/' + this.props.match.params.fpid
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
                this.setState({ fpdata: [] });
              } else {
                this.setState({
                  fpdata: data
                });
              }
              return;
            })
            .catch(error => {
              this.setAlert("Error", error.message + " (Feed Pattern data fetching)")
              return false
            });
        } else {
          return this.displayError(res)
        }

      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Feed Pattern data fetching)")
        return false
      });

  }

  handleBackClick = (e) => {
    this.props.history.goBack();
  };

  handleSubmitClick = (e) => {
    this.post();
  };
  componentWillMount = () => {
    this.selectedCows = new Set();
  }

  componentWillUnmount() {
    this.selectedCows.delete();
  }

  handleCheckChange = (e) => {
    if (this.selectedCows.has(e.target.value)) {
      this.selectedCows.delete(e.target.value);
    } else {
      this.selectedCows.add(e.target.value);
    }
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
                this.setState({ ardata: [{ animalID: 'not found' }] });
              } else {
                this.setState({ ardata: data });
                this.setState({ animalID: data[0].animalID });
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
    return (
      <CRow>
        <CCol xs="12" sm="12">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
          </CCol>
        <CCol xs="12" sm="6">
          <CCard>
            <CCardHeader>
              Selected Feed No: {this.props.match.params.fpid}
            </CCardHeader>
            <CCardBody>
              <table className="table table-striped table-hover">
                <tbody>
                  <tr >
                    <td>Animal No.:</td>
                    <td><strong>{this.state.fpdata.feedName}</strong></td>
                  </tr>
                  <tr >
                    <td>Feed Type:</td>
                    <td><strong>{this.state.fpdata.feedType}</strong></td>
                  </tr>
                  <tr >
                    <td>PercentageOfFeed:</td>
                    <td><strong>{this.state.fpdata.percentage}</strong></td>
                  </tr>
                  <tr >
                    <td>Ingredients:</td>
                    <td><strong>{this.state.fpdata.pricae}</strong></td>
                  </tr>
                  <tr >
                    <td>certiOfIngredients:</td>
                    <td><strong>{this.state.fpdata.certiOfIngredients}</strong></td>
                  </tr>
                  <tr >
                    <td>Provider:</td>
                    <td><strong>{this.state.fpdata.foodSource}</strong></td>
                  </tr>
                  <tr >
                    <td>Activation Date:</td>
                    <td><strong>{this.state.fpdata.creationDate}</strong></td>
                  </tr>
                  <tr >
                    <td>Comments: {this.state.notes}</td>
                    <td>
                      {this.state.notes}
                      <CTextarea
                        name="notes" id="notes"
                        defaultValue={this.state.fpdata.notes}
                        rows="2"
                        placeholder="Comments..."
                      />
                    </td>
                  </tr>
                </tbody>
              </table>
            </CCardBody>
          </CCard>
        </CCol>
        <CCol xs="10" sm="6">
          <CCard>
            <CCardHeader>
              List of Animals
          </CCardHeader>
            <CCardBody>
              <CCol style={this.styles}>
                <TableScrollbar rows={15} >


                  <table white-space='nowrap' width="100%">
                    <tbody>
                      {this.state.ardata && this.state.ardata.map((item,index) => {
                        let bkgclr = index % 2 === 0 ? '#f2f2f2' : 'white'
                        if (this.selectedCows.has(item.animalID)) {
                          return (
                            <tr height="40px" bgcolor={bkgclr}>
                             <td width="40%" style={this.styles}>
													<CFormGroup variant="custom-checkbox" inline>
														<CInputCheckbox custom id={item.animalID} name={item.animalID} defaultChecked={true} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
														<CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
													</CFormGroup>
												</td>
												<td width="30%" style={this.styles}>{item.breed}</td>
												<td width="30%" style={this.styles}>{item.dateOfBirth}</td>
                            </tr>)
                        } else{
                           return (
                           <tr height="40px" bgcolor={bkgclr}>
													<td width="40%" style={this.styles}>
														<CFormGroup variant="custom-checkbox" inline>
															<CInputCheckbox custom id={item.animalID} name={item.animalID} value={item.animalID} onClick={(e) => this.handleCheckChange(e)} />
															<CLabel variant="custom-checkbox" htmlFor={item.animalID}>{item.animalID}</CLabel>
														</CFormGroup>
													</td>
													<td width="30%" style={this.styles}>{item.breed}</td>
													<td width="30%" style={this.styles}>{item.dateOfBirth}</td>
												</tr>)
                        }
                      }
                      )}
                    </tbody>
                  </table>


                  
                </TableScrollbar>

              </CCol>

            </CCardBody>
            <CCardFooter>
              <CRow>
                <CCol col="8" sm="4" md="2" xl className="mb-3 mb-xl-0">
                  <CButton block color="info" onClick={this.handleSubmitClick}>Assign</CButton>
                </CCol>
                <CCol col="8" sm="4" md="2" xl className="mb-3 mb-xl-0">
                  <CButton block color="info" onClick={this.handleBackClick}>Back</CButton>
                </CCol>
              </CRow>
            </CCardFooter>
          </CCard>
        </CCol>
      </CRow>
    );
  }
}

export default AssignFeedDetail
