import React from 'react'
import { CFooter } from '@coreui/react'

const TheFooter = () => {
  return (
    <CFooter fixed={false}>
      <div>
        <a href="https://ri.se" target="_blank" rel="noopener noreferrer">Traceability</a>
        <span className="ml-1">&copy; 2020 RISE AB.</span>
      </div>
      <div className="mfs-auto">
        <span className="mr-1">Powered by</span>
        <a href="https://ri.se/tds" target="_blank" rel="noopener noreferrer">TDS RISE AB</a>
      </div>
    </CFooter>
  )
}

export default React.memo(TheFooter)
