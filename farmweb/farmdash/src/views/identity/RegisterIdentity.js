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

class RegisterIdentity extends Component {
  constructor() {
    super();
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
  purchased() {
    return <><CRow>
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
        this.setState({ isnewlyborn: false, ispurchased: true });
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
              Register Identity
              <small>  Register a user/employee. </small>
            </CCardHeader>
            <CCardBody>
              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="abtanimal">About Animal</CLabel>
                      <CSelect custom name="cabtanimal" id="cabtanimal" onChange={(e) => this.hideComponent(e)}>
                        <option value="newlyborn">Newly Born</option>
                        <option value="purchased">Purchased/Gifted</option>
                      </CSelect>
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="breed">Pregnancy Examination Report Number</CLabel>
                      <small>  It will help to identiy the parents </small>
                      <CSelect custom name="cbreed" id="cbreed" onChange={(e) => this.handleBackClick(e)}>
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
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="orderto">Animal ID</CLabel>
                      <small>  It must be unique </small>
                      <CInput id="animalid" placeholder="Enter animal ID" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="sex">Sex</CLabel>
                      <CSelect custom name="csex" id="csex">
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Othre</option>
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
                      <CLabel htmlFor="dobdate">Date of Birth</CLabel>
                      <CInput type="date" id="dobdate-input" name="dobdate-input" placeholder="date" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="input">Birth Place</CLabel>
                      <small>  name of farm or location </small>
                      <CInput id="birthplace" placeholder="Enter birth place" />
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
                      <CLabel htmlFor="enteredby">ID of registrar </CLabel>
                      <small> if you are the owener keep it bank  </small>
                      <CInput id="weight" placeholder="Enter employee ID" />
                    </CFormGroup>
                  </CCol>
                 
                    <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="weight">Weight at the time of birth (KGs) </CLabel>
                      <small> if not then current weight </small>
                      <CInput id="weight" placeholder="Enter weight (KGs)" />
                    </CFormGroup>
                  </CCol>

                  <CCol xs="1">
                  </CCol>
                </CRow>
                {isnewlyborn ? (this.purchased()) : null}
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol md="8">
                    <CLabel htmlFor="decription-input">Notes</CLabel>
                    <small>  Extra information about the animal </small>
                  </CCol>

                  <CCol xs="1">
                  </CCol>
                </CRow>

                <CRow>
                  <CCol xs="1">
                  </CCol>

                  <CCol xs="12" md="10">
                    <CTextarea
                      name="notes-input"
                      id="notes-input"
                      rows="5"
                      placeholder="Content..."
                    />
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
              </CForm>
            </CCardBody>
            <CCardFooter>
              <CRow>
                <CCol xs="1">
                </CCol>
                <CCol col="8" sm="4" md="2" xl className="mb-3 mb-xl-0">
                  <CButton block color="info">Register</CButton>
                </CCol>
                <CCol xs="1">
                </CCol>
              </CRow>

            </CCardFooter>
          </CCard>
        </CCol>
      </CRow>

    );
  }
}

export default RegisterIdentity
