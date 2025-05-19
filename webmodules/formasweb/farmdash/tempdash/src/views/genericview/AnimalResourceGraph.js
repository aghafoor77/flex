import React, { Component } from 'react'

import {
  CButton,
  CCard,
  CCardBody,
  CCardFooter,
  CCardHeader,
  CCol,
  CLabel,
  CSelect,
  CRow,
  CAlert

} from '@coreui/react'

import endpoints from "../config/Configuration";

import Graph from "react-graph-vis";

class GenericView extends Component {

  constructor(props) {
    super(props);
    this.state = {
      ardata: [],
      animalID: '',
      alert: {
        title: "Here ", message: "There", display: true
      }
    };



  }



  setAlert(title, message) {
    this.setState({ alert: { title: title, message: message, display: true } });
  }

  hideAlertClick(e) {
    this.setState({ alert: { display: false } });
  }

  displayAlert() {
    return (this.state.alert.display === true ? (<CCardHeader><CAlert color="danger">
      <CRow>
        <CCol xs="11">
          {this.state.alert.title}: {this.state.alert.message}
        </CCol>
        <CCol xs="1">
          <CButton block onClick={(e) => this.hideAlertClick(e)}>X</CButton>
        </CCol>
      </CRow></CAlert></CCardHeader>) : null);
  }

  handleChange(event) {
    switch (event.target.id) {
      case 'animalID':
        this.setState({ animalID: event.target.value });
        break;
      default: return '';
    }
  }

  handleBackClick(event) {

  }

