package ru.tutorials.jdocker.repositories;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import java.util.*;

public interface CustomQuerydslBinderCustomizer<T extends EntityPath<?>> extends QuerydslBinderCustomizer<T> {

    @Override
    default void customize(QuerydslBindings bindings, T t) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(Date.class).all((final DateTimePath<Date> path, final Collection<? extends Date> values) -> {
            if (values.size() == 2) {
                List<? extends Date> dates = new ArrayList<>(values);
                Collections.sort(dates);
                return path.between(dates.get(0), dates.get(1));
            }
            throw new IllegalArgumentException("2 date params(from & to) expected for:" + path + " found:" + values);
        });

        bindings.bind(Integer.class).all((final NumberPath<Integer> path, final Collection<? extends Integer> values) -> {
            final int size = values.size();
            if (size == 1) {
                return path.eq(values.iterator().next());
            } else if (size == 2) {
                List<Integer> numbers = new ArrayList<>(values);
                Collections.sort(numbers);
                return path.between(numbers.get(0), numbers.get(1));
            }
            throw new IllegalArgumentException("1 or 2 number params(from & to) expected for:" + path + " found:" + values);
        });

        bindings.bind(Long.class).all((final NumberPath<Long> path, final Collection<? extends Long> values) -> {
            final int size = values.size();
            if (size == 1) {
                return path.eq(values.iterator().next());
            } else if (size == 2) {
                List<Long> numbers = new ArrayList<>(values);
                Collections.sort(numbers);
                return path.between(numbers.get(0), numbers.get(1));
            }
            throw new IllegalArgumentException("1 or 2 number params(from & to) expected for:" + path + " found:" + values);
        });
    }
}
