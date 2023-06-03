package com.example.stockmarket.dao;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SqlBuilder {
   private final String select;
   private final String from;
   private final List<String> where = new ArrayList<>();
   private String orderBy;
    private Long offset;
    private Long fetchNext;

    public SqlBuilder(String select, String from) {
        this.select = select;
        this.from = from;
    }
    public SqlBuilder where(String clause){
        where.add(clause);
        return this;
    }
    public SqlBuilder orderBy(String order){
        this.orderBy = order;
        return this;
    }
    public SqlBuilder offset(long offset) {
        this.offset = offset;
        return this;
    }
    public SqlBuilder fetchNext(long fetchNext){
        this.fetchNext = fetchNext;
        return this;
    }
    public String build(){
        String build = "select " + select + " from " + from;
        if (!where.isEmpty()){
            build += " where ";
            for (int x = 0; x < where.size() - 1; x++) {
                build += where.get(x) + " and ";
            }
            build += where.get(where.size() - 1);
        }
        if (orderBy != null) {
            build += " order by " + orderBy;
        }
        if (offset != null) {
            build += " offset " + offset;
        }
        if (fetchNext != null) {
            build += " fetch next " + fetchNext + " rows only";
        }
        build += ";";
        log.debug(build);
        return build;
    }
}
