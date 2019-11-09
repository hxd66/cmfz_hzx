package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cmfz_album")
@ExcelTarget(value = "Album")
public class Album {
    @Id
    @Excel(name = "编号", needMerge = true)
    private String id;
    @Excel(name = "专辑名称", needMerge = true)
    private String title;
    @Excel(name = "封面", type = 2, needMerge = true)
    private String cover;
    private Integer chapterCount;
    private String score;
    @Excel(name = "作者", needMerge = true)
    private String author;
    private String broadcast;
    @Column(name = "publish_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishDate;
    private String brief;
    @Column(name = "create_Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @Column(name = "last_update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdateDate;
    private String status;
    private String guruId;
    @ExcelCollection(name = "章节")
    private List<Chapter> chapters;
}
