package com.zxk175.notify.module.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zxk175
 * @since 2020-03-29 14:00
 */
@Data
@Accessors(chain = true)
public class PageBeanVo {
	
	@ApiModelProperty(value = "是否有上一页", example = "false")
	private Boolean hasPre;
	
	@ApiModelProperty(value = "是否有下一页", example = "true")
	private Boolean hasNext;
	
	@ApiModelProperty(value = "当前页码", example = "1")
	private Long page;
	
	@ApiModelProperty(value = "每页记录数", example = "10")
	private Long size;
	
	@ApiModelProperty(value = "总记录数", example = "100")
	private Long total;
	
	@ApiModelProperty(value = "总页数", example = "10")
	private Long totalPage;
	
	
	public Boolean getHasPre() {
		return page > 1;
	}
	
	public Boolean getHasNext() {
		return page < calcTotalPage();
	}
	
	public Long calcTotalPage() {
		return calcTotalPage(this.total, this.size);
	}
	
	private Long calcTotalPage(Long total, Long size) {
		if (size == 0) {
			return 0L;
		}
		
		long totalPage = total / size;
		if (total % size > 0) {
			totalPage++;
		}
		
		return totalPage;
	}
	
}
