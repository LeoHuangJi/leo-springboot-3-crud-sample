package vn.leoo.common.util;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import vn.leoo.common.constants.Constants;
import vn.leoo.common.data.BaseComparator;
import vn.leoo.common.dto.PaginationDTO;

public class PagingUtils {
	int page;
	int pageSize;
	int offset;
	int pages;
	int remian;
	
	String sortField;
	String order;
	
	public PagingUtils(int page, int pageSize){
		this.page = page;
		this.pageSize = pageSize;
		
		offset = (page -1) * pageSize;
	}
	
	public PagingUtils(PaginationDTO paginationDTO) {
		if(paginationDTO == null) {
			paginationDTO = new PaginationDTO();
		}
		Pageable pageable = paginationDTO.getPageable();
		if(pageable != null) {
			offset = (int) pageable.getOffset();
			pageSize = pageable.getPageSize();
		}
		String sort = paginationDTO.getSort();
		if(StringUtils.isNotEmpty(sort)) {
			sortField = sort.split(",")[0];
			order = sort.split(",")[1];
		}
	}
	
	public static <T> Page<T> sortAndPaging(List<T> datas, PaginationDTO paginationDTO) {
		Pageable pageable = paginationDTO.getPageable();
		String sort = paginationDTO.getSort();
		if(StringUtils.isNotEmpty(sort)) {
			String field = sort.split(",")[0];
			String order = sort.split(",")[1];
			
			boolean isAscending = Constants.ASCENDING.equals(order) ? true : false;
			
			Collections.sort(datas, new BaseComparator(isAscending, field));
		}
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize() ) > datas.size() ? datas.size() : (start + pageable.getPageSize()));
		Page<T> page = new PageImpl<T>(datas.subList(start, end), pageable, datas.size());
		
		return page;
	}
	
	public <T> Page<T> sort(List<T> datas){
		boolean isAscending = Constants.ASCENDING.equals(order) ? true : false;		
		Collections.sort(datas, new BaseComparator(isAscending, sortField));
		Page<T> page = new PageImpl<T>(datas);
		
		return page;
	}

	public int getOffset() {
		return offset;
	}

	public int getPages(int total_items_number) {
		remian = total_items_number % pageSize;
		if(remian > 0) remian = 1;
		pages = total_items_number/pageSize + remian;
		
		return pages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getSortField() {
		return sortField;
	}

	public String getOrder() {
		return order;
	}
}
