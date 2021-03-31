import React, { Component } from 'react'
import moment from 'moment'

import {
    Form,
    Input,
    DatePicker,
    InputNumber,
    Button,
    notification
} from 'antd';
import { addWorklog, updateWorklog, getSpecificWorkLogData} from '../utils/APIUtils'
const FormItem = Form.Item;
class AddUpdateWorklog extends Component {
    constructor(props) {
        super(props)
        this.state = {
            workId: this.props.match.params.id,
            workNotes: '',
            workedHours: 0,
            workedDate: null,
            workUserId:null
        }
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleWorkedHoursChange = this.handleWorkedHoursChange.bind(this);
        this.handleDateChange = this.handleDateChange.bind(this);

        this.handleSubmit = this.handleSubmit.bind(this);

        this.cancel = this.cancel.bind(this);
    }


    //get the data for a particular id
    componentDidMount() {
        let promise;
        if (this.state.workId != -1) {
            promise = getSpecificWorkLogData(this.state.workId)
            promise
                .then(response => {
                    this.setState({
                        workId: response.id,
                        workNotes: response.notes,
                        workedHours: response.workingTime,
                        workedDate: moment(response.workingDate),
                        workUserId: response.userId
                    })
                }).catch(error => {
                    this.setState({
                        workId: this.props.match.params.id,
                        workNotes: '',
                        workedHours: 0,
                        workedDate: null,
                        workUserId:null
                    })
                });
        }
    }

    cancel() {
        this.props.history.push('/displayWorkLog');
    }


    getTitle() {
        if (this.state.workId == -1) {
            return <h3 className="text-center">Add Worklog</h3>
        }
        else {
            return <h3 className="text-center">Update Worklog</h3>
        }
    }

    handleInputChange(event) {
        console.log(event.target.name + " " + event.target.value);
        this.setState({
            workNotes: event.target.value
        })
    }

    handleWorkedHoursChange(value) {

        console.log("hours is  : " + value);
        this.setState({
            workedHours: value
        })
    }

    handleDateChange(date, dateString, id) {
        console.log("date is  : " + dateString);
        this.setState({
            workedDate: date,
        })
    }

    handleSubmit(event) {
        event.preventDefault();

        const workLogAddRequest = {
            userId: this.props.currentUser.id,
            notes: this.state.workNotes,
            workingDate: this.state.workedDate,
            workingHours: this.state.workedHours
        };

        const workLogUpdateRequest = {
            workId: this.state.workId,
            notes: this.state.workNotes,
            workingDate: this.state.workedDate,
            workingHours: this.state.workedHours,
            userId: this.state.workUserId
        }

        console.log(JSON.stringify(workLogAddRequest));

        if(this.state.workId == -1) {
            addWorklog(workLogAddRequest)
            .then(response => {
                notification.success({
                    message: 'Time Management App',
                    description: "Work log has been added successfully",
                });
                this.props.history.push("/displayWorkLog");
            }).catch(error => {
                notification.error({
                    message: 'Time Management App',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });
            });
        }
        else {
            updateWorklog(workLogUpdateRequest)
            .then(response => {
                notification.success({
                    message: 'Time Management App',
                    description: "Work log has been updated successfully",
                });
                this.props.history.push("/displayWorkLog");
            }).catch(error => {
                notification.error({
                    message: 'Time Management App',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });
            });
        }

    }

    render() {
        return (
            <div className="container" style={{ marginTop: '100px' }}>
                {
                    this.getTitle()
                }
                <div>
                    <Form onSubmit={this.handleSubmit}>
                        <FormItem
                            label="Work Notes">
                            <Input
                                size="default"
                                name="workNotes"
                                placeholder="Add some Work Notes"
                                value = {this.state.workNotes}
                                onChange={(event) => this.handleInputChange(event)} />
                        </FormItem>
                        <FormItem
                            label="Worked Hours">
                            <InputNumber
                                size="default"
                                name="workedHours"
                                value = {this.state.workedHours}
                                onChange={this.handleWorkedHoursChange} />
                        </FormItem>
                        <FormItem
                            label="Worked Date">
                            <DatePicker name="workedDate"
                                value = {this.state.workedDate}
                                onChange={(date, dateString) => this.handleDateChange(date, dateString, 1)}>
                            </DatePicker>
                        </FormItem>

                        <FormItem>
                            <Button type="primary"
                                htmlType="submit"
                                size="large" >Submit</Button>

                            <Button type="danger"
                                size="large" onClick={this.cancel} style={{ marginLeft: "10px" }}>Cancel</Button>
                        </FormItem>
                    </Form>
                </div>
            </div>

        )
    }
}

export default AddUpdateWorklog

