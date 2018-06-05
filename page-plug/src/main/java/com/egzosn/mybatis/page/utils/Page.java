package com.egzosn.mybatis.page.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页列表，保存了分页内容，当前页，每页记录数，总记录数和页数
 * @author egan
 *         email egzosn@gmail.com
 * @param <T> 具体的类型
 */
public class Page<T> extends Paging{


	private int pages = 1; // 页数

	private long total = 0; // 所有记录数
	
	private List<T> content; // 当前页内容


	/**
	 * 获取总记录数
	 * @return
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * 设置记录数
	 * @param total
	 */
	public void setTotal(long total) {
		this.total = total;
		// 根据总的记录数生成分页
     	setPages();
	}

	/**
	 * 获取当前页内容
	 * @return 当前页内容
	 */
	public List<T> getContent() {
		return content;
	}
	
	/**
	 * 设置当前页内容
	 * @param content 前页内容
	 */
	public void setContent(List<T> content) {
		this.content = content;
	}


	/**
	 * 获取总页数
	 * @return
	 */
	public int getPages() {
		return pages;
	}


	private void setPages() {
		if (total >  0) {
			this.pages = (int)  Math.ceil((float)total / rows );
		}
	}



	public Page() {
		content = new ArrayList<T>();
	}

	public Page(int page, int rows, long total, List<T> content) {
		this.page = page;
		this.rows = rows;
		this.total = total;
		this.content = content;
		setPages();
	}

}
