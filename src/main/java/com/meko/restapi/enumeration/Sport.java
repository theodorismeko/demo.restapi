package com.meko.restapi.enumeration;

import lombok.Getter;

@Getter
public enum Sport {
    FOOTBALL("Football"),
    BASKETBALL("Basketball");
    
    private final String displayName;
    
    Sport(String displayName) {
        this.displayName = displayName;
    }

}
