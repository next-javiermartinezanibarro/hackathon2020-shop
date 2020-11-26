package com.bbva.hackathon.bbvakids.shop.item;


import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(REQUIRED)
public class ItemService {
    @Transactional(SUPPORTS)
    public List<Item> findAllItems() {
        return Item.listAll();
    }

    @Transactional(SUPPORTS)
    public Item findItemById(Long id) {
        return Item.findById(id);
    }

}
