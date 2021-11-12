const OSS = require('ali-oss')
var path = require("path")

const client = new OSS({
    region: 'oss-cn-beijing',
    accessKeyId: '',
    accessKeySecret: '',
    bucket: '',
});

export default async function put(objectname, file) {
    try {
        // 填写Object完整路径和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
        let result = await client.put(objectname, file);
        console.log(result);
        return result.url;
    } catch (e) {
        console.log(e);
    }
}
