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
  CButton
} from '@coreui/react'

import Circle from "../mcomp/Circle";
import endpoints from "../config/Configuration";

class PregnancyWithStatusList extends Component {

	constructor() {
		super();
		this.state = {
				osdata :[],
        alert: {
          title: "Here ", message: "There", display: false
        }
			};
		this.fetchOrdersS();
	}
	




   getBadge = status => {
	  switch (status) {
	    case "Conceived": return (<Circle key='mykey' bgColor='#A1D363' width='15' height='15'></Circle>)
	    default: return (<Circle key='mykey' bgColor='#E94F37' width='15' height='15'></Circle>)
	  }
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
 fetchOrdersS() {
	    let url = window.sessionStorage.getItem(endpoints.AE)+'/application/v1/animalexamination'
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
	              this.setState({osdata:[]});
	            } else{
	              this.setState({osdata:data});
	            }
	             
	              return this.state.osdata;

	            })
	            .catch(error => {
	              this.setAlert("Server error", error.message +"(fetching animalexamination)");
							return false
						});
				} else {
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection error", error.message +"(fetching animalexamination)");

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
	            Pregnancy examination
	                 <small className="text-muted"> Results of examination after insemination or herding </small>
	                 <strong className="text-left"> [ Total Rows: {this.state.osdata.length} ]</strong>
	          </CCardHeader>
	          <CCardBody>
	            <CDataTable
	              items={this.state.osdata}
	              fields={[
	          	    { label:'No.', key: 'aeid', _style: { width: '10%'} },
	          	    { label:'Animal', key: 'animalID', _style: { width: '15%'} },
	          	    { label:'Employee', key: 'employeeID', _style: { width: '15%'} },
	          	    { label:'Date', key: 'examinationDate', _style: { width: '15%'} },
	          	    { label:'Expected Date', key: 'extepctedDate', _style: { width: '10%'} },
	          	    { label:'Ref Number', key: 'refnumber', _style: { width: '10%'} },
	        	    { label:'Status', key: 'status', _style: { width: '5%'} }
	        	  ]}
	              hover
				  striped
				  columnFilter
	              itemsPerPage={7}
	              pagination
	              clickableRows
	              onRowClick={(item) => this.props.history.push(`/insemination/pregl/${item.aeid}`)}
	              scopedSlots={{
	                'status':
	                  (item) => (
	                    <td>
	                      {this.getBadge(item.status)}
	                    </td>
	                  )
	              }}
	            />
	          </CCardBody>
	        </CCard>
	      </CCol>
	    </CRow>
	  )
  }
}


export default PregnancyWithStatusList
