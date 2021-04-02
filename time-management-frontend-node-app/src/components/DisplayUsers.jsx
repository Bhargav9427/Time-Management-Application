import React, { Component } from 'react'
import { getAllUsers, deleteSingleUser } from '../utils/APIUtils'
import './WorkLogList.css';
class DisplayUsers extends Component {
    constructor(props) {
        super(props)
        this.state = {
            usersData: []
        }
        this.addUser = this.addUser.bind(this);

    }
    componentDidMount() {
        if (this.props.currentUser == null) return;

        getAllUsers(this.props.currentUser.id)
            .then(response => {
                this.setState({
                    usersData: response.filter(user => user.id != this.props.currentUser.id)
                })
            });
    }
    addUser() {
        this.props.history.push({
            pathname: '/addUser'
        })
    }

    updateUser(user) {
        this.props.history.push({
            pathname: '/settings',
            state: user
        });
    }

    deleteUser(id) {
        deleteSingleUser(id).then(res => {
            getAllUsers(this.props.currentUser.id)
                .then(response => {
                    this.setState({
                        usersData: response.filter(user => user.id != this.props.currentUser.id)
                    })
                })
        });
    }

    render() {
        return (
            <div>
                <h2 className="text-center">Users List</h2>
                <div className="row" style={{ marginTop: '100px' }}>
                    <button className="btn btn-primary" onClick={this.addUser}>Add User</button>
                </div>
                <div className="row">
                    <table className="table table-stripped table-bordered">
                        <thead>
                            <tr>
                                <th>Full Name</th>
                                <th>UserName</th>
                                <th>EmailId</th>
                                <th>Preferred Working Hours Per Day</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.usersData.map(
                                    user =>

                                        <tr key={user.id}>
                                            <td>{user.name}</td>
                                            <td>{user.username}</td>
                                            <td>{user.email}</td>
                                            <td>{user.preferredWorkingHoursPerDay}</td>

                                            <td>
                                                <button className="btn btn-info" onClick={() => this.updateUser(user)}>Update</button>
                                                <button style={{ marginLeft: '20px' }} className="btn btn-danger" onClick={() => this.deleteUser(user.id)}>Delete</button>
                                            </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default DisplayUsers



