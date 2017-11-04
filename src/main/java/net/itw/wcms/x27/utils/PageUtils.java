package net.itw.wcms.x27.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * 分页数据工具类
 * 
 * Description:
 * 
 * @author Michael 10 Oct 2017 14:17:59
 */
public class PageUtils {
	
	public static final String PAGE_RESULT_LIST = "content";

	public static final String PAGE_TOTAL_NUM = "totalElements";

	public static final String PAGE_TOTAL_PAGE = "totalPages";

	public static final String PAGE_NUM = "number";

	public static final String PAGE_SIZE = "size";
	
	/**
	 * 分页，每页记录数
	 */
	public static final int PageSize = 20;

    /**
     * 封装分页数据到Map中
     * @param objPage
     * @return
     */
    public static Map<String, Object> getPageMap(Page<?> objPage) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put(PAGE_RESULT_LIST, objPage.getContent()); // 数据集合
        resultMap.put(PAGE_TOTAL_NUM, objPage.getTotalElements()); // 总记录数
        resultMap.put(PAGE_TOTAL_PAGE, objPage.getTotalPages()); // 总页数
        resultMap.put(PAGE_NUM, objPage.getNumber()); // 当前页码
        resultMap.put(PAGE_SIZE, objPage.getSize()); // 每页显示数量

        return resultMap;
    }

    /**
     * 创建分页请求
     * 
     * 
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * @param sortType 排序字段
     * @param direction 排序方向
     */
    public static PageRequest buildPageRequest(int pageNum, int pageSize, String sortType, String direction) {
        Sort sort = null;

        if (!StringUtils.isNotBlank(sortType)) {
            return new PageRequest(pageNum - 1, pageSize);
        } else if (StringUtils.isNotBlank(direction)) {
            if (Direction.ASC.equals(direction)) {
                sort = new Sort(Direction.ASC, sortType);
            } else {
                sort = new Sort(Direction.DESC, sortType);
            }
            return new PageRequest(pageNum - 1, pageSize, sort);
        } else {
            sort = new Sort(Direction.ASC, sortType);
            return new PageRequest(pageNum - 1, pageSize, sort);
        }
    }

    /**
     * 创建分页请求(该方法可以放到util类中)
     * @param pageNum
     * @param pageSize
     * @param sortType
     * @return
     */
    public static PageRequest buildPageRequest(int pageNum, int pageSize, String sortType) {
        return buildPageRequest(pageNum, pageSize, sortType, null);
    }
    
    /**
     * 创建分页请求
     * @param pageNum
     * @param pageSize
     * @param sort
     * @return
     */
    public static PageRequest buildPageRequest(int pageNum, int pageSize, Sort sort) {
        return new PageRequest(pageNum - 1, pageSize, sort);
    }

    /**
     * 创建分页请求(该方法可以放到util类中)
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static PageRequest buildPageRequest(int pageNum, int pageSize) {
        return buildPageRequest(pageNum, pageSize, null, null);
    }

}
