import React, { Component } from 'react'
import { getWorkLogData, deleteWorkLog, exportExcelData } from '../utils/APIUtils'
import './WorkLogList.css';
import { DatePicker } from 'antd'
const dateFormat = 'YYYY-MM-DD';
const { RangePicker } = DatePicker;
class WorkLogList extends Component {
    constructor(props) {
        super(props)
        this.state = {
            workLogData: [],
            rangeStart: '',
            rangeEnd: ''
        }
        this.addWorkLog = this.addWorkLog.bind(this);
        this.onChangeDates = this.onChangeDates.bind(this);
        this.filterRecords = this.filterRecords.bind(this);
        this.resetFilter = this.resetFilter.bind(this);
        this.generateExcel = this.generateExcel.bind(this);
    }
    componentDidMount() {
        if (this.props.currentUser == null) return;
        let promise;
        promise = getWorkLogData(this.props.currentUser.id);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {

                this.setState({
                    workLogData: response,
                    isLoading: false
                })
            }).catch(error => {
                this.setState({
                    isLoading: false
                })
            });

    }

    addWorkLog() {
        this.props.history.push(`/add-update-worklog/-1`);
    }

    updateWorkLog(workId) {
        this.props.history.push(`/add-update-worklog/${workId}`);
    }

    deleteWork(workId) {
        deleteWorkLog(workId).then(res => {
            getWorkLogData(this.props.currentUser.id).then(response => {
                this.setState({
                    workLogData: response,
                    isLoading: false
                })
            }).catch(error => {
                this.setState({
                    isLoading: false
                })
            });
        });
    }

    onChangeDates(dates, dateStrings) {
        //console.log('From: ', dates[0], ', to: ', dates[1]);

        this.setState({
            rangeStart: dateStrings[0],
            rangeEnd: dateStrings[1]
        });


        console.log('From in log: ' + this.state.rangeStart + ', to: ' + this.state.rangeEnd);
    }

    filterRecords() {
        if (this.state.rangeStart === '' || this.state.rangeEnd === '') {

            return;
        }



        this.setState({
            workLogData: this.state.workLogData
                .filter(eachLog => new Date(eachLog.workingDate) >= new Date(this.state.rangeStart)
                    && new Date(eachLog.workingDate) <= new Date(this.state.rangeEnd))
        });
    }

    resetFilter() {
        getWorkLogData(this.props.currentUser.id).then(response => {
            this.setState({
                workLogData: response,
                isLoading: false
            })
        });
    }

    generateExcel() {
        exportExcelData(this.state.workLogData).then((response) => response.blob())
            .then((blob) => {
                const url = window.URL.createObjectURL(new Blob([blob]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', 'filteredData.xlsx');
                document.body.appendChild(link);
                link.click();
                link.parentNode.removeChild(link);
            });
    }

    render() {
        return (
            <div>
                <div className="row" style={{ marginTop: '100px' }}>
                    <button className="btn btn-primary" onClick={this.addWorkLog}>Add WorkLog</button>
                    <button className="btn btn-primary" onClick={this.resetFilter} style={{ marginLeft: '360px', marginRight: '30px' }}>Reset Filter</button>
                    
                    <RangePicker
                        format={dateFormat}
                        onChange={this.onChangeDates}
                    //defaultValue={[moment(this.state.rangeStart, dateFormat), moment(this.state.rangeEnd, dateFormat)]}
                    />
                    <button className="btn btn-primary" onClick={this.filterRecords} style={{ marginLeft: '40px' }}>Filter Records</button>

                </div>
                <div className="row">
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th>User Name</th>
                                <th>Work Notes</th>
                                <th>Worked Hours</th>
                                <th>Working Date</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {

                                this.state.workLogData.map(
                                    workLog =>

                                        <tr key={workLog.id} className={`${workLog.red ? "displayred" : "displaygreen"}`}>
                                            <td>{workLog.userName}</td>
                                            <td>{workLog.notes}</td>
                                            <td>{workLog.workingTime}</td>
                                            <td>{workLog.workingDate}</td>
                                            <td>
                                                <button className="btn btn-info" onClick={() => this.updateWorkLog(workLog.id)}>Update</button>
                                                <button style={{ marginLeft: '20px' }} className="btn btn-danger" onClick={() => this.deleteWork(workLog.id)}>Delete</button>
                                            </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
                <button className="btn btn-primary" onClick={this.generateExcel}>Export Data</button>

            </div>
        )
    }
}

export default WorkLogList



