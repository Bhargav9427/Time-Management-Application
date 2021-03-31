import React, { Component } from 'react'

class NoDataComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {

        }


    }
    componentDidMount() {
        console.log("no data component called");
    }

    render() {
        return (
            <div className="container">

                <div className="row">
                    <div className="col-lg-1"></div>
                    <div className="col-lg-10">
                        <h1 style={{ textAlign: 'center' }}> Hello World</h1>
                    </div>
                    <div className="col-lg-1"></div>
                </div>
            </div>


        )
    }
}

export default NoDataComponent

