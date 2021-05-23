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
package com.eva.android.widget;

import com.eva.epc.common.dto.IdName;
import com.eva.epc.common.util.CommonUtils;
import com.eva.epc.common.util.MapNoHash;
import com.eva.epc.common.util.SimpleLogger;

import java.util.ArrayList;
import java.util.Vector;

/**
 * <p>
 * 本render用于下拉框或表格单元中单元的显示和内容进行分离（id就是内容，name就是用于显示的文本）.
 * 本render实现类目前可用于Spinner、ListView等及各自的子类中需要对显示和真正的内容进行分离的地方.
 * <pre>
 * 其中，id就是一般意义上的model里的值，而name值按Icon、ERenderDTO和其它未定义类型分别处理，如果
 * name是Icon对象时表现直接作为一图片显示，如果是ERenderDTO对象则会作为图标+文本的方式呈现，其它
 * 未定义类型直接按name.toString()进行呈现.
 *</pre>
 *
 *！设计思想借鉴自桌面平台中的ERender类.
 * </p>
 *
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 * @see MapNoHash
 */
public class DataRender
{
	/**
	 * 内容和显示键值对集合（内容就是一般意义上的model里的值，显示值是广义的显示对象，它可能是{@code Icon}
	 * 对象(render展现时是个图标)、也可能是{@code String}对象(就是平常意义上的文本显示值)、也可能
	 * 是{@code ERenderDTO}对象(封装了文本显示值和显示图标)）
	 */
	private MapNoHash<Object,Object> idAndNames = new MapNoHash<Object,Object>();

	public DataRender()
	{}
	public DataRender(Object[] ids, Object[] names)
	{
		setIdAndNames(ids, names);
	}
	/**
	 * @param idAndNames 2维数组，子数组必须且仅需2个单元素，按顺序分别是id和name
	 */
	public DataRender(Object[][] idAndNames)
	{
		Object[] ids = new Object[idAndNames.length],
		names = new Object[idAndNames.length];
		for (int i = 0; i < idAndNames.length; i++)
		{
			ids[i] = idAndNames[i][0];
			names[i] = idAndNames[i][1];
		}
		this.setIdAndNames(ids, names);
	}
	/**
	 * @param idAndNames IdName集合
	 */
	public DataRender(IdName[] idAndNames)
	{
		Object[] ids = new Object[idAndNames.length],
		names = new Object[idAndNames.length];
		for (int i = 0; i < idAndNames.length; i++)
		{
			ids[i] = idAndNames[i].getId();
			names[i] = idAndNames[i].getName();
		}
		this.setIdAndNames(ids, names);
	}
	/**
	 * @param idAndNames 2D Vector对象，子Vector必须且权需2个元素，按顺序分别是id和name
	 */
	public DataRender(Vector<Vector> idAndNames)
	{
		this(CommonUtils.to2DArray(idAndNames));
	}

	/**
	 * 重新设置本render的键值对集合.
	 *
	 * @param ids 键集合
	 * @param names 值集合
	 */
	public void setIdAndNames(Object[] ids, Object[] names)
	{
		if (ids.length != names.length)
			SimpleLogger.w("id和name数组的长度不一致，请确认！");
		for(int i=0;i<ids.length;i++)
			addIdName(ids[i],names[i]);
	}

	/**
	 * 获得键集合.
	 *
	 * @return
	 */
	public Object[] getIds()
	{
		return this.idAndNames.keys().toArray();
	}
	public ArrayList getIds2()
	{
		return this.idAndNames.keys();
	}
	/**
	 * 获得值集合.
	 *
	 * @return
	 */
	public Object[] getNames()
	{
		return this.idAndNames.elements().toArray();
	}
	public ArrayList getNames2()
	{
		return this.idAndNames.elements();
	}

	/**
	 * 获得完整的键值集合对象.
	 *
	 * @return
	 */
	public MapNoHash<Object, Object> getIdAndNames()
	{
		return idAndNames;
	}
	/**
	 * 设置完整的键值集合对象.
	 *
	 * @param idAndNames
	 */
	public void setIdAndNames(MapNoHash<Object, Object> idAndNames)
	{
		this.idAndNames = idAndNames;
	}

	/**
	 * 获得键值对集合.
	 *
	 * @return 如果idAndNames元素个数>0则返回对应的键值对集合，否则返回null
	 */
	public IdName[] getIdAndNames2()
	{
		IdName[] ins = null;
		ArrayList keys = idAndNames.keys();
		for(int i=0;i<keys.size();i++)
		{
			if(ins == null)
				ins = new IdName[idAndNames.size()];
			Object k = keys.get(i);
			ins[i] = new IdName(k,idAndNames.get(k));
		}
		return ins;
	}
	/**
	 * 设置键值对集合.
	 * @param ins
	 */
	public void setIdAndNames2(IdName[] ins)
	{
		if(ins!=null)
			for(int i=0;i<ins.length;i++)
				addIdName(ins[i]);
	}

	/**
	 * <p>
	 * 据{@code id}找到对应的{@code name}.<br>
	 * 注：该{@code name}是广义的显示对象，它可能是{@code Icon}对象(render展现时是个图标)
	 * 、也可能是{@code String}对象(就是平常意义上的文本显示值)、也可能是{@code ERenderDTO}对
	 * 象(封装了文本显示值和显示图标).
	 * </p>
	 *
	 * @param id id值，它存在于model的概念中
	 * @return 呈现对象数据封装对象（它可能封装了图标等信息，它是个广义展现对象，不一定就是普通的文本）
	 */
	public Object getNameById(Object id)
	{
		Object name = null;
		if (id != null)
			name = this.idAndNames.get(id);
		return name==null?id:name;
	}
	/**
	 * 据name找到对应的id.
	 *
	 * @param name
	 * @return 找到则返回对应的id,否则返回null
	 */
	protected Object getIdByName(Object name)
	{
		if(name != null)
		{
			ArrayList kes = this.idAndNames.keys();
			for(int i=0;i<kes.size();i++)
			{
				Object key = kes.get(i);
				Object value = this.idAndNames.get(key);
				if(value.equals(name))
					return key;
			}
		}
		return null;
	}

	/**
	 * 加入一个键值对.
	 * @param idName
	 */
	public void addIdName(IdName idName)
	{
		this.addIdName(idName.getId(), idName.getName());
	}
	/**
	 * 加入一个键值对.

	 */
	public DataRender addIdName(Object id, Object name)
	{
		this.idAndNames.put(id, name);
		return this;
	}
}
