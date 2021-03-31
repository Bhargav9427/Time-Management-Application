import { API_BASE_URL, ACCESS_TOKEN } from '../constants';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })

    if (localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = { headers: headers };
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
        .then((response) => {
            let data = response;

            // if the type is json return, interpret it as json
            if (response.headers.get('Content-Type').indexOf('application/json') > -1) {

                data = response.json();
            }
            return data;
        });
};

export function getWorkLogData(userId) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/work/getWorkLog/" + userId,
        method: 'GET'
    });
}

export function updateUser(updateUserRequest) {
    return request({
        url: API_BASE_URL + "/user/updateUserSetting",
        method: 'PUT',
        body: JSON.stringify(updateUserRequest)
    });
}

export function deleteSingleUser(userId) {
    return request({
        url: API_BASE_URL + "/user/delete/" + userId,
        method: 'DELETE'
    });
}

export function getSpecificWorkLogData(workId) {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/work/getspecificWorklog/" + workId,
        method: 'GET'
    });
}

export function addWorklog(worklogRequest) {
    return request({
        url: API_BASE_URL + "/work/addWorkLog",
        method: 'POST',
        body: JSON.stringify(worklogRequest)
    });
}

export function updateWorklog(worklogRequest) {
    return request({
        url: API_BASE_URL + "/work/updateWorkLog",
        method: 'PUT',
        body: JSON.stringify(worklogRequest)
    });
}

export function login(loginRequest) {
    return request({
        url: API_BASE_URL + "/auth/signin",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function signup(signupRequest) {
    return request({
        url: API_BASE_URL + "/auth/signup",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

export function checkUsernameAvailability(username) {
    return request({
        url: API_BASE_URL + "/user/checkUsernameAvailability?username=" + username,
        method: 'GET'
    });
}

export function checkEmailAvailability(email) {
    return request({
        url: API_BASE_URL + "/user/checkEmailAvailability?email=" + email,
        method: 'GET'
    });
}


export function getCurrentUser() {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/user/me",
        method: 'GET'
    });
}

export function getUserProfile(username) {
    return request({
        url: API_BASE_URL + "/users/" + username,
        method: 'GET'
    });
}

export function getAllUsers() {
    return request({
        url: API_BASE_URL + "/user/getUsers",
        method: 'GET'
    });
}


export function deleteWorkLog(workId) {
    return request({
        url: API_BASE_URL + "/work/deleteWorklog/" + workId,
        method: 'DELETE'
    });
}

export function exportExcelData(workLog) {
    return request({
        url: API_BASE_URL + "/work/export",
        method: 'POST',
        body: JSON.stringify(workLog)
    });
}


