package com.empresaurios.stylist.bean;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

public record PaginatedResponse<E>(int page, int totalPages,List<E> data) {

    public PaginatedResponse(PanacheQuery<E> query){
        this(query.page().index + 1, query.pageCount(), query.list());
    }
}
