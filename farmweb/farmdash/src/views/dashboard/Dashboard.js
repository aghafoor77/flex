import React, { Component } from 'react'
import {
  CBadge,
  CButton,
  CButtonGroup,
  CCard,
  CCardBody,
  CCardFooter,
  CCardHeader,
  CCol,
  CProgress,
  CRow
} from '@coreui/react'
import CIcon from '@coreui/icons-react'

import MainChartExample from '../charts/MainChartExample.js'
import { withRouter } from 'react-router-dom'
import WidgetsDropdown from '../widgets/WidgetsDropdown.js'

class Dashboard extends Component {

  constructor(props) {
    super(props);
    let bb = window.sessionStorage.getItem("vblock") === null ? this.props.history.push('/login') : "";
    this.state = {

    };
  }

  render() {
    const links = [
      { no: 1, slaughterhouse: 'Slaughter house', slucity: 'Stockholm', sluaddr: 'Stallgatan 11A', noa: 10, noabull: 2, noacow: 4, noaothers: 2, transporter: 'kohistan', transcity: 'Stockholm', transaddr: 'Stallgatan 11A', orderno: '121212', orderdetail: 'order detail', date: '2021-01-27' },
      { no: 1, slaughterhouse: 'Slaughter house', slucity: 'Stockholm', sluaddr: 'Stallgatan 11A', noa: 10, noabull: 2, noacow: 4, noaothers: 2, transporter: 'kohistan', transcity: 'Stockholm', transaddr: 'Stallgatan 11A', orderno: '121212', orderdetail: 'order detail', date: '2021-01-27' },
      { no: 1, slaughterhouse: 'Slaughter house', slucity: 'Stockholm', sluaddr: 'Stallgatan 11A', noa: 10, noabull: 2, noacow: 4, noaothers: 2, transporter: 'kohistan', transcity: 'Stockholm', transaddr: 'Stallgatan 11A', orderno: '121212', orderdetail: 'order detail', date: '2021-01-27' },
      { no: 1, slaughterhouse: 'Slaughter house', slucity: 'Stockholm', sluaddr: 'Stallgatan 11A', noa: 10, noabull: 2, noacow: 4, noaothers: 2, transporter: 'kohistan', transcity: 'Stockholm', transaddr: 'Stallgatan 11A', orderno: '121212', orderdetail: 'order detail', date: '2021-01-27' }
    ];
    return (
      <>
        <WidgetsDropdown />
        <CCard>
          <CCardBody>
            <CRow>
              <CCol sm="5">
                <h4 id="traffic" className="card-title mb-0">Animals</h4>
                <div className="small text-muted">Annualy growth of animals</div>
              </CCol>
              <CCol sm="7" className="d-none d-md-block">
                <CButton color="primary" className="float-right">
                  <CIcon name="cil-cloud-download" />
                </CButton>
                <CButtonGroup className="float-right mr-3">
                  {
                    ['Day', 'Month', 'Year'].map(value => (
                      <CButton
                        color="outline-secondary"
                        key={value}
                        className="mx-0"
                        active={value === 'Month'}
                      >
                        {value}
                      </CButton>
                    ))
                  }
                </CButtonGroup>
              </CCol>
            </CRow>
            <MainChartExample style={{ height: '300px', marginTop: '40px' }} />
          </CCardBody>
          <CCardFooter>
            <CRow className="text-center">
              <CCol md sm="12" className="mb-sm-2 mb-0">
                <div className="text-muted">Incoming</div>
                <strong>29 Cattles</strong>
                <CProgress
                  className="progress-xs mt-2"
                  precision={1}
                  color="success"
                  value={50}
                />
              </CCol>
              <CCol md sm="12" className="mb-sm-2 mb-0 d-md-down-none">
                <div className="text-muted">Sold</div>
                <strong>24 Cattles</strong>
                <CProgress
                  className="progress-xs mt-2"
                  precision={1}
                  color="info"
                  value={40}
                />
              </CCol>
              <CCol md sm="12" className="mb-sm-2 mb-0">
                <div className="text-muted">Deaths</div>
                <strong>5 Cattle</strong>
                <CProgress
                  className="progress-xs mt-2"
                  precision={1}
                  color="warning"
                  value={10}
                />
              </CCol>
              <CCol md sm="12" className="mb-sm-2 mb-0">
                <div className="text-muted">Output</div>
                <strong>87%</strong>
                <CProgress
                  className="progress-xs mt-2"
                  precision={1}
                  color="danger"
                  value={87}
                />
              </CCol>

            </CRow>

          </CCardFooter>
        </CCard>
        <CRow>
          <CCol>
            <CCard>
              <CCardHeader>
                Orders {' & '} Sales
            </CCardHeader>
              <CCardBody>

                <br />

                <table className="table table-hover table-outline mb-0 d-none d-sm-table">
                  <thead className="thead-light">
                    <tr>
                      <th className="text-center"><CIcon name="cil-people" /></th>
                      <th>Slaughterhouse</th>
                      <th className="text-left">Tansporter</th>
                      <th className="text-center">No of Animals</th>
                      <th className="text-center">Order Detail</th>
                      <th>Date</th>
                    </tr>
                  </thead>
                  <tbody>
                    {
                      links.map(link =>
                        <tr>
                          <td className="text-center">
                            <div className="c-avatar">
                              <img src={'avatars/6.jpg'} alt="not found" className="c-avatar-img" />
                              <span className="c-avatar-status bg-danger"></span>
                            </div>
                          </td>
                          <td>
                            <div>{link.slaughterhouse}</div>
                            <div className="small text-muted">
                              <span>{link.sluaddr}, {link.slucity}</span>
                            </div>
                          </td>
                          <td className="text-left">
                            <div>{link.transporter}</div>
                            <div className="small text-muted">
                              <span>{link.transaddr}, {link.transcity}</span>
                            </div>
                          </td>
                          <td className="text-center">
                            <div >{link.noa}</div>
                            <div className="small text-muted">
                              <span>bull: {link.noabull} | cow:{link.noacow} | others:{link.noaothers} </span>
                            </div>
                          </td>
                          <td className="text-center">
                            <div>No. {link.orderno}</div>
                            <div className="small text-muted">
                              <span>{link.orderdetail}</span>
                            </div>
                          </td>
                          <td>
                            <div>{link.date}</div>
                          </td>
                        </tr>
                      )
                    }
                  </tbody>
                </table>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </>
    )
  }
}

export default withRouter(Dashboard)

