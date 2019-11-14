package com.chuangxin.musicroom.core.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlanYear {

    private Integer year;

    private List<Integer> monthList = new ArrayList<>();

}
