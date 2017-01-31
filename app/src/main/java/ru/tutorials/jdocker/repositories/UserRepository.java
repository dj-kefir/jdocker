package ru.tutorials.jdocker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.tutorials.jdocker.models.QUser;
import ru.tutorials.jdocker.models.User;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, Long>,
        QueryDslPredicateExecutor<User>, CustomQuerydslBinderCustomizer<QUser> {

}
