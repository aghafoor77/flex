import React from 'react';
import { CFormGroup, CLabel } from '@coreui/react'

import {
	CCard,
	CCardBody,
	CCardHeader,
	CCol,
	CInput,
	CRow
} from '@coreui/react'

import { DocsLink } from 'src/reusable'

const TablePage = props => {

	return (
		<><CRow>
			<CCol xs="12" sm="6">
				<CCard>
					<CCardHeader>
						Animal Registration Record
              <small> animal registration and berrding deatil </small>
						<DocsLink name="-Input" />
					</CCardHeader>
					<CCardBody>
						<CRow>
							<CCol xs="12">
								<CFormGroup>
									<CLabel htmlFor="ccnumber">Credit Card Number</CLabel>
									<CInput id="ccnumber" placeholder="0000 0000 0000 0000" required />
								</CFormGroup>
							</CCol>
						</CRow>
						<CRow>
							<CCol xs="12">
								<CFormGroup>
									<CLabel htmlFor="ccnumber">Credit Card Number</CLabel>
									<CInput id="ccnumber" placeholder="0000 0000 0000 0000" required />
								</CFormGroup>
							</CCol>
						</CRow>
						<CRow>
							<CCol xs="4">
								<CFormGroup>
									<CLabel htmlFor="ccmonth">Month</CLabel>
									<CInput id="ccmonth" placeholder="0000 0000 0000 0000" required />
								</CFormGroup>
							</CCol>
							<CCol xs="4">
								<CFormGroup>
									<CLabel htmlFor="ccyear">Year</CLabel>
									<CInput id="ccyear" placeholder="0000 0000 0000 0000" required />
								</CFormGroup>
							</CCol>
							<CCol xs="4">
								<CFormGroup>
									<CLabel htmlFor="cvv">CVV/CVC</CLabel>
									<CInput id="cvv" placeholder="123" required />
								</CFormGroup>
							</CCol>
						</CRow>
					</CCardBody>
				</CCard>
			</CCol>
			<CCol xs="12" sm="6">
				<CCard>
					<CCardHeader>
						Animal Health Reports
              <small> medical and physical examination reports</small>
					</CCardHeader>
					<CCardBody>
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
			<CRow>
				<CCol xs="12" sm="6">
					<CCard>
						<CCardHeader>
							Animal feeding record
              <small> inhouse and outdoor feeding data</small>
							<DocsLink name="-Input" />
						</CCardHeader>
						<CCardBody>
							<CRow>
								<CCol xs="12">
									<CFormGroup>
										<CLabel htmlFor="name">Name</CLabel>
										<CInput id="name" placeholder="Enter your name" required />
									</CFormGroup>
								</CCol>
							</CRow>
							<CRow>
								<CCol xs="12">
									<CFormGroup>
										<CLabel htmlFor="ccnumber">Credit Card Number</CLabel>
										<CInput id="ccnumber" placeholder="0000 0000 0000 0000" required />
									</CFormGroup>
								</CCol>
							</CRow>
							<CRow>
								<CCol xs="4">
									<CFormGroup>
										<CLabel htmlFor="ccmonth">Month</CLabel>
										<CInput id="ccmonth" placeholder="0000 0000 0000 0000" required />
									</CFormGroup>
								</CCol>
								<CCol xs="4">
									<CFormGroup>
										<CLabel htmlFor="ccyear">Year</CLabel>
										<CInput id="ccyear" placeholder="0000 0000 0000 0000" required />
									</CFormGroup>
								</CCol>
								<CCol xs="4">
									<CFormGroup>
										<CLabel htmlFor="cvv">CVV/CVC</CLabel>
										<CInput id="cvv" placeholder="123" required />
									</CFormGroup>
								</CCol>
							</CRow>
						</CCardBody>
					</CCard>
				</CCol>
				<CCol xs="12" sm="6">
					<CCard>
						<CCardHeader>
							Animal movements
              <small> temporary and permanent movements</small>
						</CCardHeader>
						<CCardBody>
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
	);
};

export default TablePage;