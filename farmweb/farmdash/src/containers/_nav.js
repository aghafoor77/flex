import React from 'react'
import CIcon from '@coreui/icons-react'

const _nav = [
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
	_tag: 'CSidebarNavItem',
	name: 'Humain Resource',
	to: '/farmer/detail',
	icon: 'cil-pencil',
  },
  {
    _tag: 'CSidebarNavTitle',
    _children: ['Farm']
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Registration',
    to: '/farm/farmer',
    icon: 'cil-pencil',
  },
  {
	_tag: 'CSidebarNavItem',
	name: 'Farm',
	to: '/farm/farm',
	icon: 'cil-pencil',
  },
  {
	_tag: 'CSidebarNavItem',
	name: 'Facilities',
	to: '/farm/faciities',
	icon: 'cil-pencil',
  },
  {
    _tag: 'CSidebarNavTitle',
    _children: ['Insemination']
  },
  {
    _tag: 'CSidebarNavDropdown',
    name: 'Insemination',
    route: '/insemination',
    icon: 'cil-drop',
    _children: [

      {
        _tag: 'CSidebarNavItem',
        name: 'Order Semen',
        to: '/insemination/order',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Order List',
        to: '/insemination/orderlist',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Move Bull for Herd',
        to: '/insemination/mb4h',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Bull Movements',
        to: '/insemination/mb4hl',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Insemination Record',
        to: '/insemination/su',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Pregnancy Examination',
        to: '/insemination/pregexam',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Pregnancy Records',
        to: '/insemination/pregl',
      }
    ],
  },
  {
    _tag: 'CSidebarNavTitle',
    _children: ['Animal Registration']
  },
  {
    _tag: 'CSidebarNavDropdown',
    name: 'Register Animal',
    route: '/animal/reg',
    icon: 'cil-drop',
    _children: [

      {
        _tag: 'CSidebarNavItem',
        name: 'Register',
        to: '/animal/reg',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Registered Animal List',
        to: '/animal/reglist',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Deregister',
        to: '/movements/deregister',
      }
    ],
  }, {
    _tag: 'CSidebarNavTitle',
    _children: ['Health & Feed']
  }, {
    _tag: 'CSidebarNavDropdown',
    name: 'Feed and Heatlth',
    route: '/animals',
    icon: 'cil-puzzle',
    _children: [

      {
        _tag: 'CSidebarNavItem',
        name: 'Health Examination',
        to: '/animals/health',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Observe Animal',
        to: '/animals/observe',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Health Examination Records',
        to: '/animals/healthrecords',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Animal Feeding Pattern',
        to: '/animals/feeding',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Animal Feeding',
        to: '/animals/animalfeeding',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Drugs & Treatments',
        to: '/animals/animaldt',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Drugs & Treatments List',
        to: '/animals/animaldtlist',
      }]
  },
  {
    _tag: 'CSidebarNavTitle',
    _children: ['Animal Management']
  },
  {
    _tag: 'CSidebarNavDropdown',
    name: 'Animal Movement',
    route: '/movements',
    icon: 'cil-puzzle',
    _children: [

      {
        _tag: 'CSidebarNavItem',
        name: 'Move Out',
        to: '/movements/out',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Movements',
        to: '/movements/list',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Transport Animal',
        to: '/movements/gv',
      },
      {
          _tag: 'CSidebarNavItem',
          name: 'Animal Data',
          to: '/movements/ggv',
        },
      {
        _tag: 'CSidebarNavItem',
        name: 'Animal Resource Graph',
        to: '/movements/arg',
      },
      {
          _tag: 'CSidebarNavItem',
          name: 'Report to Slaughterhouse',
          to: '/movements/request2slaughterhus',
        },
        {
          _tag: 'CSidebarNavItem',
          name: 'Sale Orders',
          to: '/movements/r2shuslist',
        }


    ]


  },
  {
    _tag: 'CSidebarNavTitle',
    _children: ['Identity Management']
  },
  {
    _tag: 'CSidebarNavDropdown',
    name: 'Identity Management',
    route: '/identity',
    icon: 'cil-puzzle',
    _children: [
      {
        _tag: 'CSidebarNavItem',
        name: 'Register Identity',
        to: '/identity/register',
      }, {
        _tag: 'CSidebarNavItem',
        name: 'RESTAPIClient',
        to: '/identity/reg',
      }, {
        _tag: 'CSidebarNavItem',
        name: 'Test',
        to: '/identity/test',
      }, {
        _tag: 'CSidebarNavItem',
        name: 'Login',
        to: '/identity/login',
      }]
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Charts',
    to: '/charts',
    icon: 'cil-chart-pie'
  }
]

export default _nav
