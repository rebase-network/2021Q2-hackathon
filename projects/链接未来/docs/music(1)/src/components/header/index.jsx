import React, {Component} from 'react'
import {Modal} from 'antd'
import LinkButton from '../link-button'
import memoryUtils from '../../utils/memoryUtils'
import storageUtils from '../../utils/storageUtils'
import './index.less'
import {withRouter} from 'react-router-dom';

class Header extends Component {


    logout = () => {
        Modal.confirm({
            content: '确定退出吗?',
            onOk: () => {
                console.log('OK', this)
                // 删除保存的user数据
                storageUtils.removeUser()
                memoryUtils.user = {}

                // 跳转到login
                this.props.history.replace('/login')
            }
        })
    }

    render() {


        const username = memoryUtils.user.username

        return (
            <div className="header">
                <div className="header-top">
                    <span>欢迎,当前账户：<mark> {username}</mark></span>
                    <LinkButton onClick={this.logout}>退出</LinkButton>
                </div>
            </div>
        )
    }
}

export default withRouter(Header);
