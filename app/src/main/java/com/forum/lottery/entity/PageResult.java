package com.forum.lottery.entity;

import com.forum.lottery.model.PrizeUserVo;

import java.util.List;

/**
 * 分页对象
 *
 * @author Administrator
 */
public class PageResult<T> {

    /**
     * 页大小
     */
    private int pageSize;


    /**
     * 总记录数
     */
    private int totalRows;

    /**
     * 总页数
     */
    private int totalPage;

    private List<T> rows;

//    //中奖记录
//    private List<PrizeUserVo> result;

    public int getTotalPage() {
        // 计算总页数
        if (totalRows > 0 && pageSize > 0) {
            totalPage = totalRows / pageSize;
            if (totalRows % pageSize != 0) {
                totalPage++;
            }
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
//
//    public List<PrizeUserVo> getResult() {
//        return result;
//    }
//
//    public void setResult(List<PrizeUserVo> result) {
//        this.result = result;
//    }
}
