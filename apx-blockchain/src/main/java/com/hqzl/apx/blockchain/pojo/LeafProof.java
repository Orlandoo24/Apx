package com.hqzl.apx.blockchain.pojo;

import lombok.Data;

import java.util.List;


@Data
public class LeafProof {
    private List<String> proof;
    private List<String> value;
}
