package com.henry.model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.YesNoConverter;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.converter.spi.BasicValueConverter;
import org.hibernate.type.descriptor.java.BasicJavaType;
import org.hibernate.type.descriptor.java.BooleanJavaType;
import org.hibernate.type.descriptor.java.CharacterJavaType;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.type.descriptor.jdbc.CharJdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.spi.TypeConfiguration;
import org.hibernate.usertype.UserType;

public class BooleanUserTypeSupport implements UserType<Boolean> {
	private final BasicJavaType<Boolean> javaType;
	private final JdbcType jdbcType;
	private final MutabilityPlan<Boolean> mutabilityPlan;
	private final BasicValueConverter<Boolean, Character> valueConverter;

	private final ValueExtractor<Boolean> jdbcValueExtractor;
	private final ValueBinder<Character> jdbcValueBinder;

	public BooleanUserTypeSupport() {
		this.javaType = BooleanJavaType.INSTANCE;
		this.jdbcType = CharJdbcType.INSTANCE;
		this.mutabilityPlan = javaType.getMutabilityPlan();
		this.valueConverter = YesNoConverter.INSTANCE;

		// noinspection unchecked
		this.jdbcValueExtractor = jdbcType.getExtractor(javaType);
		// noinspection unchecked
		this.jdbcValueBinder = jdbcType.getBinder(CharacterJavaType.INSTANCE);
	}

	public BasicJavaType<Boolean> getJavaType() {
		return javaType;
	}

	@Override
	public JdbcType getJdbcType(TypeConfiguration typeConfiguration) {
		return jdbcType;
	}

	public MutabilityPlan<Boolean> getMutabilityPlan() {
		return mutabilityPlan;
	}

	// @Override
	// public BasicValueConverter<Boolean, Character> getValueConverter() {
	// return valueConverter;
	// }

	public ValueExtractor<Boolean> getJdbcValueExtractor() {
		return jdbcValueExtractor;
	}

	public ValueBinder<Character> getJdbcValueBinder() {
		return jdbcValueBinder;
	}

	@Override
	public int getSqlType() {
		return jdbcType.getDefaultSqlTypeCode();
	}

	@Override
	public Class<Boolean> returnedClass() {
		return javaType.getJavaTypeClass();
	}

	@Override
	public boolean equals(Boolean x, Boolean y) throws HibernateException {
		return javaType.areEqual(x, y);
	}

	@Override
	public int hashCode(Boolean x) throws HibernateException {
		return javaType.extractHashCode(x);
	}

	@Override
	public Boolean nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
			throws SQLException {
		final Boolean extracted = jdbcValueExtractor.extract(rs, position, session);

		// if ( valueConverter != null ) {
		// return valueConverter.toDomainValue( extracted );
		// }

		// noinspection unchecked
		return (Boolean) extracted;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Boolean value, int index, SharedSessionContractImplementor session)
			throws SQLException {
		final Character valueToBind;
		// if ( valueConverter != null ) {
		// valueToBind = valueConverter.toRelationalValue( value );
		// }
		// else {
		valueToBind = value ? 'Y' : 'N';
		// }

		jdbcValueBinder.bind(st, valueToBind, index, session);
	}

	@Override
	public Boolean deepCopy(Boolean value) throws HibernateException {
		return javaType.getMutabilityPlan().deepCopy(value);
	}

	@Override
	public boolean isMutable() {
		return javaType.getMutabilityPlan().isMutable();
	}

	@Override
	public Serializable disassemble(Boolean value) throws HibernateException {
		return javaType.getMutabilityPlan().disassemble(value, null);
	}

	@Override
	public Boolean assemble(Serializable cached, Object owner) throws HibernateException {
		return javaType.getMutabilityPlan().assemble(cached, null);
	}

	@Override
	public Boolean replace(Boolean original, Boolean target, Object owner) throws HibernateException {
		return deepCopy(original);
	}

}
