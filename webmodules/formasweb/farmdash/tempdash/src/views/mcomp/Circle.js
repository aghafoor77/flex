import React, { Component } from 'react'

class Circle extends Component {

	render() {
		var circleStyle = {
			padding: 0,
			margin: 0,
			display: "inline-block",
			// position:'absolute',
			backgroundColor: this.props.bgColor,
			borderRadius: "50%",
			width: parseInt(this.props.width),
			height: parseInt(this.props.height),
			left: 0,
			top: 0
		};
		return (
			<div style={circleStyle}>
			</div>
		);
	}
}

export default Circle