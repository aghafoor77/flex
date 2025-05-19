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

import endpoints from "../../config/Configuration";
class AssignHealthAnimalsTable extends Component {

	constructor() {
		super();
		this.state = {
		tmdata :[],
		//ardata:[],
		criteria:"",
		animalID:"",
        alert: {
				title: "Here ", message: "There", display: false
			}
		};
		this.fetchTMData("Select All");
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

  /*fetchRegisteredAnimals() {
		let url = "http://localhost:9002"+ '/application/v1/registeranimal';//window.sessionStorage.getItem(endpoints.AR) + '/application/v1/registeranimal'
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
  }*/

 fetchTMData(val) {
		let cri = (val === "Select All" ? "" : "/"+val); 
		let url = "http://localhost:9002"+ "/application/v1/assignanimal/records/healthcare/health@lsc.se";
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
	              this.setAlert("General error", error.message + "( Assign animal for health !)");
							return false
						});
				} else {
					return this.displayError(res)
				}
			})
			.catch(error => {
				this.setAlert("Connection Error-", error.message + " (Assign animal for health data !)")
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
					Assigned Animals for Examination
	                		<small className="text-muted"> animal health examination</small>
					</CCol>				
				</CRow>
	          </CCardHeader>
	          <CCardBody>
	            <CDataTable
	              items={this.state.tmdata}
	              fields={[
					  { label: 'No.', key: 'aaid', _style: { width: '10%'} },
					  { label: 'action', key: 'action', _style: { width: '5%'} },
					  { label: 'Date.', key: 'assignedDate', _style: { width: '10%'} },
	          	      { label: 'Examiner', key: 'examiner', _style: { width: '10%'} },
					  { label: 'Notes', key: 'notes', _style: { width: '55%'} },
					  
					  
	        	  ]}
	              hover
				  striped
				  columnFilter
	              itemsPerPage={7}
	              pagination
	              clickableRows
	              onRowClick={(item) => {
					   						this.props.history.push('/animal/assigned/mylist/'+item.aaid)}
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

export default AssignHealthAnimalsTable
