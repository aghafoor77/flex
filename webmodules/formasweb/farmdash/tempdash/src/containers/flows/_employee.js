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
          name: 'Observe Animal',
          to: '/animals/observe',
        },
        {
          _tag: 'CSidebarNavItem',
          name: 'Observation Records',
          to: '/animals/observationrecords',
        },
        {
            _tag: 'CSidebarNavItem',
            name: 'Drugs & Treatments List',
            to: '/animals/animaldtlist',
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
    _children: ['Feed Record']
  }, {
    _tag: 'CSidebarNavDropdown',
    name: 'Feed',
    route: '/animals',
    icon: 'cil-puzzle',
    _children: [

      {
        _tag: 'CSidebarNavItem',
        name: 'Animal Feeding Pattern',
        to: '/animals/feeding',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Animal Feeding',
        to: '/animals/animalfeeding',
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

export default _employee
