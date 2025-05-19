import React, { Component} from 'react'
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

import orderedSemenData from './OrderedSemenData'

class OrderSemenList extends Component {
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
                 <strong className="text-left"> [ Total Rows: {orderedSemenData.length} ]</strong>
          </CCardHeader>
          <CCardBody>
            <CDataTable
              items={orderedSemenData}
              fields={[
          	    { key: 'orderno', _style: { width: '10%'} },
          	    { key: 'to', _style: { width: '20%'} },
          	    { key: 'email', _style: { width: '20%'} },
          	    { key: 'contact', _style: { width: '10%'} },
        	    { key: 'breed', _style: { width: '10%'} },
        	    { key: 'status', _style: { width: '10%'} }
        	  ]}
              hover
              striped
              itemsPerPage={5}
              pagination
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
           
            
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
  )}
}

export default OrderSemenList
