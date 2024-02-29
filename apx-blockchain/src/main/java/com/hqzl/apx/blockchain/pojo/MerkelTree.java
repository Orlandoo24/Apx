package com.hqzl.apx.blockchain.pojo;


import lombok.Data;

import java.util.List;

@Data
public class MerkelTree {
    private List<String> leafEncoding;
    private List<LeafProof> leafProof;
    private String root;
}
