import React from 'react'
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
  CRow
  
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { DocsLink } from 'src/reusable'

const FarmerRegistrar = () => {
  const [collapsed, setCollapsed] = React.useState(true)
  const [showElements, setShowElements] = React.useState(true)

  return (
    <>
      <CRow>
        <CCol xs="12" sm="9">
          <CCard>
            <CCardHeader>
              Farm Registration
              <small> Farm registration request submission</small>
            </CCardHeader>
            <CCardBody>
              <CForm action="" method="post" encType="multipart/form-data" className="form-horizontal">
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="company">Company</CLabel>
                      <CInput id="company" placeholder="Enter your company name" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="displayname">Display Name</CLabel>
                      <CInput id="displayname" placeholder="Enter display form name" />
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
                      <CLabel htmlFor="email">Email</CLabel>
                      <CInput type="email" id="email-input" name="email-input" placeholder="Enter Email" autoComplete="email" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="5">
                    <CFormGroup>
                      <CLabel htmlFor="contact">Contact #</CLabel>
                      <CInput id="contact" placeholder="Enter contact number with country code" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol md="3">
                    <CLabel>Farm Address</CLabel>
                  </CCol>
                  <CCol xs="5">
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="10">
                    <CFormGroup>
                      <CLabel htmlFor="street">Street</CLabel>
                      <CInput type="input" id="street-input" name="street-input" placeholder="Enter Street" autoComplete="email" />
                    </CFormGroup>
                  </CCol>

                  <CCol xs="1">
                  </CCol>
                </CRow>
                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol xs="4">
                    <CFormGroup>
                      <CLabel htmlFor="city">City</CLabel>
                      <CInput id="city" placeholder="Enter your city" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="2">
                    <CFormGroup>
                      <CLabel htmlFor="postalcode">Postal Code</CLabel>
                      <CInput id="postalcode" placeholder="Enter your postal code" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="4">
                    <CFormGroup>
                      <CLabel htmlFor="country">Country</CLabel>
                      <CInput id="country" placeholder="Country name" />
                    </CFormGroup>
                  </CCol>
                  <CCol xs="1">
                  </CCol>
                </CRow>

                <CRow>
                  <CCol xs="1">
                  </CCol>
                  <CCol md="8">
                    <CLabel htmlFor="decription-input">Description</CLabel>
                  </CCol>

                  <CCol xs="1">
                  </CCol>
                </CRow>

                <CRow>
                  <CCol xs="1">
                  </CCol>

                  <CCol xs="12" md="10">
                    <CTextarea
                      name="decription-input"
                      id="decription-input"
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
               <CRow >
                  <CCol xs="12" sm="3"/>
                  <CCol xs="12" sm="3"/>
                  <CCol xs="12" sm="3">
                    <CFormGroup>
                     <CButton block color="info">Submit</CButton>
                    </CFormGroup>
                  </CCol>
                  <CCol xs="12" sm="3">
                    <CFormGroup>
                     <CButton block color="info">Back</CButton>
                    </CFormGroup>
                  </CCol>
                  <CCol xs="12" sm="3"/>
                </CRow>
            </CCardFooter>
          </CCard>
        </CCol>
        <CCol xs="12" sm="3">
          <CCard>
            <CCardHeader>
              Company
              <small> Form</small>
            </CCardHeader>
            <CCardBody>
              <CFormGroup>
                <CLabel htmlFor="company">Company</CLabel>
                <CInput id="company" placeholder="Enter your company name" />
              </CFormGroup>
              <CFormGroup>
                <CLabel htmlFor="vat">VAT</CLabel>
                <CInput id="vat" placeholder="DE1234567890" />
              </CFormGroup>
              <CFormGroup>
                <CLabel htmlFor="street">Street</CLabel>
                <CInput id="street" placeholder="Enter street name" />
              </CFormGroup>
              <CFormGroup row className="my-0">
                <CCol xs="8">
                  <CFormGroup>
                    <CLabel htmlFor="city">City</CLabel>
                    <CInput id="city" placeholder="Enter your city" />
                  </CFormGroup>
                </CCol>
                <CCol xs="4">
                  <CFormGroup>
                    <CLabel htmlFor="postal-code">Postal Code</CLabel>
                    <CInput id="postal-code" placeholder="Postal Code" />
                  </CFormGroup>
                </CCol>
              </CFormGroup>
              <CFormGroup>
                <CLabel htmlFor="country">Country</CLabel>
                <CInput id="country" placeholder="Country name" />
              </CFormGroup>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>

    </>
  )
}

export default FarmerRegistrar
