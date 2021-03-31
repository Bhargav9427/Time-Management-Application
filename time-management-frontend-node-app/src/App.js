import './App.css';
import { BrowserRouter as Router, Route, Switch, withRouter } from 'react-router-dom';
import { Layout, notification } from 'antd';
import AppHeader from './components/AppHeader';
import React, { Component } from 'react';
import WorkLogList from './components/WorkLogList';
import DisplayUsers from './components/DisplayUsers';
import Signup from './components/Signup';
import Login from './components/Login';
import { getCurrentUser } from './utils/APIUtils';
import { ACCESS_TOKEN } from './constants';
import LoadingIndicator from './helper/LoadingIndicator';
import NoDataComponent from './components/NoDataComponent';
import AddUpdateWorklog from './components/AddUpdateWorklog';
import SettingsComponent from './components/SettingsComponent';


const { Content } = Layout;
class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false
    }
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);
    this.handleUpdate = this.handleUpdate.bind(this);
    this.handleSettingClick = this.handleSettingClick.bind(this);

    notification.config({
      placement: 'topRight',
      top: 70,
      duration: 3,
    });
  }

  handleSettingClick() {
    this.props.history.push("/settings");
  }

  loadCurrentUser() {
    this.setState({
      isLoading: true
    });
    getCurrentUser()
      .then(response => {
        this.setState({
          currentUser: response,
          isAuthenticated: true,
          isLoading: false
        });
        console.log("ncajcdjnakjcnakjdc : " + this.state.currentUser.username);
      }).catch(error => {
        console.log(error);
        this.setState({
          isLoading: false
        });
        console.log("asfasdfasdfasdfasdfasdfasdfasdfasdfasdfassfd");
      });
  }

  componentDidMount() {
    //console.log("component did mount called");
    this.loadCurrentUser();
    console.log("user in : " + this.state.currentUser);
  }

  handleLogin() {
    notification.success({
      message: 'Time management App',
      description: "You're successfully logged in.",
    });
    this.loadCurrentUser();
    this.props.history.push("/displayWorkLog");
  }

  handleLogout(redirectTo = "/", notificationType = "success", description = "You're successfully logged out.") {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false
    });

    this.props.history.push(redirectTo);

    notification[notificationType]({
      message: 'Time Management App',
      description: description,
    });
  }

  handleUpdate() {
    
    this.loadCurrentUser();
    this.props.history.push("/displayWorkLog");
  }

  render() {
    if (this.state.isLoading) {
      return <LoadingIndicator />
    }
    return (
      <Layout className="app-container">
        <AppHeader isAuthenticated={this.state.isAuthenticated}
          currentUser={this.state.currentUser}
          onLogout={this.handleLogout}
          onSettingsClick={this.handleSettingClick} />
        <Content className="app-content">
          <div className="container">
            <Switch>
              {
              !this.state.isAuthenticated &&
              <Route exact path="/" component={NoDataComponent}></Route>
              }
              {
                console.log("just passing before worklog" + JSON.stringify(this.state.currentUser))
              }
              {
                this.state.isAuthenticated &&
                <Route exact path="/displayWorkLog" render={(props) => <WorkLogList isAuthenticated={this.state.isAuthenticated}
                  currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}>
                </Route>
              }
              <Route path="/login"
                render={(props) => <Login onLogin={this.handleLogin} {...props} />}></Route>
              <Route path="/signup" component={Signup}></Route>
              <Route path="/add-update-worklog/:id" render={(props) => <AddUpdateWorklog isAuthenticated={this.state.isAuthenticated}
                  currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props}/>}>

              </Route>
              <Route path="/settings" render={(props) => <SettingsComponent isAuthenticated={this.state.isAuthenticated}
                  user={this.state.currentUser} handleUpdate={this.handleUpdate} {...props}/>}>
              </Route>
              <Route path="/displayUsers" render={(props) => <DisplayUsers isAuthenticated={this.state.isAuthenticated}
                  currentUser={this.state.currentUser} {...props}/>}>
              </Route>
            </Switch>
          </div>
        </Content>
      </Layout>
    );
  }
}

export default withRouter(App);
