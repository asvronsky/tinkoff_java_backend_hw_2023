/*
 * This file is generated by jOOQ.
 */
package ru.asvronsky.scrapperjooq.domain.tables;


import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function4;
import org.jooq.Identity;
import org.jooq.JSONB;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.asvronsky.scrapperjooq.domain.Keys;
import ru.asvronsky.scrapperjooq.domain.Public;
import ru.asvronsky.scrapperjooq.domain.tables.records.LinkRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Link extends TableImpl<LinkRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.link</code>
     */
    public static final Link LINK = new Link();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LinkRecord> getRecordType() {
        return LinkRecord.class;
    }

    /**
     * The column <code>public.link.id</code>.
     */
    public final TableField<LinkRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.link.url</code>.
     */
    public final TableField<LinkRecord, String> URL = createField(DSL.name("url"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.link.updated_at</code>.
     */
    public final TableField<LinkRecord, OffsetDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "");

    /**
     * The column <code>public.link.website_data</code>.
     */
    public final TableField<LinkRecord, JSONB> WEBSITE_DATA = createField(DSL.name("website_data"), SQLDataType.JSONB, this, "");

    private Link(Name alias, Table<LinkRecord> aliased) {
        this(alias, aliased, null);
    }

    private Link(Name alias, Table<LinkRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.link</code> table reference
     */
    public Link(String alias) {
        this(DSL.name(alias), LINK);
    }

    /**
     * Create an aliased <code>public.link</code> table reference
     */
    public Link(Name alias) {
        this(alias, LINK);
    }

    /**
     * Create a <code>public.link</code> table reference
     */
    public Link() {
        this(DSL.name("link"), null);
    }

    public <O extends Record> Link(Table<O> child, ForeignKey<O, LinkRecord> key) {
        super(child, key, LINK);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<LinkRecord, Long> getIdentity() {
        return (Identity<LinkRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<LinkRecord> getPrimaryKey() {
        return Keys.LINK_PKEY;
    }

    @Override
    public List<UniqueKey<LinkRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.LINK_URL_KEY);
    }

    @Override
    public Link as(String alias) {
        return new Link(DSL.name(alias), this);
    }

    @Override
    public Link as(Name alias) {
        return new Link(alias, this);
    }

    @Override
    public Link as(Table<?> alias) {
        return new Link(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Link rename(String name) {
        return new Link(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Link rename(Name name) {
        return new Link(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Link rename(Table<?> name) {
        return new Link(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, OffsetDateTime, JSONB> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function4<? super Long, ? super String, ? super OffsetDateTime, ? super JSONB, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function4<? super Long, ? super String, ? super OffsetDateTime, ? super JSONB, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}