import React, {Component} from 'react'
import contract from '../../web3utils/contract/contract'
import {
    Form,
    Input,
    Select,
    Button,
    message,
    Spin
} from 'antd';


export default class Register extends Component {
    state = {
        music_contract: '',
        spinning: false
    };

    onFinish = async (values) => {
        console.log('表单参数: ', values);
        const {address, usertype, password, nickname} = values
        this.setState({
            spinning: true
        })
        const result = await this.state.music_contract.methods.addPeople(nickname, password, address, usertype).send({from: address})
        if (result.blockHash) {
            message.success('注册成功，已返回登录')
            this.props.history.replace('/')
        }

    };

    loadBlockchainData = async () => {
        const {music} = await contract.loadweb3()
        this.setState({
            music_contract: music
        })
    }

    async componentWillMount() {
        await this.loadBlockchainData()
        console.log(this.state)
    }


    render() {

        return (
            <div>
                <Spin tip='注册中' spinning={this.state.spinning}></Spin>
                <Form
                    name="register"
                    onFinish={this.onFinish}
                    scrollToFirstError
                >

                    <Form.Item
                        name="address"
                        label="Address"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your Address!',
                            },
                        ]}
                    >
                        <Input/>
                    </Form.Item>
                    <Form.Item label="用户类型" name="usertype" rules={[
                        {
                            required: true,
                        }
                    ]}>
                        <Select>
                            <Select.Option value="User">User</Select.Option>
                            <Select.Option value="Artist">Artist</Select.Option>
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name="password"
                        label="Password"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your password!',
                            },
                        ]}
                        hasFeedback
                    >
                        <Input.Password/>
                    </Form.Item>

                    <Form.Item
                        name="nickname"
                        label="Nickname"
                        rules={[{required: true, message: 'Please input your nickname!'}]}
                    >
                        <Input/>
                    </Form.Item>

                    <Form.Item>
                        <Button type="primary" htmlType="submit">注册</Button>
                    </Form.Item>
                </Form>
            </div>
        )
    }
}