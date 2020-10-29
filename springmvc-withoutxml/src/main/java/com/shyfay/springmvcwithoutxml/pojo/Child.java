package com.shyfay.springmvcwithoutxml.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Notes
 * @Author muxue
 * @Since 10/27/2020
 */
@Data
public class Child implements Serializable {
    private String id;
    private String name;
}
