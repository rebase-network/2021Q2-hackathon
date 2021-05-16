import React, {Component} from 'react'
import {Link, withRouter} from 'react-router-dom'
import {Menu, Button} from 'antd';
import {
    MenuUnfoldOutlined,
    MenuFoldOutlined,
    PieChartOutlined,
    DesktopOutlined,
    MailOutlined,
} from '@ant-design/icons';

import './index.less'

const {SubMenu} = Menu;

/*
左侧导航的组件
 */
class LeftNav extends Component {

    state = {
        collapsed: false,
    };

    toggleCollapsed = () => {
        this.setState({
            collapsed: !this.state.collapsed,
        });
    };

    render() {

        return (
            <div style={{width: 200}}>
                <Button type="primary" onClick={this.toggleCollapsed} style={{marginBottom: 16}}>
                    {React.createElement(this.state.collapsed ? MenuUnfoldOutlined : MenuFoldOutlined)}
                </Button>
                <Menu
                    defaultSelectedKeys={['1']}
                    defaultOpenKeys={['sub1']}
                    mode="inline"
                    theme="dark"
                    inlineCollapsed={this.state.collapsed}
                >
                    <Menu.Item key="home" icon={<PieChartOutlined/>}>
                        <Link to='/home'>主页</Link>
                    </Menu.Item>
                    <Menu.Item key="musicpage" icon={<DesktopOutlined/>}>
                        <Link to='/musicpage'>音乐馆</Link>
                    </Menu.Item>
                    <SubMenu key="sub1" icon={<MailOutlined/>} title="其他服务">
                        <Menu.Item key="tx">
                            <Link to='/tx'>转移所有权</Link>
                        </Menu.Item>
                        <Menu.Item key="7">下载音乐</Menu.Item>
                    </SubMenu>
                </Menu>
            </div>
        );
    }
}

export default withRouter(LeftNav)