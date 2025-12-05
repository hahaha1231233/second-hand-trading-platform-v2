package com.second_hand_trading_platform.dao;

import com.second_hand_trading_platform.config.DatabaseConfig;
import com.second_hand_trading_platform.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (title, description, category, status, price, location, found_date, post_date, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getTitle());
            pstmt.setString(2, item.getDescription());
            pstmt.setString(3, item.getCategory());
            pstmt.setString(4, item.getStatus());
            pstmt.setDouble(5, item.getPrice());
            pstmt.setString(6, item.getLocation());
            pstmt.setTimestamp(7, Timestamp.valueOf(item.getFoundDate()));
            pstmt.setTimestamp(8, Timestamp.valueOf(item.getPostDate()));
            pstmt.setInt(9, item.getUserId());

            int rows = pstmt.executeUpdate();
            System.out.println("添加物品: " + item.getTitle() + ", 影响行数: " + rows);
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("添加物品失败: " + e.getMessage());
            return false;
        }
    }

    public Item findItemById(int id) {
        String sql = "SELECT * FROM items WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractItemFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("查找物品失败: " + e.getMessage());
        }
        return null;
    }

    public List<Item> searchItems(String keyword, String category) {
        List<Item> items = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM items WHERE (title LIKE ? OR description LIKE ? OR location LIKE ?)");

        if (category != null && !category.isEmpty()) {
            sql.append(" AND category = ?");
        }
        sql.append(" ORDER BY post_date DESC");

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            pstmt.setString(3, "%" + keyword + "%");

            if (category != null && !category.isEmpty()) {
                pstmt.setString(4, category);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                items.add(extractItemFromResultSet(rs));
            }
            System.out.println("搜索物品, 关键词: " + keyword + ", 找到: " + items.size() + " 个");
        } catch (SQLException e) {
            System.err.println("搜索物品失败: " + e.getMessage());
        }
        return items;
    }

    public boolean updateItem(Item item) {
        String sql = "UPDATE items SET title = ?, description = ?, category = ?, price = ?, location = ? WHERE id = ? AND user_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getTitle());
            pstmt.setString(2, item.getDescription());
            pstmt.setString(3, item.getCategory());
            pstmt.setDouble(4, item.getPrice());
            pstmt.setString(5, item.getLocation());
            pstmt.setInt(6, item.getId());
            pstmt.setInt(7, item.getUserId());

            int rows = pstmt.executeUpdate();
            System.out.println("更新物品 ID: " + item.getId() + ", 影响行数: " + rows);
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("更新物品失败: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteItem(int id, int userId) {
        String sql = "DELETE FROM items WHERE id = ? AND user_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);

            int rows = pstmt.executeUpdate();
            System.out.println("删除物品 ID: " + id + ", 影响行数: " + rows);
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("删除物品失败: " + e.getMessage());
            return false;
        }
    }

    public List<Item> getItemsByUserId(int userId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE user_id = ? ORDER BY post_date DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                items.add(extractItemFromResultSet(rs));
            }
            System.out.println("获取用户ID: " + userId + " 的物品, 数量: " + items.size());
        } catch (SQLException e) {
            System.err.println("获取用户物品失败: " + e.getMessage());
        }
        return items;
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items ORDER BY post_date DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                items.add(extractItemFromResultSet(rs));
            }
            System.out.println("获取所有物品, 数量: " + items.size());
        } catch (SQLException e) {
            System.err.println("获取所有物品失败: " + e.getMessage());
        }
        return items;
    }

    private Item extractItemFromResultSet(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setTitle(rs.getString("title"));
        item.setDescription(rs.getString("description"));
        item.setCategory(rs.getString("category"));
        item.setStatus(rs.getString("status"));
        item.setPrice(rs.getDouble("price"));
        item.setLocation(rs.getString("location"));
        item.setFoundDate(rs.getTimestamp("found_date").toLocalDateTime());
        item.setPostDate(rs.getTimestamp("post_date").toLocalDateTime());
        item.setUserId(rs.getInt("user_id"));
        return item;
    }
}