package com.eva.android;

import android.util.Log;

/**
 * Android的R资源文件的反射实现版本，实现将R资源id持续化保存后还能再次在运行时还原使用。
 * <p>
 * 原理是: 当你要保存某资源时(比如表情图标),不要保存它的id值(因为id值是android的app运行时或编译时),
 * 只要保存它的id名, 在运行时使用时,通过反射就能拿到它的真正id值,也就可以在代码中正常使用了.
 * 
 * 
 * <p>使用本类必须首先设置defaultRClassPath，否则反射会失败!
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
public class HelloR
{
	private String defaultRClassPath = null;

	public String getDefaultRClassPath()
	{
		return defaultRClassPath;
	}
	public void setDefaultRClassPath(String defaultRClassPath)
	{
		this.defaultRClassPath = defaultRClassPath;
	}

	public int layout(String name)
	{
		return getResourseIdByName(getDefaultRClassPath(), "layout", name);
	}
	public int anim(String name)
	{
		return getResourseIdByName(getDefaultRClassPath(), "anim", name);
	}
	public int attr(String name)
	{
		return getResourseIdByName(getDefaultRClassPath(), "attr", name);
	}
	public int color(String name)
	{
		return getResourseIdByName(getDefaultRClassPath(), "color", name);
	}
	public int drawable(String name)
	{
		return getResourseIdByName(getDefaultRClassPath(), "drawable", name);
	}
	public int id(String name)
	{
		return getResourseIdByName(getDefaultRClassPath(), "id", name);
	}
	public int menu(String name)
	{
		return getResourseIdByName(getDefaultRClassPath(), "menu", name);
	}
	public int raw(String name)
	{
		return getResourseIdByName(getDefaultRClassPath(), "raw", name);
	}
	public int string(String name)
	{
		return getResourseIdByName(getDefaultRClassPath(), "string", name);
	}
	public int style(String name)
	{
		return getResourseIdByName(getDefaultRClassPath(), "style", name);
	}
	public int styleable(String name)
	{
		return getResourseIdByName(getDefaultRClassPath(), "styleable", name);
	}

	private int getResourseIdByName(String packageNameOfR
			, String className, String name)
	{
		Log.v(HelloR.class.getSimpleName()
				, "EVA.Android平台正在反射R资源："
					+packageNameOfR+".R."+className+"."+name);
		if(packageNameOfR == null)
			throw new IllegalArgumentException("Note by Jack Jiang: packageNameOfR can't be null!");
		int id = 0;
		try
		{
			id = getResourseIdByName(Class.forName(packageNameOfR + ".R"), className, name);
		}
		catch (ClassNotFoundException e)
		{
			System.err.println("Note by Jack Jiang: "+packageNameOfR + ".R 类不存在.");
			e.printStackTrace();
		}
		return id;
	}

	private int getResourseIdByName(Class clazzOfR
			, String className, String name)
	{
		if(clazzOfR == null)
			throw new IllegalArgumentException("Note by Jack Jiang: clazzOfR can't be null!");

		Class r = clazzOfR;
		int id = 0;

//		String hintMsgIfException = "";
		String hintMsgIfException = "反射R常量:"+clazzOfR.getName()+"$"+className+"."+name+"时出错,";
		try
		{
			Class[] classes = r.getClasses();
			Class desireClass = null;
			for (int i = 0; i < classes.length; i++)
			{
				if (classes[i].getName().split("\\$")[1].equals(className))
				{
					desireClass = classes[i];
					break;
				}
			}
			if (desireClass != null)
				id = desireClass.getField(name).getInt(desireClass);

//			hintMsgIfException = "反射R常量:"+clazzOfR.getName()+"$"+className+"."+name+"时出错,";
		}
		catch (IllegalArgumentException e)
		{
			System.err.println("Note by Jack Jiang: "+hintMsgIfException + e.getMessage());
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			System.err.println("Note by Jack Jiang: "+hintMsgIfException + e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			System.err.println("Note by Jack Jiang: "+hintMsgIfException + e.getMessage());
			e.printStackTrace();
		}
		catch (NoSuchFieldException e)
		{
			System.err.println("Note by Jack Jiang: "+hintMsgIfException + e.getMessage());
			e.printStackTrace();
		}
		return id;
	}
}