  fetchRegisteredAnimals() {
    let url = window.sessionStorage.getItem(endpoints.AR) + '/application/v1/registeranimal'
    fetch(url, {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      }
    })
      .then(res => {
        if (res.status === 200) {
          res.json()
            .then(data => {
              if (data.length === 0) {
                this.setAlert("Animal not found", "No animal identity found in the system !");
              } else {
                this.setState({ ardata: data, animalID: data[0].animalID });
              }
              return this.state.ardata;
            })
            .catch(error => {
              this.setAlert("General error", error.message);
              return false
            });
        } else {
          return this.displayError(res)
        }
      })
      .catch(error => {
        this.setAlert("Connection Error-", error.message + " (Animal data fetching)")
        return false
      });
  }

  animalRegistration(e) {
    alert(this.state.animalID);

  }
  preAndPostPregnancyPhase(e) {
    alert("registratin ");

  }
  animalPregnancyExamination(e) {
    alert("registratin ");

  }
  animalFeeding(e) {
    alert("registratin ");

  }
  animalHealthExamination(e) {
    alert("registratin ");

  }

  animalHealthObservation(e) {
    alert("registratin ");

  }

  animalTreatments(e) {
    alert("registratin ");

  }
  reportingSlaughterhouse(e) {
    alert("registratin ");

  }
  temporaryMovement(e) {
    alert("registratin ");

  }

  dislay() {
    let linkClr = "#FFEE58";
    let atoalink = "#1A237E";
    let animal = "#E0E0E0";
    let insemination = "#E6EE9C";
    let herdGroup = "#EC407A";
    let observations = "#42A5F5"
    let feedPattern = "#A5D6A7";
    let movements = "#FCE4EC";
    let drugs = "#FFE0B2";
    let examinationReports = "#BBDEFB";
    let deregister = "#F44336";


    const graph = {
      nodes: [
        { shape:"eclipse", id: 1, label: "Animal", title: "node 1 tootip text", color: animal },
        { shape:"star", id: 2, label: "Insemination", title: "node 5 tootip text", color: insemination },
        { shape:"star",  id: 3, label: "Herd Group", title: "node 5 tootip text", color: herdGroup },
        { shape:"diamond", id: 4, label: "Observations", title: "node 4 tootip text", color: observations },
        { shape:"triangleDown", id: 5, label: "Feed Pattern", title: "node 3 tootip text", color: feedPattern },
        { shape:"dot", id: 6, label: "Movements", title: "node 4 tootip text", color: movements },
        { shape:"square", id: 7, label: "Drugs", title: "node 4 tootip text", color: drugs },
        { shape:"hexagon", id: 8, label: "Examination Reports", title: "node 2 tootip text", color: examinationReports },
        { shape:"triangle", id: 9, label: "Deregister", title: "node 4 tootip text", color: deregister },
        
        { shape:"eclipse", id: 10, label: "Animal", title: "node 1 tootip text", color: animal },
        { shape:"star", id: 11, label: "Insemination", title: "node 5 tootip text", color: insemination },
        { shape:"triangle", id: 12, label: "Herd Group", title: "node 5 tootip text", color: herdGroup },
        { shape:"diamond", id: 13, label: "Observations", title: "node 4 tootip text", color: observations },
        { shape:"triangleDown", id: 14, label: "Feed Pattern", title: "node 3 tootip text", color: feedPattern },
        { shape:"dot", id: 15, label: "Movements", title: "node 4 tootip text", color: movements },

        { shape:"eclipse", id: 16, label: "Animal", title: "node 1 tootip text", color: animal },
        { shape:"star", id: 17, label: "Insemination", title: "node 5 tootip text", color: insemination },
        { shape:"triangle", id: 18, label: "Herd Group", title: "node 5 tootip text", color: herdGroup },
        { shape:"diamond", id: 19, label: "Observations", title: "node 4 tootip text", color: observations },
        { shape:"triangleDown", id: 20, label: "Feed Pattern", title: "node 3 tootip text", color: feedPattern },
        { shape:"dot", id: 21, label: "Movements", title: "node 4 tootip text", color: movements },
      ],
      edges: [
        { from: 2, to: 1, color: linkClr },
        { from: 3, to: 1, color: linkClr },
        { from: 4, to: 1, color: linkClr },
        { from: 5, to: 1, color: linkClr },
        { from: 6, to: 1, color: linkClr },
        { from: 7, to: 1, color: linkClr },
        { from: 8, to: 1, color: linkClr },
        { from: 9, to: 1, color: linkClr },
        { from: 1, to: 10, color: atoalink },
        { from: 11, to: 10, color: linkClr },
        { from: 12, to: 10, color: linkClr },
        { from: 13, to: 10, color: linkClr },
        { from: 14, to: 10, color: linkClr },
        { from: 15, to: 10, color: linkClr },
        { from: 10, to: 16, color: atoalink },
        { from: 17, to: 16, color: linkClr },
        { from: 18, to: 16, color: linkClr },
        { from: 19, to: 16, color: linkClr },
        { from: 20, to: 16, color: linkClr },
        { from: 21, to: 16, color: linkClr },
      ]
    };

    const options = {
      layout: {
        hierarchical: true
      },
      edges: {
        color: "#000000"
      },
      height: "500px"
    };
    const events = {
      select: function (event) {
         console.log(event)
        var { nodes, edges } = event;
      }
    };
    return (
      <Graph
        graph={graph}
        options={options}
        events={events}
        getNetwork={network => {
          //  if you want access to vis.js network api you can set the state in a parent component using this property
        }}
      />
    );
  }

  render() {
    return (
      <CRow>
        <CCol xs="12" sm="12">
          {this.displayAlert(this.state.alert.title, this.state.alert.message)}
        </CCol>
        <CCol xs="12" sm="12">
          <CCard>

            <CCardHeader>
              <CRow>
                <CCol xs="6">
                  Generic View for Animal Transportation
	                		<small className="text-muted"> animal control </small>
                </CCol>
                <CCol xs="6">

                </CCol>
              </CRow>
            </CCardHeader>
            <CCardBody>
              {this.dislay()}
            </CCardBody>
            <CCardFooter>
            </CCardFooter>


          </CCard>
        </CCol>
      </CRow>

    );
  }
}

export default GenericView
