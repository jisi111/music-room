package com.chuangxin.musicroom.core.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegionTreeNode {

    String name;

    String id;// 对应表中的code

    List<RegionTreeNode> sub;

}
