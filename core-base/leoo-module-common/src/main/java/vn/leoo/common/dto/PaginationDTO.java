package vn.leoo.common.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.leoo.common.util.StringUtils;

public class PaginationDTO {
	private String sort;    
    private int page = 0;    
    private int size = 10;
    
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Pageable getPageable() {
		Pageable pageable = null;
		
		if(size > 0) {
			if(StringUtils.isNotEmpty(sort))
				pageable = PageRequest.of(page, size, Sort.by(sort));
			else
				pageable = PageRequest.of(page, size);
		}
		return pageable;
	}
}