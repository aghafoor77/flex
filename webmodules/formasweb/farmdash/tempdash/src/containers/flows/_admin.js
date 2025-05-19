import React from 'react'
import CIcon from '@coreui/icons-react'

const _admin = [
  {
    _tag: 'CSidebarNavItem',
    name: 'Dashboard',
    to: '/dashboard',
    icon: <CIcon name="cil-speedometer" customClasses="c-sidebar-nav-icon" />,
    badge: {
      text: 'eT2F',
    }
  },
  {
	    _tag: 'CSidebarNavTitle',
	    _children: ['Humain Resource']
	  },
	  {
	    _tag: 'CSidebarNavDropdown',
	    name: 'Humain Resource',
	    route: '/identity',
	    icon: 'cil-puzzle',
	    _children: [
	      {
	        _tag: 'CSidebarNavItem',
	        name: 'Users',
	        to: '/humanresource/userlist',
	      }, {
		        _tag: 'CSidebarNavItem',
		        name: 'Register Employee',
		        to: '/res/register',
		      }
	      
	      ]
	  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Charts',
    to: '/charts',
    icon: 'cil-chart-pie'
  }
]

export default _admin