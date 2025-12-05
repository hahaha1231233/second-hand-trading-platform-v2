package com.second_hand_trading_platform.controller;

import com.second_hand_trading_platform.service.ItemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/search")
public class SearchController extends HttpServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category");

        System.out.println("搜索请求 - 关键词: " + keyword + ", 分类: " + category);

        if (keyword == null || keyword.trim().isEmpty()) {
            request.setAttribute("items", itemService.getAllItems());
        } else {
            request.setAttribute("items", itemService.searchItems(keyword, category));
            request.setAttribute("keyword", keyword);
            request.setAttribute("category", category);
        }

        request.getRequestDispatcher("/WEB-INF/views/search.jsp").forward(request, response);
    }
}