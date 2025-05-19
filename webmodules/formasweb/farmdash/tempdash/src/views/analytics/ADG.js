import React, { Component } from 'react'
import {
  CCard,
  CCardBody,
  CCardGroup,
  CCardHeader,
  CRow,
  CCol
} from '@coreui/react'
import {
  CChartBar
 
} from '@coreui/react-chartjs'
import { DocsLink } from 'src/reusable'

class ADG extends Component {

	constructor(props) {
	    super(props);
	    this.state = {
	    	labels : [],
	    	data: []
	    };
	    this.fetchData();	  
	}
	
	 async fetchData() {
	    let url = 'http://localhost:9040/application/v1/analytics/ADG'
	    fetch(url, {
	      method: "GET",
	      headers: {
	        Accept: "application/json",
	        "Content-Type": "application/json"
	      }
	    })
	      .then(res => {
	        if (res.status === 200) {
	        	console.log(res)
	          res.json()
	            .then(data => {
	            	console.log(data)
	            	this.state = {
	            	    	labels : data.labels,
	            	    	data: data.data}
	            	 alert(this.state.labels)
	              return data;
	            })
	            .catch(error => {
	              alert("Error "+ error.message + " (Fetching ADG data)")
	              return false
	            });
	        } else {
	          //return this.displayError(res)
	        }
	      })
	      .catch(error => {
	        alert("Connection Error-"+ error.message + " (Fetching ADG data)")
	        return false
	      });
	  }
		
	render() {return (
		<CRow>
			<CCol xs="9" sm="9">
		      <CCard>
		        <CCardHeader>
		          ADG         
		        </CCardHeader>
		        <CCardBody>
		          <CChartBar
		            datasets={[
		              {
		                label: 'Average per Day Gain (ADG)',
		                backgroundColor: '#728FCE',
		                data: this.state.data
		              }
		            ]}
		            labels= {this.state.labels}         
		          />
		        </CCardBody>
		      </CCard>
	      </CCol> 
	    </CRow>
	  )
	}
}
export default ADG