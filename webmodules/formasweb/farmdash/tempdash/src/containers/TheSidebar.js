import React from 'react'
import { useSelector, useDispatch } from 'react-redux'
import {
  CCreateElement,
  CSidebar,
  CSidebarBrand,
  CSidebarNav,
  CSidebarNavDivider,
  CSidebarNavTitle,
  CSidebarMinimizer,
  CSidebarNavDropdown,
  CSidebarNavItem,
} from '@coreui/react'

import CIcon from '@coreui/icons-react'
import endpoints from "../views/config/Configuration";
// sidebar nav config
import all from './flows/_nav'
import _admin from './flows/_admin'
import _employee from './flows/_employee'
import _health from './flows/_health'
import _owner from './flows/_owner'
import _emptylinks from './flows/emptylinks'

function viewFlow(){
	const role = window.sessionStorage.getItem("role")
	if(role  == null || role == "Not Assigned"){
		return _emptylinks;
	} else if(role == "ADMIN"){
		return _admin;
	} else if(role == "OWNER"){
		return _owner;
	} else if(role == "OBSERVER" || role == "VETERINARIAN"){
		return _health;
	} else if(role == "EMPLOYEE"){
		return _employee;
	}
	return _admin;
}

const TheSidebar = () => {
  const dispatch = useDispatch()
  const show = useSelector(state => state.sidebarShow)
  const nv =  viewFlow()
  return (
    <CSidebar
      show={show}
      onShowChange={(val) => dispatch({type: 'set', sidebarShow: val })}
    >
      <CSidebarBrand className="d-md-down-none" to="/">
        <CIcon
          className="c-sidebar-brand-full"
          name="logo-negative"
          height={35}
        />
        <CIcon
          className="c-sidebar-brand-minimized"
          name="sygnet"
          height={35}
        />
      </CSidebarBrand>
      <CSidebarNav>

        <CCreateElement
          items={viewFlow()}
          components={{
            CSidebarNavDivider,
            CSidebarNavDropdown,
            CSidebarNavItem,
            CSidebarNavTitle
          }}
        />
      </CSidebarNav>
      <CSidebarMinimizer className="c-d-md-down-none"/>
    </CSidebar>
  )
}

export default React.memo(TheSidebar)
