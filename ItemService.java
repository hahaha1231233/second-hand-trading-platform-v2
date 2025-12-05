package com.second_hand_trading_platform.service;

import com.second_hand_trading_platform.dao.ItemDAO;
import com.second_hand_trading_platform.model.Item;

import java.util.List;

public class ItemService {
    private ItemDAO itemDao = new ItemDAO();

    public boolean addItem(Item item) {
        return itemDao.addItem(item);
    }

    public Item getItemById(int id) {
        return itemDao.findItemById(id);
    }

    public List<Item> searchItems(String keyword, String category) {
        return itemDao.searchItems(keyword, category);
    }

    public boolean updateItem(Item item) {
        return itemDao.updateItem(item);
    }

    public boolean deleteItem(int id, int userId) {
        return itemDao.deleteItem(id, userId);
    }

    public List<Item> getItemsByUserId(int userId) {
        return itemDao.getItemsByUserId(userId);
    }

    public List<Item> getAllItems() {
        return itemDao.getAllItems();
    }
}