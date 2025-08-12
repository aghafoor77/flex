import React from 'react'
import CIcon from '@coreui/icons-react'

const _employee = [
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
    _children: ['Insemination & Observation']
  },
  {
    _tag: 'CSidebarNavDropdown',
    name: 'Received Infomration',
    route: '/slaughterhouse',
    icon: 'cil-drop',
    _children: [

      {
        _tag: 'CSidebarNavItem',
        name: 'Arrivals',
        to: '/slaughterhouse/downloader',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Subproducts',
        to: '/transporter/carrierlist',
      },
	  {
	  	     _tag: 'CSidebarNavItem',
	  	     name: 'Trasactions',
	  	     to: '/generic/trasactions',
	  	  },
      
	  {
        _tag: 'CSidebarNavItem',
        name: 'Trace',
        to: '/slaughterhouse/traceable',
      },
	  
	  
    ],
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Charts',
    to: '/charts',
    icon: 'cil-chart-pie'
  }
]

export default _employee
