package com.macro.mall.constans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageInfoBean<T> implements Serializable {

	private int index;
	private int length;
	private int total;
	private List<T> list = new ArrayList<T>();
	private Object extend;

	public PageInfoBean() {

	}

	public PageInfoBean(int index, int length, int total, List<T> list) {
		this.index = index;
		this.length = length;
		this.total = total;
		this.list.addAll(list);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public void add(T t) {
		if (null != t) {
			this.list.add(t);
		}
	}

	public void add(List<T> list) {
		if (null != list && !list.isEmpty()) {
			this.list.addAll(list);
		}
	}

	public Object getExtend() {
		return extend;
	}

	public void setExtend(Object extend) {
		this.extend = extend;
	}
}