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
  CRow
} from '@coreui/react'

class ObservingAnimal extends Component {

 
  constructor(props) {
    super(props);
    this.state = {
      name: "React",
      isnewlyborn: false,
      ispurchased: false
    };
    this.hideComponent = this.hideComponent.bind(this);
    this.handleBackClick = this.handleBackClick.bind(this);
  }

  handleBackClick = (e) => {
    console.log('Click happened');

  }

displayError(res) {
		switch (res.status) {
			case 200:
				return true;
			case 404:
				alert("Resource unreachable !")
				return false;
			case 500:
				alert(res.json())
				return false;
			default:
				return false;
		}
	}
	handleGet(url) {
		fetch(url)
			.then(res => {
				if (res.status === 200) {
					res.json()
						.then(data => {
              alert(data)
							return data
						})
						.catch(error => {
							alert(error);
							return false
						});
				} else {
					return this.displayError(res)
				}

			})
			.catch(console.log)
	}

  	handleGetAnimals(url) {
		fetch(url)
			.then(res => {
				if (res.status === 200) {
					res.json()
						.then(data => {
              alert(data)
							return data
						})
						.catch(error => {
							alert(error);
							return false
						});
				} else {
					return this.displayError(res)
				}

			})
			.catch(console.log)
	}




  handleRESTGet = (e) => {
    console.log('Click happened');
    //handleGet('http://localhost:8080/v1/registeranima');
      this.handleGet('http://localhost:8080/v1/registeranimal');
      
    
  }
  purchased() {
    return <>
    <CRow>
      <CCol xs="1">
      </CCol>
      <CCol xs="5">
        <CFormGroup>
          <CLabel htmlFor="orderto">Parent Animal ID</CLabel>
          <small>  Parent if of the animal </small>
          <CInput id="parentanimalid" placeholder="Enter parent animal ID" />
        </CFormGroup>
      </CCol>
      <CCol xs="5">
        <CFormGroup>
          <CLabel htmlFor="orderto">Breed </CLabel>
          <small>  Breed of animal </small>
          <CInput id="breed" placeholder="Enter breed" />
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
            <CLabel htmlFor="orderto">Received Farm Name </CLabel>
            <small> Specify the name of farm or organization </small>
            <CInput id="farmname" placeholder="Enter name of organization" />
          </CFormGroup>
        </CCol>
        <CCol xs="5">
          <CFormGroup>
            <CLabel htmlFor="orderto">Received Farm ID </CLabel>
            <small> Specify the ID of farm or organization </small>
            <CInput id="farmid" placeholder="Enter farm or organization ID" />
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
            <CLabel htmlFor="orderto">Previous Animal ID </CLabel>
            <small> Specify the ID of anmial if it has </small>
            <CInput id="farmname" placeholder="Enter previous animal ID " />
          </CFormGroup>
        </CCol>
        <CCol xs="5">
          <CFormGroup>
            <CLabel htmlFor="usefulinfor">Other useful inforamtion  </CLabel>
            <CInput id="usefulinfo" placeholder="Enter any other information related to this animal" />
          </CFormGroup>
        </CCol>
        <CCol xs="1">
        </CCol>
      </CRow>
    </>
  }


  hideComponent(e) {
    switch (e.target.value) {
      case "purchased":
        this.setState({ isnewlyborn: true, ispurchased: false });
        break;
      case "newlyborn":
        this.setState({ ispurchased: false, isnewlyborn: true });
        break;
      default:
        console.log('default');
    }
  }


  render() {
    const { isnewlyborn, ispurchased } = this.state;
    return (
      <CRow>
        <CCol xs="1" sm="9">
          <CCard>
            <CCardHeader>
              RESTAPIClient.js
              <small>  </small>
            </CCardHeader>
            <CCardBody>
              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="animalid">Animal to be examined</CLabel>
                      <small>  Select animal from folloiwng list. </small>
                      <CSelect custom name="canimalid" id="canimalid" onChange={(e) => this.handleBackClick(e)}>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                      </CSelect>
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="observer">Observer </CLabel>
                      <CInput id="observer" placeholder="Enterobserver name" />
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
                      <CLabel htmlFor="wound">Wound</CLabel>
                      <small>  Any woud on the animal skin. </small>
                      <CInput id="wound" placeholder="Wond description" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="weight">Weight</CLabel>
                      <CInput id="weight" placeholder="Weight in KGs" />
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
                      <CLabel htmlFor="BCS">BCS</CLabel>
                      <small>  BCS related info. </small>
                      <CInput id="BCS" placeholder="BCS description" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="limping">Sign of limping</CLabel>
                      <CInput id="limping" placeholder="Enter any sign of limping" />
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
                      <CLabel htmlFor="controldate">Date of Control</CLabel>
                      <CInput type="date" id="control-input" name="control-input" placeholder="date" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="decription-input">Visual Appearance</CLabel>
                      <small>  Information about visual appearance</small>
                      <CTextarea
                        name="notes-input"
                        id="notes-input"
                        rows="3"
                        placeholder="Content..."
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
                    <CButton block color="info">Submit</CButton>
                  </CFormGroup>
                </CCol>
                <CCol xs="2">
                  <CFormGroup>
                    <CButton block color="info" onClick={(e) => this.handleRESTGet(e)}>Back</CButton>
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

export default ObservingAnimal
