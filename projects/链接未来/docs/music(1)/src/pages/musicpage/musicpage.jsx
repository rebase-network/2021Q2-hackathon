import React, {Component} from 'react'
import contract from '../../web3utils/contract/contract';
import memoryUtils from '../../utils/memoryUtils'
import formateDate from '../../utils/dateUtils'
import {Drawer, Form, Button, Input, Select, Table, InputNumber, Upload, message, Spin} from 'antd';
import {PlusOutlined, UploadOutlined, UserSwitchOutlined, DownloadOutlined} from '@ant-design/icons';
import put from '../../utils/Oss';

const {Option} = Select;

export default class Musicpage extends Component {

    state = {
        visible: false,
        music_contract: '',
        data: [],
        total: '',
        spinning: false
    }

    showDrawer = () => {
        this.setState({
            visible: true,
        });
    };

    onClose = () => {
        this.setState({
            visible: false,
        });
    };

    onFinish = async (values) => {
        this.setState({
            spinning: true
        })
        const user = memoryUtils.user
        console.log('表单参数: ', values);
        const {musicName, musicIp, musicCost, musicType} = values
        const peopleType = await this.state.music_contract.methods.getPeople(user.username).call();
        if (peopleType[3] == "Artist") {
            const addok = await this.state.music_contract.methods.addMusic(musicName, musicIp, musicType, user.username, musicCost, this.state.url).send({from: user.username});
            if (addok.blockHash) {
                message.success("添加成功")
                this.setState({
                    spinning: false
                })
                this.onClose()
                this.componentWillMount()
            }
        } else {
            message.error("你非作家")
        }

    };

    loadBlockchainData = async () => {
        const {music} = await contract.loadweb3()
        this.setState({
            music_contract: music
        })
        const musiclength = await music.methods.getMusicList().call()
        let temp = new Array()
        for (let i = 0; i < musiclength; i++) {
            temp[i] = await music.methods.getMusic(await this.state.music_contract.methods.musiclist(i).call()).call()
        }
        this.setState({data: temp, total: musiclength})
    }

    async componentWillMount() {
        const user = memoryUtils.user
        this.setState({accounts: user.username})
        await this.loadBlockchainData()
        console.log(this.state)
    }

    MusicCopyright = async (owner, musicname, mcost) => {
        var word = prompt("请输入对方账户");
        if (owner == this.state.accounts && word != null) {
            this.setState({
                spinning: true
            })
            const result = await this.state.music_contract.methods.byMusicCopyright(word, musicname, mcost).send({from: owner})
            if (result.blockHash) {
                message.info('转移成功')
                this.setState({
                    spinning: false
                })
            }
        } else {
            message.error('操作失败，不是拥有者')
        }
    }

    beforeUpload = async (file) => {
        console.log(file)
        const url = await put(file.name, file)
        console.log('oss=>', url)
        this.setState({
            url: url
        })
    }

    render() {

        const columns = [
            {
                title: '音乐名称',
                dataIndex: '0',
            },
            {
                title: '目前拥有者',
                dataIndex: '6',
            },
            {
                title: '音乐资料',
                dataIndex: '5',
                render: (xxx) => {
                    console.log('hash:==?', xxx)
                    return (
                        <Button type='text' icon={<DownloadOutlined/>}>
                            <a href={xxx}>下载</a>
                        </Button>
                    )
                }
            },
            {
                title: '创作日期',
                dataIndex: '3',
                defaultSortOrder: 'descend',
                sorter: (a, b) => a[3] - b[3],
                render: (x) => formateDate(x)
            },
            {
                title: '操作',
                key: 'action',
                render: (x) => {
                    console.log('table', x)
                    return (
                        <span>
                            <Button

                                type='primary'
                                onClick={() => this.MusicCopyright(x[6], x[0], x[4])}
                            >
                                <UserSwitchOutlined/>
                                转移版权</Button>
                        </span>
                    )

                },
            },
        ];

        return (
            <div>
                <Button type="primary" onClick={this.showDrawer}>
                    <PlusOutlined/> 添加作品</Button>
                <Spin tip='加载中' spinning={this.state.spinning}></Spin>
                <Drawer
                    title="添加新的作品"
                    width={720}
                    onClose={this.onClose}
                    visible={this.state.visible}
                    bodyStyle={{paddingBottom: 80}}
                    footer={
                        <div
                            style={{
                                textAlign: 'right',
                            }}
                        >
                        </div>
                    }
                >
                    <Form layout="vertical" onFinish={this.onFinish}>
                        <Form.Item
                            name="musicName"
                            label="音乐名字"
                            rules={[{required: true, message: 'Please enter username'}]}
                        >
                            <Input placeholder="Please enter user name"/>
                        </Form.Item>
                        <Form.Item
                            name="musicIp"
                            label="音乐版权IP号"
                            rules={[{required: true, message: 'Please enter musicip'}]}
                        >
                            <Input placeholder="请输入你的音乐版权IP号"/>
                        </Form.Item>
                        <Form.Item name="musicCost" label="音乐版本号"
                                   rules={[{required: true, type: 'number', min: 0, max: 99}]}>
                            <InputNumber/>
                        </Form.Item>
                        <Form.Item name="music_hash" rules={[{required: true,}]}>
                            <Upload accept=".zip, .rar , .7z" beforeUpload={this.beforeUpload}>
                                <Button icon={<UploadOutlined/>}>上传音乐资料</Button>
                            </Upload>
                        </Form.Item>

                        <Form.Item
                            name="musicType"
                            label="音乐类型"
                            rules={[{required: true, message: 'Please choose the musicType'}]}
                        >
                            <Select placeholder="Please choose the musicType">
                                <Option value="happy">happy</Option>
                                <Option value="sad">sad</Option>
                                <Option value="love">love</Option>
                                <Option value="children">children</Option>
                                <Option value="laughter">laughter</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" htmlType="submit">提交</Button>
                        </Form.Item>
                    </Form>
                </Drawer>
                <Table
                    bordered
                    dataSource={this.state.data}
                    columns={columns}
                    pagination={{
                        total: this.state.total,
                        defaultPageSize: 5,
                        showQuickJumper: true,

                    }}
                />
            </div>


        )
    }
}