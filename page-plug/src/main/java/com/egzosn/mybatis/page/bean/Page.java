package com.egzosn.mybatis.page.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页列表，保存了分页内容，当前页，每页记录数，总记录数和页数
 * @author egan
 * email egzosn@gmail.com
 * @param <T> 具体的类型
 */
public class Page<T> extends Paging {
	/**
	 * 分页信息参数
	 */
	private static final ThreadLocal<Paging> PAGING = new ThreadLocal<Paging>();

	/**
	 * 页数
	 */
	private int pages = 1;
	/**
	 * 所有记录数
	 */
	private long total = 0;
	/**
	 * 当前页内容
	 */
	private List<T> content;


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
		if (total > 0) {
			this.pages = (int) Math.ceil((float) total / rows);
		}
	}


	public Page() {
		content = new ArrayList<T>();
	}

	public Page(int page, int rows) {
		this.page = page;
		this.rows = rows;
	}

	public Page(int page, int rows, long total, List<T> content) {
		this.page = page;
		this.rows = rows;
		this.total = total;
		this.content = content;
		setPages();
	}

	public Page(Paging paging, long total, List<T> content) {
		this(paging.getPage(), paging.getRows(), total, content);
	}

	/**
	 * 设置分页信息
	 * @param paging 分页信息
	 */
	public static void setPaging(Paging paging) {
		PAGING.set(paging);

	}
	/**
	 * 获取分页信息
	 */
	public static Paging getPaging() {
		return PAGING.get();
	}
	/**
	 * 获取分页信息
	 */
	public static void removePaging() {
		PAGING.remove();
	}
}
