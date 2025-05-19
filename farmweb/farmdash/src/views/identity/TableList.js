import React, { Component, Route, useEffect } from 'react'
import { useHistory, useLocation } from 'react-router-dom'

import {
  CBadge,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CDataTable,
  CRow,
  CPagination
} from '@coreui/react'


/*import {
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

} from '@coreui/react'*/

import orderedSemenData from './OrderedSemenData'

class OrderSemenList extends Component {
	
	
	constructor(props) {
		super(props);
		 
		 this.queryPage = this.props.location.search.match(/page=([0-9]+)/, '');
		 this.currentPage = Number(this.queryPage && this.queryPage[1] ? this.queryPage[1] : 1)
		 this.state = {page:this.currentPage};
		 this.pageChange = newPage => {
		    this.currentPage !== newPage && this.props.history.push(`/insemination/orderlist?page=${newPage}`)
		  }
	}
  
	getBadge = status => {
		  switch (status) {
		    case 'Pending': return 'warning'
		    case 'Done': return 'success'
		    case 'Cancel': return 'danger'
		    default: return 'primary'
		  }
		}

  

 

  render(){
  return (
    <CRow>
      <CCol xl={9}>
        <CCard>
          <CCardHeader>
            Order No
                 <small className="text-muted"> List of all orders</small>
          </CCardHeader>
          <CCardBody>
            <CDataTable
              items={orderedSemenData}
              fields={[
                { key: 'orderno', _classes: 'font-weight-bold' },
                'to', 'email', 'contact', 'breed', 'status'
              ]}
              hover
              columnFilter
              striped
              itemsPerPage={7}
              activePage={this.state.page}
              clickableRows
              onRowClick={(item) => this.props.history.push(`/insemination/orderlist/${item.orderno}`)}
              scopedSlots={{
                'status':
                  (item) => (
                    <td>
                      <CBadge color={this.getBadge(item.status)}>
                        {item.status}
                      </CBadge>
                    </td>
                  )
              }}
            />
            <strong className="text-left"> [ Total Rows: {orderedSemenData.length} ]</strong>
            <CPagination
              activePage={this.state.page}
              onActivePageChange={this.pageChange}
              pages={orderedSemenData.length % 10 == 0 ? orderedSemenData.length / 10 : (orderedSemenData.length / 10) + 1}
              doubleArrows={false}
              align="center"
            />
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
  )}
}

export default OrderSemenList
