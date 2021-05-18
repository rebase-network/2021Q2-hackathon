// Copyright (C) 2020 即时通讯网(52im.net) & Jack Jiang.
// The RainbowChat Project. All rights reserved.
// 
// 【本产品为著作权产品，合法授权后请放心使用，禁止外传！】
// 【本次授权给：<唐林峰（身份证号：360727198911110910）>，授权编号：<NT200909102407>，代码指纹：<A.599618247.472>，技术对接人QQ：<582901605>】
// 【授权寄送：<收件：唐林峰、地址：上海市浦东新区金豫路100号禹州国际3期2号楼、电话：18717758990>】
// 
// 【本系列产品在国家版权局的著作权登记信息如下】：
// 1）国家版权局登记名(简称)和权证号：RainbowChat    （证书号：软著登字第1220494号、登记号：2016SR041877）
// 2）国家版权局登记名(简称)和权证号：RainbowChat-Web（证书号：软著登字第3743440号、登记号：2019SR0322683）
// 3）国家版权局登记名(简称)和权证号：RainbowAV      （证书号：软著登字第2262004号、登记号：2017SR676720）
// 4）国家版权局登记名(简称)和权证号：MobileIMSDK-Web（证书号：软著登字第2262073号、登记号：2017SR676789）
// 5）国家版权局登记名(简称)和权证号：MobileIMSDK    （证书号：软著登字第1220581号、登记号：2016SR041964）
// * 著作权所有人：江顺/苏州网际时代信息科技有限公司
// 
// 【违法或违规使用投诉和举报方式】：
// 联系邮件：jack.jiang@52im.net
// 联系微信：hellojackjiang
// 联系QQ号：413980957
// 授权说明：http://www.52im.net/thread-1115-1-1.html
// 官方社区：http://www.52im.net
package com.eva.android;

import com.eva.epc.common.dto.IdName;
import com.eva.epc.common.util.SimpleLogger;

import java.util.HashMap;

/**
 * 客户端数据缓存支持根类.
 * 在Java桌面和Android平台，因取数据时的差异，只需要各自实现方法： {@link #queryIdNamesImpl()}即可.
 *
 * @author Jack Jiang
 * @version 1.0
 */
public abstract class MemoryCacheProvider
{
	protected static MemoryCacheProvider singleInstance = null;
	private HashMap<String,Object> cacheCenter = null;

	/**
	 * 获得缓存列表引用.
	 *
	 * @return
	 */
	public HashMap<String,Object> getCacheCenter()
	{
		if(cacheCenter==null)
			cacheCenter = new HashMap();
		return cacheCenter;
	}

	/**
	 * 清空指定cacheID的缓存.
	 *
	 * @param cacheID 要清空的缓存ID（注意：comboItems的存放在缓存列表中的key=
	 * cacheID+"-"+(where != null ? String.valueOf(where.hashCode()) : "0")
	 *    	+"-"+(other != null ? String.valueOf(other.hashCode()) : "1")，也就是说单独的
	 *    cacheID并不能保证一定能找到对应的元素）
	 * @return null表示不存在对应的元素（清空失败），否则返回本次清空的key对应的object
	 * @see HashMap#remove(Object)
	 */
	public Object clear(int cacheID)
	{
		return getCacheCenter().remove(cacheID+"");
	}
	/**
	 * 清空所有缓存.
	 */
	public void clearAll()
	{
		getCacheCenter().clear();
	}

	/**
	 * 缓存一个对象（已经存在该cacheID对应的对象，则不覆盖，直接报错）.
	 *
	 * @param cacheID 要缓存对象对应的ID
	 * @param willBeCached 将要被缓存的对象 
	 */
	public void putObject(int cacheID,Object willBeCached)
	{
		putObject(cacheID,willBeCached,false);
	}
	/**
	 * 缓存一个对象.
	 *
	 * @param cacheID 要缓存对象对应的ID
	 * @param willBeCached 将要被缓存的对象 
	 * @param overWriteIfExists 如果已经存在该cacheID对应的对象，true表示直接覆盖之，false表示不覆盖（并报错）
	 */
	public void putObject(int cacheID,Object willBeCached,boolean overWriteIfExists)
	{
		if(getCacheCenter().containsKey(cacheID+"")&&!overWriteIfExists)
			SimpleLogger.w("cacheCenter中，cacheID="+cacheID
					+"的缓存对象已经存在，不允许覆盖，本次putObject失败，请确认!");
		else
			getCacheCenter().put(cacheID+"", willBeCached);
	}
	/**
	 * 获得指定cacheID的缓存对象.
	 *
	 * @param cacheID 缓存ID（注意：comboItems的存放在缓存列表中的key=
	 * cacheID+"-"+(where != null ? String.valueOf(where.hashCode()) : "0")
	 *    	+"-"+(other != null ? String.valueOf(other.hashCode()) : "1")，也就是说单独的
	 *    cacheID并不能保证一定能找到对应的元素）
	 * @return 如果该对象已经缓存，则返回缓存的对象，返则返回null
	 */
	public Object getObject(int cacheID)
	{
		return getCacheCenter().get(cacheID);
	}

