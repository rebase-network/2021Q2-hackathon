/*
包含n个日期时间处理的工具函数模块
*/


/*
  格式化日期
*/
export default function formateDate(nS) {
    return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/, ' ');
}
