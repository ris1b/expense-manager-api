package com.expen.expensemanagerapi.services;

import com.expen.expensemanagerapi.domain.Category;
import com.expen.expensemanagerapi.exceptions.EtBadRequestException;
import com.expen.expensemanagerapi.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface CategoryService {
    // to manipulate category info !
    List<Category> fetchAllCategories(Integer userId);

    Category fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;

    Category addCategory(Integer userId, String title, String description) throws EtBadRequestException;

    void updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestException;

    // cascade delete !
    void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
}
