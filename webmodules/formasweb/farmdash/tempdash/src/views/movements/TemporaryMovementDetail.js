import React, { Component } from 'react'
import {
  CBadge,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CDataTable,
  CRow,
  CAlert,
  CButton,
  CLabel,
  CSelect
} from '@coreui/react'

import endpoints from "../config/Configuration";
class TemporaryMovementDetail extends Component {

	constructor() {
		super();
		this.state = {
		tmdata :[],
		ardata:[],
		criteria:"",
		animalID:"",
        alert: {
				title: "Here ", message: "There", display: false
			}
			};
		this.fetchRegisteredAnimals();
	}
  
  setAlert(title, message){
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
  

   getBadge = status => {
	  switch (status) {
	    case "Conceived": return ( <CBadge color='success'> ........... </CBadge>)
	    default: return ( <CBadge color='danger'> ....x.... </CBadge>)
	  }
	}
   

	handleChange(event) {
    switch (event.target.id) {
		case 'animalID': 
			this.setState({animalID:event.target.value})
			this.fetchTMData(event.target.value);
			break;
        default: return '';
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
								this.setState({ ardata: [{animalID:'Select All'}] });
							} else {
								data.push({animalID:"Select All"});
								this.setState({ ardata: data, animalID:"Select All" });
								this.fetchTMData("Select All");
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

 fetchTMData(val) {

		let cri = (val === "Select All"?"":"/animal/"+val); 
		let url = window.sessionStorage.getItem(endpoints.TM)+'/application/v1/temporarymovement'+cri;
	    fetch(url, {
	    method: "GET",
	    headers: {
	      Accept: "application/json",
	      "Content-Type": "application/json"
	    }		  })
	      .then(res => {
	        if (res.status === 200) {
	          res.json()
	            .then(data => {
	        
	              if(data.length === 0){
	              this.setState({tmdata:[]});
	            } else{
	              this.setState({tmdata:data});
	            }
	             
	              return this.state.tmdata;

	            })
	            .catch(error => {
	              this.setAlert("General error", error.message + "( temporarymovement data fetching)");
							return false
						});
				} else {
					return this.displayError(res)
				}
			})
			.catch(error => {
				this.setAlert("Connection Error-", error.message + " (temporarymovement data fetching)")
	              return false
	            });

	  }

  render(){
	  return (
	    <CRow>
			  <CCol xs="12" sm="12">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
          </CCol>
	      <CCol xl={12}>
	        <CCard>
	          <CCardHeader>
	           <CRow>
					<CCol xs="9">
						Movement 
	                		<small className="text-muted"> animal movement </small>
					</CCol>
					
					<CCol xs="3">
                    
                      <CLabel htmlFor="animalID">Select Animal<CSelect custom name="animalID" id="animalID"   onChange={this.handleChange.bind(this)} value={this.state.animalID}>
                        {this.state.ardata && this.state.ardata.map(item =>
														<option value={item.animalID}>{item.animalID}</option>
													)}
                      </CSelect></CLabel>
                  </CCol>
				</CRow>
	          </CCardHeader>
	          <CCardBody>
	            <CDataTable
	              items={this.state.tmdata}
	              fields={[
					  { label: 'No.', key: 'tmid', _style: { width: '10%'} },
					  { label: 'Type', key: 'tmType', _style: { width: '5%'} },
					  { label: 'Date.', key: 'outDate', _style: { width: '10%'} },
	          	      { label: 'Employee', key: 'employeeID', _style: { width: '10%'} },
					  { label: 'Purpose', key: 'purpose', _style: { width: '55%'} },
					  
					  
	        	  ]}
	              hover
				  striped
				  columnFilter
	              itemsPerPage={7}
	              pagination
	              clickableRows
	              onRowClick={(item) => {
					   						this.props.history.push('/movements/in/'+item.tmid)}
										}
	              scopedSlots={{
	                'type':
	                  (item) => (
	                    <td>
	                      {(item.tmid.startsWith("GHE") === true ?'Examination':"Observation")
											}
	                    </td>
	                  ),
	              }}
	            />
	            
	          </CCardBody>
	        </CCard>
	      </CCol>
	    </CRow>
	  )
  }
}

export default TemporaryMovementDetail
