package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cmfz_zr")
public class ZR {
    @Id
    private String id;
    @Column(name = "role_id")
    private String roleId;
    @Column(name = "ziyuan_id")
    private String ziyuanId;
}
