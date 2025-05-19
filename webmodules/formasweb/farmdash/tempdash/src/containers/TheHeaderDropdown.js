import React, { Component } from 'react'
import {
  CBadge,
  CDropdown,
  CDropdownItem,
  CDropdownMenu,
  CDropdownToggle,
  CImg
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { withRouter } from 'react-router-dom'


class TheHeaderDropdown extends Component {

  constructor(props) {
    super(props);
    this.state = {
      alert: {
        title: "Here ", message: "There", display: false
      }
    };
  }

  profile() {
	    this.props.history.push('/dt/profile');
	  }
  
  wallet() {
    this.props.history.push('/dt/wallet');
  }

  logout() {
    window.sessionStorage.removeItem("vblock");
    this.props.history.push('/login');
  }

  servicesStatus() {
    this.props.history.push('/dt/status');
  }

  render() {
    return (
      <CDropdown
        inNav
        className="c-header-nav-items mx-2"
        direction="down"
      >
        <CDropdownToggle className="c-header-nav-link" caret={false}>
          <div className="c-avatar">
            <CImg
              src={'avatars/6.jpg'}
              className="c-avatar-img"
              alt="admin@bootstrapmaster.com"
            />
          </div>
        </CDropdownToggle>
        <CDropdownMenu className="pt-0" placement="bottom-end">
          <CDropdownItem
            header
            tag="div"
            color="light"
            className="text-center"
          >
            <strong>Settings</strong>
          </CDropdownItem>
          <CDropdownItem onClick={() => { this.profile() }}>
            <CIcon name="cil-user" className="mfe-2" />Profile
        </CDropdownItem>
          <CDropdownItem>
            <CIcon name="cil-settings" className="mfe-2" />
          Settings
        </CDropdownItem>
          <CDropdownItem>
            <CIcon name="cil-envelope-open" className="mfe-2" />
          Messages

        </CDropdownItem>
          <CDropdownItem onClick={() => { this.wallet() }}>
            <CIcon name="cil-file" className="mfe-2" />
          Wallet
        </CDropdownItem>
        <CDropdownItem onClick={() => { this.servicesStatus() }}>
            <CIcon name="cil-file" className="mfe-2" />
          Services Status
        </CDropdownItem>
          <CDropdownItem divider />
          <CDropdownItem onClick={() => { this.logout() }}>
            <CIcon name="cil-lock-locked" className="mfe-2" />
          Logout
        </CDropdownItem>
        </CDropdownMenu>
      </CDropdown>
    )
  }
}

export default withRouter(TheHeaderDropdown)

