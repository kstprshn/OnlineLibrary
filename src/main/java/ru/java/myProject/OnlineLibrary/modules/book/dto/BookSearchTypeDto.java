package ru.java.myProject.OnlineLibrary.modules.book.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import ru.java.myProject.OnlineLibrary.modules.book.entity.enums.SearchType;

@AllArgsConstructor
@NoArgsConstructor
public class BookSearchTypeDto {

    private SearchType searchType;
    private String searchValue;

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
}
