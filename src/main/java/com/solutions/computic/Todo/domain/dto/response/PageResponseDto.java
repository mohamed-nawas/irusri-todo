package com.solutions.computic.Todo.domain.dto.response;

import org.springframework.data.domain.Page;

import com.solutions.computic.Todo.domain.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PageResponseDto implements ResponseDto {

    private final long totalItems;
    private final int totalPages;
    private final int page;
    private final int size;

    public PageResponseDto(Page<?> page) {
        this.totalItems = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.page = page.getNumber();
        this.size = page.getSize();
    }
    
    @Override
    public String toLogJson() {
        return toJson();
    }
    
}
