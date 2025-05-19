import React, { Component } from 'react'

import {
  CButton,
  CCard,
  CCardBody,
  CFormGroup,
  CCardHeader,
  CCol,
  CRow,
  CInputCheckbox,
  CLabel

} from '@coreui/react'



import Graph from "react-graph-vis";

class GenericGraphView extends Component {

  constructor(props) {
    super(props);
    this.state = { isLayered: true,
    recNodeData :  [
        { id: 1, type: "Animal", dataLink:["Animal 1", "Animla 2"] },
        { id: 2, type: "Insemination"},
        { id: 3, type: "HerdGroup"},
        { id: 4, type: "Observations"},
        { id: 5, type: "FeedPattern"},
        { id: 6, type: "Movements"},
        { id: 7, type: "Drugs"},
        { id: 8, type: "ExaminationReports"},
        { id: 9, type: "Deregister"},        
        { id: 10, type: "Animal", },
        { id: 11, type: "Insemination"},
        { id: 12, type: "HerdGroup"},
        { id: 13, type: "Observations"},
        { id: 14, type: "FeedPattern"},
        { id: 15, type: "Movements"},
        { id: 16, type: "Animal"},
        { id: 17, type: "Insemination"},
        { id: 18, type: "Herd Group"},
        { id: 19, type: "Observations"},
        { id: 20, type: "Feed Pattern"},
        { id: 21, type: "Movements"}] }

  }

  model() {
    let data = [{ type: 'animal', count: 5 }]
  }

  componentWillMount = () => {
    this.n = [];
  }
  
  dislay() {

    let linkClr = "#07B54F";
    let atoalink = "#1A237E";
    let animal = "blue";
    let insemination = "#E6EE9C";
    let herdGroup = "#EC407A";
    let observations = "#42A5F5"
    let feedPattern = "#A5D6A7";
    let movements = "#FCE4EC";
    let drugs = "#FFE0B2";
    let examinationReports = "#BBDEFB";
    let deregister = "#F44336";

    

    

    this.state.recNodeData.map((item) => {
          let s = {};
      switch (item.type) {
        case "Insemination":  s = { shape: "star", id: item.id, label: "Insemination", title: item.dataLink, color: insemination };  this.n.push(s); break;
        case "HerdGroup":  s = { shape: "star", id: item.id, label: "Herd Group", title: "node 5 tootip text", color: herdGroup };  this.n.push(s); break;
        case "Observations": s = { shape: "hexagon", id: item.id, label: "Observations", title: "node 4 tootip text", color: observations }; this.n.push(s); break;
        case "FeedPattern": s = { shape: "diamond", id: item.id, label: "Feed Pattern", title: "node 3 tootip text", color: feedPattern }; this.n.push(s); break;
        case "Movements": s = { shape: "dot", id: item.id, label: "Movements", title: "node 4 tootip text", color: movements }; this.n.push(s); break;
        case "Drugs": s = { shape: "hexagon", id: item.id, label: "Drugs", title: "node 4 tootip text", color: drugs }; this.n.push(s); break;
        case "ExaminationReports": s = { shape: "hexagon", id: item.id, label: "Examination Reports", title: "node 2 tootip text", color: examinationReports };this.n.push(s) ; break;
        case "Deregister": s = { shape: "triangle", id: item.id, label: "Deregister", title: "node 4 tootip text", color: deregister };this.n.push(s) ; break;
        case "Animal": s = { shape: "hexagon", id: item.id, label: "Animal", title: "node 1 tootip text", color: animal }; this.n.push(s); break;
        default:
          return false;
      }
    })
    
    const graph = {
      nodes: this.n,
      edges: [
        { from: 1, to: 10, color: atoalink },
        { from: 2, to: 1, color: linkClr },
        { from: 3, to: 1, color: linkClr },
        { from: 4, to: 1, color: linkClr },
        { from: 5, to: 1, color: linkClr },
        { from: 6, to: 1, color: linkClr },
        { from: 7, to: 1, color: linkClr },
        { from: 8, to: 1, color: linkClr },
        { from: 9, to: 1, color: linkClr },

        { from: 10, to: 16, color: atoalink },
        { from: 11, to: 10, color: linkClr },
        { from: 12, to: 10, color: linkClr },
        { from: 13, to: 10, color: linkClr },
        { from: 14, to: 10, color: linkClr },
        { from: 15, to: 10, color: linkClr },

        { from: 16, to: 22, color: atoalink },

        { from: 17, to: 16, color: linkClr },
        { from: 18, to: 16, color: linkClr },
        { from: 19, to: 16, color: linkClr },
        { from: 20, to: 16, color: linkClr },
        { from: 21, to: 16, color: linkClr },

        { from: 16, to: 22, color: atoalink },
        { from: 23, to: 22, color: linkClr },
        { from: 24, to: 22, color: linkClr },
        { from: 25, to: 22, color: linkClr },
        { from: 26, to: 22, color: linkClr },
        { from: 27, to: 22, color: linkClr }
      ]
    };

    const options = {
      layout: {
        hierarchical: this.state.isLayered
      },
      height: "600px"
    };

  
   



    return (
      <Graph
        graph={graph}
        options={options}
        events={this.events}
        getNetwork={network => {
          //  if you want access to vis.js network api you can set the state in a parent component using this property
         
        }}
      />
    );
  }

    events  = {
      select: function (event) {
        console.log(event.nodes[0])
       
        this.gg();
      }
    };

    gg(){

    }
  handleBackClick = (e) => {
    this.props.history.push('/movements/gv')
  }

  handleCheckChange(e) {
    this.setState({ isLayered: !this.state.isLayered })
  }

  render() {
    return (
      <CRow>
        <CCol xs="12" sm="18">
          <CCard>
            <CCardHeader>
              <CRow>
                <CCol xs="9">
                  Generic View for Animal Transportation
	                		<small className="text-muted"> animal control </small>
                </CCol>
                <CCol xs="2">
                  <CFormGroup variant="custom-checkbox" inline>
                    <CInputCheckbox custom id="viewtype" name="viewtype" value="viewtype" onClick={(e) => this.handleCheckChange(e)} />
                    <CLabel variant="custom-checkbox" htmlFor="viewtype">Hierarical View</CLabel>
                  </CFormGroup>
                </CCol>
                <CCol xs="1">
                  <CFormGroup>
                    <CButton block color="info" onClick={(e) => this.handleBackClick(e)}>Detail</CButton>
                  </CFormGroup>
                </CCol>
              </CRow>
            </CCardHeader>
            <CCardBody>
              {this.dislay()}
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>

    );
  }
}

export default GenericGraphView
