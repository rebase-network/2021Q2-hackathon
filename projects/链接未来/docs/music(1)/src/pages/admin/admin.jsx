import React, {Component} from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import memoryUtils from '../../utils/memoryUtils'
import {Layout} from 'antd';
import Header from '../../components/header';
import LeftNav from '../../components/left-nav'
import NotFound from '../not-found/not-found'
import Home from '../home/home';
import Musicpage from '../musicpage/musicpage'
import TX from '../tx/tx'

const {Footer, Content, Sider} = Layout;

export default class Admin extends Component {
    render() {

        const user = memoryUtils.user
        // 如果内存没有存储user ==> 当前没有登陆
        if (!user || !user.username) {
            // 自动跳转到登陆(在render()中)
            return <Redirect to='/login'/>
        }

        return (

            <Layout style={{minHeight: '100%'}}>
                <Sider>
                    <LeftNav/>
                </Sider>
                <Layout>
                    <Header>Header </Header>
                    <Content style={{margin: 20, backgroundColor: '#fff'}}>
                        <Switch>
                            <Redirect from='/' exact to='/home'/>
                            <Route path='/home' component={Home}/>
                            <Route path='/musicpage' component={Musicpage}/>
                            <Route path='/tx' component={TX}/>
                            <Route component={NotFound}/>
                        </Switch>
                    </Content>
                    <Footer style={{textAlign: 'center', color: '#cccccc'}}>推荐使用谷歌浏览器，可以获得更佳页面操作体验</Footer>
                </Layout>
            </Layout>


        )
    }
}