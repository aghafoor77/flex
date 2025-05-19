import React from 'react'
import CIcon from '@coreui/icons-react'

const _owner = [
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
    _tag: 'CSidebarNavTitle',
    _children: ['Farm']
  },
  {
  _tag: 'CSidebarNavDropdown',
  name: 'Farm & Facilities',
  route: '/farm/farmer',
  icon: 'cil-puzzle',
  _children: [
  /*{
    _tag: 'CSidebarNavItem',
    name: 'Registration',
    to: '/farm/farmer',
    
  },*/
  {
	_tag: 'CSidebarNavItem',
	name: 'Farm',
	to: '/farm/farm',
	
  },
  {
	_tag: 'CSidebarNavItem',
	name: 'Facilities',
	to: '/farm/faciities',
	
  }]}
  
  
  ,
  {
	    _tag: 'CSidebarNavTitle',
	    _children: ['Transport Animal']
	  },
	  {
	    _tag: 'CSidebarNavDropdown',
	    name: 'Transportation',
	    route: '/identity',
	    icon: 'cil-puzzle',
	    _children: [
	      {
	        _tag: 'CSidebarNavItem',
	        name: 'Transport',
	        to: '/humanresource/userlist',
	      }, {
		        _tag: 'CSidebarNavItem',
		        name: 'Transportation Status',
		        to: '/res/register',
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
		          }
	      
	      ]
	  },  
	  ,
	  {
		    _tag: 'CSidebarNavTitle',
		    _children: ['Analysis']
		  },
		  {
		    _tag: 'CSidebarNavDropdown',
		    name: 'Analysis',
		    route: '/identity',
		    icon: 'cil-puzzle',
		    _children: [
		      {
		        _tag: 'CSidebarNavItem',
		        name: 'Health',
		        to: '/humanresource/userlist',
		      }, {
			        _tag: 'CSidebarNavItem',
			        name: 'Movement',
			        to: '/res/register',
			      }, {
				        _tag: 'CSidebarNavItem',
				        name: 'Feed',
				        to: '/res/register',
				      },
			      {
			          _tag: 'CSidebarNavItem',
			          name: 'Traceability',
			          to: '/movements/gv',
			        },
			        {
			            _tag: 'CSidebarNavItem',
			            name: 'Animal Data',
			            to: '/movements/ggv',
			          }
		      
		      ]
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
				        name: 'Assign Animal For Healthcare',
				        to: '/animal/aa4health',
				  }
			      ,
			      {
				        _tag: 'CSidebarNavItem',
				        name: 'Animal Examination Requests',
				        to: '/animal/aa4health/list',
				  }
			      
			      ,
			      {
			        _tag: 'CSidebarNavItem',
			        name: 'Deregister',
			        to: '/movements/deregister',
			      }
			    ],
			  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Charts',
    to: '/charts',
    icon: 'cil-chart-pie'
  }
  ,
  {
    _tag: 'CSidebarNavItem',
    name: 'ADG',
    to: '/analytics/ADG',
    icon: 'cil-chart-pie'
  }
  
]

export default _owner