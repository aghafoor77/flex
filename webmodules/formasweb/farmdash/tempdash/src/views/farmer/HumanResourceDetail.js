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
class HumanResourceDetail extends Component {

	constructor() {
		super();
		this.state = {
		tmdata :[],
		alert: {
				title: "Here ", message: "There", display: false
			}
			};
		
		this.fetchTMData();
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

 fetchTMData() {

		let url = 'http://localhost:9020/application/v1/farmer';//window.sessionStorage.getItem(endpoints.TM)+'/application/v1/farmer';
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
					<CCol xs="12">
						Human Resources 
	                		<small className="text-muted"> animal control </small>
					</CCol>
					
				</CRow>
	          </CCardHeader>
	          <CCardBody>
	            <CDataTable
	              items={this.state.tmdata}
	              fields={[
					  { label: 'resourceId', key: 'resourceId', _style: { width: '14%'} },
					  { label: 'name', key: 'name', _style: { width: '14%'} },
					  { label: 'email.', key: 'email', _style: { width: '14%'} },
	          	      { label: 'role', key: 'role', _style: { width: '14%'} },
					  { label: 'contact', key: 'contact', _style: { width: '10%'} },
					  { label: 'farmId', key: 'farmId', _style: { width: '15%'} },
					  { label: 'street', key: 'street', _style: { width: '14%'} },
					  { label: 'postcode', key: 'postcode', _style: { width: '5%'} },
					   
						
					  
	        	  ]}
	              hover
				  striped
				  columnFilter
	              itemsPerPage={7}
	              pagination
	              clickableRows
	              onRowClick={(item) => this.props.history.push('/farmer/detail1/'+item.resourceId)}
	        
	            />
	            
	          </CCardBody>
	        </CCard>
	      </CCol>
	    </CRow>
	  )
  }
}

export default HumanResourceDetail
