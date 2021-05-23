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

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 一个简单的GridView扩展封装类.
 * 
 * <p>
 * 可实现一个图片+文本描述样式的GridView组件实现.
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
public class SimpleGridView extends LinearLayout
{
	/** 本面板所在的context所对应的Inflater引用 */
	protected LayoutInflater contextInflater;
	/** 组件的真正view实现 */
	protected View viewOfMyself;
	
	/** GridView组件实例 */
	protected GridView gridview;

	public SimpleGridView(Activity context
			, int maingridview_layout_res_id, int maingridview_gridview_res_id
			, int maingridviewitem_layout_res_id, int maingridviewitem_text_view_id, int maingridviewitem_image_view_id
			, ArrayList<DefaultElementDTO> listDatas)
	{
		this(context, null
				, maingridview_layout_res_id, maingridview_gridview_res_id
				, maingridviewitem_layout_res_id, maingridviewitem_text_view_id, maingridviewitem_image_view_id, listDatas);
	}

	public SimpleGridView(final Activity context, AttributeSet attrs
			, int maingridview_layout_res_id, int maingridview_gridview_res_id
			, int maingridviewitem_layout_res_id, int maingridviewitem_text_view_id, int maingridviewitem_image_view_id
			, ArrayList<DefaultElementDTO> listDatas)
	{
		super(context);
		this.contextInflater = LayoutInflater.from(context);
		viewOfMyself = contextInflater.inflate(maingridview_layout_res_id, null);//R.layout.chatting_list_view_bottom_waves_list, null);

		// 加入到外层布局中，以方便重用本面板的activity对Ui的组织
		addView(viewOfMyself, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		// GridView组件实例
		gridview = (GridView) viewOfMyself.findViewById(maingridview_gridview_res_id);//R.id.chatting_list_view_bottom_waves_list_id);  

		// 初始化列表及其数据设置
		initListAndAdapter(context, gridview
				, maingridview_layout_res_id, maingridview_gridview_res_id
				, maingridviewitem_layout_res_id, maingridviewitem_text_view_id, maingridviewitem_image_view_id
				, listDatas);
	}
	
	protected void initListAndAdapter(Activity context, GridView gridview
			, int maingridview_layout_res_id, int maingridview_gridview_res_id
			, int maingridviewitem_layout_res_id, int maingridviewitem_text_view_id, int maingridviewitem_image_view_id
			, ArrayList<DefaultElementDTO> listDatas)
	{
		//添加并且显示  
//		DefaultListAdapter adapter = new DefaultListAdapter(context
//				, maingridviewitem_layout_res_id   //R.layout.chatting_list_view_bottom_waves_list_item
//				, maingridviewitem_text_view_id    //R.id.chatting_list_view_bottom_waves_list_item_txt
//				, maingridviewitem_image_view_id); //R.id.chatting_list_view_bottom_waves_list_item_image);
//		adapter.setListData(listDatas);
		gridview.setAdapter(createListAdapter(context,
				maingridviewitem_layout_res_id, maingridviewitem_text_view_id, maingridviewitem_image_view_id
				,listDatas));  
		gridview.setOnItemClickListener(new DefaultItemClickListener());
	}
	
	protected DefaultListAdapter createListAdapter(Activity context,int maingridviewitem_layout_res_id
			, int maingridviewitem_text_view_id, int maingridviewitem_image_view_id
			, ArrayList<DefaultElementDTO> listDatas)
	{
		DefaultListAdapter adapter = new DefaultListAdapter(context
				, maingridviewitem_layout_res_id   //R.layout.chatting_list_view_bottom_waves_list_item
				, maingridviewitem_text_view_id    //R.id.chatting_list_view_bottom_waves_list_item_txt
				, maingridviewitem_image_view_id); //R.id.chatting_list_view_bottom_waves_list_item_image);
		adapter.setListData(listDatas);
		return adapter;
	}
	
	public DefaultListAdapter getGridViewAdapter()
	{
		return (DefaultListAdapter)this.gridview.getAdapter();
	}
	public void setListData(ArrayList<DefaultElementDTO> listDatas)
	{
		getGridViewAdapter().setListData(listDatas);
	}

	public GridView getGridview()
	{
		return gridview;
	}

	/**
	 * 本类的默认实现：OnItemClickListener实现类.
	 * 
	 * <p>
	 * 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件.
	 * 
	 * @author Jack Jiang, 2013-07-24
	 * @version 1.0
	 */
	public static class DefaultItemClickListener implements OnItemClickListener  
	{  
		@Override
		public void onItemClick(
				AdapterView<?> adapterViewWhereTheClickHappened, // The AdapterView where the click happened   
				View arg1,			 // The view within the AdapterView that was clicked  
				int position,		 // The position of the view in the adapter  
				long arg3			 // The row id of the item that was clicked  
				) 
		{  
			// 在本例中arg2=arg3
			@SuppressWarnings("unchecked")
			DefaultElementDTO item = (DefaultElementDTO)adapterViewWhereTheClickHappened.getItemAtPosition(position);  

			// 响应每一个单元的动作处理
			DefaultItemAction action = item.get__action__();
			// 把GridView的对引用也存起来备用哦
			action.setAdapterViewWhereTheClickHappened(adapterViewWhereTheClickHappened);
			// 执行动作
			action.actionPerformed(action.getIdentId());
		} 
	}

	/**
	 * 本类的默认实现：Action实现类.
	 * 
	 * <p>
	 * 本类实现了对单击列表中组件的事件处理的封装.
	 * 
	 * @author Jack Jiang, 2013-07-24
	 * @version 1.0
	 */
	public static abstract class DefaultItemAction implements Action
	{
		private Object identId;
		// 将GridView对象引用也存起来，以便在事件处理的方法中可以拿到它哦
		// ，本对象在调 用 setAdapterViewWhereTheClickHappened（。。）方法时被传过来
		protected AdapterView adapterViewWhereTheClickHappened;
		
		public DefaultItemAction(Object identId)
		{
			this.identId = identId;
		}

		@Override
		public abstract void actionPerformed(Object obj);

		public Object getIdentId()
		{
			return identId;
		}

		public DefaultItemAction setIdentId(Object identId)
		{
			this.identId = identId;
			return this;
		}

		public void setAdapterViewWhereTheClickHappened(
				AdapterView adapterViewWhereTheClickHappened)
		{
			this.adapterViewWhereTheClickHappened = adapterViewWhereTheClickHappened;
		}
	}

	/**
	 * 本类的默认实现：列表适配器实现类.
	 * 
	 * @author Jack Jiang, 2013-07-24
	 * @version 1.0
	 */
	public static class DefaultListAdapter extends AListAdapter2<DefaultElementDTO>
	{
		/** 记录选中的ListView的行索引值以备后用（目前是在：单击、长按2类事件中保存了此索引值）.*/
		protected int selectedListViewIndex = -1;
		
//		private int item_layout_res_id;
		protected int maingridviewitem_text_view_id;
		protected int maingridviewitem_image_view_id;

		public DefaultListAdapter(Activity context
				, int maingridviewitem_layout_res_id, int maingridviewitem_text_view_id, int maingridviewitem_image_view_id)
		{
			// R.layout.chatting_list_view_bottom_waves_list_item
			super(context, maingridviewitem_layout_res_id);
			
			this.maingridviewitem_text_view_id = maingridviewitem_text_view_id;
			this.maingridviewitem_image_view_id = maingridviewitem_image_view_id;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			//列表item上的：
			TextView viewText = null;
			//列表item上的：
			ImageView viewImage = null;

			//---------------------------------------------------------------------------------------- （1）UI初始化
			//当的item布局实例已经存在（不在存，意味着这个item刚好从不可见区移到可见区时）
			//** 根据android的列表ui实现，为了节比资源占用，假如列表有100行而可见区显示5行，那么任何时候
			//** 这个列表中只会有5个item的UI存在，其它的item都是当被转到可见区时自动把自
			//** 已的数据实时地更新列UI上，以便查看，也就是说item的UI并不是与后台数据一
			//** 一对应的，所以正如API文档里说的，convertView并不能确保它总能按你的想法保持不为null
			boolean needCreateItem = (convertView == null);
			//正在操作的列表行的数据集
			final DefaultElementDTO rowData = listData.get(position);
			if (needCreateItem)
				//明细item的UI实例化
				convertView = layoutInflater.inflate(itemResId, null);
			//item里的操作组件实例
			viewText = (TextView) convertView.findViewById(maingridviewitem_text_view_id);////R.id.chatting_list_view_bottom_waves_list_item_txt);
			viewImage = (ImageView) convertView.findViewById(maingridviewitem_image_view_id);//R.id.chatting_list_view_bottom_waves_list_item_image);

			//---------------------------------------------------------------------------------------- （2）增加事件处理器
			//各操作组件的事件监听器只需要在convertView被实例化时才需要重建（convertView需要被实例化
			//当然就意味着它上面的所有操作组件都已经重新新建了）
			//** 关于事件处理器的说明：事件处理器的增加其实可以不这么麻烦，直接每getView一次就给组件new一个处理器，
			//** 这样做的好处是简单，但显然存在资源浪费（每刷新一次view就新建监听器）。而现在的实现就跟Android的列表
			//** 实现原理一样，在切换到下一组item前，监听器永远只有一屏item的数量（刷新时只需要即时刷新对应item的数据到
			//** 它的监听器里），这样就节省了资源开销！
			if(needCreateItem)
			{
				//
			}

			//---------------------------------------------------------------------------------------- （3）
			viewText.setText(rowData.getText());
			viewImage.setImageDrawable(rowData.getDrawable());
			
			// 调用显示额外设置方法
			getViewEx(position, convertView, parent, rowData);

			return convertView;
		}
		
		// 本方法默认什么也不做，是留做子类重写一些附加的ui逻辑时使用的，因为
		// 重写原getView方法的话可能会代码比较多，所以干脆独立出本方法
		public void getViewEx(final int position, View convertView, ViewGroup parent, DefaultElementDTO rowData)
		{
			// default do nothing
		}

		public int getSelectedListViewIndex()
		{
			return selectedListViewIndex;
		}
		public void setSelectedListViewIndex(int selectedListViewIndex)
		{
			this.selectedListViewIndex = selectedListViewIndex;
//			this.notifyDataSetChanged();
		}
	}

	/**
	 * 本类的默认实现：列表数据传输对象封装类.
	 * 
	 * @author Jack Jiang
	 * @version 1.0
	 */
	public static class DefaultElementDTO
	{
		// 元素的唯一标识，用于处理点中某个元素时的事件处理（时用于区分到底选的是哪个元素） 
		private Object __ident__ = null;
		// 各元素对应的事件处理对象
		private DefaultItemAction __action__ = null;
		// 元素的文本描述
		private String text = null;
		// 元素对应的显示图片
		private Drawable drawable = null;

		public DefaultElementDTO(DefaultItemAction __action__, String text, Drawable drawable)
		{
			this.__action__ = __action__;
			this.__ident__ = __action__.getIdentId();
			this.text = text;
			this.drawable = drawable;
		}
		public String getText()
		{
			return text;
		}
		public void setText(String text)
		{
			this.text = text;
		}
		public Drawable getDrawable()
		{
			return drawable;
		}
		public void setDrawable(Drawable drawable)
		{
			this.drawable = drawable;
		}
		public Object get__ident__()
		{
			return __ident__;
		}
		public void set__ident__(Object __ident__)
		{
			this.__ident__ = __ident__;
		}
		public DefaultItemAction get__action__()
		{
			return __action__;
		}
		public void set__action__(DefaultItemAction __action__)
		{
			this.__action__ = __action__;
		}
	}
}