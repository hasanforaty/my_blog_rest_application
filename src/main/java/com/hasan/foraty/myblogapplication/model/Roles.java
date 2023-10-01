package com.hasan.foraty.myblogapplication.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Roles {
    ADMIN(Authority.ADD_TO_DATABASE,Authority.GET_FROM_DATABASE,Authority.REMOVE_FROM_DATABASE,Authority.UPDATE_DATABASE),
    USER(Authority.GET_FROM_DATABASE)
    ;
    final Authority[] authorities;
    Roles(Authority... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return  Arrays.stream(authorities).map(Authority::name).toList().toArray(new String[authorities.length]);
    }
}
