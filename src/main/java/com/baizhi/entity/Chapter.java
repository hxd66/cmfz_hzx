package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cmfz_chapter")
@ExcelTarget(value = "chapter")
public class Chapter {
    @Id
    @Excel(name = "章节号")
    private String id;
    @Excel(name = "章节名称")
    private String title;
    private String size;
    private String duration;
    private String url;
    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "创建时间", databaseFormat = "yyyy-MM-dd-HH-mm-ss", format = "yyyy-MM-dd", width = 50)
    private Date createDate;
    @Column(name = "last_update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdateDate;
    private String status;
    @Column(name = "album_id")
    private String albumId;
}
