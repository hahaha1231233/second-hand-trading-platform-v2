package com.second_hand_trading_platform.controller;

import com.second_hand_trading_platform.model.Item;
import com.second_hand_trading_platform.service.ItemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/item/*")
public class ItemController extends HttpServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        String action = request.getPathInfo();
        int userId = (int) session.getAttribute("userId");

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        switch (action) {
            case "/add":
                request.getRequestDispatcher("/WEB-INF/views/add_item.jsp").forward(request, response);
                break;
            case "/edit":
                showEditForm(request, response, userId);
                break;
            case "/delete":
                deleteItem(request, response, userId);
                break;
            case "/myItems":
                showMyItems(request, response, userId);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        String action = request.getPathInfo();
        int userId = (int) session.getAttribute("userId");

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        switch (action) {
            case "/add":
                addItem(request, response, userId);
                break;
            case "/edit":
                updateItem(request, response, userId);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        double price = 0;
        try {
            price = Double.parseDouble(request.getParameter("price"));
        } catch (NumberFormatException e) {
            price = 0;
        }
        String location = request.getParameter("location");

        System.out.println("添加物品: " + title + ", 用户ID: " + userId);

        Item item = new Item(title, description, category, price, location, userId);

        if (itemService.addItem(item)) {
            response.sendRedirect(request.getContextPath() + "/item/myItems");
        } else {
            request.setAttribute("error", "添加物品失败");
            request.getRequestDispatcher("/WEB-INF/views/add_item.jsp").forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        try {
            int itemId = Integer.parseInt(request.getParameter("id"));
            Item item = itemService.getItemById(itemId);

            if (item != null && item.getUserId() == userId) {
                request.setAttribute("item", item);
                request.getRequestDispatcher("/WEB-INF/views/edit_item.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/item/myItems");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/item/myItems");
        }
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        try {
            int itemId = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            double price = 0;
            try {
                price = Double.parseDouble(request.getParameter("price"));
            } catch (NumberFormatException e) {
                price = 0;
            }
            String location = request.getParameter("location");

            System.out.println("更新物品 ID: " + itemId + ", 用户ID: " + userId);

            Item item = new Item();
            item.setId(itemId);
            item.setTitle(title);
            item.setDescription(description);
            item.setCategory(category);
            item.setPrice(price);
            item.setLocation(location);
            item.setUserId(userId);

            if (itemService.updateItem(item)) {
                response.sendRedirect(request.getContextPath() + "/item/myItems");
            } else {
                request.setAttribute("error", "更新物品失败");
                request.getRequestDispatcher("/WEB-INF/views/edit_item.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/item/myItems");
        }
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response, int userId)
            throws IOException {
        try {
            int itemId = Integer.parseInt(request.getParameter("id"));
            System.out.println("删除物品 ID: " + itemId + ", 用户ID: " + userId);
            itemService.deleteItem(itemId, userId);
        } catch (NumberFormatException e) {
            // 忽略错误
        }
        response.sendRedirect(request.getContextPath() + "/item/myItems");
    }

    private void showMyItems(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException {
        request.setAttribute("items", itemService.getItemsByUserId(userId));
        request.getRequestDispatcher("/WEB-INF/views/item_list.jsp").forward(request, response);
    }
}