import React from 'react'
import CIcon from '@coreui/icons-react'

const _health = [
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
    _children: ['Insemination']
  },
  {
    _tag: 'CSidebarNavDropdown',
    name: 'Mother Care',
    route: '/insemination',
    icon: 'cil-drop',
    _children: [

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
    _children: ['Animal Care']
  }, {
    _tag: 'CSidebarNavDropdown',
    name: 'Animal Health',
    route: '/animals',
    icon: 'cil-puzzle',
    _children: [
      {
            _tag: 'CSidebarNavItem',
            name: 'Assigned Tasks',
            to: '/animal/assigned/mylist',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Health Examination',
        to: '/animals/health',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Health Examination Records',
        to: '/animals/healthrecords',
      },
      {
        _tag: 'CSidebarNavItem',
        name: 'Drugs & Treatments',
        to: '/animals/animaldt',
      }]
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Charts',
    to: '/charts',
    icon: 'cil-chart-pie'
  }
]

export default _health
