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
  CInput,
  CLabel,
  CSelect,
  CRow,
  CAlert

} from '@coreui/react'


import endpoints from "../config/Configuration";

class FeedingPattern extends Component {

  constructor(props) {
    super(props);
    this.handleBackClick = this.handleBackClick.bind(this);
    this.state = {
      foodtypes:[],
      fpid : "", 
      certiOfIngredients : "", 
      notes : "", 
      feedName : "", 
      pricae : "", 
      feedType : "", 
      percentage : "", 
      creationDate : "", 
      foodSource : "",
          alert: {
				title: "Here ", message: "There", display: false
			}
    };
    this.fetchFoodTypes();
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

 handleChange(event) {
	switch (event.target.id) {
		  case 'fpid': this.setState({ fpid: event.target.value }); break;
		  case 'certiOfIngredients': this.setState({ certiOfIngredients: event.target.value }); break;
		  case 'notes': this.setState({ notes: event.target.value }); break;
		  case 'feedName': this.setState({ feedName: event.target.value }); break;
		  case 'pricae': this.setState({ pricae: event.target.value }); break;
		  case 'feedType': this.setState({ feedType: event.target.value }); break;
		  case 'percentage': this.setState({ percentage: event.target.value }); break;
		  case 'creationDate': this.setState({ creationDate: event.target.value }); break;
		  case 'foodSource': this.setState({ foodSource: event.target.value }); break;
		  default: return '';
	}
}


getJSON(){
	let jsondata =  {
		fpid : this.state.fpid, 
		certiOfIngredients : this.state.certiOfIngredients, 
		notes : this.state.notes, 
		feedName : this.state.feedName, 
		pricae : this.state.pricae, 
		feedType : this.state.feedType, 
		percentage : this.state.percentage, 
		creationDate : this.state.creationDate, 
		foodSource : this.state.foodSource
	}
	return jsondata; 

}

  handleBackClick = (e) => {
    this.props.history.goBack();
  };

   submit = (e) => {
    console.log(this.getJSON())
    this.post();
  };



  post() {
		fetch(window.sessionStorage.getItem(endpoints.FP)+"/application/v1/feedpattern", {
			method: "POST",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json"
			},
			body: JSON.stringify(this.getJSON())
		})
			.then(res => {
				if (res.ok) {
					this.props.history.push(`/animals/healthrecords`)
				} else {
					this.displayError(res);
				}
			})
			.then(json => {
				this.setState({
					isLoaded: true,
					token: json
				});
			})
			.catch(error => {
				console.error(error)
				alert(error.message)
			});
  }
  
fetchFoodTypes() {
		console.log("Breed data fetching ")
		let url = window.sessionStorage.getItem(endpoints.BD) + '/application/v1/feedtype'
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
							let items = [];
							items.push("No Record Found ");
							if (data.length === 0) {
								this.setState({ foodtypes: items });
							} else {
                this.setState({ foodtypes: data, feedType:data[0].feedName });

                console.log(data)
							}
							//this.state.foodtypes = "Select Option";
							return items;

						})
						.catch(error => {
							this.setAlert("Error", error.message + " (Breed data fetching)")
							return false
						});
				} else {
					return this.displayError(res)
				}

			})
			.catch(error => {
				this.setAlert("Connection Error-", error.message + " (Breed data fetching)")
				return false
			});

	}

  render() {
      return (
      <CRow>
        <CCol xs="12" sm="12">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
          </CCol>
        <CCol xs="12" sm="9">
          <CCard>
            <CCardHeader>
              Define Animal Feeding Pattern
              <small>  Various combination of food for animals. </small>
            </CCardHeader>
            <CCardBody>
              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="feedName">Feed Name</CLabel>
                      <small>  Select animal from folloiwng list. </small>
                      <CInput name="feedName" id="feedName" onChange={this.handleChange.bind(this)} value={this.state.feedName} placeholder="Enter name of feed " />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="feedType">Feed Type</CLabel>
                      <CSelect custom name="feedType" id="feedType" onChange={this.handleChange.bind(this)} value={this.state.feedType}>
                        {this.state.foodtypes && this.state.foodtypes.map(item =>
														<option value={item.feedName}>{item.feedName}</option>
													)}
                       
                      </CSelect>
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="percentage">Percentage of feed </CLabel>
                      <small>  Percentage of feed seperated by - sign. </small>
                      <CInput name="percentage" id="percentage" onChange={this.handleChange.bind(this)} value={this.state.percentage} placeholder="Ratio of feed." />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="creationDate">Activation Date</CLabel>
                      <CInput type="date" name="creationDate" id="creationDate" onChange={this.handleChange.bind(this)} value={this.state.creationDate} placeholder="date" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="pricae">Ingredients [Add table]</CLabel>
                      <CInput name="pricae" id="pricae" onChange={this.handleChange.bind(this)} value={this.state.pricae} placeholder="Ingredients of feed" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="certiOfIngredients">Certification of ingredients</CLabel>
                      <small>  Any woud on the body of animal. </small>
                      <CInput name="certiOfIngredients" id="certiOfIngredients" onChange={this.handleChange.bind(this)} value={this.state.certiOfIngredients} placeholder="Certification of ingredients" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="foodSource">Food provider</CLabel>
                      <small>  Name of the company who provides food . </small>
                      <CInput name="foodSource" id="foodSource" onChange={this.handleChange.bind(this)} value={this.state.foodSource} placeholder="Name of food provider" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="notes">Comments</CLabel>
                      <small>  Specifies comments about the feed and its usage</small>
                      <CTextarea
                        name="notes" id="notes" 
                        onChange={this.handleChange.bind(this)} 
                        value={this.state.notes}
                        rows="2"
                        placeholder="Comments..."
                      />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
              </CForm>
            </CCardBody>
            <CCardFooter>
              <CRow>
                <CCol xs="4" />
                <CCol xs="3" />
                <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" onClick={(e) => this.submit(e)}>Submit</CButton>
                  </CFormGroup>
                </CCol>
                <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" onClick={(e) => this.handleBackClick(e)}>Back</CButton>
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

export default FeedingPattern
