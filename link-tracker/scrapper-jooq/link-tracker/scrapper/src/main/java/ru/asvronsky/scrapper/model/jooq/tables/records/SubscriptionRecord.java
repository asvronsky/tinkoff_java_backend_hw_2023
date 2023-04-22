/*
 * This file is generated by jOOQ.
 */
package ru.asvronsky.scrapper.model.jooq.tables.records;


import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import ru.asvronsky.scrapper.model.jooq.tables.Subscription;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SubscriptionRecord extends UpdatableRecordImpl<SubscriptionRecord> implements Record2<Long, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.subscription.link_id</code>.
     */
    public void setLinkId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.subscription.link_id</code>.
     */
    public Long getLinkId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.subscription.chat_id</code>.
     */
    public void setChatId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.subscription.chat_id</code>.
     */
    public Long getChatId() {
        return (Long) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Long, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Subscription.SUBSCRIPTION.LINK_ID;
    }

    @Override
    public Field<Long> field2() {
        return Subscription.SUBSCRIPTION.CHAT_ID;
    }

    @Override
    public Long component1() {
        return getLinkId();
    }

    @Override
    public Long component2() {
        return getChatId();
    }

    @Override
    public Long value1() {
        return getLinkId();
    }

    @Override
    public Long value2() {
        return getChatId();
    }

    @Override
    public SubscriptionRecord value1(Long value) {
        setLinkId(value);
        return this;
    }

    @Override
    public SubscriptionRecord value2(Long value) {
        setChatId(value);
        return this;
    }

    @Override
    public SubscriptionRecord values(Long value1, Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SubscriptionRecord
     */
    public SubscriptionRecord() {
        super(Subscription.SUBSCRIPTION);
    }

    /**
     * Create a detached, initialised SubscriptionRecord
     */
    public SubscriptionRecord(Long linkId, Long chatId) {
        super(Subscription.SUBSCRIPTION);

        setLinkId(linkId);
        setChatId(chatId);
        resetChangedOnNotNull();
    }
}