	/**
	 * 获得一个ComboItem数组，这些ComboItem主要用于表格的render、List以及ComboBox的render等用途.<br>
	 * 注：本方法是 {@link #getCachableComboItems(int, String, String, String, String, boolean)}方法的不使用缓存版本，
	 * 其实调用本方法相当于直接调用FWUtils里的相关getComboItems(..).方法，只是有时为了重载缓存版方法但又不需要缓存时.
	 *
	 * @return
	 * @see IdName
	 * @see FWUtils#getComboItems(String, String, String, String, boolean)
	 */
	public IdName[] getCachableComboItems(int cacheID, String table, String fieldNames, String where
			, String other)
	{
		return getCachableComboItems(cacheID, table, fieldNames, where, other ,false);
	}

	/**
	 * 获得一个ComboItem数组，这些ComboItem主要用于表格的render、List以及ComboBox的render等用途.<br>
	 * <br>
	 * <b>重要说明</b>：当取的是需要缓存的数据时，不同的缓存数据一定要使用不同的cacheID，否则会使得缓存对象产生混乱.<br>
	 * <br>
	 * <b>另一提示</b>：有时候，在程序里调用render使用各种继承方法时，却错误的没有区分不同ID（只在重载根方法里<br>
	 * 指明了ID），这时本方法为了尽可能避免缓存混乱，而自动的在存放数据并缓存时ID实际使用的是：
	 * cacheID+"-"+(where != null ? String.valueOf(where.hashCode()) : "0")
	 *    	+"-"+(other != null ? String.valueOf(other.hashCode()) : "1")，使其也能自动据不同的where和other条件
	 * 区分不同的缓存对象，此种方法是为了尽量避免因程序员的疏忽而产生的错误，但它不是最佳实践，<b><u>不同的缓存数据最佳方法是
	 * 使用不同的ID进行区分</u></b>！
	 *
	 * @param cacheID 缓存ID，必须唯一
	 * @param table render对应的数据库表名（可以是任何SQL语句支持的如select * from [table] ...的[table]片段）
	 * @param fieldNames render对应的数据库表列名（0列是id、1列是name，如果只有1列则它将既是ID也是Name，多于2列则其余列将被忽略）
	 * @param where 对应SQL语句的条件字符串（不需要加"where"关键字）
	 * @param other 额外的SQL语句片段，可以是如：group by xxx、order by xxx等SQL允许的语句片段（本安段如果trim后长度是
	 * 		0则默认使用第一列作为order列并以ASC排序
	 * @param isCache 是否需要缓存，当不需要缓存时就相当于直接调用FWUtils里的相关getComboItems(..).方法
	 * @return
	 * @see IdName
	 * @see FWUtils#queryIdNames(String, String, String, String)
	 */
	public IdName[] getCachableComboItems(int cacheID, String table, String fieldNames, String where
			, String other, boolean isCache)
	{
		IdName[] value = null;
		//*** 注：where.hashCode()是用于解决那些使用的是同一个缓存ID，但是过滤条件确实不一样的情况， 这样即使
		//*** 缓存ID一样，但是因where条件不一样也不会取错，这种方法不是最好的（推荐方法是不同的where就用不同的ID，只是麻烦一点而已）
		//*** ，以后或许可以考虑更好的方法
		//另注：对于字符串而言，内容完全一样的字符串hashCode肯定相同（哪怕不是同一对象引用）（但除String以外的对象hashCode是用于区分不同对象的）
		String key = cacheID+"-"+(where != null ? String.valueOf(where.hashCode()) : "0")
				+"-"+(other != null ? String.valueOf(other.hashCode()) : "1");

		if(isCache)//一定是缓存的
			//先尝试取缓存中的对象
			value = (IdName[])(getCacheCenter().get(key));
		//还没有被缓存
		if(value == null)
		{
			SimpleLogger.d("正从缓存中心取getCachableComboItems("+key+",..) 的数据,缓存版?"+isCache);
			//新取数据
			value = queryIdNamesImpl(table,fieldNames,where,other);
			if(value!=null)
				if(isCache)
					//并查询出的数据缓存起来（下次就不需要再从数据库取了）
					getCacheCenter().put(key, value);
		}
		return value;
	}

	/**
	 * <p>
	 * 从指定的数据库表中获取数据，并组成IdName数组.<br>
	 *
	 * <b>重要说明:</b>本方法子类必须实现。<br>
	 * <ul>
	 * <li>如在Java桌面平台，本方法是直接调用方法：
	 * com.eva.epc.platf.endc.std.util.FWUtils#queryIdNames(String, String, String,String)方法现的.</li>
	 * <li>如在Android平台，本方法是直接调用方法：
	 * com.eva.android.platf.util.AToolKits#queryIdNames(String, String, String,String)方法现的.</li>
	 * </ul>
	 * </p>
	 *
	 * @param table 取值表名，可以是合法的SQL语句中from后where前的任何样式，如形如：t1 a join t2 on t1.key=t2.key等
	 * @param queryFieldNames 取值列名，一般为2个字段，前1个作为ID,后1个作为Name（特殊情况下：
	 * 		当只有1列时，则认为该列即是ID也是Name；当列多于2个时其它列会被忽略）
	 * @param where where条件
	 * @param other 其他条件，可以是诸如order by语句的任何合法SQL后缀.
	 * (注：如果other参数是空的，则默认取第一列作自动排序列（order by [第1列]），如果真想排序则最好明确指明，因自动取会有这样的问题：
	 * 当第一列是一个函数且有不只一个参数则以逗号作为分隔符取第一列就不对了）)
	 * @return 返回据指定语句查询出的数据构造成的IdName数组
	 * @see #queryIdNames(String)
	 */
	protected abstract IdName[] queryIdNamesImpl(String table, String queryFieldNames, String where, String other);
}
