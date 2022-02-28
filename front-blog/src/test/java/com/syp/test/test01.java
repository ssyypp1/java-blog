package com.syp.test;

import org.jetbrains.annotations.TestOnly;
import org.junit.Test;

import java.util.TreeMap;

public class test01 {
    @Test
    public void test01(){
        TreeMap<Integer,Integer> treeMap=new TreeMap<Integer,Integer>((a,b)->{
            return b-a;
        });

    }
}
