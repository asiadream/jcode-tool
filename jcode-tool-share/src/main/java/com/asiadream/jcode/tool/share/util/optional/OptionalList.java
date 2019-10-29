package com.asiadream.jcode.tool.share.util.optional;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class OptionalList<T> {
    private List<T> innerList; // nullable

    public static <T> OptionalList<T> ofNullable(List<T> list) {
        return new OptionalList<>(list);
    }

    public OptionalList(List<T> list) {
        this.innerList = list;
    }

    public void forEach(Consumer<T> action) {
        Optional.ofNullable(innerList).ifPresent(list -> list.forEach(action));
    }
}
