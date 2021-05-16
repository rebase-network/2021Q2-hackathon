import React, {Component} from 'react'
import {Link, Redirect} from 'react-router-dom'
import {Form, Input, Button, Checkbox, message} from 'antd';
import {UserOutlined, LockOutlined} from '@ant-design/icons';
import contract from '../../web3utils/contract/contract'
import memoryUtils from '../../utils/memoryUtils'
import storageUtils from '../../utils/storageUtils'
import './login.less'

export default class Login extends Component {

    state = {
        music_contract: ''
    }

    loadBlockchainData = async () => {
        const {music} = await contract.loadweb3()
        this.setState({
            music_contract: music
        })
    }

    onFinish = async (values) => {
        console.log('表单参数: ', values);
        const {username, password} = values
        const isvertify = await this.state.music_contract.methods.vertifyAccounts(username, password).call({from: username})
        console.log('isvertify', isvertify)
        if (isvertify) {
            const user = {
                username: username,
                password: password
            }
            memoryUtils.user = user
            storageUtils.saveUser(user)
            this.props.history.replace('/')
        } else {
            message.error('登录错误')
        }
    };

    async componentWillMount() {
        await this.loadBlockchainData()
        console.log(this.state)
    }

    render() {

        const user = memoryUtils.user
        if (user && user.username) {
            return <Redirect to='/'/>
        }


        return (
            <div className="login">
                <header className="login-header">
                    <h1>React项目: 音乐Block</h1>
                </header>
                <section className="login-content">
                    <h2>用户登陆</h2>
                    <Form
                        name="normal_login"
                        className="login-form"
                        initialValues={{
                            remember: true,
                        }}
                        onFinish={this.onFinish}
                        className="login-form"
                    >
                        <Form.Item
                            name="username"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your Username!',
                                },
                            ]}
                        >
                            <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="Username"/>
                        </Form.Item>
                        <Form.Item
                            name="password"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your Password!',
                                },
                            ]}
                        >
                            <Input
                                prefix={<LockOutlined className="site-form-item-icon"/>}
                                type="password"
                                placeholder="Password"
                            />
                        </Form.Item>

                        <Form.Item name="remember" valuePropName="checked" noStyle>
                            <Checkbox>Remember me</Checkbox>
                        </Form.Item>

                        <Form.Item>
                            <Button type="primary" htmlType="submit" className="login-form-button">
                                Log in</Button>
                            <Button type="link" className="login-form-button">
                                <Link to='/register'>注册</Link>
                            </Button>
                        </Form.Item>

                    </Form>
                </section>
            </div>
        )
    }
}