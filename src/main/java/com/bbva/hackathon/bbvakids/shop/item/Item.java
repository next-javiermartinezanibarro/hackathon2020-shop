package com.bbva.hackathon.bbvakids.shop.item;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.Entity;


@Schema(description = "The Item on shop")
@Entity
public class Item extends PanacheEntity {

    public String type;
    public String name;
    public int requiredLevel;
    public int requiredGems;
}
