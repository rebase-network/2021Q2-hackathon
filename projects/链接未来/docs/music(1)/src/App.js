import React, {Component} from 'react'
import {BrowserRouter, Route, Switch} from 'react-router-dom'

import Login from './pages/login/login'
import Admin from './pages/admin/admin'
import Register from './pages/register/register'

export default class App extends Component {


    render() {
        return (
            < BrowserRouter >
            < Switch > {/*只匹配其中一个*/}
            < Route
        path = '/login'
        component = {Login} > < /Route>
            < Route
        path = '/register'
        component = {Register} > < /Route>
            < Route
        path = '/'
        component = {Admin} > < /Route>
            < /Switch>
            < /BrowserRouter>
    )
    }
}