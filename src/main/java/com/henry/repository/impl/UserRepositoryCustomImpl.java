package com.henry.repository.impl;

import com.henry.model.QAddress;
import com.henry.model.QUser;
import com.henry.model.User;
import com.henry.repository.UserRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

public class UserRepositoryCustomImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

    public UserRepositoryCustomImpl() {
        super(User.class);
    }


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer getSum(int a, int b) {

        Session session = entityManager.unwrap(Session.class);

        int result = session.doReturningWork(new ReturningWork<Integer>() {
            @Override
            public Integer execute(Connection connection) throws SQLException {
                CallableStatement call = connection.prepareCall("{ ? = call get_sum(?,?) }");
                call.registerOutParameter(1, Types.INTEGER); // or whatever it is
                call.setInt(2, a);
                call.setInt(3, b);
                call.execute();
                return call.getInt(1); // propagate this back to enclosing class
            }
        });

        return  result;
    }

    @Override
    public Optional<User> findByFirstName(String firstName) {
		QUser user = QUser.user;
		BooleanBuilder builder = new BooleanBuilder(user.deleted.isFalse());
		builder.and(user.firstName.eq(firstName));
		return Optional.ofNullable(findSingleUser(builder.getValue()));

	}

	private User findSingleUser(Predicate predicate) {
		return userQuery().where(predicate).fetchOne();
	}

    
    public JPQLQuery<User> userQuery() {
		QUser user = QUser.user;
        QAddress homeAddress = new QAddress("homeAddress");
		QAddress workAddress = new QAddress("workAddress");
		return from(user).leftJoin(user.homeAddress, homeAddress).fetchJoin()
				.leftJoin(user.workAddress, workAddress).fetchJoin();
	}
}
