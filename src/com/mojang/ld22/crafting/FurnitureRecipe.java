package com.mojang.ld22.crafting;

import com.mojang.ld22.entity.Anvil;
import com.mojang.ld22.entity.Chest;
import com.mojang.ld22.entity.Furnace;
import com.mojang.ld22.entity.Furniture;
import com.mojang.ld22.entity.Lantern;
import com.mojang.ld22.entity.Oven;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.Workbench;
import com.mojang.ld22.item.FurnitureItem;

public class FurnitureRecipe extends Recipe {
    private FurnitureType type;

    public FurnitureRecipe(FurnitureType t) {
        super(new FurnitureItem(t.newInst()));
        this.type = t;
    }

    public void craft(Player player) {
        player.inventory.add(0, new FurnitureItem(type.newInst()));
    }

    public enum FurnitureType {
        LANTERN {
            @Override
            public Furniture newInst() {
                return new Lantern();
            }
        },
        OVEN {
            @Override
            public Furniture newInst() {
                return new Oven();
            }
        },
        FURNACE {
            @Override
            public Furniture newInst() {
                return new Furnace();
            }
        },
        WORKBENCH {
            @Override
            public Furniture newInst() {
                return new Workbench();
            }
        },
        CHEST {
            @Override
            public Furniture newInst() {
                return new Chest();
            }
        },
        ANVIL {
            @Override
            public Furniture newInst() {
                return new Anvil();
            }
        };

        public abstract Furniture newInst();
    }
}
